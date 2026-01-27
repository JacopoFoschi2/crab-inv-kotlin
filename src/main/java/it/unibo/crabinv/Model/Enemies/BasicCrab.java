package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.entity.AbstractEntity;

public class BasicCrab extends AbstractEntity implements Enemy {
    private final EnemyType type;

    public BasicCrab(final EnemyType type, int maxHealth, double x, double y) {
        super(maxHealth, x, y);
        this.type = type;
    }

    @Override
    public EnemyType getEnemyType() {
        return this.type;
    }
}
