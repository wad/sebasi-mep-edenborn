package org.sebasi.mep.tool.datastructure.v1.highperf;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.FiringComputer;
import org.sebasi.mep.tool.datastructure.v1.Neuron;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;
import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

import static org.junit.Assert.assertEquals;

public class NeuronWithoutMemoryWithHighPerfTest {

    @Test
    public void testLabel() {
        Neuron neuron = new NeuronWithoutMemoryWithHighPerf(
                FiringComputer.ALWAYS,
                TickPriority.second,
                DendriticTreeSize.TwoE4,
                new Helper(),
                "a");
        assertEquals("a", neuron.getLabel());
    }
}
