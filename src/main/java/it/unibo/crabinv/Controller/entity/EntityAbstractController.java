package it.unibo.crabinv.Controller.entity;

import it.unibo.crabinv.Model.entity.Delta;

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
}
