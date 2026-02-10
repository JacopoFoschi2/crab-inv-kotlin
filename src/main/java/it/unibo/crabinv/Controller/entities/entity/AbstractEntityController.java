package it.unibo.crabinv.Controller.entities.entity;

import it.unibo.crabinv.Model.entities.entity.Entity;

/**
 * Provides the methods an EntityController should implement.
 *
 * @param <T> the {@link Entity} implementation the controller should control
 */
public abstract class AbstractEntityController<T extends Entity> implements EntityController {
    private final T entity;

    /**
     * Links the controller to its referred entity.
     *
     * @param entity an entity implementation
     */
    public AbstractEntityController(final T entity) {
        this.entity = entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return getEntity().isAlive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeDamage(final int damage) {
        getEntity().takeDamage(damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHealth() {
        return getEntity().getHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxHealth() {
        return getEntity().getMaxHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getX() {
        return getEntity().getX();
    }

    /**
     * {@inheritDoc}
     */
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
