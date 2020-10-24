package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClusterOfNeurons extends Cluster {

    List<Neuron> neurons = null;
    Map<String, Neuron> neuronsByLabel;
    Map<Integer, Neuron> neuronsByNeuronIndex;
    int nextNeuronIndexNumber = 0;

    public ClusterOfNeurons(Helper helper) {
        this(helper, null);
    }

    public ClusterOfNeurons(
            Helper helper,
            String label) {
        super(helper, label);
        neuronsByNeuronIndex = new HashMap<>();
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
        return neuronsByLabel;
    }

    public void addNeuron(Neuron neuronToAdd) {
        getNeurons().add(neuronToAdd);
        if (neuronToAdd.getLabel() != null) {
            getNeuronsByLabel().put(neuronToAdd.getLabel(), neuronToAdd);
        }

        neuronToAdd.setNeuronIndex(nextNeuronIndexNumber);
        neuronsByNeuronIndex.put(nextNeuronIndexNumber, neuronToAdd);
        nextNeuronIndexNumber++;
    }

    public Neuron getNeuron(String label) {
        return getNeuronsByLabel().get(label);
    }

    public Neuron getNeuron(int neuronIndex) {
        return neuronsByNeuronIndex.get(neuronIndex);
    }

    public int getGreatestNeuronIndex() {
        return nextNeuronIndexNumber - 1;
    }

    @Override
    public void tick() {
        neurons.forEach(Neuron::tick);
    }
}
