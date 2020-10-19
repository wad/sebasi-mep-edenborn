package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

public class NeuronWithMemory extends Neuron {

    Dendrites16kWithMemory dendrites16kWithMemory;

    public NeuronWithMemory(Helper helper) {
        this(helper, null);
    }

    public NeuronWithMemory(
            Helper helper,
            String label) {
        super(helper, label);
        dendrites = new Dendrites16kWithMemory(this);
        dendrites16kWithMemory = (Dendrites16kWithMemory) dendrites;
    }

    @Override
    public void receiveInput(int port) {
        this.accumulator += dendrites16kWithMemory.lookupPortStrength(port);
    }

    public void attachPort(
            int port,
            int strength) {
        dendrites16kWithMemory.attachPort(port, strength);
    }
}
