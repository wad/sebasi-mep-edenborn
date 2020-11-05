package org.sebasi.mep.tool.datastructure.v1.output;

import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.highperf.NeuronForMotorOutputWithHighPerf;
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
        NeuronForMotorOutputWithHighPerf neuron = (NeuronForMotorOutputWithHighPerf) getNeuron(outputNeuronLabel);
        if (neuron == null) {
            throw new NeuronConnectionException("Failed to check lamp with label " + outputNeuronLabel + ".", getLabel());
        }

        return neuron.isLampOn();
    }

    public void resetLamp(String outputNeuronLabel) {
        NeuronForMotorOutputWithHighPerf neuron = (NeuronForMotorOutputWithHighPerf) getNeuron(outputNeuronLabel);
        if (neuron == null) {
            throw new NeuronConnectionException("Failed to reset lamp with label " + outputNeuronLabel + ".", getLabel());
        }

        neuron.resetLamp();
    }

    public void resetAllLamps() {
        for (int neuronIndex = 0; neuronIndex <= getGreatestNeuronIndex(); neuronIndex++) {
            NeuronForMotorOutputWithHighPerf neuron = (NeuronForMotorOutputWithHighPerf) getNeuron(neuronIndex);
            if (neuron != null) {
                neuron.resetLamp();
            }
        }
    }

    public String showAllOutputAsBinary() {
        int numNeurons = getGreatestNeuronIndex() + 1;
        char[] binaryDigits = new char[numNeurons];
        for (int neuronIndex = 0; neuronIndex < numNeurons; neuronIndex++) {
            NeuronForMotorOutputWithHighPerf neuron = (NeuronForMotorOutputWithHighPerf) getNeuron(neuronIndex);
            if (neuron != null) {
                binaryDigits[neuronIndex] = neuron.isLampOn() ? '1' : '0';
            }
        }
        return new String(binaryDigits);
    }

    public String showLampsHex() {
        int numNeurons = getGreatestNeuronIndex() + 1;

        // four bits per hex digit.
        // if the number of neurons isn't evenly divisible by four, just ignore those last 1, 2 or 3 for now.
        int numHexDigits = numNeurons >> 2;

        // put the hex digits into this buffer
        char[] hexDigits = new char[numHexDigits];

        int neuronIndex = 0;
        for (int i = 0; i < numHexDigits; i++) {
            NeuronForMotorOutputWithHighPerf neuron0 = (NeuronForMotorOutputWithHighPerf) getNeuron(neuronIndex);
            NeuronForMotorOutputWithHighPerf neuron1 = (NeuronForMotorOutputWithHighPerf) getNeuron(neuronIndex + 1);
            NeuronForMotorOutputWithHighPerf neuron2 = (NeuronForMotorOutputWithHighPerf) getNeuron(neuronIndex + 2);
            NeuronForMotorOutputWithHighPerf neuron3 = (NeuronForMotorOutputWithHighPerf) getNeuron(neuronIndex + 3);
            hexDigits[i] = convertBitsToHexDigit(
                    neuron0 != null && neuron0.isLampOn(),
                    neuron1 != null && neuron1.isLampOn(),
                    neuron2 != null && neuron2.isLampOn(),
                    neuron3 != null && neuron3.isLampOn());
            neuronIndex += 4;
        }

        String output = new String(hexDigits);

        // we need another hex digit if there's not a quantity divisible by 4
        int numRemainingNeuronsToCheck = numNeurons & 3;
        if (numRemainingNeuronsToCheck > 0) {
            char lastHexDigit;
            NeuronForMotorOutputWithHighPerf neuron0 = (NeuronForMotorOutputWithHighPerf) getNeuron(neuronIndex);
            boolean bit0 = neuron0 != null && neuron0.isLampOn();
            switch (numRemainingNeuronsToCheck) {
                case 1: {
                    lastHexDigit = convertBitsToHexDigit(
                            bit0,
                            false,
                            false,
                            false);
                    break;
                }
                case 2: {
                    NeuronForMotorOutputWithHighPerf neuron1 = (NeuronForMotorOutputWithHighPerf) getNeuron(neuronIndex + 1);
                    lastHexDigit = convertBitsToHexDigit(
                            bit0,
                            neuron1 != null && neuron1.isLampOn(),
                            false,
                            false);
                    break;
                }
                case 3: {
                    NeuronForMotorOutputWithHighPerf neuron1 = (NeuronForMotorOutputWithHighPerf) getNeuron(neuronIndex + 1);
                    NeuronForMotorOutputWithHighPerf neuron2 = (NeuronForMotorOutputWithHighPerf) getNeuron(neuronIndex + 2);
                    lastHexDigit = convertBitsToHexDigit(
                            bit0,
                            neuron1 != null && neuron1.isLampOn(),
                            neuron2 != null && neuron2.isLampOn(),
                            false);
                    break;
                }
                default:
                    throw new RuntimeException("bug");
            }
            output += lastHexDigit;
        }

        return output;
    }

    private char convertBitsToHexDigit(
            boolean bit0,
            boolean bit1,
            boolean bit2,
            boolean bit3) {
        int v = (bit0 ? 8 : 0) | (bit1 ? 4 : 0) | (bit2 ? 2 : 0) | (bit3 ? 1 : 0);
        if (v < 10) {
            return (char) ('0' + v);
        }
        return (char) ('A' + (v - 10));
    }
}
