package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public class NeuronWithMemoryWithHighPerf extends NeuronWithDendriticTreeWithHighPerf {

    DendriticTreeWithHighPerfWithMemory dendriticTreeWithHighPerfWithMemory;

    public NeuronWithMemoryWithHighPerf(
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

    public NeuronWithMemoryWithHighPerf(
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
        dendriticTreeWithHighPerfWithMemory = new DendriticTreeWithHighPerfWithMemoryType1(dendriticTreeSize, this);
    }

    @Override
    protected DendriticTreeWithHighPerf getDendriticTree() {
        return this.dendriticTreeWithHighPerfWithMemory;
    }

    @Override
    public void receiveInput(int synapticIndex) {
        this.accumulator += dendriticTreeWithHighPerfWithMemory.lookupSynapseStrength(synapticIndex);
    }

    public void attachSynapse(
            int synapticIndex,
            int strength) {
        dendriticTreeWithHighPerfWithMemory.attachSynapse(synapticIndex, strength);
    }
}
