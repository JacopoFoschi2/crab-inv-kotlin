package it.unibo.crabinv.Model.Save;

import java.util.List;

/**
 * Represents the list of all saved SessionRecord
 */
public interface PlayerMemorial {

    /**
     * Returns all the SessionRecords
     * @return the complete List of SessionRecords
     */
    List<SessionRecord> getMemorialList();

    /**
     * Returns a SessionRecord based on the sessionTimeStamp
     * @param sessionTimeStamp the sessionTimeStamp that identifies the Session required
     * @return SessionRecord required
     */
    SessionRecord getMemorialRecord(long sessionTimeStamp);

    /**
     * Adds a SessionRecord to the PlayerMemorial
     * @param record the SessionRecord to add
     */
    void addMemorialRecord(SessionRecord record);
}
