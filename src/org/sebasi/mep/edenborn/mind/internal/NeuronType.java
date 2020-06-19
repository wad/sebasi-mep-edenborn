package org.sebasi.mep.edenborn.mind.internal;

public enum NeuronType {
    Standard;

    public Neuron createNeuron(
            NeuronCluster neuronCluster,
            long neuronId) {
        switch (this) {
            case Standard:
                return new NeuronStandard(neuronCluster, neuronId);
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
