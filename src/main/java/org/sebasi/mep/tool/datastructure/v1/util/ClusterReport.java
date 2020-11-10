package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.ClusterOfNeurons;
import org.sebasi.mep.tool.datastructure.v1.NeuralType;
import org.sebasi.mep.tool.datastructure.v1.Neuron;
import org.sebasi.mep.tool.datastructure.v1.SynapseOnDendrite;

import java.util.*;

public class ClusterReport {

    ClusterOfNeurons clusterOfNeurons;
    String clusterLabel;
    int numNeuronsThatHaveEverBeenAdded;
    int numActualNeurons = 0;
    Histogram numConnectionsOnAxon = new Histogram();
    Histogram numConnectionsOnDendriticTrees = new Histogram();
    Map<NeuralType, Integer> countByNeuralType = new HashMap<>();
    Histogram countsOfSynapticStrengthsAcrossAllNeurons = new Histogram();

    public ClusterReport(ClusterOfNeurons clusterOfNeurons) {
        this.clusterOfNeurons = clusterOfNeurons;
        make();
    }

    void make() {
        clusterLabel = clusterOfNeurons.getLabel();
        numNeuronsThatHaveEverBeenAdded = clusterOfNeurons.getGreatestNeuronIndex() + 1;

        for (int neuronIndex = 0; neuronIndex <= clusterOfNeurons.getGreatestNeuronIndex(); neuronIndex++) {
            Neuron neuron = clusterOfNeurons.getNeuron(neuronIndex);
            if (neuron != null) {
                numActualNeurons++;
                numConnectionsOnAxon.addDataPoint(neuron.getNumConnectionsOnAxon());
                numConnectionsOnDendriticTrees.addDataPoint(neuron.getNumConnectionsOnDendriticTree());
                incrementNeuralTypeCount(neuron.getNeuralType());

                for (SynapseOnDendrite synapseOnDendrite : neuron.getSynapsesOnDendriticTree()) {
                    int synapticStrengthValue = synapseOnDendrite.getSynapticStrengthValue();
                    countsOfSynapticStrengthsAcrossAllNeurons.addDataPoint(synapticStrengthValue);
                }
            }
        }
    }

    void incrementNeuralTypeCount(NeuralType neuralType) {
        int count = countByNeuralType.getOrDefault(neuralType, 0);
        countByNeuralType.put(neuralType, count + 1);
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

        appendCountsByNeuralType(indentationPrefix, newline, builder);
        appendCountsOfSynapticStrengthsAcrossAllNeurons(indentationPrefix, newline, builder);

        builder.append(indentationPrefix)
                .append("Num connections on Axons: ")
                .append(newline);
        numConnectionsOnAxon.makeReport(builder, indentationPrefix + "  ");

        builder.append(indentationPrefix)
                .append("Num connections on dendritic trees: ")
                .append(newline);
        numConnectionsOnDendriticTrees.makeReport(builder, indentationPrefix + "  ");
    }

    void appendCountsByNeuralType(
            String indentationPrefix,
            String newline,
            StringBuilder builder) {
        builder.append(indentationPrefix)
                .append("Counts by NeuralType: ")
                .append(newline);

        List<NeuralType> neuralTypes = new ArrayList<>(countByNeuralType.keySet());
        Collections.sort(neuralTypes);
        for (NeuralType neuralType : neuralTypes) {
            builder.append(indentationPrefix)
                    .append("  ")
                    .append(neuralType.getNeuralTypeName())
                    .append(": ")
                    .append(countByNeuralType.get(neuralType))
                    .append(newline);
        }
    }

    void appendCountsOfSynapticStrengthsAcrossAllNeurons(
            String indentationPrefix,
            String newline,
            StringBuilder builder) {
        builder.append(indentationPrefix)
                .append("Counts of synaptic strengths across all neurons: ")
                .append(newline);
        countsOfSynapticStrengthsAcrossAllNeurons.makeReport(builder, indentationPrefix);
    }
}
