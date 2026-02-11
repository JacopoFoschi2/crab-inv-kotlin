package it.unibo.crabinv.model.entities.enemies;

/**
 * It's the identifier of the enemy.
 */
public enum EnemyType {
    SERVANT(10);

    private final int rewardForKill;

    /**
     * It's the constructor of the enemyType.
     *
     * @param rewardForKill the reward for the death of the enemy
     */
    EnemyType(final int rewardForKill) {
        this.rewardForKill = rewardForKill;
    }

    /**
     * Getter for the reward.
     *
     * @return the actual value for the reward
     */
    public int getRewardForKill() {
        return rewardForKill;
    }

}
