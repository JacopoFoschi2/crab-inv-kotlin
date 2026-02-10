package it.unibo.crabinv.model.entities.player;

import it.unibo.crabinv.model.core.collisions.CollisionGroups;
import it.unibo.crabinv.model.entities.entity.Delta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestPlayer {
    Player player;

    @BeforeEach
    void setup() {
        player = Player.builder()
                .x(0)
                .y(0)
                .maxHealth(3)
                .health(3)
                .collisionGroup(CollisionGroups.FRIENDLY)
                .radius(10)
                .speed(1)
                .fireRate(1)
                .minBound(-1)
                .maxBound(1)
                .build();
    }

    @Test
    void testPlayerMovement() {
        player.move(Delta.INCREASE);
        Assertions.assertEquals(1, player.getX());
    }

    @Test
    void testOutOfBounds() {
        player.move(Delta.INCREASE);
        Assertions.assertEquals(1, player.getX());
        player.move(Delta.DECREASE);
        Assertions.assertEquals(0, player.getX());
        player.move(Delta.DECREASE);
        Assertions.assertEquals(-1, player.getX());
        player.move(Delta.DECREASE);
        Assertions.assertEquals(-1, player.getX());
    }

    @Test
    void testNoMovement() {
        player.move(Delta.NO_ACTION);
        Assertions.assertEquals(0, player.getX());
    }

    @Test
    void testShoot() {
        Assertions.assertTrue(player.isAbleToShoot());
        player.shoot();
        Assertions.assertFalse(player.isAbleToShoot());
    }

    @Test
    void testDeathByDamage() {
        player.takeDamage(999);
        Assertions.assertFalse(player.isAlive());
    }

    @Test
    void testDeathByDestroy() {
        player.destroy();
        Assertions.assertFalse(player.isAlive());
    }

    @Test
    void testReload() {
        player.shoot();
        Assertions.assertFalse(player.isAbleToShoot());
        player.tick();
        Assertions.assertTrue(player.isAbleToShoot());
    }
}
