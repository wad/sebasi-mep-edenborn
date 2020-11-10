package org.sebasi.mep.tool.datastructure.v1.util;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.FiringComputer;
import org.sebasi.mep.tool.datastructure.v1.NeuronWithDendriticTreeStandard;

public class ConnectomeGeneratorTest {

    @Test
    public void testConnectingClusterSimple() {
        Helper helper = new Helper();
        ClusterOfNeurons cluster = new ClusterOfNeurons(helper, "cluster");
        for (int i = 0; i < 1000; i++) {
            cluster.addNeuron(new NeuronWithDendriticTreeStandard(
                    FiringComputer.ON_ANY_INPUT,
                    TickPriority.second,
                    helper,
                    "num_" + i));
        }

        ConnectomeGenerator.makeRandomConnections(
                cluster,
                Chance.hundredPercent(),
                Chance.percent(50),
                50,
                25);

        ClusterReport clusterReport = new ClusterReport(cluster);
        StringBuilder builder =new StringBuilder();
        clusterReport.appendReport(builder);
        System.out.println(builder.toString());
    }

    @Test
    public void testConnectingClusterTwoStrengthGroupings() {
        Helper helper = new Helper();
        ClusterOfNeurons cluster = new ClusterOfNeurons(helper, "cluster");
        for (int i = 0; i < 1000; i++) {
            cluster.addNeuron(new NeuronWithDendriticTreeStandard(
                    FiringComputer.ON_ANY_INPUT,
                    TickPriority.second,
                    helper,
                    "num_" + i));
        }
        int numConnectionsInGroup1 = ConnectomeGenerator.makeRandomConnections(
                cluster,
                Chance.percent(10),
                Chance.percent(50),
                100,
                25);
        int numConnectionsInGroup2 = ConnectomeGenerator.makeRandomConnections(
                cluster,
                Chance.percent(10),
                Chance.percent(50),
                500,
                25);

        System.out.println("Num connections in group 1: " + numConnectionsInGroup1);
        System.out.println("Num connections in group 2: " + numConnectionsInGroup2);

        ClusterReport clusterReport = new ClusterReport(cluster);
        StringBuilder builder =new StringBuilder();
        clusterReport.appendReport(builder);
        System.out.println(builder.toString());
    }

    @Test
    public void testConnectingTwoClusters() {
        Helper helper = new Helper();
        ClusterOfNeurons src = new ClusterOfNeurons(helper, "src");
        ClusterOfNeurons dest = new ClusterOfNeurons(helper, "dest");
        for (int i = 0; i < 1000; i++) {
            src.addNeuron(new NeuronWithDendriticTreeStandard(
                    FiringComputer.ON_ANY_INPUT,
                    TickPriority.second,
                    helper));
            dest.addNeuron(new NeuronWithDendriticTreeStandard(
                    FiringComputer.ON_ANY_INPUT,
                    TickPriority.second,
                    helper));
        }

        ConnectomeGenerator.makeRandomConnections(
                src,
                dest,
                Chance.percent(20),
                Chance.percent(50),
                50,
                25);
    }
}
