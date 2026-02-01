package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Controller.entity.EntityAbstractController;
import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.entity.Movable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WaveImpl extends EntityAbstractController implements Wave {

    private final List<EnemyType> enemyTypes;
    private final List<Enemy> activeEnemies = new ArrayList<>();
    private final EnemyFactory enemyFactory;
    private final RewardsService rewardsService;

    //temp usando x e y javaFX per sviluppo, da correggere quando pronto
    // JavaFX = Y dall'alto verso il basso
    double TEMP_SPAWN_Y = 100;
    double TEMP_BOTTOM_Y = 800;
    double TEMP_SPAWN_X = 10;
    double TEMP_SPACING_X = 10;

    private final double spawnY; //is also the upper bound of Y
    private final double spawnX;
    private final double spawnXSpacing;
    private final double bottomY;
    private boolean spawned;


    /**
     * Constructor with default (CONSTANT) values
     * @param enemyTypes
     * @param enemyFactory
     * @param rewardsService
     */
    public WaveImpl(List<EnemyType> enemyTypes,
                    EnemyFactory enemyFactory,
                    RewardsService rewardsService) {

        this.enemyTypes = List.copyOf(Objects.requireNonNull(enemyTypes,"enemyTypes cannot be null"));
        this.enemyFactory = Objects.requireNonNull(enemyFactory, "enemyFactory cannot be null");
        this.rewardsService = Objects.requireNonNull(rewardsService, "rewardsService cannot be null");

        this.spawnY = TEMP_SPAWN_Y;
        this.spawnX = TEMP_SPAWN_X;
        this.spawnXSpacing = TEMP_SPACING_X;
        this.bottomY = TEMP_BOTTOM_Y;
        this.spawned = false;
    }

    private void spawnIfNeeded(){ //Adapted from MoseBarbieri
        if(!this.spawned){
            double x = spawnX;
            for (EnemyType type : enemyTypes){
                activeEnemies.add(enemyFactory.createEnemy(type, x, spawnY));
                x += spawnXSpacing;
            }
            this.spawned = true;
        }

    }

    private void updateMovement(){
        update(Delta.INCREASE);
    }

    private void cleanUpAndRewards(){ //Adapted from MoseBarbieri
        activeEnemies.removeIf(enemy -> {
            if (!enemy.isAlive()) {
                rewardsService.rewardEnemyDeath(enemy);
                return true;
            }
            return false;
        });
    }

    @Override
    public void tickLogicUpdate() {
        spawnIfNeeded();
        updateMovement();
        cleanUpAndRewards();
    }

    @Override
    public List<Enemy> getAliveEnemies() {
        return List.copyOf(activeEnemies);
    }

    @Override
    public boolean isWaveFinished() {
        return spawned && activeEnemies.isEmpty();
    }

    @Override
    public void update(Delta delta){
        for (Enemy enemy : activeEnemies){
            if (enemy instanceof Movable movableEnemy){
                movableEnemy.move(delta, spawnY, bottomY);
            }
        }
    }

}
