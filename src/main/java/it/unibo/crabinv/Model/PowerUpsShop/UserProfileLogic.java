package it.unibo.crabinv.Model.PowerUpsShop;

import it.unibo.crabinv.Model.Save.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class UserProfileLogic implements UserProfile {
    private int currentPlayerCurrency;
    private final Map<String, PowerUp> ownedPowerUps = new HashMap<>();

    @Override
    public int getCurrentPlayerCurrency() {
        return this.currentPlayerCurrency;
    }

    @Override
    public void subtractCurrency(int requiredCurrency) {
        if (this.currentPlayerCurrency >= requiredCurrency) {
            this.currentPlayerCurrency =- requiredCurrency;
        }
        else {
            System.out.println("Not enough Currency! : " + this.currentPlayerCurrency );
        }
    }

    @Override
    public void hasPowerUp(PowerUp powUp, int level) {

    }

    @Override
    public void updatePowerUp(PowerUp powUp, int level) {

    }

    @Override
    public void increaseCurrency(int currency) {
        this.currentPlayerCurrency =+ currency;
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
