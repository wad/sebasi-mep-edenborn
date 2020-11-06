package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public class NeuronWithDendriticTreeStandard extends NeuronWithDendriticTree {

    DendriticTreeStandard dendriticTreeStandard;

    public NeuronWithDendriticTreeStandard(
            FiringComputer firingComputer,
            TickPriority tickPriority,
            Helper helper) {
        this(firingComputer, tickPriority, helper, null);
    }

    public NeuronWithDendriticTreeStandard(
            FiringComputer firingComputer,
            TickPriority tickPriority,
            Helper helper,
            String label) {
        super(firingComputer, tickPriority, helper, label);
        this.dendriticTreeStandard = new DendriticTreeStandard(this);
    }

    @Override
    public int attachSynapse() {
        return dendriticTreeStandard.attachSynapse();
    }

    @Override
    protected DendriticTree getDendriticTree() {
        return dendriticTreeStandard;
    }

    @Override
    public void receiveInput(int synapticIndex) {
        accumulate(dendriticTreeStandard.lookupSynapseStrength(synapticIndex));
    }

    @Override
    public boolean isSynapseConnected(int synapticIndex) {
        return dendriticTreeStandard.isSynapseAttached(synapticIndex);
    }

    @Override
    public void createOutgoingAxonConnection(
            NeuronWithDendriticTree destinationNeuron,
            int synapticIndex) {
        destinationNeuron.attachSynapse(synapticIndex);
    }

    @Override
    protected int getMaxNumSynapticConnections() {
        return Integer.MAX_VALUE;
    }
}
