package it.unibo.crabinv.Controller.entity;

import it.unibo.crabinv.Model.entity.Entity;

/**
 * Provides the methods an EntityController should implement.
 */
public abstract class EntityAbstractController<T extends Entity> implements EntityController {
    private final T entity;

    public EntityAbstractController(T entity) {
        this.entity = entity;
    }

    @Override
    public boolean isAlive() {
        return entity.isAlive();
    }

    @Override
    public void onCollisionWith(Entity other) {
        entity.onCollisionWith(other);
    }

    @Override
    public int getHealth() {
        return entity.getHealth();
    }

    @Override
    public int getMaxHealth() {
        return entity.getMaxHealth();
    }

    @Override
    public double getX() {
        return entity.getX();
    }

    @Override
    public double getY() {
        return entity.getY();
    }

    protected T getEntity() {
        return entity;
    }
}
