package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;

import static org.junit.Assert.assertEquals;
import static org.sebasi.mep.tool.datastructure.v1.Neuron.NEURON_INDEX_IF_NOT_SET;

public class MotorOutputLampPanelTest {

    @Test
    public void testNeuronIndexes() {
        Helper helper = new Helper();
        MotorOutputLampPanel lampPanel = new MotorOutputLampPanel(helper);

        NeuronForMotorOutput neuron0 = new NeuronForMotorOutput(
                FiringComputer.FireAlways,
                DendriticTreeSize.TwoE4,
                helper,
                "n0");
        NeuronForMotorOutput neuron1 = new NeuronForMotorOutput(
                FiringComputer.FireAlways,
                DendriticTreeSize.TwoE4,
                helper,
                "n1");
        NeuronForMotorOutput neuron2 = new NeuronForMotorOutput(
                FiringComputer.FireAlways,
                DendriticTreeSize.TwoE4,
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
}
