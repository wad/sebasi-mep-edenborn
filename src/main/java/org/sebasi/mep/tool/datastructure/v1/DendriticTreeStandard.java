package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.NeuronConnectionException;

import java.util.*;

public class DendriticTreeStandard extends DendriticTree {

    // todo: Can dramatically improve performance by removing the whole synaptic index idea from this class.
    //  Keeping it now, just for diagnostic reasons.

    Map<Integer, SynapseOnDendrite> connectedSynapsesBySynapticIndex;
    Set<Integer> availableSynapticIndexes;
    int greatestSynapticIndexEverMade;

    static int DEFAULT_INITIAL_SYNAPTIC_STRENGTH_VALUE = 100;
    int initialSynapticStrengthValue = DEFAULT_INITIAL_SYNAPTIC_STRENGTH_VALUE;

    public DendriticTreeStandard(Neuron neuron) {
        super(neuron);
        connectedSynapsesBySynapticIndex = new HashMap<>();
        availableSynapticIndexes = new HashSet<>();
        greatestSynapticIndexEverMade = -1;
    }

    public void setInitialSynapticStrengthValue(int initialSynapticStrengthValue) {
        this.initialSynapticStrengthValue = initialSynapticStrengthValue;
    }

    @Override
    public boolean isSynapseAttached(int synapticIndex) {
        return connectedSynapsesBySynapticIndex.containsKey(synapticIndex);
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


        // todo: bug, this is getting called twice
        SynapseOnDendrite synapseOnDendrite = new SynapseOnDendrite(initialSynapticStrengthValue);
        connectedSynapsesBySynapticIndex.put(synapticIndex, synapseOnDendrite);
        numConnectedSynapses++;
    }

    @Override
    public void detachSynapse(int synapticIndex) {
        validateConnection(true, synapticIndex);

        connectedSynapsesBySynapticIndex.remove(synapticIndex);
        availableSynapticIndexes.add(synapticIndex);
        numConnectedSynapses--;
    }

    @Override
    public SynapseOnDendrite getSynapse(int synapticIndex) {
        return connectedSynapsesBySynapticIndex.get(synapticIndex);
    }

    @Override
    public Collection<SynapseOnDendrite> getSynapses() {
        return connectedSynapsesBySynapticIndex.values();
    }

    @Override
    protected void validateConnection(
            boolean expectationIsAlreadyConnected,
            int synapticIndex) {
        if (neuron.getHelper().getOperationMode().shouldValidateDendriteAttachments()) {
            boolean connectionExists = connectedSynapsesBySynapticIndex.containsKey(synapticIndex);
            if (expectationIsAlreadyConnected != connectionExists) {
                throw new NeuronConnectionException(
                        "Failed to validate synapse connected. Expected connected = " + expectationIsAlreadyConnected,
                        neuron.getLabel());
            }
        }
    }

    public int lookupSynapseStrength(int synapticIndex) {
        validateConnection(true, synapticIndex);

        return connectedSynapsesBySynapticIndex.get(synapticIndex).getSynapticStrengthValue();
    }
}
