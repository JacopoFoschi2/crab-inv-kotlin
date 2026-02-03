package it.unibo.crabinv.Controller.entity;

import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.entity.Entity;

/**
 * Provides the methods an EntityController should absolutely implement in one of its overloads
 */
public class EntityAbstractController implements EntityController {
    @Override
    public void update(boolean firePressed, Delta delta) {

    }

    @Override
    public void update(Delta delta) {

    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void onCollisionWith(Entity other) {

    }

    @Override
    public int getHealth() {
        return 0;
    }

    @Override
    public int getMaxHealth() {
        return 0;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }
}
