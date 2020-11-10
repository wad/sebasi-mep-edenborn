package org.sebasi.mep.tool.datastructure.v1;

public class SynapseOnDendrite {

    int synapticStrength;

    public SynapseOnDendrite(int initialSynapticStrength) {
        this.synapticStrength = initialSynapticStrength;
    }

    public int getSynapticStrength() {
        return synapticStrength;
    }

    public void setSynapticStrength(int synapticStrength) {
        this.synapticStrength = synapticStrength;
    }

    public void modifySynapticStrength(int delta) {
        synapticStrength += delta;
    }
}
