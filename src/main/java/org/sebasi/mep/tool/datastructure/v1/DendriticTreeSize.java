package org.sebasi.mep.tool.datastructure.v1;

public enum DendriticTreeSize {

    // todo: test that these sizes work
//    TwoE0(1),
//    TwoE1(2),

    TwoE4(16),
    TwoE8(256),
    TwoE16(65536);

    private final int numSynapses;

    DendriticTreeSize(int numSynapses) {
        this.numSynapses = numSynapses;
    }

    public int getNumSynapses() {
        return numSynapses;
    }
}
