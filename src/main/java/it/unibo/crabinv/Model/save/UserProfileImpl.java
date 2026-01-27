package it.unibo.crabinv.Model.Save;

import java.util.HashMap;
import java.util.Map;

public class UserProfileImpl implements UserProfile {

    private static final int STARTING_CURRENCY = 0;

    private int currency;
    private final Map<String, Integer> powerUpLevels = new HashMap<>();

    public UserProfileImpl() {
        this.currency = STARTING_CURRENCY;
    }

    @Override
    public int getCurrency() {
        return this.currency;
    }

    @Override
    public void addCurrency(int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        this.currency += amount;
    }

    @Override
    public void subCurrency(int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        DomainUtils.requireNonNegativeSubtraction(this.currency, amount);
        this.currency -= amount;
    }

    @Override
    public int getPowerUpLevel(String powUpName) {
        return this.powerUpLevels.getOrDefault(powUpName, 0);
    }

    @Override
    public void updatePowerUp(String powUpName, int level) {
        DomainUtils.requireNonNegativeAmount(level);
        this.powerUpLevels.put(powUpName, level);
    }
}
