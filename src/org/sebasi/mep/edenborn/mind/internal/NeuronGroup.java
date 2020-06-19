package org.sebasi.mep.edenborn.mind.internal;

import java.util.HashMap;
import java.util.Map;

public class NeuronGroup {
    private final NeuronRegion neuronRegion;
    private final long groupId;
    private String label;
    private long nextClusterId = InitialSettings.INITIAL_CLUSTER_ID;
    private final Map<Long, NeuronCluster> clustersById = new HashMap<>();
    private final Map<String, NeuronCluster> clustersByLabel = new HashMap<>();

    public NeuronGroup(
            NeuronRegion neuronRegion,
            long groupId) {
        this.neuronRegion = neuronRegion;
        this.groupId = groupId;
        if (groupId == InitialSettings.INITIAL_GROUP_ID) {
            setLabel(InitialSettings.INITIAL_GROUP_LABEL);
        }
    }

    public NeuronRegion getNeuronRegion() {
        return neuronRegion;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (getNeuronRegion().getGroupByLabel(label) != null) {
            throw new RuntimeException("Group label already used: " + label);
        }
        getNeuronRegion().assignLabelToGroup(label, this);
        this.label = label;
    }

    public long getGroupId() {
        return groupId;
    }

    public NeuronCluster getClusterById(long clusterId) {
        return clustersById.get(clusterId);
    }

    public NeuronCluster getClusterByLabel(String label) {
        return clustersByLabel.get(label);
    }

    public NeuronCluster createCluster() {
        NeuronCluster neuronCluster = new NeuronCluster(this, nextClusterId++);
        clustersById.put(neuronCluster.getClusterId(), neuronCluster);
        return neuronCluster;
    }

    public long getNumNeurons() {
        long count = 0L;
        for (NeuronCluster cluster : clustersById.values()) {
            count += cluster.getNumNeurons();
        }
        return count;
    }

    public void assignLabelToCluster(
            String label,
            NeuronCluster neuronCluster) {
        clustersByLabel.put(label, neuronCluster);
    }
}
