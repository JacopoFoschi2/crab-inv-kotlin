package it.unibo.crabinv.controller.entities.bullets

import it.unibo.crabinv.controller.entities.entity.AbstractEntityController
import it.unibo.crabinv.model.entities.bullets.BulletPlayer
import it.unibo.crabinv.model.entities.entity.Delta

/**
 * It's the PlayerBulletController that handles the PlayerBullets.
 * @param entity it's the entity needed to modify it
 */
class PlayerBulletController(
    entity: BulletPlayer?,
) : AbstractEntityController<BulletPlayer?>(entity),
    BulletController {
    override fun update(delta: Delta) {
        move(delta)
    }

    val speed: Double
        /**
         * Gives the speed of the bullet.
         * @return the speed of the bullet
         */
        get() = super.entity!!.speed

    /**
     * Tells the bullet to go to a specific direction for 1 tick.
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private fun move(delta: Delta) {
        super.entity!!.move(delta)
    }
}
