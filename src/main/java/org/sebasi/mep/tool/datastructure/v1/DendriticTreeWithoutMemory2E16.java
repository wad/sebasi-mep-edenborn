package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;

public class DendriticTreeWithoutMemory2E16 extends DendriticTreeWithoutMemory {

    static final DendriticTreeSize DENDRITIC_TREE_SIZE = DendriticTreeSize.TwoE16;

    public DendriticTreeWithoutMemory2E16(Neuron neuron) {
        super(
                DENDRITIC_TREE_SIZE,

                // One bit per synapse, so divide the number of synapses by 8.
                DENDRITIC_TREE_SIZE.getNumSynapses() >>> 3,

                neuron);
    }

    @Override
    public long computeFiringThreshold() {
        // trigger when the total number of triggered inputs is the same as the number of connected synapses
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
        // initialized to zeroes, which means none of the synapses are connected.
        synapticStates = new byte[numBytesNeededToHoldSynapticStates];
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
