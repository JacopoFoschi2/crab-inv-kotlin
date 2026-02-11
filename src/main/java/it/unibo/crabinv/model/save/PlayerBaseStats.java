package it.unibo.crabinv.model.save;

import it.unibo.crabinv.model.powerups.PowerUpType;

public enum PlayerBaseStats {
    SPEED(0.01, PowerUpType.SPEED_UP),
    FIRE_RATE(8, PowerUpType.FIRERATE_UP),
    PLAYER_HEALTH(2, PowerUpType.HEALTH_UP);

    private final double value;
    private final PowerUpType powerUpType;

    PlayerBaseStats(double value, PowerUpType powerUpType) {
        this.value = value;
        this.powerUpType = powerUpType;
    }

    public int getIntValue() {
        return (int) this.value;
    }

    public double getDoubleValue() {
        return this.value;
    }

    public PowerUpType getPowerUpType(){
        return this.powerUpType;
    }

    public static double getDoubleValueOf(PowerUpType powerUpType){
        for (PlayerBaseStats stat : values()){
            if (stat.powerUpType == powerUpType){
                return stat.getDoubleValue();
            }
        }
        throw new IllegalArgumentException("PowerUpType " + powerUpType + " is not associated to any PlayerBaseStat");
    }

    public static int getIntValueOf(PowerUpType powerUpType){
        return (int) getDoubleValueOf(powerUpType);
    }

}


