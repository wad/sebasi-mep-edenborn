package org.sebasi.mep.edenborn.mind.internal;

import java.util.HashMap;
import java.util.Map;

public class NeuronRegion {
    private final Brain brain;
    private Map<Long, NeuronGroup> groupsById = new HashMap<>();
    private final Map<String, NeuronGroup> groupsByLabel = new HashMap<>();
    private final long regionId;
    private String label;
    private long nextGroupId = InitialSettings.INITIAL_GROUP_ID;

    public NeuronRegion(
            Brain brain,
            long regionId) {
        this.brain = brain;
        this.regionId = regionId;
        if (regionId == InitialSettings.INITIAL_REGION_ID) {
            setLabel(InitialSettings.INITIAL_REGION_LABEL);
        }
    }

    public Brain getBrain() {
        return brain;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (getBrain().getRegionByLabel(label) != null) {
            throw new RuntimeException("Region label already used: " + label);
        }
        getBrain().assignLabelToRegion(label, this);
        this.label = label;
    }

    public NeuronGroup getGroupById(long groupId) {
        return groupsById.get(groupId);
    }

    public NeuronGroup getGroupByLabel(String label) {
        return groupsByLabel.get(label);
    }

    public long getRegionId() {
        return regionId;
    }

    public NeuronGroup createGroup() {
        NeuronGroup neuronGroup = new NeuronGroup(this, nextGroupId++);
        groupsById.put(neuronGroup.getGroupId(), neuronGroup);
        return neuronGroup;
    }

    public long getNumNeurons() {
        long count = 0L;
        for (NeuronGroup group : groupsById.values()) {
            count += group.getNumNeurons();
        }
        return count;
    }

    void assignLabelToGroup(
            String label,
            NeuronGroup neuronGroup) {
        groupsByLabel.put(label, neuronGroup);
    }
}
