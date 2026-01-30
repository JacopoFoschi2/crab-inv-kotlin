package it.unibo.crabinv.Model.save;

import java.util.*;

/** {@inheritDoc} */
public class UserProfileImpl implements UserProfile {

    private static final int STARTING_CURRENCY = 0;

    private int currency;
    private final Map<String, Integer> powerUpLevels = new LinkedHashMap<>();

    public UserProfileImpl() {
        this.currency = STARTING_CURRENCY;
    }

    /** {@inheritDoc} */
    @Override
    public int getCurrency() {
        return this.currency;
    }

    /** {@inheritDoc} */
    @Override
    public void addCurrency(int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        this.currency += amount;
    }

    /** {@inheritDoc} */
    @Override
    public void subCurrency(int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        DomainUtils.requireNonNegativeSubtraction(this.currency, amount);
        this.currency -= amount;
    }

    /** {@inheritDoc} */
    @Override
    public int getPowerUpLevel(String powUpName) {
        return this.powerUpLevels.getOrDefault(powUpName, 0);
    }

    /** {@inheritDoc} */
    @Override
    public void updatePowerUp(String powUpName, int level) {
        DomainUtils.requireNonNegativeAmount(level);
        this.powerUpLevels.put(powUpName, level);
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getPowerUpList (){
        return new ArrayList<>(powerUpLevels.keySet());
    }
}
