package org.sebasi.mep.tool.datastructure.v1;

public class NeuronWithMemory extends Neuron {

    DendritesWithMemory dendritesWithMemory;

    public NeuronWithMemory() {
        this(null);
    }

    public NeuronWithMemory(String label) {
        super(label);
        dendrites = new DendritesWithMemory();
        dendritesWithMemory = (DendritesWithMemory) dendrites;
    }

    @Override
    public void receiveInput(int port) {
        this.accumulator += dendritesWithMemory.lookupPortStrength(port);
    }

    public void attachPort(
            int port,
            int strength) {
        dendritesWithMemory.attachPort(port, strength);
    }
}
