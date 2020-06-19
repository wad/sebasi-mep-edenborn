package org.sebasi.mep.edenborn.mind.internal;

import java.util.HashMap;
import java.util.Map;

public class NeuronGroup {
    private final NeuronRegion neuronRegion;
    private final long groupId;
    private long nextClusterId = 1L;
    private Map<Long, NeuronCluster> clusters = new HashMap<>();

    public NeuronGroup(
            NeuronRegion neuronRegion,
            long groupId) {
        this.neuronRegion = neuronRegion;
        this.groupId = groupId;
    }

    public NeuronRegion getNeuronRegion() {
        return neuronRegion;
    }

    public long getGroupId() {
        return groupId;
    }

    public NeuronCluster createCluster() {
        NeuronCluster neuronCluster = new NeuronCluster(this, nextClusterId++);
        clusters.put(neuronCluster.getClusterId(), neuronCluster);
        return neuronCluster;
    }
}
