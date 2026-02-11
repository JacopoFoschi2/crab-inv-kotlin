package it.unibo.crabinv.model.save;

import java.util.UUID;

/**
 * Retrieves all data from other save-related interfaces and keep identifiers.
 */
public interface Save {

    /**
     * Returns the {@link UUID} of to the {@link Save}.
     *
     * @return the {@link UUID} of to the {@link Save}
     */
    UUID getSaveId();

    /**
     * Returns the {@code creationTimeStamp} {@link Save}.
     *
     * @return the {@code creationTimeStamp} {@link Save}
     */
    long getCreationTimeStamp();

    /**
     * Creates a new GameSession based on the UserProfile.
     */
    void newGameSession();

    /**
     * Closes the GameSession.
     */
    void closeGameSession();

    /**
     * Returns the {@link GameSession} bound to the {@link Save}.
     *
     * @return the {@link GameSession} bound to the {@link Save}
     */
    GameSession getGameSession();

    /**
     * Returns the {@link UserProfile} bound to the {@link Save}.
     *
     * @return the {@link UserProfile} bound to the {@link Save}
     */
    UserProfile getUserProfile();

    /**
     * Returns the {@link PlayerMemorial} bound to the {@link Save}.
     *
     * @return the {@link PlayerMemorial} bound to the {@link Save}
     */
    PlayerMemorial getPlayerMemorial();
}
