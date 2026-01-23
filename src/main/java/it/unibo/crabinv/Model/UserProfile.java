package it.unibo.crabinv.Model;

public interface UserProfile {
    public int getCurrentPlayerCurrency();
    public void subtractCurrency( int requiredCurrency);
    public void increaseCurrency(int currency);
    public boolean hasPowerUp(PowerUp powerUP);
    public void updatePowerUp(PowerUp powerUp);
}
