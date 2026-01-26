package it.unibo.crabinv.Model.save;

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

    /* Factory */
    private SaveImpl(UUID sId, long cts, GameSession gs, UserProfile up, PlayerMemorial pm){
        this.saveId = sId;
        this.creationTimeStamp = cts;
        this.saveGameSession = gs;
        this.saveUserProfile = up;
        this.savePlayerMemorial = pm;
    }

    public static Save createNewSave(){
        return new SaveImpl(
                UUID.randomUUID(),
                Instant.now().toEpochMilli(),
                new GameSessionImpl(),
                new UserProfileImpl(),
                new PlayerMemorialImpl()
        );
    }

    public static Save restoreSave(UUID sId, long cts, GameSession gs, UserProfile up, PlayerMemorial pm){
        return new SaveImpl(sId, cts, gs, up, pm);
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
