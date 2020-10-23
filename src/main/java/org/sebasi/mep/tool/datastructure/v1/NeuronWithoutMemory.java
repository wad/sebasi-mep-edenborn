package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

// The dendrites (and their synapses) in this type of neuron don't have strengths, which
// means that they can't "learn".
public class NeuronWithoutMemory extends Neuron {

    static final int DEFAULT_INPUT_SIGNAL_STRENGTH = 1;

    DendriticTree2E16WithoutMemory dendriticTree2E16WithoutMemory;

    // On this neuron, if any input gets triggered, the accumulator accumulates this value. Every dendritic synapse
    // yields the same strength input.
    int defaultInputSignalStrength;

    public NeuronWithoutMemory(Helper helper) {
        this(helper, null);
    }

    public NeuronWithoutMemory(
            Helper helper,
            String label) {
        super(helper, label);
        dendriticTree = new DendriticTree2E16WithoutMemory(this);
        dendriticTree2E16WithoutMemory = (DendriticTree2E16WithoutMemory) dendriticTree;
        defaultInputSignalStrength = DEFAULT_INPUT_SIGNAL_STRENGTH;
    }

    public int getDefaultInputSignalStrength() {
        return defaultInputSignalStrength;
    }

    public void setDefaultInputSignalStrength(int defaultInputSignalStrength) {
        this.defaultInputSignalStrength = defaultInputSignalStrength;
    }

    @Override
    public void receiveInput(int synapticIndex) {
        if (!isSynapseConnected(synapticIndex)) {
            throw new RuntimeException("Bug: Tried to receive input on disconnected synapse");
        }

        this.accumulator += defaultInputSignalStrength;
    }
}
