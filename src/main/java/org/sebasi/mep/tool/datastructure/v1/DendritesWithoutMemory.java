package org.sebasi.mep.tool.datastructure.v1;

public class DendritesWithoutMemory extends Dendrites {

    // One bit per port, so divide the number of ports by 8.
    static final int NUM_BYTES_NEEDED_TO_TO_HOLD_PORT_INFO = NUM_DENDRITE_INPUTS >>> 3;

    public DendritesWithoutMemory() {
        super();
    }

    @Override
    public long computeFiringThreshold() {
        // trigger when the total number of triggered inputs is the same as the number of connected ports
        return numConnectedPorts;
    }

    @Override
    public void attachPort(int port) {
        int infoBitIndex = convertPortNumberToInfoBitIndex(port);
        byte infoBits = inputPortInfo[infoBitIndex];
        inputPortInfo[infoBitIndex] = (byte) (infoBits | getMaskForSetting(port));
    }

    @Override
    public void detachPort(int port) {
        int bitInfoIndex = convertPortNumberToInfoBitIndex(port);
        byte infoBits = inputPortInfo[bitInfoIndex];
        byte zeroMask = (byte) (~getMaskForSetting(port));
        inputPortInfo[bitInfoIndex] = (byte) (infoBits & zeroMask);
    }

    @Override
    protected void initializePortInfo() {
        // initialized to zeroes, which means none of the ports are connected.
        inputPortInfo = new byte[NUM_BYTES_NEEDED_TO_TO_HOLD_PORT_INFO];
    }

    // just returns one bit
    @Override
    int getPortInfoBits(int port) {
        byte infoBits = inputPortInfo[convertPortNumberToInfoBitIndex(port)];
        // 0 --> 1000 0000 --> (>> 7)
        // 1 --> 0100 0000 --> (>> 6)
        // 6 --> 0000 0010 --> (>> 1)
        // 7 --> 0000 0001 --> (>> 0)
        int numTimesToShift = 7 - (port & 0x7);
        return ((infoBits >>> numTimesToShift) & 0x1);
    }

    int convertPortNumberToInfoBitIndex(int port) {
        // eight bits in one byte
        return port >> 3;
    }

    int getMaskForSetting(int port) {
        int numTimesToShift = 7 - (port & 0x7);
        // port 0 --> (<< 7)
        // port 1 --> (<< 6)
        // port 6 --> (<< 1)
        // port 7 --> (<< 0)
        return 1 << numTimesToShift;
    }
}
