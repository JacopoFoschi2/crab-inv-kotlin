package it.unibo.crabinv.Model;

public interface PowerUp {
    public int getCost();
    public int getLevel();
    public int getMaxLevel();
    public void incrementLevel();
    public String getName();
}
