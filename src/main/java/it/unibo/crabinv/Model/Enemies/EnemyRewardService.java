package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.save.UserProfile;

public class EnemyRewardService implements RewardsService {
    private final UserProfile profile;

    public  EnemyRewardService(UserProfile profile){
        this.profile = profile;
    }

    @Override
    public void rewardEnemyDeath(Enemy enemy) {
        profile.addCurrency(enemy.getReward());
    }
}
