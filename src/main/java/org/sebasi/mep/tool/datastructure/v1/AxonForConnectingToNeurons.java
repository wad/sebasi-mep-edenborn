package org.sebasi.mep.tool.datastructure.v1;

import java.util.ArrayList;
import java.util.List;

public class AxonForConnectingToNeurons extends Axon {

    List<SynapseOnAxon> axonConnections;

    public AxonForConnectingToNeurons() {
        axonConnections = new ArrayList<>();
    }

    @Override
    public void fire() {
        for (SynapseOnAxon outputConnection : axonConnections) {
            outputConnection.trigger();
        }
    }

    public void createOutgoingConnection(
            NeuronWithDendriticTree destinationNeuron,
            int synapticIndex) {
        axonConnections.add(new SynapseOnAxon(destinationNeuron, synapticIndex));
        destinationNeuron.attachSynapse(synapticIndex);
    }

    @Override
    public int getNumConnections() {
        return axonConnections.size();
    }
}
