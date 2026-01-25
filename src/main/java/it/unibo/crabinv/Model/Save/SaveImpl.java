package it.unibo.crabinv.Model.Save;

import java.time.Instant;
import java.util.UUID;

/**
 * Implementation of {@link Save}
 * Identifies the save with a UUID (for identification) and a timeStamp (for sorting),
 * references to all other save related interfaces
 */
public class SaveImpl implements Save{
    private final UUID saveId;
    private final long creationTimeStamp;
    private final GameSession saveGameSession;
    private final UserProfile saveUserProfile;
    private final PlayerMemorial savePlayerMemorial;

    /**
     * Constructor, creates and assigns UUID and timeStamp
     * Creates all new save related Classes
     */
    public SaveImpl(){
        this.saveId = UUID.randomUUID();
        this.creationTimeStamp = Instant.now().toEpochMilli();
        this.saveGameSession = new GameSessionImpl();
        this.saveUserProfile = new UserProfileImpl();
        this.savePlayerMemorial = new PlayerMemorialImpl();
    }

    /** {@inheritDoc} */
    @Override
    public UUID getSaveId() {
        return saveId;
    }
    /** {@inheritDoc} */
    @Override
    public long getCreationTimeStamp() {
        return creationTimeStamp;
    }
    /** {@inheritDoc} */
    @Override
    public GameSession getGameSession() {
        return saveGameSession;
    }
    /** {@inheritDoc} */
    @Override
    public UserProfile getUserProfile() {
        return saveUserProfile;
    }

    /** {@inheritDoc} */
    @Override
    public PlayerMemorial getPlayerMemorial() {
        return savePlayerMemorial;
    }
}
