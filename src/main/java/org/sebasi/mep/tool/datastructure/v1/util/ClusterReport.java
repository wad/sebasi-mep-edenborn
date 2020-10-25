package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.Neuron;

public class ClusterReport {

    String clusterLabel;
    int numNeuronsThatHaveEverBeenAdded;
    int numActualNeurons = 0;
    Histogram numConnectionsOnAxon = new Histogram();
    Histogram numConnectionsOnDendriticTrees = new Histogram();
    NeuronReport sampleNeuronReport;

    public ClusterReport(ClusterOfNeurons clusterOfNeurons) {
        populateData(clusterOfNeurons);
    }

    public void populateData(ClusterOfNeurons clusterOfNeurons) {
        clusterLabel = clusterOfNeurons.getLabel();
        numNeuronsThatHaveEverBeenAdded = clusterOfNeurons.getGreatestNeuronIndex() + 1;

        Neuron sampleNeuron = null;
        for (int neuronIndex = 0; neuronIndex <= clusterOfNeurons.getGreatestNeuronIndex(); neuronIndex++) {
            Neuron neuron = clusterOfNeurons.getNeuron(neuronIndex);
            if (neuron != null) {

                // just grab one on the way by
                if (sampleNeuron == null) {
                    sampleNeuron = neuron;
                }

                numActualNeurons++;
                numConnectionsOnAxon.addDataPoint(neuron.getNumConnectionsOnAxon());
                numConnectionsOnDendriticTrees.addDataPoint(neuron.getNumConnectionsOnDendriticTree());
            }
        }

        if (sampleNeuron != null) {
            sampleNeuronReport = sampleNeuron.getInfoForReport();
        }
    }

    public void appendReport(StringBuilder builder) {
        String indentationPrefix = "  ";
        String newline = "\n";

        builder.append("Report on ")
                .append(this.getClass().getSimpleName())
                .append(" cluster '")
                .append(clusterLabel == null ? "" : clusterLabel)
                .append("'")
                .append(newline);

        builder.append(indentationPrefix)
                .append("Num neurons that were ever added: ")
                .append(numNeuronsThatHaveEverBeenAdded)
                .append(newline);

        builder.append(indentationPrefix)
                .append("Num actual neurons: ")
                .append(numActualNeurons)
                .append(newline);

        builder.append(indentationPrefix)
                .append("Num connections on Axons: ")
                .append(newline);
        numConnectionsOnAxon.makeReport(builder, indentationPrefix + "  ");

        builder.append(indentationPrefix)
                .append("Num connections on dendritic trees: ")
                .append(newline);
        numConnectionsOnDendriticTrees.makeReport(builder, indentationPrefix + "  ");
    }
}
