package org.sebasi.mep.tool.datastructure.v1;

public class DendritesWithMemory extends Dendrites {

    // 16 bits
    static final int NUM_DENDRITE_INPUTS = 65536;

    // 4 bits per port, so we need half as many bytes as ports to hold the strength bits
    static final int NUM_BYTES_NEEDED_TO_TO_HOLD_PORT_INFO = NUM_DENDRITE_INPUTS >> 1;

    // Memory is achieved through a neuron persisting how sensitive its input ports are.
    // Four bits indicate the port's strength, which is between -7 through +7.
    //  X -> 0000 == 0x0 == special value, indicating the port is not connected
    // -7 -> 0001 == 0x1
    // -6 -> 0010 == 0x2
    // -5 -> 0011 == 0x3
    // -4 -> 0100 == 0x4
    // -3 -> 0101 == 0x5
    // -2 -> 0110 == 0x6
    // -1 -> 0111 == 0x7
    //  0 -> 1000 == 0x8
    // +1 -> 1001 == 0x9
    // +2 -> 1010 == 0xA
    // +3 -> 1011 == 0xB
    // +4 -> 1100 == 0xC
    // +5 -> 1101 == 0xD
    // +6 -> 1110 == 0xE
    // +7 -> 1111 == 0xF

    // The default starting port strength is +2 (0xA)
    static final int PORT_STRENGTH_DEFAULT_VALUE = 2;
    static final int PORT_STRENGTH_BITS_CORRESPONDING_TO_NEUTRAL = 0x8;

    @Override
    protected void initializePortInfo() {
        // initialized to zeroes, which means none of the ports are connected.
        inputPortInfo = new byte[NUM_BYTES_NEEDED_TO_TO_HOLD_PORT_INFO];
    }

    @Override
    public long computeFiringThreshold() {
        return numConnectedPorts * PORT_STRENGTH_DEFAULT_VALUE;
    }

    @Override
    byte getPortInfoBits(int port) {
        byte infoBits = inputPortInfo[port >> 1];
        boolean isPortIndexEven = (port & 1) == 0;
        if (isPortIndexEven) {
            return (byte) (infoBits >> 4);
        } else {
            return (byte) (infoBits & 0x0F);
        }
    }

    @Override
    boolean doPortInfoBitsIndicateItIsConnected(byte infoBits) {
        // this considers only the four bits.
        return infoBits != 0;
    }

    int convertInfoBitsToStrength(byte infoBits) {
        return infoBits - PORT_STRENGTH_BITS_CORRESPONDING_TO_NEUTRAL;
    }

    private byte convertStrengthToInfoBits(int strength) {
        if (strength > 7) {
            strength = 7;
        } else if (strength < -7) {
            strength = -7;
        }
        return (byte) (strength + PORT_STRENGTH_BITS_CORRESPONDING_TO_NEUTRAL);
    }

    int lookupPortStrength(int port) {
        byte infoBits = getPortInfoBits(port);
        if (!doPortInfoBitsIndicateItIsConnected(infoBits)) {
            throw new RuntimeException("Bug: port not connected.");
        }

        return convertInfoBitsToStrength(infoBits);
    }

    @Override
    public void attachPort(int port) {
        attachPort(port, PORT_STRENGTH_DEFAULT_VALUE);
    }

    public void attachPort(
            int port,
            int strength) {
        int index = convertPortToBitInfoIndex(port);
        byte infoBitsForBoth = inputPortInfo[index];

        byte infoBitsForOne = convertStrengthToInfoBits(strength);

        boolean isPortIndexEven = (port & 1) == 0;
        if (isPortIndexEven) {
            inputPortInfo[index] = (byte) (infoBitsForBoth | (infoBitsForOne << 4));
        } else {
            inputPortInfo[index] = (byte) (infoBitsForBoth | infoBitsForOne);
        }
    }

    int convertPortToBitInfoIndex(int port) {
        return port >> 1;
    }

    byte getMaskForSetting(int port) {
        boolean isPortIndexEven = (port & 1) == 0;
        if (isPortIndexEven) {
            return 0x0F;
        } else {
            return (byte) 0xF0;
        }
    }

    @Override
    public void detachPort(int port) {
        int index = port >> 1;
        byte infoBitsForBoth = inputPortInfo[index];
        inputPortInfo[index] = (byte) (infoBitsForBoth & getMaskForSetting(port));
    }
}
