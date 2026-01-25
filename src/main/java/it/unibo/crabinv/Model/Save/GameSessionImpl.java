package it.unibo.crabinv.Model.Save;

import java.time.Instant;

public class GameSessionImpl implements GameSession {

    private static final int STARTING_LEVEL = 1;
    private static final int STARTING_CURRENCY = 0;
    public static final int STARTING_PLAYER_HEALTH = 3; // ATTENZIONE: VALORE E VISIBILITA' PLACEHOLDER
    // manca logica di calcolo HP

    private int currentLevel;
    private boolean gameOver; //quando diventa true viene salvato tutto da SessionRecord
    private final long startingTimeStamp;
    private int currency;
    private int playerHealth;


    public GameSessionImpl() {
        this.currentLevel = STARTING_LEVEL;
        this.gameOver = false;
        this.startingTimeStamp = Instant.now().toEpochMilli();
        this.currency = STARTING_CURRENCY;
        this.playerHealth = STARTING_PLAYER_HEALTH;
    }

    private static void requireNonNegativeAmount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative!: amount:" + amount);
        }
    }

    private static int subClampedToZero(int currentAmount, int amountToSub) {
        requireNonNegativeAmount(amountToSub);
        return Math.max(0, currentAmount - amountToSub);
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
    public void advanceLevel() {
        this.currentLevel++;
    }

    @Override
    public void markGameOver() {
        this.gameOver = true;
    }

    @Override
    public void addCurrency(int amount) {
        requireNonNegativeAmount(amount);
        this.currency += amount;
    }

    @Override
    public void subCurrency(int amount) {
        this.currency = subClampedToZero(this.currency, amount);
    }

    @Override
    public void addPlayerHealth(int amount) {
        requireNonNegativeAmount(amount);
        this.playerHealth += amount;
    }

    @Override
    public void subPlayerHealth(int amount) {
        this.playerHealth = subClampedToZero(this.playerHealth, amount);
    }
}
