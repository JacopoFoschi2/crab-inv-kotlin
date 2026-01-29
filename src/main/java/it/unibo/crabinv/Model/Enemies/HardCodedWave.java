package it.unibo.crabinv.Model.Enemies;

import java.util.ArrayList;
import java.util.List;

public class HardCodedWave implements Wave{
    private final List<EnemyType> enemyTypes;
    private final List<Enemy> activeEnemies = new ArrayList<>();
    private final EnemyFactory enemyFactory;
    private boolean isSpawned = false;


    public HardCodedWave(List<EnemyType> enemyTypes, EnemyFactory enemyFactory) {
        this.enemyTypes = enemyTypes;
        this.enemyFactory = enemyFactory;
    }


    private void spawnIfNeeded(){
        if(!isSpawned){
            //TODO: make this better and if needed transform it
            enemyTypes.forEach(type -> activeEnemies.add(enemyFactory.createEnemy(type,0 ,0 )));
            isSpawned = true;
        }
    }



    @Override
    public boolean isWaveFinished() {
        return activeEnemies.stream().noneMatch(Enemy::isAlive);
    }

}
