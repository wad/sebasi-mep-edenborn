package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Chance;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;
import org.sebasi.mep.tool.datastructure.v1.util.RandomUtil;

// This is a primitive construct to input signals to a brain.
// The idea is that it's a panel of little light buttons, each with a number.
// (Note that these buttons don't stay pressed, they spring right back out. One press is one signal pulse.)
public class SensoryInputButtonPanel extends ClusterOfNeurons {

    public SensoryInputButtonPanel(Helper helper) {
        this(helper, null);
    }

    public SensoryInputButtonPanel(
            Helper helper,
            String label) {
        super(helper, label);
    }

    public void pressButton(int inputNeuronIndex) {
        NeuronForSensoryInput neuron = (NeuronForSensoryInput) getNeuron(inputNeuronIndex);
        if (neuron == null) {
            throw new NeuronConnectionException("Failed to press button with index " + inputNeuronIndex + ".", label);
        }

        pressButton(neuron);
    }

    public void pressButton(String inputNeuronLabel) {
        NeuronForSensoryInput neuron = (NeuronForSensoryInput) getNeuron(inputNeuronLabel);
        if (neuron == null) {
            throw new NeuronConnectionException("Failed to press button with label " + inputNeuronLabel + ".", label);
        }

        pressButton(neuron);
    }

    void pressButton(NeuronForSensoryInput neuron) {
        neuron.receiveInputStimulus();
    }

    public void pushRandomButtons(Chance chance) {
        for (int neuronIndex = 0; neuronIndex <= getGreatestNeuronIndex(); neuronIndex++) {
            if (getHelper().getRandomUtil().shouldEventTrigger(chance)) {
                pressButton(neuronIndex);
            }
        }
    }
}
