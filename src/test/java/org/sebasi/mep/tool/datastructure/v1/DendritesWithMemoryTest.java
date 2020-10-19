package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;

import static org.junit.Assert.*;

public class DendritesWithMemoryTest {

    @Test
    public void testIt() {
        assertEquals(32768, DendritesWithMemory.NUM_BYTES_NEEDED_TO_TO_HOLD_PORT_INFO);
    }

    @Test
    public void testInfoBits() {
        DendritesWithMemory den = new DendritesWithMemory();
        assertEquals(0, den.computeFiringThreshold());

        // If all four bits are 0, it means it's disconnected.
        assertFalse(den.doPortInfoBitsIndicateItIsConnected((byte) 0x0));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected((byte) 0x1));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected((byte) 0x2));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected((byte) 0x7));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected((byte) 0x8));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected((byte) 0x9));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected((byte) 0xF));

        assertEquals((byte) 0x0, den.getPortInfoBits(0));
        den.attachPort(0, 7);
        assertEquals((byte) 0xF, den.getPortInfoBits(0));
        den.detachPort(0);
        assertEquals((byte) 0x0, den.getPortInfoBits(0));
    }
}
