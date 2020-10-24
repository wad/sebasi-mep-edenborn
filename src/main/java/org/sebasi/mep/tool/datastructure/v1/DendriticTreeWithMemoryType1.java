package org.sebasi.mep.tool.datastructure.v1;

// Q: What is this "Type1" about?
// A: Nothing yet, there will eventually be multiple types of neurons, most likely,
// this is just a placeholder for the first one to be implemented.
public class DendriticTreeWithMemoryType1 extends DendriticTreeWithMemory {

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

    public DendriticTreeWithMemoryType1(
            DendriticTreeSize dendriticTreeSize,
            Neuron neuron) {
        super(
                dendriticTreeSize,

                // 4 bits per synaptic state, so we need half as many bytes as synapses to hold the strength bits
                dendriticTreeSize.getNumSynapses() >> 1,

                neuron);
    }

    @Override
    protected int getSynapseStrengthDefaultValue() {
        return SYNAPSE_STRENGTH_DEFAULT_VALUE;
    }

    @Override
    public void attachSynapse(
            int synapticIndex,
            int strength) {

        validateConnection(false, synapticIndex);

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

        validateConnection(true, synapticIndex);

        int SynapticStateIndex = convertSynapticIndexToSynapticStateIndex(synapticIndex);
        int synapticStateBitsForBoth = synapticStates[SynapticStateIndex];

        // Set either left leftmost 4 bits to 0000, or the rightmost 4 bits to 0000, leaving the other side alone.
        synapticStates[SynapticStateIndex] = (byte) (synapticStateBitsForBoth & getMaskForSetting(synapticIndex));

        numConnectedSynapses--;
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

    @Override
    protected int convertSynapticStateBitsToStrength(int synapticStateBits) {
        return synapticStateBits - SYNAPSE_STRENGTH_BITS_CORRESPONDING_TO_NEUTRAL;
    }

    @Override
    protected int convertStrengthToSynapticStateBits(int strength) {
        if (strength > 7) {
            strength = 7;
        } else if (strength < -7) {
            strength = -7;
        }
        return strength + SYNAPSE_STRENGTH_BITS_CORRESPONDING_TO_NEUTRAL;
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
