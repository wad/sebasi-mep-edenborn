package org.sebasi.mep.edenborn.mind.internal;

import java.util.HashMap;
import java.util.Map;

public class Brain {
    long nextRegionId = 1L;
    Map<Long, NeuronRegion> regions = new HashMap<>();
    Map<NeuronAddress, Neuron> neurons = new HashMap<>();

    public static void main(String... args) {
        Brain brain = new Brain();
        NeuronRegion region = brain.createRegion();
        NeuronGroup group = region.createGroup();
        NeuronCluster cluster = group.createCluster();
        cluster.createNeuron();
        cluster.createNeuron();
        cluster.createNeuron();
        cluster.createNeuron();
        cluster.createNeuron();

        brain.showNeuronAddresses();
    }

    public Brain() {
    }

    NeuronRegion createRegion() {
        NeuronRegion neuronRegion = new NeuronRegion(this, nextRegionId++);
        regions.put(neuronRegion.getRegionId(), neuronRegion);
        return neuronRegion;
    }

    public void registerNeuron(Neuron neuron) {
        neurons.put(neuron.getNeuronAddress(), neuron);
    }

    void showNeuronAddresses() {
        for (Map.Entry<NeuronAddress, Neuron> entry : neurons.entrySet()) {
            NeuronAddress neuronAddress = entry.getValue().getNeuronAddress();
            System.out.println(neuronAddress);
        }
    }
}
