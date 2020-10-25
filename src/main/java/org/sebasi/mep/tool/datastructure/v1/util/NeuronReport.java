package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.Neuron;

public class NeuronReport {

    String typeName;
    int numSynapsesOnDendriticTree = 0;

    public NeuronReport(Neuron neuron) {
        typeName = neuron.getClass().getSimpleName();
    }

    public void setNumSynapsesOnDendriticTree(int numSynapsesOnDendriticTree) {
        this.numSynapsesOnDendriticTree = numSynapsesOnDendriticTree;
    }

    public void appendReport(StringBuilder builder) {
        builder.append("Sample neuron type: ").append(typeName);
        builder.append("Num synapses on dendritic tree: ").append(numSynapsesOnDendriticTree);
    }
}
