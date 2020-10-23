package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

// This type of neuron has no dendrites, just a trigger button that fires the axon.
public class NeuronForSensoryInput extends Neuron {

    public NeuronForSensoryInput(
            FiringComputer firingComputer,
            Helper helper) {
        this(
                firingComputer,
                helper,
                null);
    }

    public NeuronForSensoryInput(
            FiringComputer firingComputer,
            Helper helper,
            String label) {
        super(firingComputer, helper, label);
    }

    @Override
    protected void initializeAxon() {
        axon = new AxonForConnectingToNeurons();
    }

    @Override
    protected int getMaxNumSynapticConnections() {
        return 0;
    }

    @Override
    protected int getNumSynapticConnections() {
        return 0;
    }

    @Override
    public void createOutgoingAxonConnection(
            NeuronWithDendriticTree destinationNeuron,
            int synapticIndex) {
        ((AxonForConnectingToNeurons) axon).createOutgoingConnection(destinationNeuron, synapticIndex);
    }
}
