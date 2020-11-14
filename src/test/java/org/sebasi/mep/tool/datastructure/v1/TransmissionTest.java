package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.input.NeuronForSensoryInput;
import org.sebasi.mep.tool.datastructure.v1.input.PanelInputWithButtons;
import org.sebasi.mep.tool.datastructure.v1.output.NeuronForMotorOutput;
import org.sebasi.mep.tool.datastructure.v1.output.PanelOutputWithLamps;
import org.sebasi.mep.tool.datastructure.v1.util.*;

public class TransmissionTest {
    @Test
    public void testInputMiddleAndOutput() {
        Helper helper = new Helper();

        ///////// middle cluster
        ClusterOfNeurons middle = new ClusterOfNeurons(helper, "middle");
        for (int i = 0; i < 5000; i++) {
            middle.addNeuron(new NeuronWithDendriticTreeStandard(
                    FiringComputer.WHEN_TEN_TIMES_NUM_CONNECTED_SYNAPSES,
                    TickPriority.second,
                    helper,
                    "mid_" + i));
        }
        ConnectomeGenerator.makeRandomConnections(
                middle,
                Chance.percent(20),
                Chance.percent(5),
                100,
                25);
        ConnectomeGenerator.makeRandomConnections(
                middle,
                Chance.percent(10),
                Chance.fraction(1, 1000),
                1000,
                80);

        ///////// input
        PanelInputWithButtons inputPanel = new PanelInputWithButtons(helper, "input");
        for (int i = 0; i < 100; i++) {
            inputPanel.addNeuron(new NeuronForSensoryInput(
                    FiringComputer.WHEN_TEN_TIMES_NUM_CONNECTED_SYNAPSES,
                    helper,
                    "inp_" + i));
        }
        ConnectomeGenerator.makeRandomConnections(
                inputPanel,
                middle,
                Chance.hundredPercent(),
                Chance.percent(50),
                500,
                1);

        ///////////// output
        PanelOutputWithLamps outputPanel = new PanelOutputWithLamps(helper, "output");
        for (int i = 0; i < 100; i++) {
            outputPanel.addNeuron(new NeuronForMotorOutput(
                    FiringComputer.WHEN_TEN_TIMES_NUM_CONNECTED_SYNAPSES,
                    helper,
                    "out_" + i));
        }
        ConnectomeGenerator.makeRandomConnectionsToOutput(
                middle,
                outputPanel,
                Chance.percent(10),
                100,
                10);

        StringBuilder builder = new StringBuilder();
        ClusterReport clusterReportInput = new ClusterReport(inputPanel);
        clusterReportInput.appendReport(builder);
        ClusterReport clusterReportMiddle = new ClusterReport(middle);
        clusterReportMiddle.appendReport(builder);
        ClusterReport clusterReportOutput = new ClusterReport(outputPanel);
        clusterReportOutput.appendReport(builder);
        System.out.println(builder.toString());
    }
}
