package org.sebasi.mep.tool.datastructure.v1;

// Q: What is this "Type2" about?
// A: Nothing yet, there will eventually be multiple types of neurons, most likely,
// this is just a placeholder for one of the eventual types.
public class DendriticTreeWithoutMemoryType2 extends DendriticTreeWithoutMemory {

    public DendriticTreeWithoutMemoryType2(
            DendriticTreeSize dendriticTreeSize,
            Neuron neuron) {
        super(
                dendriticTreeSize,

                // One bit per synapse, so divide the number of synapses by 8.
                dendriticTreeSize.getNumSynapses() >>> 3,

                neuron);
    }

    @Override
    public long computeFiringThreshold() {
        // trigger when the total number of triggered inputs is the same as the number of connected synapses
        return numConnectedSynapses;
    }

    @Override
    public void attachSynapse(int synapticIndex) {

        validateConnection(false, synapticIndex);

        int synapticStateIndex = convertSynapticIndexToSynapticStateIndex(synapticIndex);
        byte synapticStateBits = synapticStates[synapticStateIndex];
        synapticStates[synapticStateIndex] = (byte) (synapticStateBits | getMaskForSetting(synapticIndex));
        numConnectedSynapses++;
    }

    @Override
    public void detachSynapse(int synapticIndex) {

        validateConnection(true, synapticIndex);

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
