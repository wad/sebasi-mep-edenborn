package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NeuronTest {

    @Test
    public void testIt() {
        Neuron neuron = new NeuronWithoutMemory("a");
        assertEquals("a", neuron.getLabel());

    }
}
