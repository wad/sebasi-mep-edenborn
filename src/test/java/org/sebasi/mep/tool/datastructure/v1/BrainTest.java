package org.sebasi.mep.tool.datastructure.v1;

import org.junit.Test;
import org.sebasi.mep.tool.datastructure.v1.util.Helper;

import static org.junit.Assert.assertEquals;

public class BrainTest {

    @Test
    public void testBrain() {

        Helper helper = new Helper();
        ClusterOfNeurons clusterOfNeurons = makeNeuronCluster(helper);

        assertEquals(1000, clusterOfNeurons.neurons.size());

    }

    ClusterOfNeurons makeNeuronCluster(Helper helper) {
        int numNeuronsInCluster = 1000;
        DendriticTreeSize neuronSize = DendriticTreeSize.TwoE16;
        ClusterOfNeurons clusterOfNeurons = new ClusterOfNeurons(helper, "nc1");
        for (int i = 0; i < numNeuronsInCluster; i++) {
            Neuron neuron = new NeuronWithoutMemory(neuronSize, helper);
            clusterOfNeurons.addNeuron(neuron);
        }
        return clusterOfNeurons;
    }

}
