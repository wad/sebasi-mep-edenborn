package org.sebasi.mep.tool.datastructure.v1;

public class SynapseOnDendrite {

    int synapticStrength = 1;

    public SynapseOnDendrite() {
    }

    public int getSynapticStrength() {
        return synapticStrength;
    }

    public void modifySynapticStrength(int delta) {
        // todo: this will need to be more sophisticated
        synapticStrength += delta;
    }
}
