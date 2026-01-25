package it.unibo.crabinv.Model.Save;

/**
 * Helper methods for Save related classes
 */

public final class SaveUtils {

    private SaveUtils(){
    }

    /**
     * Ensures that {@code amount} is non‑negative.
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public static void requireNonNegativeAmount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative: amount:" + amount);
        }
    }
    /**
     * Subtracts {@code amountToSub} from {@code currentAmount} but never
     * goes below zero (clamped).
     *
     * @param currentAmount the current value
     * @param amountToSub   the amount to subtract (must be ≥0)
     * @return the result clamped to zero
     */
    public static int subClampedToZero(int currentAmount, int amountToSub) {
        SaveUtils.requireNonNegativeAmount(amountToSub);
        return Math.max(0, currentAmount - amountToSub);
    }
}

