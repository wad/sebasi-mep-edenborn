package org.sebasi.mep.tool.datastructure.v1;

public class NeuronWithoutMemory extends Neuron {

    public NeuronWithoutMemory() {
        this(null);
    }

    public NeuronWithoutMemory(String label) {
        super(label);
        dendrites = new DendritesWithoutMemory();
    }

    @Override
    public void receiveInput(int port) {
        // todo: check to see if the port is connected.

        this.accumulator += 1; // todo: Is this right?
    }

    public void attachPort(
            int port,
            int strength) {
        dendrites.attachPort(port);
    }
}
