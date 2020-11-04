package org.sebasi.mep.tool.datastructure.v1.highperf;

import org.sebasi.mep.tool.datastructure.v1.Neuron;

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
