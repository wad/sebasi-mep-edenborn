package org.sebasi.mep.tool.datastructure.v1.input;

import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.util.Chance;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;

// This is a primitive construct to input signals to a brain.
// The idea is that it's a panel of little light buttons, each with a number.
// (Note that these buttons don't stay pressed, they spring right back out. One press is one signal pulse.)
public class PanelInputWithButtons extends ClusterOfNeurons {

    public PanelInputWithButtons(Helper helper) {
        this(helper, null);
    }

    public PanelInputWithButtons(
            Helper helper,
            String label) {
        super(helper, label);
    }

    // todo: stronger presses should make firing profile fire multiple times

    public void pressButton(
            int inputNeuronIndex,
            ButtonPressStrength buttonPressStrength) {
        NeuronForSensoryInput neuron = (NeuronForSensoryInput) getNeuron(inputNeuronIndex);
        if (neuron == null) {
            throw new NeuronConnectionException("Failed to press button with index " + inputNeuronIndex + ".", getLabel());
        }

        pressButton(neuron, buttonPressStrength);
    }

    public void pressButton(
            String inputNeuronLabel,
            ButtonPressStrength buttonPressStrength) {
        NeuronForSensoryInput neuron = (NeuronForSensoryInput) getNeuron(inputNeuronLabel);
        if (neuron == null) {
            throw new NeuronConnectionException("Failed to press button with label " + inputNeuronLabel + ".", getLabel());
        }

        pressButton(neuron, buttonPressStrength);
    }

    void pressButton(
            NeuronForSensoryInput neuron,
            ButtonPressStrength buttonPressStrength) {
        neuron.receiveInputStimulus(buttonPressStrength);
    }

    public void pushRandomButtons(
            Chance chanceEachButtonIsPressed,
            ButtonPressStrength buttonPressStrength) {
        for (int neuronIndex = 0; neuronIndex <= getGreatestNeuronIndex(); neuronIndex++) {
            if (getHelper().getRandomUtil().shouldEventTrigger(chanceEachButtonIsPressed)) {
                pressButton(neuronIndex, buttonPressStrength);
            }
        }
    }
}
