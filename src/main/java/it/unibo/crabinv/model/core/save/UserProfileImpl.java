package it.unibo.crabinv.model.core.save;

import it.unibo.crabinv.model.powerups.PowerUpType;

import java.util.Map;

/**
 * Implementation of {@link UserProfile}.
 */
public class UserProfileImpl implements UserProfile {

    private Map<PowerUpType, Integer> powerUpMap;
    private int currency;

    /**
     * Constructor of {@link UserProfileImpl}.
     *
     * @param powerUpMap the power up map associated with this User Profile
     */
    public UserProfileImpl(final Map<PowerUpType, Integer> powerUpMap) {
        this.powerUpMap = initPowerUpMap(powerUpMap);
        this.currency = StartingSaveValues.CURRENCY.getIntValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getCurrency() {
        return this.currency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addCurrency(final int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        this.currency += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void subCurrency(final int amount) {
        DomainUtils.requireNonNegativeAmount(amount);
        DomainUtils.requireNonNegativeSubtraction(this.currency, amount);
        this.currency -= amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPowerUpLevel(final PowerUpType powerUpType) {
        return this.powerUpMap.get(powerUpType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updatePowerUp(final PowerUpType powerUpType, final int level) {
        DomainUtils.requireNonNegativeAmount(level);
        this.powerUpMap.put(powerUpType, level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double applyMultiplyPowerUp(final PowerUpType powerUpType) {
        return PlayerBaseStats.getDoubleValueOf(powerUpType) * powerUpType.getStatMultiplier() * getLevel(powerUpType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double applyDividePowerUp(final PowerUpType powerUpType) {
        return PlayerBaseStats.getDoubleValueOf(powerUpType) / (powerUpType.getStatMultiplier() * getLevel(powerUpType));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double applyAddPowerUp(final PowerUpType powerUpType) {
        return PlayerBaseStats.getDoubleValueOf(powerUpType) + powerUpType.getStatMultiplier() * getLevel(powerUpType);
    }

    /**
     * Returns the level associated with the power up of the power up map of the {@link UserProfile}.
     *
     * @param powerUpType the power up type
     * @return the level of the power up
     */
    private int getLevel(final PowerUpType powerUpType) {
        return this.powerUpMap.get(powerUpType);
    }

    /**
     * Initializes or validates a powerUpMap.
     *
     * @param newPowerUpMap the existing powerUpMap to validate, can be null
     * @return the new validated powerUpMap
     */
    private Map<PowerUpType, Integer> initPowerUpMap(final Map<PowerUpType, Integer> newPowerUpMap) {
        final Map<PowerUpType, Integer> validMap = new java.util.EnumMap<>(PowerUpType.class);
        if (newPowerUpMap.isEmpty()) {
            for (final PowerUpType type : PowerUpType.values()) {
                validMap.put(type, StartingSaveValues.BASE_LEVEL_POWER_UP.getIntValue());
            }
        } else {
            for (final PowerUpType type : PowerUpType.values()) {
                Integer level = (newPowerUpMap.get(type) == null) ? null : newPowerUpMap.get(type);
                level = level != null && level >= StartingSaveValues.BASE_LEVEL_POWER_UP.getIntValue()
                                ? level
                                : StartingSaveValues.BASE_LEVEL_POWER_UP.getIntValue();
                validMap.put(type, level);
                updatePowerUpMap(validMap);
            }
        }
        return validMap;
    }

    /**
     * Updates the power up map of the {@link UserProfile}.
     *
     * @param newPowerUpMap the power up map used to update the current one
     */
    private void updatePowerUpMap(final Map<PowerUpType, Integer> newPowerUpMap) {
        this.powerUpMap = newPowerUpMap;
    }
}
