package it.unibo.crabinv.Model.save;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link PlayerMemorial}
 * Contains and manages the list of all saved SessionRecord
 */
public class PlayerMemorialImpl implements PlayerMemorial {

    private final Map<Long, SessionRecord> memorial;

    public PlayerMemorialImpl() {
        this.memorial = new LinkedHashMap<>();
    }

    @Override
    public List<SessionRecord> getMemorialList() {
        return new ArrayList<>(memorial.values());
    }

    /**
     * {@inheritDoc}
     * <p>If no SessionRecord is found will return null
     */
    @Override
    public SessionRecord getMemorialRecord(long sessionTimeStamp) {
        return memorial.getOrDefault(sessionTimeStamp, null);
    }

    @Override
    public void addMemorialRecord(SessionRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("record null not allowed");
        }
        long keyRecord = record.getStartingTimeStamp();
        memorial.put(keyRecord, record);
    }
}
