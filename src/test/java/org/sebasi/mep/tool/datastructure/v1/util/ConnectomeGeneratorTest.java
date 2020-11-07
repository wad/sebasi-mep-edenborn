package org.sebasi.mep.tool.datastructure.v1.util;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.FiringComputer;
import org.sebasi.mep.tool.datastructure.v1.NeuronWithDendriticTreeStandard;

public class ConnectomeGeneratorTest {

    @Test
    public void testConnectingCluster() {
        Helper helper = new Helper();
        ClusterOfNeurons cluster = new ClusterOfNeurons(helper, "cluster");
        for (int i = 0; i < 10; i++) {
            cluster.addNeuron(new NeuronWithDendriticTreeStandard(
                    FiringComputer.ON_ANY_INPUT,
                    TickPriority.second,
                    helper,
                    "num_" + i));
        }

        ConnectomeGenerator.makeRandomConnections(
                cluster,
                Chance.hundredPercent(),
                Chance.percent(50));
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
                Chance.hundredPercent(),
                Chance.percent(50));
    }
}