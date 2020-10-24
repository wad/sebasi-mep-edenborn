package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;

// This is a primitive construct to read the output.
// The idea is that it's a panel of little light bulbs, each with a number and a reset button by it.
public class MotorOutputLampPanel extends ClusterOfNeurons {

    public MotorOutputLampPanel(Helper helper) {
        this(helper, null);
    }

    public MotorOutputLampPanel(
            Helper helper,
            String label) {
        super(helper, label);
    }

    public boolean isLampOn(String outputNeuronLabel) {
        NeuronForMotorOutput neuron = (NeuronForMotorOutput) getNeuron(outputNeuronLabel);
        if (neuron == null) {
            throw new NeuronConnectionException("Failed to check lamp with label " + outputNeuronLabel + ".", label);
        }

        return neuron.isLampOn();
    }

    public void resetLamp(String outputNeuronLabel) {
        NeuronForMotorOutput neuron = (NeuronForMotorOutput) getNeuron(outputNeuronLabel);
        if (neuron == null) {
            throw new NeuronConnectionException("Failed to reset lamp with label " + outputNeuronLabel + ".", label);
        }

        neuron.resetLamp();
    }

    public String showAllOutputAsBinary() {
        StringBuilder builder = new StringBuilder();
        int highestNeuronIndex = getGreatestNeuronIndex();
        for (int neuronIndex = 0; neuronIndex < highestNeuronIndex; neuronIndex++) {
            NeuronForMotorOutput neuron = (NeuronForMotorOutput) getNeuron(neuronIndex);
            if (neuron != null) {
                builder.append(neuron.isLampOn() ? "1" : "0");
            }
        }
        return builder.toString();
    }

    public String showAllOutputAsHex() {
        StringBuilder builder = new StringBuilder();
        int highestNeuronIndex = getGreatestNeuronIndex();
        int hexDigit = 0;
        int numNeuronsFoundForThisHexDigit = 0;
        for (int neuronIndex = 0; neuronIndex < highestNeuronIndex; neuronIndex++) {
            NeuronForMotorOutput neuron = (NeuronForMotorOutput) getNeuron(neuronIndex);
            if (neuron != null) {
                hexDigit <<= 1;
                if (neuron.isLampOn()) {
                    hexDigit |= 1;
                }
                numNeuronsFoundForThisHexDigit++;
                if (numNeuronsFoundForThisHexDigit >= 5) {
                    // todo: remaining leftover bits?
                }
            }
        }
        return builder.toString();
    }
}
