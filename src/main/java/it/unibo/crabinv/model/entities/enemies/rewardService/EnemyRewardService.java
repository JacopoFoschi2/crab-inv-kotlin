package it.unibo.crabinv.model.entities.enemies.rewardService;

import it.unibo.crabinv.model.entities.enemies.Enemy;
import it.unibo.crabinv.model.save.GameSession;

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
