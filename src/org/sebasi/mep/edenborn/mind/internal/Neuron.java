package org.sebasi.mep.edenborn.mind.internal;

public class Neuron {
    private final NeuronAddress neuronAddress;
    private final NeuronCluster neuronCluster;

    private final long cellId;

    public Neuron(
            NeuronCluster neuronCluster,
            long cellId) {
        this.neuronCluster = neuronCluster;
        this.cellId = cellId;
        this.neuronAddress = new NeuronAddress(
                neuronCluster.getNeuronGroup().getNeuronRegion().getRegionId(),
                neuronCluster.getNeuronGroup().getGroupId(),
                neuronCluster.getClusterId(),
                cellId);
    }

    public long getCellId() {
        return cellId;
    }

    public NeuronCluster getNeuronCluster() {
        return neuronCluster;
    }

    public NeuronAddress getNeuronAddress() {
        return neuronAddress;
    }

    @Override
    public String toString() {
        return "Neuron{" +
                "neuronAddress=" + neuronAddress +
                '}';
    }
}
