package it.unibo.crabinv.Model.save;

import it.unibo.crabinv.Model.Enemies.Enemy;
import it.unibo.crabinv.Model.PowerUpsShop.PowerUpType;

import java.time.Instant;

/**
 * Implementation of {@link GameSession}
 * Tracks the state of a single attempt (level, currency, player health and start timestamp).
 *
 * <p>The start timestamp is fixed at construction time; all updates to
 * level, currency and health are constrained to non‑negative values.
 */
public class GameSessionImpl implements GameSession {

    private static final int STARTING_LEVEL = 1;
    private static final int STARTING_CURRENCY = 0;
    public static final int STARTING_PLAYER_HEALTH = 3; // ATTENZIONE: VALORE E VISIBILITA' PLACEHOLDER
    // manca logica di calcolo HP

    private int currentLevel;
    private boolean gameOver;
    private final long startingTimeStamp;
    private int currency;
    private int playerHealth;
    private double speedMultiplier = 1.0;
    private double fireRateMultiplier = 1.0;
    private int bonusHealth = 0;


    /**
     * Constructor with default values
     */
    public GameSessionImpl() {
        this.currentLevel = STARTING_LEVEL;
        this.gameOver = false;
        this.startingTimeStamp = Instant.now().toEpochMilli();
        this.currency = STARTING_CURRENCY;
        this.playerHealth = STARTING_PLAYER_HEALTH;
    }

    /** {@inheritDoc} */
    @Override
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /** {@inheritDoc} */
    @Override
    public int getNextLevel() {
        return this.currentLevel + 1;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }

    /** {@inheritDoc} */
    @Override
    public long getStartingTimeStamp() {
        return this.startingTimeStamp;
    }

    /** {@inheritDoc} */
    @Override
    public int getCurrency() {
        return this.currency;
    }

    /** {@inheritDoc} */
    @Override
    public int getPlayerHealth() {
        return this.playerHealth;
    }

    /** {@inheritDoc} */
    @Override
    public void advanceLevel() {
        this.currentLevel++;
    }

    /** {@inheritDoc} */
    @Override
    public void markGameOver() {
        this.gameOver = true;
    }

    /** {@inheritDoc} */
    @Override
    public void addCurrency(int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        this.currency += amount;
    }

    /** {@inheritDoc} */
    @Override
    public void subCurrency(int amount) {
        DomainUtils.requireNonNegativeSubtraction(this.currency, amount);
        this.currency -= amount;
    }

    /**
     * {@inheritDoc}
     * <p>Also clamps the result to STARTING_PLAYER_HEALTH
     */
    @Override
    public void addPlayerHealth(int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        this.playerHealth = Math.min(STARTING_PLAYER_HEALTH, this.playerHealth + amount);
    }

    /** {@inheritDoc} */
    @Override
    public void subPlayerHealth(int amount) {
        this.playerHealth = DomainUtils.subClampedToZero(this.playerHealth, amount);
    }
    

    public  void applyPowerUps(UserProfile profile) {
        speedMultiplier = 1.0;
        fireRateMultiplier = 1.0;
        bonusHealth = STARTING_PLAYER_HEALTH;
        for (String powerUpName : profile.getPowerUpList()){
            int powerUpLevel = profile.getPowerUpLevel(powerUpName);
            PowerUpType type = PowerUpType.fromName(powerUpName);
            if (powerUpLevel > 0 && type != null) {
                switch (type) {
                    case SPEED_UP ->
                            this.speedMultiplier = speedMultiplier * type.getStatMultiplier() * powerUpLevel;
                    case HEALTH_UP ->
                            this.playerHealth = playerHealth * (int) type.getStatMultiplier() * powerUpLevel;
                    case FIRERATE_UP ->
                            this.fireRateMultiplier = fireRateMultiplier * type.getStatMultiplier() * powerUpLevel;
                }
            }
        }
    }
}
