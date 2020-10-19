package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DendritesWithoutMemoryTest {

    @Test
    public void testIt() {
        assertEquals(8192, DendritesWithoutMemory.NUM_BYTES_NEEDED_TO_TO_HOLD_PORT_INFO);
    }

    @Test
    public void testInfoBits() {
        DendritesWithoutMemory den = new DendritesWithoutMemory();
        assertEquals(0, den.computeFiringThreshold());
        assertFalse(den.doPortInfoBitsIndicateItIsConnected((byte) 0));
        assertTrue(den.doPortInfoBitsIndicateItIsConnected((byte) 1));

        // set up some bits
        den.inputPortInfo[0] = 0x00; // ports 0 - 7, all low
        den.inputPortInfo[1] = 0x01; // ports 8 - 15, 15 is high
        den.inputPortInfo[2] = 0x02; // ports 16 - 23, 22 is high
        den.inputPortInfo[3] = 0x04; // ports 24 - 31, 29 is high
        den.inputPortInfo[4] = 0x08; // ports 32 - 39, 36 is high
        den.inputPortInfo[5] = 0x10; // ports 40 - 47, 43 is high
        den.inputPortInfo[6] = 0x20; // ports 48 - 55, 50 is high
        den.inputPortInfo[7] = 0x40; // ports 56 - 63, 57 is high
        den.inputPortInfo[8] = (byte)0x80; // ports 64 - 71, 64 is high

        Set<Integer> highPorts = new HashSet<>(Arrays.asList(15, 22, 29, 36, 43, 50, 57, 64));

        for (int port = 0; port < 100; port++) {
            if (highPorts.contains(port)) {
                assertEquals("PORT=" + port + " should have been high.", 1, den.getPortInfoBits(port));
            } else {
                assertEquals("PORT=" + port + " should have been low.", 0, den.getPortInfoBits(port));
            }
        }

        den.attachPort(1000);
        den.attachPort(1001);
        den.attachPort(1002);
        assertEquals(0, den.getPortInfoBits(999));
        assertEquals(1, den.getPortInfoBits(1000));
        assertEquals(1, den.getPortInfoBits(1001));
        assertEquals(1, den.getPortInfoBits(1002));
        assertEquals(0, den.getPortInfoBits(1003));
        den.detachPort(1001);
        assertEquals(0, den.getPortInfoBits(999));
        assertEquals(1, den.getPortInfoBits(1000));
        assertEquals(0, den.getPortInfoBits(1001));
        assertEquals(1, den.getPortInfoBits(1002));
        assertEquals(0, den.getPortInfoBits(1003));
    }
}
