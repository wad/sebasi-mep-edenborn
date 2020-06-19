package org.sebasi.mep.edenborn.mind.internal;

public class NeuronStandard extends Neuron {
    public NeuronStandard(
            NeuronCluster neuronCluster,
            long neuronId) {
        super(neuronCluster, neuronId, NeuronType.Standard);
    }
}
