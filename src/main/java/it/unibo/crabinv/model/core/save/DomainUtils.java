package it.unibo.crabinv.model.core.save;

/**
 * Helper methods for save related classes.
 */
public final class DomainUtils {

    /**
     * Private constructor to avoid construction.
     */
    private DomainUtils() {
    }

    /**
     * Ensures that {@code amount} is non‑negative.
     *
     * @param amount the amount to check
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public static void requireNonNegativeAmount(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative: amount:" + amount);
        }
    }

    /**
     * Ensures that the subtraction between {@code initialAmount} and {@code subAmount} is not negative.
     *
     * @param initialAmount the initial amount
     * @param subAmount     the amount to sub
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public static void requireNonNegativeSubtraction(final int initialAmount, final int subAmount) {
        final int result = initialAmount - subAmount;
        if (result < 0) {
            throw new IllegalArgumentException("result would be negative: result:" + result);
        }
    }

    /**
     * Subtracts {@code amountToSub} from {@code currentAmount} but never goes below zero (clamped).
     *
     * @param currentAmount the current value
     * @param amountToSub   the amount to subtract (must be ≥0)
     * @return the result clamped to zero
     */
    public static int subClampedToZero(final int currentAmount, final int amountToSub) {
        requireNonNegativeAmount(amountToSub);
        return Math.max(0, currentAmount - amountToSub);
    }
}
