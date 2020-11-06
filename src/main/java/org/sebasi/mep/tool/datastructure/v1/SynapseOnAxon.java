package org.sebasi.mep.tool.datastructure.v1;

public class SynapseOnAxon {
    NeuronWithDendriticTree receivingNeuron;
    int synapticIndex;

    public SynapseOnAxon(
            NeuronWithDendriticTree receivingNeuron,
            int synapticIndex) {
        this.receivingNeuron = receivingNeuron;
        this.synapticIndex = synapticIndex;
    }

    public void trigger() {
        receivingNeuron.receiveInput(synapticIndex);
    }
}
