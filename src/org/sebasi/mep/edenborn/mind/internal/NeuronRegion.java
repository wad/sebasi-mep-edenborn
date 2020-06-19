package org.sebasi.mep.edenborn.mind.internal;

import java.util.HashMap;
import java.util.Map;

public class NeuronRegion {
    private Brain brain;
    private final long regionId;
    private long nextGroupId = 1L;
    private Map<Long, NeuronGroup> groups = new HashMap<>();

    public NeuronRegion(
            Brain brain,
            long regionId) {
        this.brain = brain;
        this.regionId = regionId;
    }

    public Brain getBrain() {
        return brain;
    }

    public long getRegionId() {
        return regionId;
    }

    public NeuronGroup createGroup() {
        NeuronGroup neuronGroup = new NeuronGroup(this, nextGroupId++);
        groups.put(neuronGroup.getGroupId(), neuronGroup);
        return neuronGroup;
    }
}
