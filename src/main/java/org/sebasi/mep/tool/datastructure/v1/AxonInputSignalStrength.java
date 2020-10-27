package org.sebasi.mep.tool.datastructure.v1;

public enum AxonInputSignalStrength {
    Unspecified(0),
    S1(20),
    S2(15),
    S3(10),
    S4(5),
    S5(0);

    final int numExtraTicksBetweenFirings;

    AxonInputSignalStrength(int numExtraTicksBetweenFirings) {
        this.numExtraTicksBetweenFirings = numExtraTicksBetweenFirings;
    }

    public int getNumExtraTicksBetweenFirings() {
        return numExtraTicksBetweenFirings;
    }
}
