package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.HelperHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cluster extends HelperHolder {

    String label;
    List<Cluster> clusters = null;
    Map<String, Cluster> clustersByLabel = null;

    public Cluster(Helper helper) {
        this(helper, null);
    }

    public Cluster(
            Helper helper,
            String label) {
        super(helper);
        this.label = label;
    }

    private List<Cluster> getClusters() {
        if (clusters == null) {
            clusters = new ArrayList<>();
        }
        return clusters;
    }

    private Map<String, Cluster> getClustersByLabel() {
        if (clustersByLabel == null) {
            clustersByLabel = new HashMap<>();
        }
        return clustersByLabel;
    }

    public void addCluster(Cluster clusterToAdd) {
        getClusters().add(clusterToAdd);
        if (clusterToAdd.getLabel() != null) {
            getClustersByLabel().put(clusterToAdd.getLabel(), clusterToAdd);
        }
    }

    public String getLabel() {
        return label;
    }

    public Cluster lookup(String label) {
        if (clustersByLabel == null) {
            return null;
        }
        return clustersByLabel.get(label);
    }
}
