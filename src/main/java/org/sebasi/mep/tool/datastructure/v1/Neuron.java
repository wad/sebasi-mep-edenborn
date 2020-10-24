package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.HelperHolder;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public abstract class Neuron extends HelperHolder implements Ticker {

    public static final int NEURON_INDEX_IF_NOT_SET = -1;

    // Neurons can have an optional label, and can be looked-up in a cluster by this label.
    String label;

    // Neurons have an index number if they are in a cluster, specific to that cluster.
    int neuronIndex = NEURON_INDEX_IF_NOT_SET;

    Axon axon;
    long accumulator;
    FiringComputer firingComputer;

    public Neuron(
            FiringComputer firingComputer,
            TickPriority tickPriority,
            Helper helper) {
        this(
                firingComputer,
                tickPriority,
                helper,
                null);
    }

    public Neuron(
            FiringComputer firingComputer,
            TickPriority tickPriority,
            Helper helper,
            String label) {
        super(helper);
        this.firingComputer = firingComputer;
        this.label = label;
        resetAccumulator();
        initializeAxon();
        registerTicker(tickPriority);
    }

    public String getLabel() {
        return label;
    }

    protected abstract void initializeAxon();

    void resetAccumulator() {
        accumulator = 0L;
    }

    protected abstract int getMaxNumSynapticConnections();

    protected abstract int getNumSynapticConnections();

    public void fireIfReady() {
        if (FiringComputer.shouldFire(
                firingComputer,
                accumulator,
                getMaxNumSynapticConnections(),
                getNumSynapticConnections())) {

            if (getHelper().shouldShowLogMessages()) {
                getHelper().getMessageDisplay().show("Neuron " + label + " is firing.");
            }

            axon.fire();
            resetAccumulator();
        }
    }

    public abstract void createOutgoingAxonConnection(
            NeuronWithDendriticTree destinationNeuron,
            int synapticIndex);

    @Override
    public void tick() {
        fireIfReady();
    }

    @Override
    public void registerTicker(TickPriority tickPriority) {
        getHelper().getTickers().registerTicker(tickPriority, this);
    }

    @Override
    public String toString() {
        if (label == null) {
            return super.toString();
        } else {
            return getClass().getSimpleName() + " " + label;
        }
    }

    public int getNeuronIndex() {
        return neuronIndex;
    }

    public void setNeuronIndex(int neuronIndex) {
        this.neuronIndex = neuronIndex;
    }
}
