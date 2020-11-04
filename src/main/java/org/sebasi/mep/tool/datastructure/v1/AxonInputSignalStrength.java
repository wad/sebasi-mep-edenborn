package org.sebasi.mep.tool.datastructure.v1;

public class AxonInputSignalStrength {

    int numExtraTicksBetweenFirings;

    public AxonInputSignalStrength(int numExtraTicksBetweenFirings) {
        this.numExtraTicksBetweenFirings = numExtraTicksBetweenFirings;
    }

    public void setNumExtraTicksBetweenFirings(int numExtraTicksBetweenFirings) {
        this.numExtraTicksBetweenFirings = numExtraTicksBetweenFirings;
    }

    public int getNumExtraTicksBetweenFirings() {
        return numExtraTicksBetweenFirings;
    }
}
