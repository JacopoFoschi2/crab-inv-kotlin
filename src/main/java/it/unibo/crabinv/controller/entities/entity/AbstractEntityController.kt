package it.unibo.crabinv.controller.entities.entity

import it.unibo.crabinv.model.entities.entity.Entity

/**
 * Provides the methods an EntityController should implement.
 * @param <T> the [Entity] implementation the controller should control
 * @param entity an entity implementation
</T> */
abstract class AbstractEntityController<T : Entity?>(
    /**
     * @return the instance of entity to access getters and methods from for controllers that implement this one.
     */
    protected val entity: T?,
) : EntityController {
    override val isAlive: Boolean
        get() = this.entity!!.isAlive()

    override fun takeDamage(damage: Int) {
        this.entity!!.takeDamage(damage)
    }

    override val health: Int
        get() = this.entity!!.getHealth()

    override val maxHealth: Int
        get() = this.entity!!.getMaxHealth()

    override val x: Double
        get() = this.entity!!.getX()

    override val y: Double
        get() = this.entity!!.getY()
}
