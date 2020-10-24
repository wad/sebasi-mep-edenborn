package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DendriticTreeWithoutMemoryTest {

    @Test
    public void testSynapticStateBitsDirectly() {
        NeuronWithoutMemory neuron = new NeuronWithoutMemory(
                FiringComputer.FireAlways,
                TickPriority.second,
                DendriticTreeSize.TwoE16,
                new Helper());
        DendriticTreeWithoutMemory den = (DendriticTreeWithoutMemory) neuron.getDendriticTree();
        assertFalse(den.doSynapticStateBitsIndicateConnected((byte) 0));
        assertTrue(den.doSynapticStateBitsIndicateConnected((byte) 1));

        // set up some states manually
        den.synapticStates[0] = 0x00; // synapses 0 - 7, all low
        den.synapticStates[1] = 0x01; // synapses 8 - 15, 15 is high
        den.synapticStates[2] = 0x02; // synapses 16 - 23, 22 is high
        den.synapticStates[3] = 0x04; // synapses 24 - 31, 29 is high
        den.synapticStates[4] = 0x08; // synapses 32 - 39, 36 is high
        den.synapticStates[5] = 0x10; // synapses 40 - 47, 43 is high
        den.synapticStates[6] = 0x20; // synapses 48 - 55, 50 is high
        den.synapticStates[7] = 0x40; // synapses 56 - 63, 57 is high
        den.synapticStates[8] = (byte) 0x80; // ports 64 - 71, 64 is high

        // only these synapses are connected.
        Set<Integer> synapticIndexesThatAreConnected = new HashSet<>(Arrays.asList(15, 22, 29, 36, 43, 50, 57, 64));

        // verify that everything is working
        for (int synapticIndex = 0; synapticIndex < 100; synapticIndex++) {
            if (synapticIndexesThatAreConnected.contains(synapticIndex)) {
                assertEquals("SYNAPTIC_INDEX=" + synapticIndex + " should have been high.", 1, den.getSynapticStateBits(synapticIndex));
                assertTrue(den.isSynapseAttached(synapticIndex));
            } else {
                assertEquals("SYNAPTIC_INDEX=" + synapticIndex + " should have been low.", 0, den.getSynapticStateBits(synapticIndex));
                assertFalse(den.isSynapseAttached(synapticIndex));
            }
        }
    }

    @Test
    public void testAttachAndDetachSynapses() {
        NeuronWithoutMemory neuron = new NeuronWithoutMemory(
                FiringComputer.FireAlways,
                TickPriority.second,
                DendriticTreeSize.TwoE16,
                new Helper());
        DendriticTreeWithoutMemory den = (DendriticTreeWithoutMemory) neuron.getDendriticTree();
        assertEquals(0, den.getNumConnectedSynapses());

        den.attachSynapse(1000);
        den.attachSynapse(1001);
        den.attachSynapse(1002);
        assertEquals(3, den.getNumConnectedSynapses());

        assertEquals(0, den.getSynapticStateBits(999));
        assertEquals(1, den.getSynapticStateBits(1000));
        assertEquals(1, den.getSynapticStateBits(1001));
        assertEquals(1, den.getSynapticStateBits(1002));
        assertEquals(0, den.getSynapticStateBits(1003));
        den.detachSynapse(1001);
        assertEquals(2, den.getNumConnectedSynapses());
        assertEquals(0, den.getSynapticStateBits(999));
        assertEquals(1, den.getSynapticStateBits(1000));
        assertEquals(0, den.getSynapticStateBits(1001));
        assertEquals(1, den.getSynapticStateBits(1002));
        assertEquals(0, den.getSynapticStateBits(1003));
    }
}
