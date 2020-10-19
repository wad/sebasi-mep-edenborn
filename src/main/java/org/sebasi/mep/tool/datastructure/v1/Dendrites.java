package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.HelperHolder;

public abstract class Dendrites {

    protected byte[] inputPortInfo;
    protected int numConnectedPorts;

    protected Neuron neuron;

    public Dendrites(Neuron neuron) {
        this.neuron = neuron;
        initializePortInfo();
    }

    public boolean isPortAttached(int port) {
        return doPortInfoBitsIndicateItIsConnected(getPortInfoBits(port));
    }

    public int getNumConnectedPorts() {
        return numConnectedPorts;
    }

    // If the accumulator firing threshold is reached or exceeded, the neuron fires, sending a signal
    // to all the other neurons, and their ports, that it is connected to.
    public abstract long computeFiringThreshold();

    public abstract void attachPort(int port);

    public abstract void detachPort(int port);

    protected abstract void initializePortInfo();

    abstract int getPortInfoBits(int port);

    boolean doPortInfoBitsIndicateItIsConnected(int infoBits) {
        return infoBits != 0x0;
    }
}