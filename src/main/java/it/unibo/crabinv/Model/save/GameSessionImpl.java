package it.unibo.crabinv.Model.save;

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
    private static final double STARTING_SPEED = 0.01; //TODO rendere ENUM
    private static final int STARTING_FIRE_RATE = 1; //TODO rendere ENUM
    public static final int STARTING_PLAYER_HEALTH = 3; //TODO rendere ENUM

    private int currentLevel;
    private boolean gameOver;
    private final long startingTimeStamp;
    private int currency;
    private int playerHealth;
    private double playerSpeed;
    private int playerFireRate;


    /**
     * LOAD_GAME Constructor, to be used in a Controller to load an existing GameSession
     * @param currency the currency to assign to the player of the GameSession
     * @param playerHealth the Health to assign to the player of the GameSession
     * @param playerSpeed the Speed to assign to the player of the GameSession
     * @param playerFireRate the FireRate to assign to the player of the GameSession
     */
    public GameSessionImpl(int currency, int playerHealth, int playerSpeed, int playerFireRate){
        this.currentLevel = StartingSaveValues.LEVEL.getIntValue();
        this.gameOver = false;
        this.startingTimeStamp = Instant.now().toEpochMilli();
        this.currency = currency;
        this.playerHealth = StartingSaveValues.PLAYER_HEALTH.getIntValue();
        this.playerSpeed = StartingSaveValues.SPEED.getDoubleValue();
        this.playerFireRate = StartingSaveValues.FIRE_RATE.getIntValue();
    }

    /**
     * NEW_GAME Constructor, to be used in a Controller to create a new GameSession
     * @param playerHealth the Health to assign to the player of the GameSession
     * @param playerSpeed the Speed to assign to the player of the GameSession
     * @param playerFireRate the FireRate to assign to the player of the GameSession
     */
    public GameSessionImpl(int playerHealth, int playerSpeed, int playerFireRate){
        this.currentLevel = StartingSaveValues.LEVEL.getIntValue();
        this.gameOver = false;
        this.startingTimeStamp = Instant.now().toEpochMilli();
        this.currency = StartingSaveValues.CURRENCY.getIntValue();
        this.playerHealth = StartingSaveValues.PLAYER_HEALTH.getIntValue();
        this.playerSpeed = StartingSaveValues.SPEED.getDoubleValue();
        this.playerFireRate = StartingSaveValues.FIRE_RATE.getIntValue();
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

    @Override
    public double getPlayerSpeed() {
        return this.playerSpeed;
    }

    @Override
    public int getPlayerFireRate() {
        return this.playerFireRate;
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
        this.playerHealth += amount;
    }

    /** {@inheritDoc} */
    @Override
    public void subPlayerHealth(int amount) {
        this.playerHealth = DomainUtils.subClampedToZero(this.playerHealth, amount);
    }

    public void applyPowerUps(UserProfile profile) {
        for (String powerUpName : profile.getPowerUpList()){
            int powerUpLevel = profile.getPowerUpLevel(powerUpName);
            PowerUpType type = PowerUpType.fromName(powerUpName);
            if (powerUpLevel > 0 && type != null) {
                switch (type) {
                    case SPEED_UP ->
                            this.playerSpeed =
                                    StartingSaveValues.SPEED.getDoubleValue() * type.getStatMultiplier() * powerUpLevel;
                    case HEALTH_UP ->
                            this.playerHealth =
                                    StartingSaveValues.PLAYER_HEALTH.getIntValue() * (int) type.getStatMultiplier() * powerUpLevel;
                    case FIRERATE_UP ->
                            this.playerFireRate =
                                    StartingSaveValues.FIRE_RATE.getIntValue() * (int) type.getStatMultiplier() * powerUpLevel;
                }
            }
        }
    }

}
