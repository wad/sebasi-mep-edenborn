package org.sebasi.mep.tool.datastructure.v1;

// todo: Strengthen synapses when they receive input (might depend on a probability)
// todo: Reduce strength of synapse when they don't receive input (definitely depending on a probability)

public class DendritesWithMemory extends Dendrites {

    // 16 bits
    static final int NUM_DENDRITE_INPUTS = 65536;

    // 4 bits per port, so we need half as many bytes as ports to hold the strength bits
    static final int NUM_BYTES_NEEDED_TO_TO_HOLD_PORT_INFO = NUM_DENDRITE_INPUTS >>> 1;

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

    public DendritesWithMemory(Helper helper) {
        super(helper);
    }

    @Override
    public long computeFiringThreshold() {
        // Just a starting formula. There are probably better ones.
        return numConnectedPorts * PORT_STRENGTH_DEFAULT_VALUE;
    }

    @Override
    public void attachPort(int port) {
        attachPort(
                port,
                PORT_STRENGTH_DEFAULT_VALUE);
    }

    public void attachPort(
            int port,
            int strength) {

        if (helper.getOperationMode().shouldValidateDendriteAttachments()) {
            if (isPortAttached(port)) {
                throw new RuntimeException("Attempted to connect, but already connected.");
            }
        }

        int bitInfoIndex = convertPortToBitInfoIndex(port);
        int infoBitsForBoth = inputPortInfo[bitInfoIndex];
        int newInfoBitsForOne = convertStrengthToInfoBits(strength);
        if (isPortEven(port)) {
            inputPortInfo[bitInfoIndex] = (byte) (infoBitsForBoth | (newInfoBitsForOne << 4));
        } else {
            inputPortInfo[bitInfoIndex] = (byte) (infoBitsForBoth | newInfoBitsForOne);
        }
        numConnectedPorts++;
    }

    @Override
    public void detachPort(int port) {

        if (helper.getOperationMode().shouldValidateDendriteAttachments()) {
            if (!isPortAttached(port)) {
                throw new RuntimeException("Attempted to disconnect, but not connected.");
            }
        }

        int bitInfoIndex = convertPortToBitInfoIndex(port);
        int infoBitsForBoth = inputPortInfo[bitInfoIndex];

        // Set either left leftmost 4 bits to 0000, or the rightmost 4 bits to 0000, leaving the other side alone.
        inputPortInfo[bitInfoIndex] = (byte) (infoBitsForBoth & getMaskForSetting(port));

        numConnectedPorts--;
    }

    @Override
    protected void initializePortInfo() {
        // initialized to zeroes, which means none of the ports are connected.
        inputPortInfo = new byte[NUM_BYTES_NEEDED_TO_TO_HOLD_PORT_INFO];
        numConnectedPorts = 0;
    }

    @Override
    int getPortInfoBits(int port) {
        int infoBits = inputPortInfo[convertPortToBitInfoIndex(port)];
        if (isPortEven(port)) {
            return (infoBits & 0xF0) >>> 4;
        } else {
            return infoBits & 0x0F;
        }
    }

    int convertInfoBitsToStrength(int infoBits) {
        return infoBits - PORT_STRENGTH_BITS_CORRESPONDING_TO_NEUTRAL;
    }

    int convertStrengthToInfoBits(int strength) {
        if (strength > 7) {
            strength = 7;
        } else if (strength < -7) {
            strength = -7;
        }
        return strength + PORT_STRENGTH_BITS_CORRESPONDING_TO_NEUTRAL;
    }

    int lookupPortStrength(int port) {
        int infoBits = getPortInfoBits(port);
        if (!doPortInfoBitsIndicateItIsConnected(infoBits)) {
            throw new RuntimeException("Bug: port not connected.");
        }

        return convertInfoBitsToStrength(infoBits);
    }

    int convertPortToBitInfoIndex(int port) {
        return port >>> 1;
    }

    boolean isPortEven(int port) {
        return (port & 1) == 0;
    }

    int getMaskForSetting(int port) {
        return isPortEven(port) ? 0x0F : 0xF0;
    }
}
