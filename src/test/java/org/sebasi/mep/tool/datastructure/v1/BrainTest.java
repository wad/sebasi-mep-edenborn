package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

import static org.junit.Assert.*;

public class BrainTest {

    @Test
    public void testInputOutput_directlyConnected() {

        Helper helper = new Helper();

        SensoryInputButtonPanel inputPanel = new SensoryInputButtonPanel(helper, "input");
        NeuronForSensoryInput in1 = new NeuronForSensoryInput(FiringComputer.FireOnAnyInput, helper, "in1");
        NeuronForSensoryInput in2 = new NeuronForSensoryInput(FiringComputer.FireOnAnyInput, helper, "in2");
        inputPanel.addNeuron(in1);
        inputPanel.addNeuron(in2);

        MotorOutputLampPanel outputPanel = new MotorOutputLampPanel(helper, "output");
        NeuronForMotorOutput out1 = new NeuronForMotorOutput(FiringComputer.FireOnAnyInput, DendriticTreeSize.TwoE4, helper, "out1");
        NeuronForMotorOutput out2 = new NeuronForMotorOutput(FiringComputer.FireOnAnyInput, DendriticTreeSize.TwoE4, helper, "out2");
        outputPanel.addNeuron(out1);
        outputPanel.addNeuron(out2);

        in1.createOutgoingAxonConnection(out1, 0);
        in2.createOutgoingAxonConnection(out2, 0);

        assertFalse(outputPanel.isLampOn("out1"));
        assertFalse(outputPanel.isLampOn("out2"));

        inputPanel.pressButton("in1");

        helper.getTickers().tick();

        assertTrue(outputPanel.isLampOn("out1"));
        assertFalse(outputPanel.isLampOn("out2"));

        outputPanel.resetLamp("out1");

        helper.getTickers().tick();

        assertFalse(outputPanel.isLampOn("out1"));
        assertFalse(outputPanel.isLampOn("out2"));

        inputPanel.pressButton("in2");

        helper.getTickers().tick();

        assertFalse(outputPanel.isLampOn("out1"));
        assertTrue(outputPanel.isLampOn("out2"));
    }

    @Test
    public void testBrain() {

        Helper helper = new Helper();
        ClusterOfNeurons clusterOfNeurons = makeNeuronCluster(helper);

        assertEquals(1000, clusterOfNeurons.neurons.size());

        // todo: more good stuff
    }

    ClusterOfNeurons makeNeuronCluster(Helper helper) {
        int numNeuronsInCluster = 1000;
        DendriticTreeSize neuronSize = DendriticTreeSize.TwoE16;
        ClusterOfNeurons clusterOfNeurons = new ClusterOfNeurons(helper, "nc1");
        for (int i = 0; i < numNeuronsInCluster; i++) {
            Neuron neuron = new NeuronWithoutMemory(
                    FiringComputer.FireOnAnyInput,
                    TickPriority.second,
                    neuronSize,
                    helper);
            clusterOfNeurons.addNeuron(neuron);
        }
        return clusterOfNeurons;
    }
}
