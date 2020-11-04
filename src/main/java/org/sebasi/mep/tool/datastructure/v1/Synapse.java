package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.highperf.NeuronWithDendriticTreeWithHighPerf;

public class Synapse {
    NeuronWithDendriticTreeWithHighPerf receivingNeuron;
    int synapticIndex;

    public Synapse(
            NeuronWithDendriticTreeWithHighPerf receivingNeuron,
            int synapticIndex) {
        this.receivingNeuron = receivingNeuron;
        this.synapticIndex = synapticIndex;
    }

    public void trigger() {
        receivingNeuron.receiveInput(synapticIndex);
    }
}
