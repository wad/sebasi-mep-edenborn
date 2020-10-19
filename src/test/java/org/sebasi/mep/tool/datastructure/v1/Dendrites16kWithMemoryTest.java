package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;

import static org.junit.Assert.*;

public class Dendrites16kWithMemoryTest {

    @Test
    public void testNumBytesToHoldPortInfo() {
        assertEquals(65536, Dendrites16kWithMemory.NUM_DENDRITE_INPUTS);

        // Can fit information on two ports into one byte.
        assertEquals(32768, Dendrites16kWithMemory.NUM_BYTES_NEEDED_TO_TO_HOLD_PORT_INFO);
    }

    @Test
    public void testInfoBitsDirectly() {
        Dendrites16kWithMemory den = new Dendrites16kWithMemory(new Helper());
        assertEquals(0, den.computeFiringThreshold());

        // If all four bits are 0, it means it's disconnected.
        assertFalse(den.doPortInfoBitsIndicateItIsConnected(0x0));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected(0x1));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected(0x2));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected(0x7));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected(0x8));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected(0x9));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected(0xF));

        assertEquals(0x0, den.getPortInfoBits(0));
        den.attachPort(0, 7);
        assertEquals(0xF, den.getPortInfoBits(0));
        den.detachPort(0);
        assertEquals(0x0, den.getPortInfoBits(0));
    }

    @Test
    public void testAttachDetachPorts() {
        Dendrites16kWithMemory den = new Dendrites16kWithMemory(new Helper());
        assertEquals(0, den.getNumConnectedPorts());

        den.attachPort(1000, -7);
        den.attachPort(1001, 0);
        den.attachPort(1002, 7);
        assertEquals(3, den.getNumConnectedPorts());

        assertEquals(0x0, den.getPortInfoBits(999));
        assertEquals(0x1, den.getPortInfoBits(1000));
        assertEquals(0x8, den.getPortInfoBits(1001));
        assertEquals(0xF, den.getPortInfoBits(1002));
        assertEquals(0x0, den.getPortInfoBits(1003));
        den.detachPort(1001);
        assertEquals(2, den.getNumConnectedPorts());
        assertEquals(0x0, den.getPortInfoBits(999));
        assertEquals(0x1, den.getPortInfoBits(1000));
        assertEquals(0x0, den.getPortInfoBits(1001));
        assertEquals(0xF, den.getPortInfoBits(1002));
        assertEquals(0x0, den.getPortInfoBits(1003));
    }
}
