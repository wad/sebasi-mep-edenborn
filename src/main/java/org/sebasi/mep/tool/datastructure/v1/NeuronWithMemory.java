package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

public class NeuronWithMemory extends Neuron {

    DendriticTreeWithMemory dendriticTreeWithMemory;

    public NeuronWithMemory(
            DendriticTreeSize dendriticTreeSize,
            Helper helper) {
        this(
                dendriticTreeSize,
                helper,
                null);
    }

    public NeuronWithMemory(
            DendriticTreeSize dendriticTreeSize,
            Helper helper,
            String label) {
        super(helper, label);
        dendriticTreeWithMemory = new DendriticTreeWithMemoryType1(dendriticTreeSize, this);
    }

    @Override
    protected DendriticTree getDendriticTree() {
        return this.dendriticTreeWithMemory;
    }

    @Override
    public void receiveInput(int synapticIndex) {
        this.accumulator += dendriticTreeWithMemory.lookupSynapseStrength(synapticIndex);
    }

    public void attachSynapse(
            int synapticIndex,
            int strength) {
        dendriticTreeWithMemory.attachSynapse(synapticIndex, strength);
    }
}
