package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public abstract class NeuronWithDendriticTree extends Neuron {

    public NeuronWithDendriticTree(
            FiringComputer firingComputer,
            TickPriority tickPriority,
            Helper helper) {
        this(
                firingComputer,
                tickPriority,
                helper,
                null);
    }

    public NeuronWithDendriticTree(
            FiringComputer firingComputer,
            TickPriority tickPriority,
            Helper helper,
            String label) {
        super(
                firingComputer,
                tickPriority,
                helper,
                label);
    }

    protected abstract DendriticTree getDendriticTree();

    @Override
    protected int getMaxNumSynapticConnections() {
        return getDendriticTree().dendriticTreeSize.getNumSynapses();
    }

    @Override
    protected int getNumSynapticConnections() {
        return getDendriticTree().getNumConnectedSynapses();
    }

    @Override
    protected void initializeAxon() {
        axon = new AxonForConnectingToNeurons();
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

    @Override
    public void createOutgoingAxonConnection(
            NeuronWithDendriticTree destinationNeuron,
            int synapticIndex) {
        ((AxonForConnectingToNeurons) axon).createOutgoingConnection(destinationNeuron, synapticIndex);
    }
}
