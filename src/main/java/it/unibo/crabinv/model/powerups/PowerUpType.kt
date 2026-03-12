package it.unibo.crabinv.model.powerups;

import it.unibo.crabinv.model.core.i18n.TextKeys;

/**
 * It describes the types of powerUps.
 */
public enum PowerUpType {
    SPEED_UP(0.25, TextKeys.SPEED_DESC),
    FIRERATE_UP(0.1, TextKeys.FIRERATE_DESC),
    HEALTH_UP(1, TextKeys.HEALTH_DESC);
    private final double multiplier;
    private final TextKeys description;

    /**
     * It's the constructor of the powerType.
     *
     * @param multiplier it's used for the value of the powerUp
     * @param description it's the description displayed for each power up
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

}
