package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Controller.entity.EntityAbstractController;
import it.unibo.crabinv.Model.entity.Delta;

import java.util.ArrayList;
import java.util.List;

public class HardCodedWave extends EntityAbstractController implements Wave{
    private final List<EnemyType> enemyTypes;
    private final List<Enemy> activeEnemies = new ArrayList<>();
    private final EnemyFactory enemyFactory;
    private boolean isSpawned = false;
    private final RewardsService rewardsService;


    public HardCodedWave(List<EnemyType> enemyTypes, EnemyFactory enemyFactory, RewardsService rewardsService) {
        this.enemyTypes = enemyTypes;
        this.enemyFactory = enemyFactory;
        this.rewardsService = rewardsService;
    }


    private void spawnIfNeeded(){
        if(!isSpawned){
            enemyTypes.forEach(type -> activeEnemies.add(enemyFactory.createEnemy(type,1 ,0.0 ,0.0,1,0.25)));
            isSpawned = true;
        }
    }


    @Override
    public void tickLogicUpdate() {

    }

    @Override
    public List<Enemy> getAliveEnemies() {
        return List.of();
    }

    @Override
    public boolean isWaveFinished() {
        return activeEnemies.stream().noneMatch(Enemy::isAlive);
    }

    //TODO: Jhonny this is to make the movement, it comes from EntityAbstract controller try to implement it as it is
    @Override
    public void update(Delta delta) {
        super.update(delta);

        activeEnemies.removeIf(enemy -> {
            if (!enemy.isAlive()) {
                rewardsService.rewardEnemyDeath(enemy);
                return true;
            }
            return false;
        });

    }
}
