package it.unibo.crabinv.controller.core.collision

import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.collisions.CollisionSystem
import it.unibo.crabinv.model.entities.entity.Entity

/**
 * Provides the method `resolve()` to resolve collisions across entities.
 *
 * @param audio the pre-existing instance of [AudioController]
 */
class CollisionController(
    private val audio: AudioController,
) {
    private val collisionSystem = CollisionSystem()

    /**
     * Calls the resolve method of [CollisionSystem] and plays sound if there has been a collision.
     * @param entities the list of entities on screen
     */
    fun resolve(entities: List<Entity>) {
        if (collisionSystem.resolve(entities)) {
            audio.playSFX(SFXTracks.EXPLOSION)
        }
    }
}
