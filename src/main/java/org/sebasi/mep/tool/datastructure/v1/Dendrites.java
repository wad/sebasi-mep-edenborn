package org.sebasi.mep.tool.datastructure.v1;

public abstract class Dendrites {

    // 16 bits
    protected static final int NUM_DENDRITE_INPUTS = 65536;

    protected byte[] inputPortInfo;

    int numConnectedPorts = 0;

    public Dendrites() {
        initializePortInfo();
    }

    // If the accumulator firing threshold is reached or exceeded, the neuron fires, sending a signal
    // to all the other neurons, and their ports, that it is connected to.
    public abstract long computeFiringThreshold();

    public abstract void attachPort(int port);

    public abstract void detachPort(int port);

    protected abstract void initializePortInfo();

    abstract byte getPortInfoBits(int port);

    boolean doPortInfoBitsIndicateItIsConnected(byte infoBits) {
        return infoBits != 0x0;
    }
}
