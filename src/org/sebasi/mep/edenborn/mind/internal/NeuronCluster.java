package org.sebasi.mep.edenborn.mind.internal;

import java.util.HashMap;
import java.util.Map;

public class NeuronCluster {
    private final NeuronGroup neuronGroup;
    private final long clusterId;
    private String label;
    private long nextNeuronId = InitialSettings.INITIAL_NEURON_ID;
    private Map<Long, Neuron> neuronsById = new HashMap<>();

    public NeuronCluster(
            NeuronGroup neuronGroup,
            long clusterId) {
        this.neuronGroup = neuronGroup;
        this.clusterId = clusterId;
        if (clusterId == InitialSettings.INITIAL_CLUSTER_ID) {
            setLabel(InitialSettings.INITIAL_CLUSTER_LABEL);
        }
    }

    public NeuronGroup getNeuronGroup() {
        return neuronGroup;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (getNeuronGroup().getClusterByLabel(label) != null) {
            throw new RuntimeException("Cluster label already used: " + label);
        }
        getNeuronGroup().assignLabelToCluster(label, this);
        this.label = label;
    }

    public long getClusterId() {
        return clusterId;
    }

    public Neuron createNeuron(NeuronType neuronType) {
        Neuron neuron = neuronType.createNeuron(this, nextNeuronId++);
        neuronsById.put(neuron.getNeuronId(), neuron);
        getNeuronGroup().getNeuronRegion().getBrain().registerNeuron(neuron);
        return neuron;
    }

    public long getNumNeurons() {
        return neuronsById.size();
    }
}
