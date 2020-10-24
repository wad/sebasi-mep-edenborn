package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public class NeuronWithMemory extends NeuronWithDendriticTree {

    DendriticTreeWithMemory dendriticTreeWithMemory;

    public NeuronWithMemory(
            FiringComputer firingComputer,
            TickPriority tickPriority,
            DendriticTreeSize dendriticTreeSize,
            Helper helper) {
        this(
                firingComputer,
                tickPriority,
                dendriticTreeSize,
                helper,
                null);
    }

    public NeuronWithMemory(
            FiringComputer firingComputer,
            TickPriority tickPriority,
            DendriticTreeSize dendriticTreeSize,
            Helper helper,
            String label) {
        super(
                firingComputer,
                tickPriority,
                helper,
                label);
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
