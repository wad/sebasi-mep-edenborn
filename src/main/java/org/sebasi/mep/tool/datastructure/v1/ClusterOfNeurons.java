package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClusterOfNeurons extends Cluster {

    List<Neuron> neurons = null;
    Map<String, Neuron> neuronsByLabel;

    public ClusterOfNeurons(Helper helper) {
        this(helper, null);
    }

    public ClusterOfNeurons(
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
            neuronsByLabel = new HashMap<>();
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
