package it.unibo.crabinv.model.save;

import java.time.Instant;

/**
 * Implementation of {@link GameSession},
 * tracks the state of a single attempt (level, currency, player health and start timestamp).
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
     * Constructor, to be used in a controller to load an existing GameSession.
     *
     * @param currency the currency to assign to the player of the GameSession
     * @param playerHealth the Health to assign to the player of the GameSession
     * @param playerSpeed the Speed to assign to the player of the GameSession
     * @param playerFireRate the FireRate to assign to the player of the GameSession
     */
    public GameSessionImpl(final int currency,
                           final double playerHealth,
                           final double playerSpeed,
                           final double playerFireRate) {
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
    public final int getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public final int getNextLevel() {
        return this.currentLevel + 1;
    }

    @Override
    public final boolean isGameOver() {
        return this.gameOver;
    }

    @Override
    public final boolean isGameWon() {
        return this.gameWon;
    }

    @Override
    public final long getStartingTimeStamp() {
        return this.startingTimeStamp;
    }

    @Override
    public final int getCurrency() {
        return this.currency;
    }

    @Override
    public final int getPlayerHealth() {
        return this.playerHealth;
    }

    @Override
    public final double getPlayerSpeed() {
        return this.playerSpeed;
    }

    @Override
    public final int getPlayerFireRate() {
        return this.playerFireRate;
    }

    @Override
    public final void advanceLevel() {
        this.currentLevel++;
    }

    @Override
    public final void markGameOver() {
        this.gameOver = true;
    }

    @Override
    public final void markGameWon() {
        this.gameWon = true;
    }

    @Override
    public final void addCurrency(final int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        this.currency += amount;
    }

    @Override
    public final void subCurrency(final int amount) {
        DomainUtils.requireNonNegativeSubtraction(this.currency, amount);
        this.currency -= amount;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Also clamps the result to STARTING_PLAYER_HEALTH
     */
    @Override
    public final void addPlayerHealth(final int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        this.playerHealth += amount;
    }

    @Override
    public final void subPlayerHealth(final int amount) {
        this.playerHealth = DomainUtils.subClampedToZero(this.playerHealth, amount);
    }

}
