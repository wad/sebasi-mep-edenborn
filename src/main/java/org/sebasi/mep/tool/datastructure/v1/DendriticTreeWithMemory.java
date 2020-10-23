package org.sebasi.mep.tool.datastructure.v1;

public abstract class DendriticTreeWithMemory extends DendriticTree {

    public DendriticTreeWithMemory(
            DendriticTreeSize dendriticTreeSize,
            int numBytesNeededToHoldSynapticStates,
            Neuron neuron) {
        super(
                dendriticTreeSize,
                numBytesNeededToHoldSynapticStates,
                neuron);
    }

    @Override
    protected void initializeSynapticStates() {
        // initialized to zeroes, which means none of the synapses are connected.
        synapticStates = new byte[numBytesNeededToHoldSynapticStates];
        numConnectedSynapses = 0;
    }

    @Override
    public void attachSynapse(int synapticIndex) {
        attachSynapse(
                synapticIndex,
                getSynapseStrengthDefaultValue());
    }

    public abstract void attachSynapse(
            int synapticIndex,
            int strength);

    protected abstract int getSynapseStrengthDefaultValue();

    protected abstract int convertSynapticStateBitsToStrength(int synapticStateBits);

    protected abstract int convertStrengthToSynapticStateBits(int strength);

    protected int lookupSynapseStrength(int synapticIndex) {
        validateConnection(true, synapticIndex);
        int synapticStateBits = getSynapticStateBits(synapticIndex);
        return convertSynapticStateBitsToStrength(synapticStateBits);
    }
}
