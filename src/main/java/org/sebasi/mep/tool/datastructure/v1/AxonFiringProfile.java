package org.sebasi.mep.tool.datastructure.v1;

public enum AxonFiringProfile {

    Immediate(1, 0, 0),
    Variable1(10, 4, 12);

    private final int numFirings;
    private final int defaultNumExtraTicksBetweenFirings;
    private final int numTicksAfterFinalFireUntilReset;

    public int getNumFirings() {
        return numFirings;
    }

    public int getNumTicksBetweenFirings(AxonInputSignalStrength signalStrength) {
        if (signalStrength == null) {
            return defaultNumExtraTicksBetweenFirings;
        }
        return signalStrength.getNumExtraTicksBetweenFirings();
    }

    public int getNumTicksAfterFinalFireUntilReset() {
        return numTicksAfterFinalFireUntilReset;
    }

    AxonFiringProfile(
            int numFirings,
            int defaultNumExtraTicksBetweenFirings,
            int numTicksAfterFinalFireUntilReset) {
        this.numFirings = numFirings;
        this.defaultNumExtraTicksBetweenFirings = defaultNumExtraTicksBetweenFirings;
        this.numTicksAfterFinalFireUntilReset = numTicksAfterFinalFireUntilReset;
    }
}
