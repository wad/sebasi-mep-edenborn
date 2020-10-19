package org.sebasi.mep.tool.datastructure.v1;

public abstract class Neuron {

    // An optional name for this neuron, so it can be looked-up later.
    String label;

    // as dendrites receive inputs, their strengths are added to this value.
    protected long accumulator;

    protected Dendrites dendrites;

    Helper helper;

    Axon axon;

    public Neuron(Helper helper) {
        this(helper, null);
    }

    public Neuron(
            Helper helper,
            String label) {
        this.helper = helper;
        this.label = label;
        axon = new Axon();
        resetAccumulator();
    }

    public String getLabel() {
        return label;
    }

    // return true if it fired
    public boolean fireIfReady() {
        if (accumulator < dendrites.computeFiringThreshold()) {
            return false;
        }

        axon.fire();
        resetAccumulator();
        return true;
    }

    public void attachPort(int port) {
        dendrites.attachPort(port);
    }

    public void detachPort(int port) {
        dendrites.detachPort(port);
    }

    abstract public void receiveInput(int port);

    protected boolean isPortConnected(int port) {
        return dendrites.doPortInfoBitsIndicateItIsConnected(
                dendrites.getPortInfoBits(port));
    }

    void resetAccumulator() {
        accumulator = 0L;
    }
}
