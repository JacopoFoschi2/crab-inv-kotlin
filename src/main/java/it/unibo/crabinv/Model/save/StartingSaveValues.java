package it.unibo.crabinv.Model.save;

public enum StartingSaveValues {
    LEVEL(1),
    CURRENCY(0);

    private final int value;

    StartingSaveValues(int value) {
        this.value = value;
    }

    public int getIntValue() {
        return value;
    }
}


