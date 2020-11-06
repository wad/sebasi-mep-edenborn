package org.sebasi.mep.tool.datastructure.v1.standardperf;

import org.sebasi.mep.tool.datastructure.v1.DendriticTree;
import org.sebasi.mep.tool.datastructure.v1.Neuron;
import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DendriticTreeWithStandardPerf extends DendriticTree {

    // synapses by synaptic index
    Map<Integer, SynapseOnDendrite> connectedSynapses;
    Set<Integer> availableSynapticIndexes;
    int greatestSynapticIndexEverMade;

    public DendriticTreeWithStandardPerf(Neuron neuron) {
        super(neuron);
        connectedSynapses = new HashMap<>();
        availableSynapticIndexes = new HashSet<>();
        greatestSynapticIndexEverMade = -1;
    }

    @Override
    public boolean isSynapseAttached(int synapticIndex) {
        return connectedSynapses.containsKey(synapticIndex);
    }

    // returns the new synaptic index
    public int attachSynapse() {
        int synapticIndex = identifySynapticIndexForNewAttachment();
        attachSynapse(synapticIndex);
        return synapticIndex;
    }

    int identifySynapticIndexForNewAttachment() {
        if (availableSynapticIndexes.isEmpty()) {
            return ++greatestSynapticIndexEverMade;
        } else {
            // Just get any available index.
            int synapticIndex = availableSynapticIndexes.iterator().next();
            availableSynapticIndexes.remove(synapticIndex);
            return synapticIndex;
        }
    }

    @Override
    public void attachSynapse(int synapticIndex) {
        // This line can probably be omitted for better performance, as this
        // is called from another method that identifies the index to use.
        validateConnection(false, synapticIndex);

        SynapseOnDendrite synapseOnDendrite = new SynapseOnDendrite();
        connectedSynapses.put(synapticIndex, synapseOnDendrite);
    }

    @Override
    public void detachSynapse(int synapticIndex) {
        validateConnection(false, synapticIndex);

        connectedSynapses.remove(synapticIndex);
        availableSynapticIndexes.add(synapticIndex);
    }

    @Override
    protected void validateConnection(
            boolean expectationIsAlreadyConnected,
            int synapticIndex) {
        if (neuron.getHelper().getOperationMode().shouldValidateDendriteAttachments()) {
            if (expectationIsAlreadyConnected != connectedSynapses.containsKey(synapticIndex)) {
                throw new NeuronConnectionException("Failed to validate synaptic connected. Expected connected = " + expectationIsAlreadyConnected);
            }
        }
    }
}
