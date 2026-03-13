package it.unibo.crabinv.controller.entities.player

import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.entities.entity.AbstractEntityController
import it.unibo.crabinv.controller.entities.entity.EntityCapableOfInputController
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.engine.GameEngine
import it.unibo.crabinv.model.entities.entity.Delta
import it.unibo.crabinv.model.entities.player.Player

/**
 * Provides all the apis to control a [Player].
 * @param player the player
 * @param audio the audioController used to play sounds
 * @param engine the engine used to spawn bullets
 */
class PlayerController(
    player: Player?,
    private val audio: AudioController,
    private val engine: GameEngine,
) : AbstractEntityController<Player?>(player),
    EntityCapableOfInputController {
    override fun update(
        firePressed: Boolean,
        delta: Delta,
    ) {
        tick()
        move(delta)
        if (firePressed) {
            shoot()
        }
    }

    val speed: Double
        /**
         * @return the speed of the player
         */
        get() = super.entity!!.speed

    /**
     * Tells the player to move in a certain direction for 1 tick.
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private fun move(delta: Delta) {
        super.entity!!.move(delta)
    }

    /**
     * Tells the player to shoot if possible.
     */
    private fun shoot() {
        if (super.entity!!.isAbleToShoot) {
            super.entity!!.shoot()
            engine.spawnPlayerBullet()
            audio.playSFX(SFXTracks.SHOT_PLAYER)
        }
    }

    /**
     * Updates the player status for the tick.
     */
    private fun tick() {
        super.entity!!.tick()
    }
}
