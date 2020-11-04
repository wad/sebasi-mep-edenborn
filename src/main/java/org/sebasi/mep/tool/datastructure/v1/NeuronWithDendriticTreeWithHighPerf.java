package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.NeuronReport;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public abstract class NeuronWithDendriticTreeWithHighPerf extends Neuron {

    public NeuronWithDendriticTreeWithHighPerf(
            FiringComputer firingComputer,
            TickPriority tickPriority,
            Helper helper) {
        this(
                firingComputer,
                tickPriority,
                helper,
                null);
    }

    public NeuronWithDendriticTreeWithHighPerf(
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

    protected abstract DendriticTreeWithHighPerf getDendriticTree();

    public DendriticTreeSize getDendriticTreeSize() {
        return getDendriticTree().getDendriticTreeSize();
    }

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

    public boolean isSynapseConnected(int synapticIndex) {
        return getDendriticTree().doSynapticStateBitsIndicateConnected(
                getDendriticTree().getSynapticStateBits(synapticIndex));
    }

    @Override
    public void createOutgoingAxonConnection(
            NeuronWithDendriticTreeWithHighPerf destinationNeuron,
            int synapticIndex) {
        ((AxonForConnectingToNeurons) axon).createOutgoingConnection(destinationNeuron, synapticIndex);
    }

    @Override
    public int getNumConnectionsOnDendriticTree() {
        return getDendriticTree().getNumConnectedSynapses();
    }

    @Override
    public NeuronReport getInfoForReport() {
        NeuronReport neuronReport = new NeuronReport(this);
        neuronReport.setNumSynapsesOnDendriticTree(getNumConnectionsOnDendriticTree());
        return neuronReport;
    }
}
