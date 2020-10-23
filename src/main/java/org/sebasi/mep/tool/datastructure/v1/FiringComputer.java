package org.sebasi.mep.tool.datastructure.v1;

public enum FiringComputer {

    FireAlways,
    FireNever,
    FireOnAnyInput,
    FireWhenNumTriggeredInputsMoreThanNumConnectedSynapses;

    public static boolean shouldFire(
            FiringComputer firingComputer,
            long accumulatorValue,
            int maxNumSynapticConnections,
            int numSynapticConnections) {

        switch (firingComputer) {
            case FireAlways:
                return true;
            case FireNever:
                return false;
            case FireOnAnyInput:
                return accumulatorValue > 0;
            case FireWhenNumTriggeredInputsMoreThanNumConnectedSynapses:
                return accumulatorValue > numSynapticConnections;
            default:
                throw new IllegalStateException("Unexpected value: " + firingComputer);
        }
    }
}
