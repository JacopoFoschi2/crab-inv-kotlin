package it.unibo.crabinv.model.core.collisions;

import it.unibo.crabinv.model.entities.bullets.BulletEnemy;
import it.unibo.crabinv.model.entities.bullets.BulletPlayer;
import it.unibo.crabinv.model.entities.enemies.EnemyImpl;
import it.unibo.crabinv.model.entities.entity.Entity;
import it.unibo.crabinv.model.entities.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

final class TestCollisionSystem {
    private final CollisionSystem collisionSystem = new CollisionSystem();

    private static final class EntitiesExamples {
        public static EnemyImpl getEnemyExample() {
            return EnemyImpl.builder()
                    .x(0)
                    .y(0)
                    .collisionGroup(CollisionGroups.HOSTILE)
                    .radius(10)
                    .maxHealth(1)
                    .health(1)
                    .build();
        }

        public static Player getPlayerExample() {
            return Player.builder()
                    .x(0)
                    .y(0)
                    .collisionGroup(CollisionGroups.FRIENDLY)
                    .radius(10)
                    .maxHealth(1)
                    .health(1)
                    .build();
        }

        public static BulletPlayer getPlayerBulletExample() {
            return BulletPlayer.builder()
                    .x(0)
                    .y(0)
                    .radius(10)
                    .collisionGroup(CollisionGroups.FRIENDLY)
                    .maxHealth(1)
                    .health(1)
                    .build();
        }

        public static BulletEnemy getEnemyBulletExample() {
            return BulletEnemy.builder()
                    .x(0)
                    .y(0)
                    .radius(10)
                    .collisionGroup(CollisionGroups.HOSTILE)
                    .maxHealth(1)
                    .health(1)
                    .build();
        }

        public static EnemyImpl getAlreadyDeadEnemyExample() {
            return EnemyImpl.builder()
                    .x(0)
                    .y(0)
                    .radius(10)
                    .collisionGroup(CollisionGroups.HOSTILE)
                    .maxHealth(1)
                    .health(0)
                    .build();
        }
    }

    @Test
    void testCollisionBetweenLiveEnemies() {
        final Entity enemy = EntitiesExamples.getEnemyExample();
        final Entity player = EntitiesExamples.getPlayerExample();
        final List<Entity> entities = new ArrayList<>();
        entities.add(enemy);
        entities.add(player);
        Assertions.assertTrue(collisionSystem.resolve(entities));
        Assertions.assertFalse(enemy.isAlive());
        Assertions.assertFalse(player.isAlive());
    }

    @Test
    void testCollisionBetweenFriendlies() {
        final Entity bullet = EntitiesExamples.getPlayerBulletExample();
        final Entity player = EntitiesExamples.getPlayerExample();
        final List<Entity> entities = new ArrayList<>();
        entities.add(bullet);
        entities.add(player);
        Assertions.assertFalse(collisionSystem.resolve(entities));
        Assertions.assertTrue(bullet.isAlive());
        Assertions.assertTrue(player.isAlive());
    }

    @Test
    void testCollisionBetweenEnemies() {
        final Entity bullet = EntitiesExamples.getEnemyBulletExample();
        final Entity enemy = EntitiesExamples.getEnemyExample();
        final List<Entity> entities = new ArrayList<>();
        entities.add(bullet);
        entities.add(enemy);
        Assertions.assertFalse(collisionSystem.resolve(entities));
        Assertions.assertTrue(bullet.isAlive());
        Assertions.assertTrue(enemy.isAlive());
    }

    @Test
    void testCollisionWhereOneEntityIsDead() {
        final Entity enemy = EntitiesExamples.getAlreadyDeadEnemyExample();
        final Entity player = EntitiesExamples.getPlayerExample();
        final List<Entity> entities = new ArrayList<>();
        entities.add(enemy);
        entities.add(player);
        Assertions.assertFalse(collisionSystem.resolve(entities));
        Assertions.assertFalse(enemy.isAlive());
        Assertions.assertTrue(player.isAlive());
    }
}
