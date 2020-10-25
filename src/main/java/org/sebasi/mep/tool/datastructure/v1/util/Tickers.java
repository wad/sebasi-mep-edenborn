package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.Ticker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tickers extends HelperHolder {

    long tickCounter;

    Map<TickPriority, Set<Ticker>> tickersByPriority;

    public Tickers(Helper helper) {
        super(helper);

        tickCounter = 0L;

        tickersByPriority = new HashMap<>(TickPriority.values().length);
        for (TickPriority tickPriority : TickPriority.values()) {
            tickersByPriority.put(tickPriority, new HashSet<>());
        }
    }

    public void registerTicker(
            TickPriority tickPriority,
            Ticker ticker) {
        tickersByPriority.get(tickPriority).add(ticker);
    }

    public void tick() {

        for (TickPriority tickPriority : TickPriority.values()) {
            Set<Ticker> tickers = tickersByPriority.get(tickPriority);
            for (Ticker ticker : tickers) {
                ticker.tick();
            }
        }

        if (getHelper().shouldShowLogMessages()) {
            getHelper().getMessageDisplay().show("Tick " + tickCounter + " [" + showTickCounts() + "]");
        }

        tickCounter++;
    }

    String showTickCounts() {
        StringBuilder builder = new StringBuilder();
        for (TickPriority tickPriority : TickPriority.values()) {
            builder.append(tickPriority.name());
            builder.append(": ");
            builder.append(tickersByPriority.get(tickPriority).size());
            builder.append(" ");
        }
        return builder.toString();
    }
}
