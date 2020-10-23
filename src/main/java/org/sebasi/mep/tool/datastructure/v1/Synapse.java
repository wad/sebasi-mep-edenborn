package org.sebasi.mep.tool.datastructure.v1;

public class Synapse {
    Neuron receivingNeuron;
    int synapticIndex;

    public Synapse(
            Neuron receivingNeuron,
            int synapticIndex) {
        this.receivingNeuron = receivingNeuron;
        this.synapticIndex = synapticIndex;
    }

    public void trigger() {
        receivingNeuron.receiveInput(synapticIndex);
    }
}
