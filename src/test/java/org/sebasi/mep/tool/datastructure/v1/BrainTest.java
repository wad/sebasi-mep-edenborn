package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BrainTest {

    @Test
    public void testInputOutput_directlyConnected() {

        Helper helper = new Helper();

        SensoryInputButtonPanel inputPanel = new SensoryInputButtonPanel(helper, "input");
        NeuronForSensoryInput in1 = new NeuronForSensoryInput(FiringComputer.ON_ANY_INPUT, helper, "in1");
        NeuronForSensoryInput in2 = new NeuronForSensoryInput(FiringComputer.ON_ANY_INPUT, helper, "in2");
        inputPanel.addNeuron(in1);
        inputPanel.addNeuron(in2);

        MotorOutputLampPanel outputPanel = new MotorOutputLampPanel(helper, "output");
        NeuronForMotorOutput out1 = new NeuronForMotorOutput(FiringComputer.ON_ANY_INPUT, DendriticTreeSize.TwoE4, helper, "out1");
        NeuronForMotorOutput out2 = new NeuronForMotorOutput(FiringComputer.ON_ANY_INPUT, DendriticTreeSize.TwoE4, helper, "out2");
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
    public void testInputOutput_with3MiddleLayers() {

        int numInputs = 256;
        int numMiddleNeurons1 = 256;
        int numMiddleNeurons2 = 256;
        int numMiddleNeurons3 = 256;
        int numOutputs = 256;

        Helper helper = new Helper();
        helper.setShouldShowLogMessages(false);

        SensoryInputButtonPanel inputPanel = new SensoryInputButtonPanel(helper, "input");
        for (int i = 0; i < numInputs; i++) {
            NeuronForSensoryInput inputNeuron = new NeuronForSensoryInput(
                    FiringComputer.ALWAYS,
                    helper);
            inputPanel.addNeuron(inputNeuron);
        }

        ClusterOfNeurons middleCluster1 = new ClusterOfNeurons(helper, "middle1");
        for (int i = 0; i < numMiddleNeurons1; i++) {
            NeuronWithoutMemory neuron = new NeuronWithoutMemory(
                    FiringComputer.WHEN_TWO_TIMES_NUM_CONNECTED_SYNAPSES,
                    TickPriority.second,
                    DendriticTreeSize.TwoE8,
                    helper);
            middleCluster1.addNeuron(neuron);
        }

        ClusterOfNeurons middleCluster2 = new ClusterOfNeurons(helper, "middle2");
        for (int i = 0; i < numMiddleNeurons2; i++) {
            NeuronWithoutMemory neuron = new NeuronWithoutMemory(
                    FiringComputer.WHEN_TWO_TIMES_NUM_CONNECTED_SYNAPSES,
                    TickPriority.second,
                    DendriticTreeSize.TwoE8,
                    helper);
            middleCluster2.addNeuron(neuron);
        }

        ClusterOfNeurons middleCluster3 = new ClusterOfNeurons(helper, "middle3");
        for (int i = 0; i < numMiddleNeurons3; i++) {
            NeuronWithoutMemory neuron = new NeuronWithoutMemory(
                    FiringComputer.WHEN_TWO_TIMES_NUM_CONNECTED_SYNAPSES,
                    TickPriority.second,
                    DendriticTreeSize.TwoE8,
                    helper);
            middleCluster3.addNeuron(neuron);
        }

        MotorOutputLampPanel outputPanel = new MotorOutputLampPanel(helper, "output");
        for (int i = 0; i < numOutputs; i++) {
            NeuronForMotorOutput outputNeuron = new NeuronForMotorOutput(
                    FiringComputer.WHEN_TWO_TIMES_NUM_CONNECTED_SYNAPSES,
                    DendriticTreeSize.TwoE8,
                    helper);
            outputPanel.addNeuron(outputNeuron);
        }

        Electrician electrician = new Electrician(helper);

        electrician.wireRandomly(
                middleCluster3,
                Chance.percent(100),
                Chance.percent(100),
                outputPanel);

        electrician.wireRandomly(
                middleCluster2,
                Chance.percent(100),
                Chance.percent(100),
                middleCluster3);

        electrician.wireRandomly(
                middleCluster1,
                Chance.percent(100),
                Chance.percent(100),
                middleCluster2);

        electrician.wireRandomly(
                inputPanel,
                Chance.percent(100),
                Chance.percent(100),
                middleCluster1);

        StringBuilder builder = new StringBuilder();
        new ClusterReport(inputPanel).appendReport(builder);
        new ClusterReport(middleCluster1).appendReport(builder);
        new ClusterReport(middleCluster2).appendReport(builder);
        new ClusterReport(middleCluster3).appendReport(builder);
        new ClusterReport(outputPanel).appendReport(builder);

        System.out.println(builder.toString());

        Chance chanceEachButtonIsPressed = new Chance(1, 2);
        System.out.println("Output lamps:");
        for (int i = 0; i < 20; i++) {
            System.out.println(outputPanel.showLampsHex());
            outputPanel.resetAllLamps();
            helper.getTickers().tick();
            inputPanel.pushRandomButtons(chanceEachButtonIsPressed);
        }
    }
}
