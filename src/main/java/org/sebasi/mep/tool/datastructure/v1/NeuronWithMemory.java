package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

public class NeuronWithMemory extends Neuron {

    DendriticTree2E16WithMemory dendriticTree2E16WithMemory;

    public NeuronWithMemory(Helper helper) {
        this(helper, null);
    }

    public NeuronWithMemory(
            Helper helper,
            String label) {
        super(helper, label);
        dendriticTree = new DendriticTree2E16WithMemory(this);
        dendriticTree2E16WithMemory = (DendriticTree2E16WithMemory) dendriticTree;
    }

    @Override
    public void receiveInput(int synapticIndex) {
        this.accumulator += dendriticTree2E16WithMemory.lookupSynapseStrength(synapticIndex);
    }

    public void attachSynapse(
            int synapticIndex,
            int strength) {
        dendriticTree2E16WithMemory.attachSynapse(synapticIndex, strength);
    }
}
