package org.sebasi.mep.tool.datastructure.v1;

public abstract class DendriticTreeWithHighPerfWithoutMemory extends DendriticTreeWithHighPerf {

    public DendriticTreeWithHighPerfWithoutMemory(
            DendriticTreeSize dendriticTreeSize,
            int numBytesNeededToHoldSynapticStates,
            Neuron neuron) {
        super(
                dendriticTreeSize,
                numBytesNeededToHoldSynapticStates,
                neuron);
    }
}
