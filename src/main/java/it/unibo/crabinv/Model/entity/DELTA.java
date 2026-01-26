package it.unibo.crabinv.Model.entity;

public enum DELTA {
    DECREASE(-1),
    NO_ACTION(0),
    INCREASE(1);

    private final int value;
    DELTA(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
