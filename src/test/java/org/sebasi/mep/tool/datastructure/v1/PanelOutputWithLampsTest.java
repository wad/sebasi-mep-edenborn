package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.output.NeuronForMotorOutput;
import org.sebasi.mep.tool.datastructure.v1.output.PanelOutputWithLamps;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;

import static org.junit.Assert.assertEquals;
import static org.sebasi.mep.tool.datastructure.v1.Neuron.NEURON_INDEX_IF_NOT_SET;

public class PanelOutputWithLampsTest {

    @Test
    public void testNeuronIndexes() {
        Helper helper = new Helper();
        PanelOutputWithLamps lampPanel = new PanelOutputWithLamps(helper);

        NeuronForMotorOutput neuron0 = new NeuronForMotorOutput(
                FiringComputer.ALWAYS,
                helper,
                "n0");
        NeuronForMotorOutput neuron1 = new NeuronForMotorOutput(
                FiringComputer.ALWAYS,
                helper,
                "n1");
        NeuronForMotorOutput neuron2 = new NeuronForMotorOutput(
                FiringComputer.ALWAYS,
                helper,
                "n2");

        assertEquals(NEURON_INDEX_IF_NOT_SET, neuron0.getNeuronIndex());
        assertEquals(NEURON_INDEX_IF_NOT_SET, neuron1.getNeuronIndex());
        assertEquals(NEURON_INDEX_IF_NOT_SET, neuron2.getNeuronIndex());

        lampPanel.addNeuron(neuron0);
        lampPanel.addNeuron(neuron1);
        lampPanel.addNeuron(neuron2);

        assertEquals(0, neuron0.getNeuronIndex());
        assertEquals(1, neuron1.getNeuronIndex());
        assertEquals(2, neuron2.getNeuronIndex());
    }

    @Test
    public void testHexOutput_justSomeZeroes() {
        Helper helper = new Helper();
        PanelOutputWithLamps lampPanel = new PanelOutputWithLamps(helper);

        int numNeuronsToMake = 3;
        for (int i = 0; i < numNeuronsToMake; i++) {
            NeuronForMotorOutput neuron = new NeuronForMotorOutput(
                    FiringComputer.ALWAYS,
                    helper,
                    "n" + i);
            lampPanel.addNeuron(neuron);
        }

        // neuron index starts at 0
        assertEquals(numNeuronsToMake - 1, lampPanel.getGreatestNeuronIndex());

        String hex = lampPanel.showLampsHex();
        assertEquals("0", hex);
    }

    @Test
    public void testHexOutput_A() {
        Helper helper = new Helper();
        PanelOutputWithLamps lampPanel = new PanelOutputWithLamps(helper);

        int numNeuronsToMake = 3;
        for (int i = 0; i < numNeuronsToMake; i++) {
            NeuronForMotorOutput neuron = new NeuronForMotorOutput(
                    FiringComputer.ALWAYS,
                    helper,
                    "n" + i);
            lampPanel.addNeuron(neuron);
        }

        // neuron index starts at 0
        assertEquals(numNeuronsToMake - 1, lampPanel.getGreatestNeuronIndex());

        // 101 --> 1010 --> A
        setHasFired(lampPanel, 0, 2);

        String hex = lampPanel.showLampsHex();
        assertEquals("A", hex);

        assertEquals("101", lampPanel.showAllOutputAsBinary());
    }

    @Test
    public void testHexOutput_multiDigit() {
        Helper helper = new Helper();
        PanelOutputWithLamps lampPanel = new PanelOutputWithLamps(helper);

        int numNeuronsToMake = 12;
        for (int i = 0; i < numNeuronsToMake; i++) {
            NeuronForMotorOutput neuron = new NeuronForMotorOutput(
                    FiringComputer.ALWAYS,
                    helper);
            lampPanel.addNeuron(neuron);
        }

        // neuron index starts at 0
        assertEquals(numNeuronsToMake - 1, lampPanel.getGreatestNeuronIndex());

        // 0101 1111 0100
        setHasFired(lampPanel, 1, 3, 4, 5, 6, 7, 9);
        assertEquals("5F4", lampPanel.showLampsHex());

        // add one more neuron, the 13th, that didn't fire.
        NeuronForMotorOutput neuron = new NeuronForMotorOutput(
                FiringComputer.ALWAYS,
                helper);
        lampPanel.addNeuron(neuron);

        // 0101 1111 0100 --> 0101 1111 0100 0 --> 0101 1111 0100 0000 --> 5F40
        assertEquals("5F40", lampPanel.showLampsHex());

        // now fire that last one.
        setHasFired(lampPanel, 12);
        // 0101 1111 0100 0000 --> 0101 1111 0100 1000 --> 5F48
        assertEquals("5F48", lampPanel.showLampsHex());

        assertEquals("0101111101001", lampPanel.showAllOutputAsBinary());
    }

    void setHasFired(
            PanelOutputWithLamps lampPanel,
            int... neuronIndexes) {
        for (int neuronIndex : neuronIndexes) {
            NeuronForMotorOutput neuron = (NeuronForMotorOutput) lampPanel.getNeuron(neuronIndex);
            AxonForMotorOutput axon = (AxonForMotorOutput) neuron.axon;
            axon.hasFired = true;
        }
    }
}
