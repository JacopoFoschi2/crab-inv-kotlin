package it.unibo.crabinv.model.core.collisions

import it.unibo.crabinv.model.entities.bullets.BulletEnemy
import it.unibo.crabinv.model.entities.bullets.BulletPlayer
import it.unibo.crabinv.model.entities.enemies.EnemyImpl
import it.unibo.crabinv.model.entities.entity.Entity
import it.unibo.crabinv.model.entities.player.Player
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class TestCollisionSystem {
    private val collisionSystem = CollisionSystem()

    @Test
    fun testCollisionBetweenLiveEnemies() {
        val enemy: Entity = EntitiesExamples.enemyExample
        val player: Entity = EntitiesExamples.playerExample
        val entities: MutableList<Entity?> = ArrayList<Entity?>()
        entities.add(enemy)
        entities.add(player)
        Assertions.assertTrue(collisionSystem.resolve(entities))
        Assertions.assertFalse(enemy.isAlive())
        Assertions.assertFalse(player.isAlive())
    }

    @Test
    fun testCollisionBetweenFriendlies() {
        val bullet: Entity = EntitiesExamples.playerBulletExample
        val player: Entity = EntitiesExamples.playerExample
        val entities: MutableList<Entity?> = ArrayList<Entity?>()
        entities.add(bullet)
        entities.add(player)
        Assertions.assertFalse(collisionSystem.resolve(entities))
        Assertions.assertTrue(bullet.isAlive())
        Assertions.assertTrue(player.isAlive())
    }

    @Test
    fun testCollisionBetweenEnemies() {
        val bullet: Entity = EntitiesExamples.enemyBulletExample
        val enemy: Entity = EntitiesExamples.enemyExample
        val entities: MutableList<Entity?> = ArrayList<Entity?>()
        entities.add(bullet)
        entities.add(enemy)
        Assertions.assertFalse(collisionSystem.resolve(entities))
        Assertions.assertTrue(bullet.isAlive())
        Assertions.assertTrue(enemy.isAlive())
    }

    @Test
    fun testCollisionWhereOneEntityIsDead() {
        val enemy: Entity = EntitiesExamples.alreadyDeadEnemyExample
        val player: Entity = EntitiesExamples.playerExample
        val entities: MutableList<Entity?> = ArrayList<Entity?>()
        entities.add(enemy)
        entities.add(player)
        Assertions.assertFalse(collisionSystem.resolve(entities))
        Assertions.assertFalse(enemy.isAlive())
        Assertions.assertTrue(player.isAlive())
    }

    private object EntitiesExamples {
        val enemyExample: EnemyImpl
            get() =
                EnemyImpl
                    .builder()
                    .x(0.0)
                    .y(0.0)
                    .collisionGroup(CollisionGroups.HOSTILE)
                    .radius(10.0)
                    .maxHealth(1)
                    .health(1)
                    .build()

        val playerExample: Player
            get() =
                Player
                    .builder()
                    .x(0.0)
                    .y(0.0)
                    .collisionGroup(CollisionGroups.FRIENDLY)
                    .radius(10.0)
                    .maxHealth(1)
                    .health(1)
                    .build()

        val playerBulletExample: BulletPlayer
            get() =
                BulletPlayer
                    .builder()
                    .x(0.0)
                    .y(0.0)
                    .radius(10.0)
                    .collisionGroup(CollisionGroups.FRIENDLY)
                    .maxHealth(1)
                    .health(1)
                    .build()

        val enemyBulletExample: BulletEnemy
            get() =
                BulletEnemy
                    .builder()
                    .x(0.0)
                    .y(0.0)
                    .radius(10.0)
                    .collisionGroup(CollisionGroups.HOSTILE)
                    .maxHealth(1)
                    .health(1)
                    .build()

        val alreadyDeadEnemyExample: EnemyImpl
            get() =
                EnemyImpl
                    .builder()
                    .x(0.0)
                    .y(0.0)
                    .radius(10.0)
                    .collisionGroup(CollisionGroups.HOSTILE)
                    .maxHealth(1)
                    .health(0)
                    .build()
    }
}
