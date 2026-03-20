package it.unibo.crabinv.model.core.save;

import it.unibo.crabinv.model.entities.player.Player;

/**
 * {@code enum} of the starting values of the {@link GameSession}'s {@link Player}.
 */
public enum StartingSaveValues {
    LEVEL(1),
    CURRENCY(0),
    BASE_LEVEL_POWER_UP(1);

    private final int value;

    /**
     * Creates the enum constant.
     *
     * @param value the value to associate with the constant
     */
    StartingSaveValues(final int value) {
        this.value = value;
    }

    /**
     * Returns the value of the selected constant.
     *
     * @return the value of the selected constant.
     */
    public int getIntValue() {
        return value;
    }
}
