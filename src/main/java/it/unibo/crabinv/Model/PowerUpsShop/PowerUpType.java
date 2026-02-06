package it.unibo.crabinv.Model.PowerUpsShop;

import it.unibo.crabinv.Model.core.i18n.TextKeys;

public enum PowerUpType {
    SPEED_UP (0.25, TextKeys.SPEED_DESC),
    FIRERATE_UP(0.10, TextKeys.FIRERATE_DESC),
    HEALTH_UP(1, TextKeys.HEALTH_DESC);

    final double multiplier;
    final TextKeys description;

    PowerUpType(double multiplier, TextKeys description) {
        this.multiplier = multiplier;
        this.description = description;
    }

    /**
     * Method to get the number to modify the stat
     * @return the number to modify the stat
     */
    public double getStatMultiplier(){
        return multiplier;
    };

    /**
     * Method that returns the description of the power up
     * @return the description of the single power up
     */
    public TextKeys getDescription(){return description;}

    public static PowerUpType fromName(String name) {
        try {
            return PowerUpType.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
