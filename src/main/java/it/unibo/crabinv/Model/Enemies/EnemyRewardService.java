package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.save.GameSession;

public class EnemyRewardService implements RewardsService {
    private final GameSession profile;

    public  EnemyRewardService(GameSession profile){
        this.profile = profile;
    }

    @Override
    public void rewardEnemyDeath(Enemy enemy) {
        profile.addCurrency(enemy.getReward());
    }
}
