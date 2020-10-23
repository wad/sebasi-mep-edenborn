package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

public abstract class NeuronWithDendriticTree extends Neuron {

    protected long accumulator;

    public NeuronWithDendriticTree(Helper helper) {
        this(helper, null);
    }

    public NeuronWithDendriticTree(
            Helper helper,
            String label) {
        super(helper, label);
        resetAccumulator();
    }

    protected abstract DendriticTree getDendriticTree();

    @Override
    protected void initializeAxon() {
        axon = new AxonForConnectingToNeurons();
    }

    @Override
    public boolean fireIfReady() {
        if (accumulator < getDendriticTree().computeFiringThreshold()) {
            return false;
        }

        axon.fire();
        resetAccumulator();
        return true;
    }

    public void attachSynapse(int synapticIndex) {
        getDendriticTree().attachSynapse(synapticIndex);
    }

    public void detachSynapse(int synapticIndex) {
        getDendriticTree().detachSynapse(synapticIndex);
    }

    abstract public void receiveInput(int synapticIndex);

    protected boolean isSynapseConnected(int synapticIndex) {
        return getDendriticTree().doSynapticStateBitsIndicateConnected(
                getDendriticTree().getSynapticStateBits(synapticIndex));
    }

    void resetAccumulator() {
        accumulator = 0L;
    }
}
