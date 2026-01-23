package it.unibo.crabinv.Model;

public interface PowerUp {
    int getCost();
    int getLevel();
    int getMaxLevel();
    void incrementLevel();
    String getPowerUpName();
}
