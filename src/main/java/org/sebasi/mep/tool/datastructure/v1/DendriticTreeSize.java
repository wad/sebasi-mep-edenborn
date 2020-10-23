package org.sebasi.mep.tool.datastructure.v1;

public enum DendriticTreeSize {

    TwoE4(16),
    TwoE16(65536);

    private final int numSynapses;

    DendriticTreeSize(int numSynapses) {
        this.numSynapses = numSynapses;
    }

    public int getNumSynapses() {
        return numSynapses;
    }
}
