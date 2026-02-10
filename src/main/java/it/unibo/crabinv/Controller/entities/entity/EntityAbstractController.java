package it.unibo.crabinv.Controller.entities.entity;

import it.unibo.crabinv.Model.entities.entity.Entity;

/**
 * Provides the methods an EntityController should implement.
 */
public abstract class EntityAbstractController<T extends Entity> implements EntityController {
    private final T entity;

    /**
     * Links the controller to its referred entity.
     *
     * @param entity an entity implementation
     */
    public EntityAbstractController(final T entity) {
        this.entity = entity;
    }

    @Override
    public boolean isAlive() {
        return getEntity().isAlive();
    }

    @Override
    public void takeDamage(int damage) {
        getEntity().takeDamage(damage);
    }

    @Override
    public int getHealth() {
        return getEntity().getHealth();
    }

    @Override
    public int getMaxHealth() {
        return getEntity().getMaxHealth();
    }

    @Override
    public double getX() {
        return getEntity().getX();
    }

    @Override
    public double getY() {
        return getEntity().getY();
    }

    /**
     * @return the instance of entity to access getters and methods from for controllers that implement this one.
     */
    protected T getEntity() {
        return entity;
    }
}
