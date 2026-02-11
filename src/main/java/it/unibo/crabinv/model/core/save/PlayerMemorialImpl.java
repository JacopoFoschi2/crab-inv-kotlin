package it.unibo.crabinv.model.core.save;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link PlayerMemorial}.
 * Contains and manages the list of all saved SessionRecord
 */
public class PlayerMemorialImpl implements PlayerMemorial {

    private final Map<Long, SessionRecord> memorial;

    /**
     * Constructor, creates an empty memorial.
     */
    public PlayerMemorialImpl() {
        this.memorial = new LinkedHashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SessionRecord> getMemorialList() {
        return new ArrayList<>(memorial.values());
    }

    /**
     * {@inheritDoc}
     *
     * <p>If no {@link SessionRecord} is found, it will return null.
     */
    @Override
    public SessionRecord getMemorialRecord(final long sessionTimeStamp) {
        return memorial.getOrDefault(sessionTimeStamp, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMemorialRecord(final SessionRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("record null not allowed");
        }
        final long keyRecord = record.getStartingTimeStamp();
        memorial.put(keyRecord, record);
    }
}
