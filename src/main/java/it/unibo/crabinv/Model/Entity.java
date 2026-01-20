package it.unibo.crabinv.Model;


import it.unibo.crabinv.Model.Utils.Position;

public interface Entity {
    public int getHealthPoints();
    public boolean isAlive();
    public Position getCoordinates();

    /**
     * Manages the impact with another entity
     * @param otherEntity the entity that collides with it
     */

    public void onCollisionWith(Entity otherEntity);
}
