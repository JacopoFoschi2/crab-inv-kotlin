package it.unibo.crabinv.Model.PowerUpsShop;

public enum PowerUpType {
    SPEED_UP (0.25),
    FIRERATE_UP(0.10),
    HEALTH_UP(1);

    final double multiplier;
    PowerUpType(double multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Method to get the number to modify the stat
     * @return the number to modify the stat
     */
    public double getStatMultiplier(){
        return multiplier;
    };

    public static PowerUpType fromName(String name) {
        try {
            return PowerUpType.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
