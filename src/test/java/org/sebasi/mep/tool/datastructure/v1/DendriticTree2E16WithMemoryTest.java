package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;

import static org.junit.Assert.*;

public class DendriticTree2E16WithMemoryTest {

    @Test
    public void testNumBytesToHoldSynapticStates() {
        assertEquals(65536, DendriticTree2E16WithMemory.NUM_SYNAPSES);

        // Can fit information on two synapses into one byte.
        assertEquals(32768, DendriticTree2E16WithMemory.NUM_BYTES_NEEDED_TO_HOLD_SYNAPTIC_STATES);
    }

    @Test
    public void testInfoBitsDirectly() {
        DendriticTree2E16WithMemory den = new DendriticTree2E16WithMemory(new NeuronWithMemory(new Helper()));
        assertEquals(0, den.computeFiringThreshold());

        // If all four bits are 0, it means it's disconnected.
        assertFalse(den.doSynapticStateBitsIndicateConnected(0x0));
        assertTrue(den.doSynapticStateBitsIndicateConnected(0x1));
        assertTrue(den.doSynapticStateBitsIndicateConnected(0x2));
        assertTrue(den.doSynapticStateBitsIndicateConnected(0x7));
        assertTrue(den.doSynapticStateBitsIndicateConnected(0x8));
        assertTrue(den.doSynapticStateBitsIndicateConnected(0x9));
        assertTrue(den.doSynapticStateBitsIndicateConnected(0xF));

        assertEquals(0x0, den.getSynapticStateBits(0));
        den.attachSynapse(0, 7);
        assertEquals(0xF, den.getSynapticStateBits(0));
        den.detachSynapse(0);
        assertEquals(0x0, den.getSynapticStateBits(0));
    }

    @Test
    public void testAttachDetachSynapses() {
        DendriticTree2E16WithMemory den = new DendriticTree2E16WithMemory(new NeuronWithMemory(new Helper()));
        assertEquals(0, den.getNumConnectedSynapses());

        den.attachSynapse(1000, -7);
        den.attachSynapse(1001, 0);
        den.attachSynapse(1002, 7);
        assertEquals(3, den.getNumConnectedSynapses());

        assertEquals(0x0, den.getSynapticStateBits(999));
        assertEquals(0x1, den.getSynapticStateBits(1000));
        assertEquals(0x8, den.getSynapticStateBits(1001));
        assertEquals(0xF, den.getSynapticStateBits(1002));
        assertEquals(0x0, den.getSynapticStateBits(1003));
        den.detachSynapse(1001);
        assertEquals(2, den.getNumConnectedSynapses());
        assertEquals(0x0, den.getSynapticStateBits(999));
        assertEquals(0x1, den.getSynapticStateBits(1000));
        assertEquals(0x0, den.getSynapticStateBits(1001));
        assertEquals(0xF, den.getSynapticStateBits(1002));
        assertEquals(0x0, den.getSynapticStateBits(1003));
    }
}
