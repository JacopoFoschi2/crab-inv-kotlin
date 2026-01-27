package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.entity.Entity;

import java.util.List;

public class HardCodedWave implements Wave{
    private final List<Enemy> enemies;

    public HardCodedWave(List<Enemy>  enemies) {
        this.enemies  = enemies;
    }

    @Override
    public boolean isWaveFinished() {
        return enemies.stream().noneMatch(Entity::isAlive);
    }

}
