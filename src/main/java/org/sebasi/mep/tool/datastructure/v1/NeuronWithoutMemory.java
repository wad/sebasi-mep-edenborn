package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

// The dendrites (and their synapses) in this type of neuron don't have strengths, which
// means that they can't "learn".
public class NeuronWithoutMemory extends Neuron {

    static final int DEFAULT_INPUT_SIGNAL_STRENGTH = 1;

    DendriticTreeWithoutMemory dendriticTreeWithoutMemory;

    // On this neuron, if any input gets triggered, the accumulator accumulates this value.
    // Every dendritic synapse yields the same strength input.
    int defaultInputSignalStrength;

    public NeuronWithoutMemory(
            DendriticTreeSize dendriticTreeSize,
            Helper helper) {
        this(
                dendriticTreeSize,
                helper,
                null);
    }

    public NeuronWithoutMemory(
            DendriticTreeSize dendriticTreeSize,
            Helper helper,
            String label) {
        super(helper, label);
        dendriticTreeWithoutMemory = new DendriticTreeWithoutMemoryType2(dendriticTreeSize, this);
        defaultInputSignalStrength = DEFAULT_INPUT_SIGNAL_STRENGTH;
    }

    public int getDefaultInputSignalStrength() {
        return defaultInputSignalStrength;
    }

    public void setDefaultInputSignalStrength(int defaultInputSignalStrength) {
        this.defaultInputSignalStrength = defaultInputSignalStrength;
    }

    @Override
    protected DendriticTree getDendriticTree() {
        return this.dendriticTreeWithoutMemory;
    }

    @Override
    public void receiveInput(int synapticIndex) {
        getDendriticTree().validateConnection(true, synapticIndex);
        this.accumulator += defaultInputSignalStrength;
    }
}
