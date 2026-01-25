package it.unibo.crabinv.Model.PowerUpsShop;

import it.unibo.crabinv.Model.Save.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class UserProfileLogic implements UserProfile {
    private int currentPlayerCurrency;
    private final Map<String, PowerUp> ownedPowerUps = new HashMap<>();
    private final Map<String, Integer> powerUpLevel = new HashMap<>();

    public UserProfileLogic( int currentPlayerCurrency) {
        this.currentPlayerCurrency = currentPlayerCurrency;

    }

    @Override
    public int getCurrentPlayerCurrency() {
        return this.currentPlayerCurrency;
    }

    @Override
    public void increaseCurrency(int currency) {
        this.currentPlayerCurrency += currency;
    }

    @Override
    public void subtractCurrency(int requiredCurrency) {
        if (this.currentPlayerCurrency >= requiredCurrency) {
            this.currentPlayerCurrency -= requiredCurrency;
        }
        else {
            System.out.println("Not enough Currency! : " + this.currentPlayerCurrency );
        }
    }

    @Override
    public int getPowerUpLevel(String powUpName) {
        return this.powerUpLevel.getOrDefault(powUpName, 0);
    }

    @Override
    public void updatePowerUp(String powUpName, int level) {
        this.powerUpLevel.put(powUpName,level);
    }


/*
    @Override
    public boolean hasPowerUp(PowerUp powerUP) {
        if (ownedPowerUps.containsKey(powerUP.getPowerUpName()) &&
                ownedPowerUps.get(powerUP.getPowerUpName()).getLevel() > 0){
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void updatePowerUp(PowerUp powerUp) {
        ownedPowerUps.put(powerUp.getPowerUpName(),powerUp);
    }
 */
}
