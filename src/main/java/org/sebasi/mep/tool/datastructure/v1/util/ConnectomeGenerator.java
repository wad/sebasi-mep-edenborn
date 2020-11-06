package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.Neuron;
import org.sebasi.mep.tool.datastructure.v1.NeuronWithDendriticTree;

public class ConnectomeGenerator {

    public static void makeRandomConnections(
            ClusterOfNeurons sourceCluster,
            ClusterOfNeurons destCluster,
            Chance chanceEachNeuronInDestConnects) {
        for (Neuron destNeuron : destCluster.getNeurons()) {
            if (destCluster.getHelper().getRandomUtil().shouldEventTrigger(chanceEachNeuronInDestConnects)) {
                Neuron sourceNeuron = sourceCluster.getRandomNeuron();
                int synapticIndex = ((NeuronWithDendriticTree) destNeuron).attachSynapse();
                sourceNeuron.createOutgoingAxonConnection((NeuronWithDendriticTree) destNeuron, synapticIndex);
            }
        }
    }
}
