package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DendriticTreeStandard extends DendriticTree {

    // todo: Can dramatically improve performance by removing the whole synaptic index idea from this class.
    //  Keeping it now, just for visibility reasons.

    // synapses by synaptic index
    Map<Integer, SynapseOnDendrite> connectedSynapses;
    Set<Integer> availableSynapticIndexes;
    int greatestSynapticIndexEverMade;

    public DendriticTreeStandard(Neuron neuron) {
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
        // todo: fix this, there are problems with validation
//        validateConnection(false, synapticIndex);

        SynapseOnDendrite synapseOnDendrite = new SynapseOnDendrite();
        connectedSynapses.put(synapticIndex, synapseOnDendrite);
        numConnectedSynapses++;
    }

    @Override
    public void detachSynapse(int synapticIndex) {
        validateConnection(true, synapticIndex);

        connectedSynapses.remove(synapticIndex);
        availableSynapticIndexes.add(synapticIndex);
        numConnectedSynapses--;
    }

    @Override
    protected void validateConnection(
            boolean expectationIsAlreadyConnected,
            int synapticIndex) {
        if (neuron.getHelper().getOperationMode().shouldValidateDendriteAttachments()) {
            boolean connectionExists = connectedSynapses.containsKey(synapticIndex);
            if (expectationIsAlreadyConnected != connectionExists) {
                throw new NeuronConnectionException(
                        "Failed to validate synapse connected. Expected connected = " + expectationIsAlreadyConnected,
                        neuron.getLabel());
            }
        }
    }

    public int lookupSynapseStrength(int synapticIndex) {
        validateConnection(true, synapticIndex);

        return connectedSynapses.get(synapticIndex).getSynapticStrength();
    }
}
