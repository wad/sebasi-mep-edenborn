package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.Neuron;
import org.sebasi.mep.tool.datastructure.v1.NeuronWithDendriticTree;
import org.sebasi.mep.tool.datastructure.v1.SynapseOnDendrite;

import java.util.List;

// The variable named axonConnectivityDistribution (ACD) expresses how connected clusters are,
// from the perspective of axons.
// If N is the number of neurons in a cluster, then each axon has N * ACD connections to dendritic synapses.

// The variable named dendriteConnectivityDistribution (DCD) expresses how connected clusters are,
// from the perspective of dendrites.
// If N is the number if neurons in a source cluster, then each dendritic tree in the destination output cluster
// has N * DCD connections from axons in the source cluster.

@SuppressWarnings("UnusedReturnValue")
public class ConnectomeGenerator {

    // returns the number of connections made
    public static int makeRandomConnections(
            ClusterOfNeurons cluster,
            Chance chanceEachNeuronHasOfConnectingItsAxon,
            Chance axonConnectivityDistribution,
            int synapticStrengthMean,
            int synapticStrengthStandardDev) {
        int numConnectionsMade = 0;
        RandomUtil randomUtil = cluster.getHelper().getRandomUtil();
        List<Neuron> neurons = cluster.getNeurons();
        int numAxonConnectionsPerNeuron = axonConnectivityDistribution.multipliedBy(neurons.size());
        for (Neuron sourceNeuron : neurons) {
            if (randomUtil.shouldEventTrigger(chanceEachNeuronHasOfConnectingItsAxon)) {
                for (int i = 0; i < numAxonConnectionsPerNeuron; i++) {
                    connect(
                            sourceNeuron,
                            cluster.getRandomNeuron(),
                            synapticStrengthMean,
                            synapticStrengthStandardDev,
                            randomUtil);
                    numConnectionsMade++;
                }
            }
        }
        return numConnectionsMade;
    }

    // returns the number of connections made
    public static int makeRandomConnections(
            ClusterOfNeurons sourceCluster,
            ClusterOfNeurons destCluster,
            Chance chanceEachNeuronHasOfConnectingItsAxon,
            Chance axonConnectivityDistribution,
            int synapticStrengthMean,
            int synapticStrengthStandardDev) {
        int numConnectionsMade = 0;
        RandomUtil randomUtil = sourceCluster.getHelper().getRandomUtil();
        int numAxonConnectionsPerNeuron = axonConnectivityDistribution.multipliedBy(destCluster.getNeurons().size());
        for (Neuron sourceNeuron : sourceCluster.getNeurons()) {
            if (randomUtil.shouldEventTrigger(chanceEachNeuronHasOfConnectingItsAxon)) {
                for (int i = 0; i < numAxonConnectionsPerNeuron; i++) {
                    connect(
                            sourceNeuron,
                            destCluster.getRandomNeuron(),
                            synapticStrengthMean,
                            synapticStrengthStandardDev,
                            randomUtil);
                    numConnectionsMade++;
                }
            }
        }
        return numConnectionsMade;
    }

    // returns the number of connections made
    // This method goes in a reverse order, making sure each output neuron is connected.
    public static int makeRandomConnectionsToOutput(
            ClusterOfNeurons sourceCluster,
            ClusterOfNeurons outputCluster,
            Chance dendriteConnectivityDistribution,
            int synapticStrengthMean,
            int synapticStrengthStandardDev) {
        RandomUtil randomUtil = sourceCluster.getHelper().getRandomUtil();
        int numConnectionsMade = 0;
        int numConnectionsPerOutputNeuron = dendriteConnectivityDistribution.multipliedBy(
                sourceCluster.getNeurons().size());
        for (Neuron destNeuron : outputCluster.getNeurons()) {
            for (int i = 0; i < numConnectionsPerOutputNeuron; i++) {
                connect(
                        sourceCluster.getRandomNeuron(),
                        destNeuron,
                        synapticStrengthMean,
                        synapticStrengthStandardDev,
                        randomUtil);
                numConnectionsMade++;
            }
        }
        return numConnectionsMade;
    }

    static void connect(
            Neuron sourceNeuron,
            Neuron destNeuron,
            int synapticStrengthMean,
            int synapticStrengthStandardDev,
            RandomUtil randomUtil) {
        int synapticIndex = ((NeuronWithDendriticTree) destNeuron).attachSynapse();
        SynapseOnDendrite synapseOnDendrite = ((NeuronWithDendriticTree) destNeuron).getSynapse(synapticIndex);
        int newSynapticStrength = randomUtil.getRandomNumberInNormalDistribution(
                synapticStrengthMean,
                synapticStrengthStandardDev);
        synapseOnDendrite.setSynapticStrengthValue(newSynapticStrength);
        sourceNeuron.createOutgoingAxonConnection((NeuronWithDendriticTree) destNeuron, synapticIndex);
    }
}
