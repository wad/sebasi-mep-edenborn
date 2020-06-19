package org.sebasi.mep.edenborn.mind.internal;

import java.util.HashMap;
import java.util.Map;

public class Brain {
    private final Map<NeuronAddress, Neuron> neuronsByAddress = new HashMap<>();

    private final Map<Long, NeuronRegion> regionsById = new HashMap<>();
    private final Map<String, NeuronRegion> regionsByLabel = new HashMap<>();

    private long nextRegionId = InitialSettings.INITIAL_REGION_ID;

    public Brain() {
        NeuronRegion region = createRegion();
        NeuronGroup group = region.createGroup();
        group.createCluster();
    }

    NeuronRegion createRegion() {
        NeuronRegion region = new NeuronRegion(this, nextRegionId++);
        regionsById.put(region.getRegionId(), region);
        return region;
    }

    void registerNeuron(Neuron neuron) {
        neuronsByAddress.put(neuron.getNeuronAddress(), neuron);
    }

    void showNeuronAddresses() {
        for (Map.Entry<NeuronAddress, Neuron> entry : neuronsByAddress.entrySet()) {
            NeuronAddress neuronAddress = entry.getValue().getNeuronAddress();
            System.out.println(neuronAddress);
        }
    }

    void assignLabelToRegion(
            String label,
            NeuronRegion neuronRegion) {
        regionsByLabel.put(label, neuronRegion);
    }

    public NeuronRegion getRegionByLabel(String label) {
        return regionsByLabel.get(label);
    }

    public long countAllNeurons() {
        long count = 0L;
        for (NeuronRegion region : regionsById.values()) {
            count += region.getNumNeurons();
        }
        return count;
    }
}
