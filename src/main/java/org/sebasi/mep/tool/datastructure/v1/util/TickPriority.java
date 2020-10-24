package org.sebasi.mep.tool.datastructure.v1.util;

public enum TickPriority {

    // These must remain in priority order

    // The first priority are the ones that tend to be early in the cascade of activity, such as sensory input.
    first,

    // Most neurons are second priority.
    second,

    // Last should be motor output neurons.
    third
}
