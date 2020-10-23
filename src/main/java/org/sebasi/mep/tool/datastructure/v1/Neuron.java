package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.HelperHolder;

public abstract class Neuron extends HelperHolder {

    // An optional name for this neuron, so it can be looked-up later in clusters.
    String label;

    Axon axon;

    public Neuron(Helper helper) {
        this(helper, null);
    }

    public Neuron(
            Helper helper,
            String label) {
        super(helper);
        this.label = label;
        initializeAxon();
    }

    public String getLabel() {
        return label;
    }

    protected abstract void initializeAxon();

    // return true if it fired
    public abstract boolean fireIfReady();
}
