package it.unibo.crabinv.Model.save;

public enum StartingSaveValues {
    LEVEL(1),
    CURRENCY(0),
    SPEED(0.01),
    FIRE_RATE(1),
    PLAYER_HEALTH(3);

    private final double value;
    StartingSaveValues(double value) {
        this.value = value;
    }

    public int getIntValue() {
        return (int) value;
    }

    public double getDoubleValue() {
        return value;
    }
}


