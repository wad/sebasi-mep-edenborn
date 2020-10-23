package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

public class NeuronWithMemory extends Neuron {

    DendriticTreeWithMemory2E16 dendriticTreeWithMemory2E16;

    public NeuronWithMemory(Helper helper) {
        this(helper, null);
    }

    public NeuronWithMemory(
            Helper helper,
            String label) {
        super(helper, label);
        dendriticTree = new DendriticTreeWithMemory2E16(this);
        dendriticTreeWithMemory2E16 = (DendriticTreeWithMemory2E16) dendriticTree;
    }

    @Override
    public void receiveInput(int synapticIndex) {
        this.accumulator += dendriticTreeWithMemory2E16.lookupSynapseStrength(synapticIndex);
    }

    public void attachSynapse(
            int synapticIndex,
            int strength) {
        dendriticTreeWithMemory2E16.attachSynapse(synapticIndex, strength);
    }
}
