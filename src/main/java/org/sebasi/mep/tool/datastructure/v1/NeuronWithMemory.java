package org.sebasi.mep.tool.datastructure.v1;

public class NeuronWithMemory extends Neuron {

    DendritesWithMemory dendritesWithMemory;

    public NeuronWithMemory() {
        this(null);
    }

    public NeuronWithMemory(String label) {
        super(label);
        dendritesWithMemory = new DendritesWithMemory();
    }

    @Override
    public void receiveInput(int port) {
        this.accumulator += dendritesWithMemory.lookupPortStrength(port);
    }

    @Override
    public void attachPort(int port) {
        dendritesWithMemory.attachPort(port);
    }

    public void attachPort(
            int port,
            int strength) {
        dendritesWithMemory.attachPort(port, strength);
    }

    @Override
    public void detachPort(int port) {
        dendritesWithMemory.detachPort(port);
    }
}
