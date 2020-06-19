package org.sebasi.mep.edenborn.mind.internal;

import java.util.Objects;

public class NeuronAddress implements Comparable<NeuronAddress> {
    private final long regionId;
    private final long groupId;
    private final long clusterId;
    private final long cellId;

    public NeuronAddress(
            long regionId,
            long groupId,
            long clusterId,
            long cellId) {
        this.regionId = regionId;
        this.groupId = groupId;
        this.clusterId = clusterId;
        this.cellId = cellId;
    }

    public long getRegionId() {
        return regionId;
    }

    public long getGroupId() {
        return groupId;
    }

    public long getClusterId() {
        return clusterId;
    }

    public long getCellId() {
        return cellId;
    }

    @Override
    public String toString() {
        return regionId + "." + groupId + "." + clusterId + "." + cellId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeuronAddress that = (NeuronAddress) o;
        return regionId == that.regionId &&
                groupId == that.groupId &&
                clusterId == that.clusterId &&
                cellId == that.cellId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, groupId, clusterId, cellId);
    }

    @Override
    public int compareTo(NeuronAddress o) {
        if (regionId == o.getRegionId()
                && groupId == o.getGroupId()
                && clusterId == o.getClusterId()
                && cellId == o.getCellId()) {
            return 0;
        }
        if (regionId == o.getRegionId()
                && groupId == o.getGroupId()
                && clusterId == o.getClusterId()) {
            return Long.compare(cellId, o.getCellId());
        }
        if (regionId == o.getRegionId()
                && groupId == o.getGroupId()) {
            return Long.compare(clusterId, o.getClusterId());
        }
        if (regionId == o.getRegionId()) {
            return Long.compare(groupId, o.getGroupId());
        }
        return Long.compare(regionId, o.getRegionId());
    }
}
