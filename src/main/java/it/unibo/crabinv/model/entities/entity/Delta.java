package it.unibo.crabinv.model.entities.entity;

/**
 * Provides all the deltas that the entity that wishes to move should handle.
 * The selected delta affects the movement by either decreasing or increasing its coordinate in the axis.
 */
public enum Delta {
    DECREASE(-1),
    NO_ACTION(0),
    INCREASE(1);

    private final int value;

    Delta(final int value) {
        this.value = value;
    }

    /**
     * @return the value of delta
     */
    public int getValue() {
        return value;
    }
}
