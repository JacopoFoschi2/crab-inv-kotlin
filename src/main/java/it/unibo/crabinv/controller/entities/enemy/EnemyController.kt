package it.unibo.crabinv.controller.entities.enemy

import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.entities.entity.AbstractEntityController
import it.unibo.crabinv.controller.entities.entity.EntityNotCapableOfInputController
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.engine.GameEngine
import it.unibo.crabinv.model.entities.enemies.Enemy
import it.unibo.crabinv.model.entities.entity.Delta
import java.util.Random

/**
 * It's the EnemyController, should control each enemy.
 * @param enemy the enemy to be created.
 * @param audio the audio that it's needed in the class.
 * @param engine the game engine.
 */
class EnemyController(
    enemy: Enemy?,
    private val audio: AudioController,
    private val engine: GameEngine,
) : AbstractEntityController<Enemy?>(enemy),
    EntityNotCapableOfInputController {
    private val rand = Random()

    override fun update(delta: Delta) {
        tick()
        move(delta)

        if (rand.nextDouble() < ENEMY_SHOOTING_CHANCE) {
            shoot()
        }
    }

    val speed: Double
        /**
         * Gives the speed of the enemy.
         * @return the speed of the enemy
         */
        get() = super.entity!!.speed

    /**
     * Tells the enemy to go to a specific direction for 1 tick.
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private fun move(delta: Delta) {
        super.entity!!.move(delta)
    }

    /**
     * Makes the enemy shoot if it can.
     */
    private fun shoot() {
        if (super.entity!!.isAbleToShoot) {
            super.entity!!.shoot()
            engine.spawnEnemyBullet(super.entity)
            audio.playSFX(SFXTracks.SHOT_ENEMY)
        }
    }

    /**
     * Updates the status for the tick.
     */
    private fun tick() {
        super.entity!!.tick()
    }

    companion object {
        private const val ENEMY_SHOOTING_CHANCE = 0.007
    }
}
