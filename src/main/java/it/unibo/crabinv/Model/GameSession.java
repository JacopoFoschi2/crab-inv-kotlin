package it.unibo.crabinv.Model;

public interface GameSession {
    public int getCurrentLevel();
    public boolean isGameOver();
    public int getNextLevel();
    public int getCurrency(); //valuta della run
    public int getPlayerHealth();
}

