package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;

public abstract class DendriticTreeWithHighPerf extends DendriticTree {

    protected int numBytesNeededToHoldSynapticStates;
    protected DendriticTreeSize dendriticTreeSize;
    protected byte[] synapticStates;

    public DendriticTreeWithHighPerf(
            DendriticTreeSize dendriticTreeSize,
            int numBytesNeededToHoldSynapticStates,
            Neuron neuron) {
        super(neuron);
        this.dendriticTreeSize = dendriticTreeSize;
        this.numBytesNeededToHoldSynapticStates = numBytesNeededToHoldSynapticStates;
        initializeSynapticStates();
    }

    @Override
    public boolean isSynapseAttached(int synapticIndex) {
        return doSynapticStateBitsIndicateConnected(getSynapticStateBits(synapticIndex));
    }

    public DendriticTreeSize getDendriticTreeSize() {
        return dendriticTreeSize;
    }

    protected abstract void initializeSynapticStates();

    abstract int getSynapticStateBits(int synapticIndex);

    boolean doSynapticStateBitsIndicateConnected(int synapticStateBits) {
        return synapticStateBits != 0x0;
    }

    @Override
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
