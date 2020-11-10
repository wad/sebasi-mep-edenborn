package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.NeuronReport;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

import java.util.Collection;

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
    protected int getNumSynapticConnections() {
        return getDendriticTree().getNumConnectedSynapses();
    }

    @Override
    protected void initializeAxon() {
        axon = new AxonForConnectingToNeurons();
    }

    // return the synaptic index that it was attached to.
    public abstract int attachSynapse();

    public void detachSynapse(int synapticIndex) {
        getDendriticTree().detachSynapse(synapticIndex);
    }

    abstract public void receiveInput(int synapticIndex);

    public abstract boolean isSynapseConnected(int synapticIndex);

    @Override
    public int getNumConnectionsOnDendriticTree() {
        return getDendriticTree().getNumConnectedSynapses();
    }

    @Override
    public Collection<SynapseOnDendrite> getSynapsesOnDendriticTree() {
        return getDendriticTree().getSynapses();
    }

    @Override
    public NeuronReport getInfoForReport() {
        NeuronReport neuronReport = new NeuronReport(this);
        neuronReport.setNumSynapsesOnDendriticTree(getNumConnectionsOnDendriticTree());
        return neuronReport;
    }

    public SynapseOnDendrite getSynapse(int synapticIndex) {
        return getDendriticTree().getSynapse(synapticIndex);
    }
}
