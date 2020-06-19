package org.sebasi.mep.edenborn.mind.internal;

import java.util.HashMap;
import java.util.Map;

public class NeuronCluster {
    private NeuronGroup neuronGroup;
    private final long clusterId;
    private long nextCellId = 1L;
    private Map<Long, Neuron> neurons = new HashMap<>();

    public NeuronCluster(
            NeuronGroup neuronGroup,
            long clusterId) {
        this.neuronGroup = neuronGroup;
        this.clusterId = clusterId;
    }

    public NeuronGroup getNeuronGroup() {
        return neuronGroup;
    }

    public long getClusterId() {
        return clusterId;
    }

    public Neuron createNeuron() {
        Neuron neuron = new Neuron(this, nextCellId++);
        neurons.put(neuron.getCellId(), neuron);
        getNeuronGroup().getNeuronRegion().getBrain().registerNeuron(neuron);
        return neuron;
    }
}
