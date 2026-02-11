package it.unibo.crabinv.model.save;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.model.powerups.PowerUpType;

import java.util.UUID;

/**
 * Implementation of {@link Save}.
 *
 * <p>
 * Identifies the save with a UUID (for identification) and a timeStamp (for sorting),
 * references to all other save-related interfaces
 */
public class SaveImpl implements Save {
    private final UUID saveId;
    private final long creationTimeStamp;
    private final UserProfile userProfile;
    private final PlayerMemorial playerMemorial;
    private GameSession gameSession; //Not to be assigned at Save creation

    /**
     * Constructor for {@link SaveImpl}, also creates and assigns UUID and timeStamp.
     *
     * @param saveId the {@link UUID} of the {@link SaveImpl}
     * @param creationTimeStamp creation timestamp of the {@link SaveImpl}
     * @param userProfile {@link UserProfile} of the {@link SaveImpl}
     * @param playerMemorial {@link PlayerMemorial} of the {@link SaveImpl}
     * @param gameSession {@link GameSession} of the {@link SaveImpl}
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") //dependencies are injected and owned by caller
    public SaveImpl(final UUID saveId,
                    final long creationTimeStamp,
                    final UserProfile userProfile,
                    final PlayerMemorial playerMemorial,
                    final GameSession gameSession) {
        this.saveId = saveId;
        this.creationTimeStamp = creationTimeStamp;
        this.userProfile = userProfile;
        this.playerMemorial = playerMemorial;
        this.gameSession = gameSession;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final UUID getSaveId() {
        return saveId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getCreationTimeStamp() {
        return creationTimeStamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void newGameSession() {
        this.gameSession = new GameSessionImpl(
                StartingSaveValues.CURRENCY.getIntValue(),
                this.userProfile.applyAddPowerUp(PowerUpType.HEALTH_UP),
                this.userProfile.applyMultiplyPowerUp(PowerUpType.SPEED_UP),
                this.userProfile.applyDividePowerUp(PowerUpType.FIRERATE_UP));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void closeGameSession() {
        this.gameSession = null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings("EI_EXPOSE")//exposes internal representation by design
    @Override
    public final GameSession getGameSession() {
        return gameSession;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings("EI_EXPOSE")//exposes internal representation by design
    @Override
    public final UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings("EI_EXPOSE")//exposes internal representation by design
    @Override
    public final PlayerMemorial getPlayerMemorial() {
        return playerMemorial;
    }
}
