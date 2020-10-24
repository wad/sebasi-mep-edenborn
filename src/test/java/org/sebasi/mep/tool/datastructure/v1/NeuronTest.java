package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

import static org.junit.Assert.assertEquals;

public class NeuronTest {

    @Test
    public void testLabel() {
        Neuron neuron = new NeuronWithoutMemory(
                FiringComputer.ALWAYS,
                TickPriority.second,
                DendriticTreeSize.TwoE4,
                new Helper(),
                "a");
        assertEquals("a", neuron.getLabel());
    }
}
