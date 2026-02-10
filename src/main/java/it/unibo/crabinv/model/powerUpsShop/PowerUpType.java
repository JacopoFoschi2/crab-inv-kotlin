package it.unibo.crabinv.model.powerUpsShop;

import it.unibo.crabinv.model.core.i18n.TextKeys;

/**
 * It's the powerTypes of the powerUps.
 */
public enum PowerUpType {
    SPEED_UP(0.25, TextKeys.SPEED_DESC),
    FIRERATE_UP(0.1, TextKeys.FIRERATE_DESC),
    HEALTH_UP(1, TextKeys.HEALTH_DESC);

    private final double multiplier;
    private final TextKeys description;

    /**
     * It's the constructor fo the PowerUpType.
     *
     * @param multiplier the multiplier for the power up
     * @param description the description for each power up
     */
    PowerUpType(final double multiplier, final TextKeys description) {
        this.multiplier = multiplier;
        this.description = description;
    }

    /**
     * Method to get the number to modify the stat.
     *
     * @return the number to modify the stat
     */
    public double getStatMultiplier() {
        return multiplier;
    }

    /**
     * Method that returns the description of the power up.
     *
     * @return the description of the single power up
     */
    public TextKeys getDescription() {
        return description;
    }

    /**
     * From the name takes the value.
     *
     * @param name the name of the powerUp
     *
     * @return the {@link PowerUpType}
     */
    public static PowerUpType fromName(final String name) {
        try {
            return PowerUpType.valueOf(name);
        } catch (final IllegalArgumentException e) {
            return null;
        }
    }
}
