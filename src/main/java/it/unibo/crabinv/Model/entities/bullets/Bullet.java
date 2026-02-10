package it.unibo.crabinv.Model.entities.bullets;

import it.unibo.crabinv.Model.entities.entity.Entity;
import it.unibo.crabinv.Model.entities.entity.EntitySprites;
import it.unibo.crabinv.Model.entities.entity.Movable;

/**
 * Provides all bullet implementations of all the methods they should implement by combining Entity and Movable.
 */
public interface Bullet extends Entity, Movable {
    /**
     * Getter of the sprite.
     *
     * @return the image path to the sprite
     */
    EntitySprites getSprites();

}
