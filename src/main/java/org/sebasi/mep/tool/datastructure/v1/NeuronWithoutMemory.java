package org.sebasi.mep.tool.datastructure.v1;

// The dendrites (and their synapses) in this type of neuron don't have strengths, which
// means that they can't "learn".
public class NeuronWithoutMemory extends Neuron {

    static final int DEFAULT_INPUT_SIGNAL_STRENGTH = 1;

    DendritesWithoutMemory dendritesWithoutMemory;

    // On this neuron, if any input gets triggered, the accumulator accumulates this value. Every dendritic synapse
    // yields the same strength input.
    int defaultInputSignalStrength;

    public NeuronWithoutMemory() {
        this(null);
    }

    public NeuronWithoutMemory(String label) {
        super(label);
        dendrites = new DendritesWithoutMemory();
        dendritesWithoutMemory = (DendritesWithoutMemory) dendrites;
        defaultInputSignalStrength = DEFAULT_INPUT_SIGNAL_STRENGTH;
    }

    public int getDefaultInputSignalStrength() {
        return defaultInputSignalStrength;
    }

    public void setDefaultInputSignalStrength(int defaultInputSignalStrength) {
        this.defaultInputSignalStrength = defaultInputSignalStrength;
    }

    @Override
    public void receiveInput(int port) {
        if (!isPortConnected(port)) {
            throw new RuntimeException("Bug: Tried to receive input on disconnected port");
        }

        this.accumulator += defaultInputSignalStrength;
    }
}
