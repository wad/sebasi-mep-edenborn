package org.sebasi.mep.tool.datastructure.v1;

// todo: Strengthen synapses when they receive input (might depend on a probability)
// todo: Reduce strength of synapse when they don't receive input (definitely depending on a probability)

import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;

// 2 ^ 16
public class DendriticTree2E16WithMemory extends DendriticTree {

    static final int NUM_SYNAPSES = 65536;

    // 4 bits per port, so we need half as many bytes as ports to hold the strength bits
    static final int NUM_BYTES_NEEDED_TO_HOLD_SYNAPTIC_STATES = NUM_SYNAPSES >>> 1;

    // Memory is achieved through a neuron persisting how sensitive its synapses are.
    // Four bits indicate the strength, which is between -7 through +7.
    //  X -> 0000 == 0x0 == special value, indicating the synapse is not connected
    // -7 -> 0001 == 0x1
    // -6 -> 0010 == 0x2
    // -5 -> 0011 == 0x3
    // -4 -> 0100 == 0x4
    // -3 -> 0101 == 0x5
    // -2 -> 0110 == 0x6
    // -1 -> 0111 == 0x7
    //  0 -> 1000 == 0x8
    // +1 -> 1001 == 0x9
    // +2 -> 1010 == 0xA
    // +3 -> 1011 == 0xB
    // +4 -> 1100 == 0xC
    // +5 -> 1101 == 0xD
    // +6 -> 1110 == 0xE
    // +7 -> 1111 == 0xF

    // The default starting synapse strength is +2 (0xA)
    static final int SYNAPSE_STRENGTH_DEFAULT_VALUE = 2;
    static final int SYNAPSE_STRENGTH_BITS_CORRESPONDING_TO_NEUTRAL = 0x8;

    public DendriticTree2E16WithMemory(Neuron neuron) {
        super(neuron);
    }

    @Override
    public long computeFiringThreshold() {
        // Just a starting formula. There are probably better ones.
        return numConnectedSynapses * SYNAPSE_STRENGTH_DEFAULT_VALUE;
    }

    @Override
    public void attachSynapse(int synapticIndex) {
        attachSynapse(
                synapticIndex,
                SYNAPSE_STRENGTH_DEFAULT_VALUE);
    }

    public void attachSynapse(
            int synapticIndex,
            int strength) {

        if (neuron.getHelper().getOperationMode().shouldValidateDendriteAttachments()) {
            if (isSynapseAttached(synapticIndex)) {
                throw new RuntimeException("Attempted to connect, but already connected.");
            }
        }

        int synapticStateIndex = convertSynapticIndexToSynapticStateIndex(synapticIndex);
        int synapticStateBitsForBoth = synapticStates[synapticStateIndex];
        int newSynapticStateBitsForOne = convertStrengthToSynapticStateBits(strength);
        if (isSynapticIndexEven(synapticIndex)) {
            synapticStates[synapticStateIndex] = (byte) (synapticStateBitsForBoth | (newSynapticStateBitsForOne << 4));
        } else {
            synapticStates[synapticStateIndex] = (byte) (synapticStateBitsForBoth | newSynapticStateBitsForOne);
        }
        numConnectedSynapses++;
    }

    @Override
    public void detachSynapse(int synapticIndex) {

        if (neuron.getHelper().getOperationMode().shouldValidateDendriteAttachments()) {
            if (!isSynapseAttached(synapticIndex)) {
                throw new NeuronConnectionException(
                        "Attempted to disconnect, but not connected.",
                        neuron.getLabel());
            }
        }

        int SynapticStateIndex = convertSynapticIndexToSynapticStateIndex(synapticIndex);
        int synapticStateBitsForBoth = synapticStates[SynapticStateIndex];

        // Set either left leftmost 4 bits to 0000, or the rightmost 4 bits to 0000, leaving the other side alone.
        synapticStates[SynapticStateIndex] = (byte) (synapticStateBitsForBoth & getMaskForSetting(synapticIndex));

        numConnectedSynapses--;
    }

    @Override
    protected void initializeSynapticStates() {
        // initialized to zeroes, which means none of the ports are connected.
        synapticStates = new byte[NUM_BYTES_NEEDED_TO_HOLD_SYNAPTIC_STATES];
        numConnectedSynapses = 0;
    }

    @Override
    int getSynapticStateBits(int synapticIndex) {
        int synapticStateBits = synapticStates[convertSynapticIndexToSynapticStateIndex(synapticIndex)];
        if (isSynapticIndexEven(synapticIndex)) {
            return (synapticStateBits & 0xF0) >>> 4;
        } else {
            return synapticStateBits & 0x0F;
        }
    }

    int convertSynapticStateBitsToStrength(int synapticStateBits) {
        return synapticStateBits - SYNAPSE_STRENGTH_BITS_CORRESPONDING_TO_NEUTRAL;
    }

    int convertStrengthToSynapticStateBits(int strength) {
        if (strength > 7) {
            strength = 7;
        } else if (strength < -7) {
            strength = -7;
        }
        return strength + SYNAPSE_STRENGTH_BITS_CORRESPONDING_TO_NEUTRAL;
    }

    int lookupSynapseStrength(int synapticIndex) {
        int synapticStateBits = getSynapticStateBits(synapticIndex);

        if (neuron.getHelper().getOperationMode().shouldValidateDendriteAttachments()) {
            if (!doSynapticStateBitsIndicateConnected(synapticStateBits)) {
                throw new NeuronConnectionException(
                        "Bug: synapse not connected.",
                        neuron.getLabel());
            }
        }

        return convertSynapticStateBitsToStrength(synapticStateBits);
    }

    int convertSynapticIndexToSynapticStateIndex(int synapticIndex) {
        return synapticIndex >>> 1;
    }

    boolean isSynapticIndexEven(int synapticIndex) {
        return (synapticIndex & 1) == 0;
    }

    int getMaskForSetting(int synapticIndex) {
        return isSynapticIndexEven(synapticIndex) ? 0x0F : 0xF0;
    }
}
