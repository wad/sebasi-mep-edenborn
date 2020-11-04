package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public abstract class DendriticTree implements Ticker {

    protected int numConnectedSynapses;

    protected Neuron neuron;

    public DendriticTree(Neuron neuron) {
        this.neuron = neuron;
    }

    public abstract boolean isSynapseAttached(int synapticIndex);

    public int getNumConnectedSynapses() {
        return numConnectedSynapses;
    }

    public abstract void attachSynapse(int synapticIndex);

    public abstract void detachSynapse(int synapticIndex);

    protected abstract void validateConnection(
            boolean expectationIsAlreadyConnected,
            int synapticIndex);

        @Override
    public void tick() {
    }

    @Override
    public void registerTicker(TickPriority tickPriority) {
        neuron.getHelper().getTickers().registerTicker(tickPriority, this);
    }
}
