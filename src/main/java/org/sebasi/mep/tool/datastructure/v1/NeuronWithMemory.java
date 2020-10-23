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

        switch (dendriticTreeSize) {
            case TwoE4:
                dendriticTree = new DendriticTreeWithMemory2E4(this);
                break;
            case TwoE16:
                dendriticTree = new DendriticTreeWithMemory2E16(this);
                break;
        }

        dendriticTreeWithMemory = (DendriticTreeWithMemory) dendriticTree;
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
