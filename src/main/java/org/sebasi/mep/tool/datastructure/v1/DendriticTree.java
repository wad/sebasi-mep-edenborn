package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public abstract class DendriticTree implements Ticker {

    protected int numBytesNeededToHoldSynapticStates;
    protected DendriticTreeSize dendriticTreeSize;
    protected byte[] synapticStates;
    protected int numConnectedSynapses;

    protected Neuron neuron;

    public DendriticTree(
            DendriticTreeSize dendriticTreeSize,
            int numBytesNeededToHoldSynapticStates,
            Neuron neuron) {
        this.dendriticTreeSize = dendriticTreeSize;
        this.numBytesNeededToHoldSynapticStates = numBytesNeededToHoldSynapticStates;
        this.neuron = neuron;
        initializeSynapticStates();
    }

    public boolean isSynapseAttached(int synapticIndex) {
        return doSynapticStateBitsIndicateConnected(getSynapticStateBits(synapticIndex));
    }

    public int getNumConnectedSynapses() {
        return numConnectedSynapses;
    }

    public DendriticTreeSize getDendriticTreeSize() {
        return dendriticTreeSize;
    }

    public abstract void attachSynapse(int synapticIndex);

    public abstract void detachSynapse(int synapticIndex);

    protected abstract void initializeSynapticStates();

    abstract int getSynapticStateBits(int synapticIndex);

    boolean doSynapticStateBitsIndicateConnected(int synapticStateBits) {
        return synapticStateBits != 0x0;
    }

    @Override
    public void tick() {
    }

    @Override
    public void registerTicker(TickPriority tickPriority) {
        neuron.getHelper().getTickers().registerTicker(tickPriority, this);
    }

    protected void validateConnection(
            boolean expectationIsAlreadyConnected,
            int synapticIndex) {

        if (neuron.getHelper().getOperationMode().shouldValidateDendriteAttachments()) {

            int naxNumSynapses = dendriticTreeSize.getNumSynapses();
            if (synapticIndex < 0 || synapticIndex >= naxNumSynapses) {
                throw new NeuronConnectionException(
                        neuron.getLabel(),
                        "Specifed synaptic index of " + synapticIndex + " but max is " + naxNumSynapses + ".");
            }

            if (isSynapseAttached(synapticIndex) != expectationIsAlreadyConnected) {
                throw new NeuronConnectionException(
                        neuron.getLabel(),
                        "Unexpected synaptic connection status. Expected = " + expectationIsAlreadyConnected);
            }
        }
    }
}
