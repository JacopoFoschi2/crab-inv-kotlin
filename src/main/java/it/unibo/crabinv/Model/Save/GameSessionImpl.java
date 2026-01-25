package it.unibo.crabinv.Model.Save;

import java.time.Instant;

public class GameSessionImpl implements GameSession {

    private int currentLevel;
    private int nextLevel;
    private boolean gameOver = false; //quando diventa true viene salvato tutto da SessionRecord
    private long startingTimeStamp;
    private int currency;
    private int playerHealth;

    private void setStartingTimeStamp(){ //Appena viene creato un nuovo tentativo
        this.startingTimeStamp = Instant.now().toEpochMilli();
    }

    @Override
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public int getNextLevel() {
        return this.nextLevel;
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
        this.currentLevel += 1;
        this.nextLevel += 1;
    }

    @Override
    public void setGameOver() {
        this.gameOver = true;
    }

    @Override
    public void addCurrency(int amount) {
        this.currency += amount;
    }

    @Override
    public void subCurrency(int amount) {
        this.currency -= amount;
    }

    @Override
    public void addPlayerHealth(int amount) {
        this.playerHealth += amount;

    }

    @Override
    public void subPlayerHealth(int amount) {
        this.playerHealth -= amount;
    }
}
