package org.sebasi.mep.tool.datastructure.v1;

import org.sebasi.mep.tool.datastructure.v1.util.TickPriority;

public interface Ticker {
    void tick();

    void registerTicker(TickPriority tickPriority);
}
