package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;

public class DendriticTree2E16WithoutMemory extends DendriticTree {

    // 2 ^ 16
    static final int NUM_SYNAPSES = 65536;

    // One bit per port, so divide the number of ports by 8.
    static final int NUM_BYTES_NEEDED_TO_HOLD_SYNAPTIC_STATES = NUM_SYNAPSES >>> 3;

    public DendriticTree2E16WithoutMemory(Neuron neuron) {
        super(neuron);
    }

    @Override
    public long computeFiringThreshold() {
        // trigger when the total number of triggered inputs is the same as the number of connected ports
        return numConnectedSynapses;
    }

    @Override
    public void attachSynapse(int synapticIndex) {
        if (neuron.getHelper().getOperationMode().shouldValidateDendriteAttachments()) {
            if (isSynapseAttached(synapticIndex)) {
                throw new NeuronConnectionException(
                        "Attempted to attach synapse, but it's already attached.",
                        neuron.getLabel());
            }
        }

        int synapticStateIndex = convertSynapticIndexToSynapticStateIndex(synapticIndex);
        byte synapticStateBits = synapticStates[synapticStateIndex];
        synapticStates[synapticStateIndex] = (byte) (synapticStateBits | getMaskForSetting(synapticIndex));
        numConnectedSynapses++;
    }

    @Override
    public void detachSynapse(int synapticIndex) {
        if (neuron.getHelper().getOperationMode().shouldValidateDendriteAttachments()) {
            if (!isSynapseAttached(synapticIndex)) {
                throw new NeuronConnectionException(
                        "Attempted to detach synapse, but it's not attached.",
                        neuron.getLabel());
            }
        }

        int synapticStateIndex = convertSynapticIndexToSynapticStateIndex(synapticIndex);
        byte synapticStateBits = synapticStates[synapticStateIndex];
        byte zeroMask = (byte) (~getMaskForSetting(synapticIndex));
        synapticStates[synapticStateIndex] = (byte) (synapticStateBits & zeroMask);
        numConnectedSynapses--;
    }

    @Override
    protected void initializeSynapticStates() {
        // initialized to zeroes, which means none of the synaptses are connected.
        synapticStates = new byte[NUM_BYTES_NEEDED_TO_HOLD_SYNAPTIC_STATES];
        numConnectedSynapses = 0;
    }

    // just returns one bit
    @Override
    int getSynapticStateBits(int synapticIndex) {
        byte synapticStateBits = synapticStates[convertSynapticIndexToSynapticStateIndex(synapticIndex)];
        // 0 --> 1000 0000 --> (>> 7)
        // 1 --> 0100 0000 --> (>> 6)
        // 6 --> 0000 0010 --> (>> 1)
        // 7 --> 0000 0001 --> (>> 0)
        int numTimesToShift = 7 - (synapticIndex & 0x7);
        return ((synapticStateBits >>> numTimesToShift) & 0x1);
    }

    int convertSynapticIndexToSynapticStateIndex(int synapticIndex) {
        // eight bits in one byte
        return synapticIndex >> 3;
    }

    int getMaskForSetting(int synapticIndex) {
        int numTimesToShift = 7 - (synapticIndex & 0x7);
        // synapticIndex 0 --> (<< 7)
        // synapticIndex 1 --> (<< 6)
        // synapticIndex 6 --> (<< 1)
        // synapticIndex 7 --> (<< 0)
        return 1 << numTimesToShift;
    }
}
