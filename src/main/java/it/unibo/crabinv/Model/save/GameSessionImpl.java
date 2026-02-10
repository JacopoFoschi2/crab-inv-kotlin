package it.unibo.crabinv.Model.save;

import it.unibo.crabinv.Model.powerUpsShop.PowerUpType;

import java.time.Instant;

/**
 * Implementation of {@link GameSession}
 * Tracks the state of a single attempt (level, currency, player health and start timestamp).
 *
 * <p>The start timestamp is fixed at construction time; all updates to
 * level, currency and health are constrained to non‑negative values.
 */
public class GameSessionImpl implements GameSession {

    private int currentLevel;
    private boolean gameOver;
    private boolean gameWon;
    private final long startingTimeStamp;
    private int currency;
    private int playerHealth;
    private final double playerSpeed;
    private final int playerFireRate;

    /**
     * Constructor, to be used in a Controller to load an existing GameSession
     * @param currency the currency to assign to the player of the GameSession
     * @param playerHealth the Health to assign to the player of the GameSession
     * @param playerSpeed the Speed to assign to the player of the GameSession
     * @param playerFireRate the FireRate to assign to the player of the GameSession
     */
    public GameSessionImpl(int currency, double playerHealth, double playerSpeed, double playerFireRate){
        this.currentLevel = StartingSaveValues.LEVEL.getIntValue();
        this.gameOver = false;
        this.gameWon = false;
        this.startingTimeStamp = Instant.now().toEpochMilli();
        this.currency = currency;
        this.playerHealth = (int) playerHealth;
        this.playerSpeed = playerSpeed;
        this.playerFireRate = (int) playerFireRate;
    }

    @Override
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public int getNextLevel() {
        return this.currentLevel + 1;
    }

    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }

    @Override
    public boolean isGameWon() {
        return this.gameWon;
    }

    @Override
    public long getStartingTimeStamp() {
        return this.startingTimeStamp;
    }

    @Override
    public int getCurrency() {
        return this.currency;
    }

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

    @Override
    public void advanceLevel() {
        this.currentLevel++;
    }

    @Override
    public void markGameOver() {
        this.gameOver = true;
    }

    @Override
    public void markGameWon() {
        this.gameWon = true;
    }

    @Override
    public void addCurrency(int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        this.currency += amount;
    }

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

    @Override
    public void subPlayerHealth(int amount) {
        this.playerHealth = DomainUtils.subClampedToZero(this.playerHealth, amount);
    }

}
