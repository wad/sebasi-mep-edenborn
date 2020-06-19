package org.sebasi.mep.edenborn.mind.internal;

public abstract class Neuron {

    private final long neuronId;
    private final NeuronAddress neuronAddress;
    private final NeuronCluster neuronCluster;
    private final NeuronType neuronType;

    public Neuron(
            NeuronCluster neuronCluster,
            long neuronId,
            NeuronType neuronType) {
        this.neuronCluster = neuronCluster;
        this.neuronId = neuronId;
        this.neuronType = neuronType;
        this.neuronAddress = new NeuronAddress(
                neuronCluster.getNeuronGroup().getNeuronRegion().getRegionId(),
                neuronCluster.getNeuronGroup().getGroupId(),
                neuronCluster.getClusterId(),
                neuronId);
    }

    public long getNeuronId() {
        return neuronId;
    }

    public NeuronCluster getNeuronCluster() {
        return neuronCluster;
    }

    public NeuronType getNeuronType() {
        return neuronType;
    }

    public NeuronAddress getNeuronAddress() {
        return neuronAddress;
    }

    @Override
    public String toString() {
        return "Neuron{" +
                "neuronId=" + neuronId +
                ", neuronAddress=" + neuronAddress +
                ", neuronType=" + neuronType +
                '}';
    }
}
