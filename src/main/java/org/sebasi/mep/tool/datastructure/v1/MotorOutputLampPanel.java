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
}
