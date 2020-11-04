package org.sebasi.mep.tool.datastructure.v1;

import java.util.ArrayList;
import java.util.List;

public class AxonForConnectingToNeurons extends Axon {

    List<Synapse> axonConnections;

    public AxonForConnectingToNeurons() {
        axonConnections = new ArrayList<>();
    }

    @Override
    public void fire() {
        for (Synapse outputConnection : axonConnections) {
            outputConnection.trigger();
        }
    }

    public void createOutgoingConnection(
            NeuronWithDendriticTreeWithHighPerf destinationNeuron,
            int synapticIndex) {
        axonConnections.add(new Synapse(destinationNeuron, synapticIndex));
        destinationNeuron.attachSynapse(synapticIndex);
    }

    @Override
    public int getNumConnections() {
        return axonConnections.size();
    }
}
