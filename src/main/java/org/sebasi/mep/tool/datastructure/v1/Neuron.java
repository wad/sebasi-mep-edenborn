package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.HelperHolder;

public abstract class Neuron extends HelperHolder implements Ticker {

    String label;
    Axon axon;
    long accumulator;
    FiringComputer firingComputer;

    public Neuron(
            FiringComputer firingComputer,
            Helper helper) {
        this(
                firingComputer,
                helper,
                null);
    }

    public Neuron(
            FiringComputer firingComputer,
            Helper helper,
            String label) {
        super(helper);
        this.firingComputer = firingComputer;
        this.label = label;
        resetAccumulator();
        initializeAxon();
        registerTicker();
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
    public void registerTicker() {
        getHelper().getTickers().registerTicker(this);
    }

    @Override
    public String toString() {
        if (label == null) {
            return super.toString();
        } else {
            return getClass().getSimpleName() + " " + label;
        }
    }
}
