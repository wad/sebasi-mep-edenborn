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
}
