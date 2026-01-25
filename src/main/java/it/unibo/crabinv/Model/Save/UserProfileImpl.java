package it.unibo.crabinv.Model.Save;

import java.util.HashMap;
import java.util.Map;

public class UserProfileImpl implements UserProfile {
    private int currentPlayerCurrency;
    private final Map<String, Integer> powerUpLevels = new HashMap<>();

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
            this.currentPlayerCurrency =- requiredCurrency;
        }
        else {
            System.out.println("Not enough Currency! : " + this.currentPlayerCurrency );
        }
    }

    @Override
    public int getPowerUpLevel(String powUpName) {
        return this.powerUpLevels.getOrDefault(powUpName, 0);
    }

    @Override
    public void updatePowerUp(String powUpName, int level) {
        this.powerUpLevels.put(powUpName,level);
    }

}
