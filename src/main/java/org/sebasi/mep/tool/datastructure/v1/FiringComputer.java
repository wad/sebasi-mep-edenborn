package org.sebasi.mep.tool.datastructure.v1;

public enum FiringComputer {

    ALWAYS,
    NEVER,
    ON_ANY_INPUT,
    WHEN_NUM_TRIGGERED_INPUTS_MORE_THAN_NUM_CONNECTED_SYNAPSES,
    WHEN_TEN_TIMES_NUM_CONNECTED_SYNAPSES,
    WHEN_TWO_TIMES_NUM_CONNECTED_SYNAPSES;

    public static boolean shouldFire(
            FiringComputer firingComputer,
            long accumulatorValue,
            int maxNumSynapticConnections,
            int numSynapticConnections) {

        switch (firingComputer) {
            case ALWAYS:
                return true;
            case NEVER:
                return false;
            case ON_ANY_INPUT:
                return accumulatorValue > 0;
            case WHEN_NUM_TRIGGERED_INPUTS_MORE_THAN_NUM_CONNECTED_SYNAPSES:
                return accumulatorValue > numSynapticConnections;
            case WHEN_TEN_TIMES_NUM_CONNECTED_SYNAPSES:
                return accumulatorValue > (numSynapticConnections * 10);
            case WHEN_TWO_TIMES_NUM_CONNECTED_SYNAPSES:
                return accumulatorValue > (numSynapticConnections * 2);
            default:
                throw new IllegalStateException("Unexpected value: " + firingComputer);
        }
    }
}
