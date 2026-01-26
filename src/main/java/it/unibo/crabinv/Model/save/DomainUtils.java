package it.unibo.crabinv.Model.save;

/**
 * Helper methods for save related classes
 */

public final class DomainUtils {

    /**
     * Ensures that {@code amount} is non‑negative.
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public static void requireNonNegativeAmount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative: amount:" + amount);
        }
    }

    public static void requireNonNegativeSubtraction(int initialAmount, int subAmount){
        int result = initialAmount-subAmount;
        if (result < 0){
            throw new IllegalArgumentException("result would be negative: result:" + result);
        }
    }

    private DomainUtils(){
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
        DomainUtils.requireNonNegativeAmount(amountToSub);
        return Math.max(0, currentAmount - amountToSub);
    }
}

