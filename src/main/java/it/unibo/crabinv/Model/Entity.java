package it.unibo.crabinv.Model;


import it.unibo.crabinv.Model.Utils.Position;

public interface Entity {

    /**
     *
     * @return the healthPoints of the entity
     */
    int getHealthPoints();

    /**
     *
     * @return the state of the entity, if it is alive or not
     */
    boolean isAlive();

    /**
     *
     * @return the Position of the entity
     */
    Position getCoordinates();

    /**
     * Manages the impact with another entity
     * @param otherEntity the entity that collides with it
     */

    void onCollisionWith(Entity otherEntity);
}
