package it.unibo.crabinv.Model.Save;

import java.time.Instant;

public class SessionRecordImpl implements SessionRecord {
    private long timeStamp;
    private int lastLevel;
    private int lastCurrency;

    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }

    @Override
    public int getLastLevel() {
        return this.lastLevel;
    }

    @Override
    public int getLastCurrency() {
        return this.lastCurrency;
    }
}
