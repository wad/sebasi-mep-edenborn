package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;

public class ClusterReport {

    String clusterLabel;
    int numNeuronsThatHaveEverBeenAdded;
    int actualNumNeurons = 0;

    // todo: in progress

    public ClusterReport(ClusterOfNeurons clusterOfNeurons) {
        clusterLabel = clusterOfNeurons.getLabel();
        numNeuronsThatHaveEverBeenAdded = clusterOfNeurons.getGreatestNeuronIndex() + 1;
        for (int neuronIndex = 0; neuronIndex <= clusterOfNeurons.getGreatestNeuronIndex(); neuronIndex++) {
            if (clusterOfNeurons.getNeuron(neuronIndex) != null) {
                actualNumNeurons++;
            }
        }
    }

    @Override
    public String toString() {
        return "";
    }
}
