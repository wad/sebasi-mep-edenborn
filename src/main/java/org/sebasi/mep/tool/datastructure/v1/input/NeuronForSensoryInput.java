package org.sebasi.mep.tool.datastructure.v1.input;

import org.sebasi.mep.tool.datastructure.v1.*;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.NeuronReport;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

import java.util.Collections;
import java.util.Set;

// This type of neuron has no dendrites, just a trigger button that fires the axon.
public class NeuronForSensoryInput extends Neuron {

    static final int STARTING_STIMULUS_STRENGTH = 1;

    int stimulusStrength = STARTING_STIMULUS_STRENGTH;

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
        super(
                firingComputer,
                TickPriority.first,
                helper,
                label);
    }

    @Override
    public NeuralType getNeuralType() {
        return NeuralType.SensoryInput;
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

    public void setStimulusStrength(int stimulusStrength) {
        this.stimulusStrength = stimulusStrength;
    }

    public void receiveInputStimulus() {
        accumulate(stimulusStrength);
    }

    public void receiveInputStimulus(ButtonPressStrength buttonPressStrength) {
        accumulate(buttonPressStrength.getStrengthValue());
    }

    @Override
    public int getNumConnectionsOnDendriticTree() {
        return 0;
    }

    @Override
    public Set<SynapseOnDendrite> getSynapsesOnDendriticTree() {
        return Collections.emptySet();
    }

    @Override
    public NeuronReport getInfoForReport() {
        NeuronReport neuronReport = new NeuronReport(this);
        neuronReport.setNumSynapsesOnDendriticTree(0);
        return neuronReport;
    }
}
