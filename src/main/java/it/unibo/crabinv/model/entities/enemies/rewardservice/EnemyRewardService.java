package it.unibo.crabinv.model.entities.enemies.rewardservice;

import it.unibo.crabinv.model.entities.enemies.Enemy;
import it.unibo.crabinv.model.save.GameSession;

/**
 * It's the implementation of the reward service.
 */
public final class EnemyRewardService implements RewardsService {
    private final GameSession profile;

    /**
     * It's the constructor of the reward service.
     *
     * @param profile it's the profile needed to give the reward
     */
    public EnemyRewardService(final GameSession profile) {
        this.profile = profile;
    }

    /**
     * {@inheritDoc}
     *
     * @param enemy the dead enemy
     */
    @Override
    public void rewardEnemyDeath(final Enemy enemy) {
        profile.addCurrency(enemy.getReward());
    }
}
