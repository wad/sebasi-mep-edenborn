package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

import static org.junit.Assert.*;

public class DendriticTreeWithMemoryTest {

    @Test
    public void testInfoBitsDirectly() {
        NeuronWithMemory neuron = new NeuronWithMemory(
                FiringComputer.ALWAYS,
                TickPriority.second,
                DendriticTreeSize.TwoE16,
                new Helper());
        DendriticTreeWithMemory den = (DendriticTreeWithMemory) neuron.getDendriticTree();

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
        NeuronWithMemory neuron = new NeuronWithMemory(
                FiringComputer.ALWAYS,
                TickPriority.second,
                DendriticTreeSize.TwoE16,
                new Helper());
        DendriticTreeWithMemory den = (DendriticTreeWithMemory) neuron.getDendriticTree();
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
