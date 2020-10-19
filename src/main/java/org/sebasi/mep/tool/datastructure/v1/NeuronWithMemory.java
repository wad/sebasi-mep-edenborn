package org.sebasi.mep.tool.datastructure.v1;

public class NeuronWithMemory extends Neuron {

    DendritesWithMemory dendritesWithMemory;

    public NeuronWithMemory(Helper helper) {
        this(helper, null);
    }

    public NeuronWithMemory(
            Helper helper,
            String label) {
        super(helper, label);
        dendrites = new DendritesWithMemory(helper);
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
