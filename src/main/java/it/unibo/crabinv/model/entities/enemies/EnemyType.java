package it.unibo.crabinv.model.entities.enemies;

/**
 * It's the enum of the type of the enemies.
 */
public enum EnemyType {
    SERVANT(10);
    private final int rewardForKill;

    /**
     * The constructor to give the enemy reward.
     *
     * @param rewardForKill it's the reward at the death
     */
    EnemyType(final int rewardForKill) {
        this.rewardForKill = rewardForKill;
    }

    /**
     * The getter for the reward for the kill.
     *
     * @return rewardForKill
     */
    public int getRewardForKill() {
        return rewardForKill;
    }
}
