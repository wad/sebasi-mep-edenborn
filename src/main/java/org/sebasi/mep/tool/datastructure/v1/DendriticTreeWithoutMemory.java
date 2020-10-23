package org.sebasi.mep.tool.datastructure.v1;

public abstract class DendriticTreeWithoutMemory extends DendriticTree {

    public DendriticTreeWithoutMemory(
            DendriticTreeSize dendriticTreeSize,
            int numBytesNeededToHoldSynapticStates,
            Neuron neuron) {
        super(
                dendriticTreeSize,
                numBytesNeededToHoldSynapticStates,
                neuron);
    }
}
