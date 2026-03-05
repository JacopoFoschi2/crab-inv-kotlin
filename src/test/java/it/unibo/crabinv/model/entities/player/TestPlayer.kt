package it.unibo.crabinv.model.entities.player

import it.unibo.crabinv.model.core.collisions.CollisionGroups
import it.unibo.crabinv.model.entities.entity.Delta
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestPlayer {
    private var player: Player? = null

    @BeforeEach
    fun setup() {
        player =
            Player
                .builder()
                .x(0.0)
                .y(0.0)
                .maxHealth(3)
                .health(3)
                .collisionGroup(CollisionGroups.FRIENDLY)
                .radius(10.0)
                .speed(1.0)
                .fireRate(1)
                .minBound(-1.0)
                .maxBound(1.0)
                .build()
    }

    @Test
    fun testPlayerMovement() {
        player!!.move(Delta.INCREASE)
        Assertions.assertEquals(1.0, player!!.x)
    }

    @Test
    fun testOutOfBounds() {
        player!!.move(Delta.INCREASE)
        Assertions.assertEquals(1.0, player!!.x)
        player!!.move(Delta.DECREASE)
        Assertions.assertEquals(0.0, player!!.x)
        player!!.move(Delta.DECREASE)
        Assertions.assertEquals(-1.0, player!!.x)
        player!!.move(Delta.DECREASE)
        Assertions.assertEquals(-1.0, player!!.x)
    }

    @Test
    fun testNoMovement() {
        player!!.move(Delta.NO_ACTION)
        Assertions.assertEquals(0.0, player!!.x)
    }

    @Test
    fun testShoot() {
        Assertions.assertTrue(player!!.isAbleToShoot)
        player!!.shoot()
        Assertions.assertFalse(player!!.isAbleToShoot)
    }

    @Test
    fun testDeathByDamage() {
        player!!.takeDamage(BIG_DAMAGE)
        Assertions.assertFalse(player!!.isAlive)
    }

    @Test
    fun testDeathByDestroy() {
        player!!.destroy()
        Assertions.assertFalse(player!!.isAlive)
    }

    @Test
    fun testReload() {
        player!!.shoot()
        Assertions.assertFalse(player!!.isAbleToShoot)
        player!!.tick()
        Assertions.assertTrue(player!!.isAbleToShoot)
    }

    companion object {
        private const val BIG_DAMAGE = 999
    }
}
