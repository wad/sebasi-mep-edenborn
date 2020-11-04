package org.sebasi.mep.tool.datastructure.v1.highperf;

import org.sebasi.mep.tool.datastructure.v1.FiringComputer;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

// The dendrites (and their synapses) in this type of neuron don't have strengths, which
// means that they can't "learn".
public class NeuronWithoutMemoryWithHighPerf extends NeuronWithDendriticTreeWithHighPerf {

    static final int DEFAULT_INPUT_SIGNAL_STRENGTH = 1;

    DendriticTreeWithHighPerfWithoutMemory dendriticTreeWithHighPerfWithoutMemory;

    // On this neuron, if any input gets triggered, the accumulator accumulates this value.
    // Every dendritic synapse yields the same strength input.
    int defaultInputSignalStrength;

    public NeuronWithoutMemoryWithHighPerf(
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

    public NeuronWithoutMemoryWithHighPerf(
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
        dendriticTreeWithHighPerfWithoutMemory = new DendriticTreeWithHighPerfWithoutMemoryType2(dendriticTreeSize, this);
        defaultInputSignalStrength = DEFAULT_INPUT_SIGNAL_STRENGTH;
    }

    public int getDefaultInputSignalStrength() {
        return defaultInputSignalStrength;
    }

    public void setDefaultInputSignalStrength(int defaultInputSignalStrength) {
        this.defaultInputSignalStrength = defaultInputSignalStrength;
    }

    @Override
    protected DendriticTreeWithHighPerf getDendriticTree() {
        return this.dendriticTreeWithHighPerfWithoutMemory;
    }

    @Override
    public void receiveInput(int synapticIndex) {
        getDendriticTree().validateConnection(true, synapticIndex);
        accumulate(defaultInputSignalStrength);
    }
}
