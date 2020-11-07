package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.Neuron;
import org.sebasi.mep.tool.datastructure.v1.NeuronWithDendriticTree;

import java.util.List;

// The variable named axonConnectivityDistribution (ACD) expresses how connected clusters are.
// If N is the number of neurons in a cluster, the each axon has N * ACD connections to dendritic synapses.

public class ConnectomeGenerator {

    public static void makeRandomConnections(
            ClusterOfNeurons cluster,
            Chance chanceEachNeuronHasOfConnectingItsAxon,
            Chance axonConnectivityDistribution) {


        List<Neuron> neurons = cluster.getNeurons();
        int numAxonConnectionsPerNeuron = axonConnectivityDistribution.multipliedBy(neurons.size());
        for (Neuron sourceNeuron : neurons) {
            if (cluster.getHelper().getRandomUtil().shouldEventTrigger(chanceEachNeuronHasOfConnectingItsAxon)) {
                for (int i = 0; i < numAxonConnectionsPerNeuron; i++) {
                    Neuron destNeuron = cluster.getRandomNeuron();
                    int synapticIndex = ((NeuronWithDendriticTree) destNeuron).attachSynapse();
                    sourceNeuron.createOutgoingAxonConnection((NeuronWithDendriticTree) destNeuron, synapticIndex);
//                    System.out.println("Connected neuron " + sourceNeuron.getLabel()
//                            + " to synaptic index " + synapticIndex
//                            + " of neuron " + destNeuron.getLabel());
                }
            }
        }
    }

    public static void makeRandomConnections(
            ClusterOfNeurons sourceCluster,
            ClusterOfNeurons destCluster,
            Chance chanceEachNeuronHasOfConnectingItsAxon,
            Chance axonConnectivityDistribution) {
        int numAxonConnectionsPerNeuron = axonConnectivityDistribution.multipliedBy(destCluster.getNeurons().size());
        for (Neuron sourceNeuron : sourceCluster.getNeurons()) {
            if (sourceCluster.getHelper().getRandomUtil().shouldEventTrigger(chanceEachNeuronHasOfConnectingItsAxon)) {
                for (int i = 0; i < numAxonConnectionsPerNeuron; i++) {
                    Neuron destNeuron = destCluster.getRandomNeuron();
                    int synapticIndex = ((NeuronWithDendriticTree) destNeuron).attachSynapse();
                    sourceNeuron.createOutgoingAxonConnection((NeuronWithDendriticTree) destNeuron, synapticIndex);
                }
            }
        }
    }
}
