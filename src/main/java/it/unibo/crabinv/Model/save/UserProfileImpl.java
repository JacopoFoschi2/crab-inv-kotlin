package it.unibo.crabinv.Model.save;

import it.unibo.crabinv.Model.PowerUpsShop.PowerUpType;

import java.util.Map;

/**
 * {@inheritDoc}
 */
public class UserProfileImpl implements UserProfile {

    private final Map<PowerUpType, Integer> powerUpMap;
    private int currency;

    public UserProfileImpl(Map<PowerUpType, Integer> powerUpMap) {
        this.powerUpMap = initPowerUpMap(powerUpMap);
        this.currency = StartingSaveValues.CURRENCY.getIntValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrency() {
        return this.currency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCurrency(int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        this.currency += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subCurrency(int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        DomainUtils.requireNonNegativeSubtraction(this.currency, amount);
        this.currency -= amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPowerUpLevel(PowerUpType powerUpType) {
        return this.powerUpMap.get(powerUpType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePowerUp(PowerUpType powerUpType, int level) {
        DomainUtils.requireNonNegativeAmount(level);
        this.powerUpMap.put(powerUpType, level);
    }

    @Override
    public double applyMultiplyPowerUp(PowerUpType powerUpType) {
        return PlayerBaseStats.getDoubleValueOf(powerUpType) * powerUpType.getStatMultiplier() * getLevel(powerUpType);
    }

    @Override
    public double applyAddPowerUp(PowerUpType powerUpType) {
        return PlayerBaseStats.getDoubleValueOf(powerUpType) + powerUpType.getStatMultiplier() * getLevel(powerUpType);
    }

    private int getLevel(PowerUpType powerUpType){
        return this.powerUpMap.get(powerUpType);
    }

    /**
     * Initializes or validates a powerUpMap
     * @param powerUpMap the existing powerUpMap to validate, can be null
     * @return the new validated powerUpMap
     */
    private Map<PowerUpType, Integer> initPowerUpMap(Map<PowerUpType, Integer> powerUpMap) {
        final Map<PowerUpType, Integer> validMap = new java.util.EnumMap<>(PowerUpType.class);
        if (powerUpMap.isEmpty()) {
            for (final PowerUpType type : PowerUpType.values()) {
                validMap.put(type, 0);
            }
        } else {
            for (final PowerUpType type : PowerUpType.values()) {
                Integer level = (powerUpMap.get(type) == null) ? null : powerUpMap.get(type);
                level = level != null && level >= 0 ? level : 0;
                validMap.put(type, level);
            }
        }
        return validMap;
    }

}
