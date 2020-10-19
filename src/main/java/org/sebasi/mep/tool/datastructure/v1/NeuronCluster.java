package org.sebasi.mep.tool.datastructure.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeuronCluster extends Cluster {

    List<Neuron> neurons = null;
    Map<String, Neuron> neuronsByLabel;

    public NeuronCluster(Helper helper) {
        this(helper, null);
    }

    public NeuronCluster(
            Helper helper,
            String label) {
        super(helper, label);
    }

    private List<Neuron> getNeurons() {
        if (neurons == null) {
            neurons = new ArrayList<>();
        }
        return neurons;
    }

    private Map<String, Neuron> getNeuronsByLabel() {
        if (neuronsByLabel == null) {
            neuronsByLabel = new HashMap<String, Neuron>();
        }
        return  neuronsByLabel;
    }

    public void addNeuron(Neuron neuronToAdd) {
        getNeurons().add(neuronToAdd);
        if (neuronToAdd.getLabel() != null) {
            getNeuronsByLabel().put(neuronToAdd.getLabel(), neuronToAdd);
        }
    }

    public Neuron getNeuron(String label) {
        return getNeuronsByLabel().get(label);
    }
}
