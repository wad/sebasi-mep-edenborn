package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.HelperHolder;

import java.util.List;

public class Axon {
    List<Synapse> axonConnections;

    public void fire() {
        for (Synapse outputConnection : axonConnections) {
            outputConnection.trigger();
        }
    }
}
