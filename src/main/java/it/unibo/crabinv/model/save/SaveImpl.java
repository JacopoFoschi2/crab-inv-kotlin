package it.unibo.crabinv.model.save;

import it.unibo.crabinv.model.powerups.PowerUpType;

import java.util.UUID;

/**
 * Implementation of {@link Save}
 * Identifies the save with a UUID (for identification) and a timeStamp (for sorting),
 * references to all other save-related interfaces
 */
public class SaveImpl implements Save{
    private final UUID saveId;
    private final long creationTimeStamp;
    private final UserProfile userProfile;
    private final PlayerMemorial playerMemorial;
    private GameSession gameSession; //Not to be assigned at Save creation

    /**
     * Constructor, creates and assigns UUID and timeStamp
     */
    public SaveImpl(UUID saveId,
                    long creationTimeStamp,
                    UserProfile userProfile,
                    PlayerMemorial playerMemorial,
                    GameSession gameSession){
        this.saveId = saveId;
        this.creationTimeStamp = creationTimeStamp;
        this.userProfile = userProfile;
        this.playerMemorial = playerMemorial;
        this.gameSession = gameSession;
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

    @Override
    public void newGameSession() {
        this.gameSession = new GameSessionImpl(
                StartingSaveValues.CURRENCY.getIntValue(),
                this.userProfile.applyAddPowerUp(PowerUpType.HEALTH_UP),
                this.userProfile.applyMultiplyPowerUp(PowerUpType.SPEED_UP),
                this.userProfile.applyDividePowerUp(PowerUpType.FIRERATE_UP));
    }
    
    @Override
    public void closeGameSession() {
        this.gameSession = null;
    }

    /** {@inheritDoc} */
    @Override
    public GameSession getGameSession() {
        return gameSession;
    }

    /** {@inheritDoc} */
    @Override
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /** {@inheritDoc} */
    @Override
    public PlayerMemorial getPlayerMemorial() {
        return playerMemorial;
    }
}
