package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DendriticTreeStandardTest {
    @Test
    public void testBasicStuff() {
        Helper helper = new Helper();
        NeuronWithDendriticTreeStandard neuron = new NeuronWithDendriticTreeStandard(
                FiringComputer.ALWAYS,
                TickPriority.second,
                helper);
        DendriticTreeStandard dt = new DendriticTreeStandard(neuron);

        assertEquals(0, dt.getNumConnectedSynapses());

        int synapticIndex0 = dt.attachSynapse();
        assertEquals(0, synapticIndex0);
        assertEquals(1, dt.getNumConnectedSynapses());

        int synapticIndex1 = dt.attachSynapse();
        assertEquals(1, synapticIndex1);
        int synapticIndex2 = dt.attachSynapse();
        assertEquals(2, synapticIndex2);
        assertEquals(3, dt.getNumConnectedSynapses());
        assertEquals(2, dt.greatestSynapticIndexEverMade);

        dt.detachSynapse(synapticIndex1);
        assertTrue(dt.availableSynapticIndexes.contains(synapticIndex1));
        synapticIndex1 = dt.attachSynapse();
        assertEquals(1, synapticIndex1);
        assertTrue(dt.availableSynapticIndexes.isEmpty());
    }
}
