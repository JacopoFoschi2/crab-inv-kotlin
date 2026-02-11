package it.unibo.crabinv.model.save;

import it.unibo.crabinv.model.powerups.PowerUpType;

/**
 * {@code enum} of all the admissible player stats.
 */
public enum PlayerBaseStats {
    SPEED(0.01, PowerUpType.SPEED_UP),
    FIRE_RATE(8, PowerUpType.FIRERATE_UP),
    PLAYER_HEALTH(2, PowerUpType.HEALTH_UP);

    private final double value;
    private final PowerUpType powerUpType;

    /**
     * Creates an element of {@code PlayerBaseStats}.
     *
     * @param value       the {@code double} value of the element
     * @param powerUpType the {@link PowerUpType} that can alter the stat
     */
    PlayerBaseStats(final double value, final PowerUpType powerUpType) {
        this.value = value;
        this.powerUpType = powerUpType;
    }

    /**
     * Returns the {@code double} value to {@link int} of the value associated with the {@code PowerUpType}.
     *
     * @param powerUpType the {@code PowerUpType} to look up
     * @return the associated {@code double} value
     */
    public static double getDoubleValueOf(final PowerUpType powerUpType) {
        for (final PlayerBaseStats stat : values()) {
            if (stat.powerUpType == powerUpType) {
                return stat.getDoubleValue();
            }
        }
        throw new IllegalArgumentException("PowerUpType " + powerUpType + " is not associated to any PlayerBaseStat");
    }

    /**
     * Returns the value cast to {@link int} of the value associated with the {@code PowerUpType}.
     *
     * @param powerUpType the {@code PowerUpType} to look up
     * @return the associated value cast to {@link int}
     */
    public static int getIntValueOf(final PowerUpType powerUpType) {
        return (int) getDoubleValueOf(powerUpType);
    }

    /**
     * Returns the {@code double} value selected.
     *
     * @return the {@code double} value selected
     */
    public double getDoubleValue() {
        return this.value;
    }
}
