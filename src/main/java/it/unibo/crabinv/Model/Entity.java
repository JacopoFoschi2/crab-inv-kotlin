package it.unibo.crabinv.Model;


import it.unibo.crabinv.Model.Utils.Position;

public interface Entity {
    int getHealthPoints();
    boolean isAlive();
    Position getCoordinates();

    /**
     * Manages the impact with another entity
     * @param otherEntity the entity that collides with it
     */

    void onCollisionWith(Entity otherEntity);
}
