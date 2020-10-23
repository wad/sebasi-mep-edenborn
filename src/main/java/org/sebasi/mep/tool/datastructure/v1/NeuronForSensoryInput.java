package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

// This type of neuron has no dendrites, just a trigger button that fires the axon.
public class NeuronForSensoryInput extends Neuron {

    public NeuronForSensoryInput(Helper helper) {
        this(
                helper,
                null);
    }

    public NeuronForSensoryInput(
            Helper helper,
            String label) {
        super(helper, label);
    }

    @Override
    protected void initializeAxon() {
        axon = new AxonForConnectingToNeurons();
    }

    @Override
    public boolean fireIfReady() {
        // Always just fire.
        axon.fire();
        return true;
    }
}
