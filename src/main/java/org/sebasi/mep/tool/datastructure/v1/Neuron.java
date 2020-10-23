package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.HelperHolder;

public abstract class Neuron extends HelperHolder {

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
            axon.fire();
            resetAccumulator();
        }
    }

    public abstract void createOutgoingAxonConnection(
            NeuronWithDendriticTree destinationNeuron,
            int synapticIndex);
}
