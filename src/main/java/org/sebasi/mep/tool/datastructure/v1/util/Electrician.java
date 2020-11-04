package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.Neuron;
import org.sebasi.mep.tool.datastructure.v1.highperf.NeuronWithDendriticTreeWithHighPerf;

// He wires stuff up!
public class Electrician extends HelperHolder {

    public Electrician(Helper helper) {
        super(helper);
    }

    public void wireRandomly(
            ClusterOfNeurons source,
            Chance chanceEachNeuronInDestinationAttemptsToConnect,
            Chance chanceEachSynapseInDestinationDendriticTreeAttemptsToConnect,
            ClusterOfNeurons dest) {
        for (int destNeuronIndex = 0; destNeuronIndex <= dest.getGreatestNeuronIndex(); destNeuronIndex++) {
            if (getHelper().getRandomUtil().shouldEventTrigger(chanceEachNeuronInDestinationAttemptsToConnect)) {
                NeuronWithDendriticTreeWithHighPerf destNeuron = (NeuronWithDendriticTreeWithHighPerf) dest.getNeuron(destNeuronIndex);
                if (destNeuron != null) {
                    for (int synapticIndex = 0; synapticIndex < destNeuron.getDendriticTreeSize().getNumSynapses(); synapticIndex++) {
                        if (getHelper().getRandomUtil().shouldEventTrigger(chanceEachSynapseInDestinationDendriticTreeAttemptsToConnect)) {
                            Neuron sourceNeuron = chooseRandomNeuron(source);
                            if (sourceNeuron != null) {
                                if (!destNeuron.isSynapseConnected(synapticIndex)) {
                                    sourceNeuron.createOutgoingAxonConnection(destNeuron, synapticIndex);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // This might return null
    Neuron chooseRandomNeuron(ClusterOfNeurons cluster) {
        int greatestNeuronIndex = cluster.getGreatestNeuronIndex();
        int randomNeuronIndex = getHelper().getRandomUtil().getRandomNumber(greatestNeuronIndex + 1);
        return cluster.getNeuron(randomNeuronIndex);
    }
}
