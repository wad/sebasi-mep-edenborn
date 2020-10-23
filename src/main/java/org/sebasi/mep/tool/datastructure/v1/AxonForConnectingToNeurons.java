package org.sebasi.mep.tool.datastructure.v1;

import java.util.List;

public class AxonForConnectingToNeurons extends Axon {

    List<Synapse> axonConnections;

    @Override
    public void fire() {
        for (Synapse outputConnection : axonConnections) {
            outputConnection.trigger();
        }
    }
}
