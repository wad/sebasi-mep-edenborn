package org.sebasi.mep.tool.datastructure.v1;

public class Synapse {
    Neuron receivingNeuron;
    int port;

    public Synapse(
            Neuron receivingNeuron,
            int port) {
        this.receivingNeuron = receivingNeuron;
        this.port = port;
    }

    public void trigger() {
        receivingNeuron.receiveInput(port);
    }
}
