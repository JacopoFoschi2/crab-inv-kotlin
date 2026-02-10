package it.unibo.crabinv.controller.core.collision;

import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.core.collisions.CollisionSystem;
import it.unibo.crabinv.model.entities.entity.Entity;

import java.util.List;

/**
 * Provides the method {@code resolve()} to resolve collisions across entities.
 */
public class CollisionController {
    private final CollisionSystem collisionSystem;
    private final AudioController audio;

    /**
     * @param audioController the pre-existing instance of audioController
     */
    public CollisionController(final AudioController audioController) {
        this.audio = audioController;
        this.collisionSystem = new CollisionSystem();
    }

    /**
     * Calls the resolve method of CollisionSystem and plays sound if there has been a collision.
     *
     * @param entities the list of entities on screen
     */
    public void resolve(final List<Entity> entities) {
        if (collisionSystem.resolve(entities)) {
            audio.playSFX(SFXTracks.EXPLOSION);
        }
    }
}
