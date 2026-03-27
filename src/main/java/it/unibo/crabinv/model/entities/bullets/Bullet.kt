package it.unibo.crabinv.model.entities.bullets

import it.unibo.crabinv.model.entities.entity.Entity
import it.unibo.crabinv.model.entities.entity.Movable

/**
 * Provides all bullet implementations of all the methods they should implement by combining Entity and Movable.
 */
interface Bullet :
    Entity,
    Movable
