package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.Neuron;
import org.sebasi.mep.tool.datastructure.v1.NeuronWithDendriticTree;
import org.sebasi.mep.tool.datastructure.v1.SynapseOnDendrite;

import java.util.List;

// The variable named axonConnectivityDistribution (ACD) expresses how connected clusters are.
// If N is the number of neurons in a cluster, then each axon has N * ACD connections to dendritic synapses.

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
                    Neuron destNeuron = cluster.getRandomNeuron();
                    int synapticIndex = ((NeuronWithDendriticTree) destNeuron).attachSynapse();
                    SynapseOnDendrite synapseOnDendrite = ((NeuronWithDendriticTree) destNeuron).getSynapse(synapticIndex);
                    int newSynapticStrength = randomUtil.getRandomNumberInNormalDistribution(
                            synapticStrengthMean,
                            synapticStrengthStandardDev);
                    synapseOnDendrite.setSynapticStrengthValue(newSynapticStrength);
                    sourceNeuron.createOutgoingAxonConnection((NeuronWithDendriticTree) destNeuron, synapticIndex);
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
        int numAxonConnectionsPerNeuron = axonConnectivityDistribution.multipliedBy(destCluster.getNeurons().size());
        for (Neuron sourceNeuron : sourceCluster.getNeurons()) {
            RandomUtil randomUtil = sourceCluster.getHelper().getRandomUtil();
            if (randomUtil.shouldEventTrigger(chanceEachNeuronHasOfConnectingItsAxon)) {
                for (int i = 0; i < numAxonConnectionsPerNeuron; i++) {
                    Neuron destNeuron = destCluster.getRandomNeuron();
                    int synapticIndex = ((NeuronWithDendriticTree) destNeuron).attachSynapse();
                    SynapseOnDendrite synapseOnDendrite = ((NeuronWithDendriticTree) destNeuron).getSynapse(synapticIndex);
                    int newSynapticStrength = randomUtil.getRandomNumberInNormalDistribution(
                            synapticStrengthMean,
                            synapticStrengthStandardDev);
                    synapseOnDendrite.setSynapticStrengthValue(newSynapticStrength);
                    sourceNeuron.createOutgoingAxonConnection((NeuronWithDendriticTree) destNeuron, synapticIndex);
                    numConnectionsMade++;
                }
            }
        }
        return numConnectionsMade;
    }
}
