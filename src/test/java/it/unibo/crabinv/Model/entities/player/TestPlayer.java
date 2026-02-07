package it.unibo.crabinv.Model.entities.player;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.Delta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlayer {
    Player player;

    @BeforeEach
    public void setup() {
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
    public void TestPlayerMovement() {
        player.move(Delta.INCREASE);
        Assertions.assertEquals(1, player.getX());
    }

    @Test
    public void TestOutOfBounds() {
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
    public void TestNoMovement() {
        player.move(Delta.NO_ACTION);
        Assertions.assertEquals(0, player.getX());
    }

    @Test
    public void TestShoot() {
        Assertions.assertTrue(player.isAbleToShoot());
        player.shoot();
        Assertions.assertFalse(player.isAbleToShoot());
    }

    @Test
    public void TestDeathByDamage() {
        player.takeDamage(999);
        Assertions.assertFalse(player.isAlive());
    }

    @Test
    public void TestDeathByDestroy() {
        player.destroy();
        Assertions.assertFalse(player.isAlive());
    }

    @Test
    public void TestReload() {
        player.shoot();
        Assertions.assertFalse(player.isAbleToShoot());
        player.tick();
        Assertions.assertTrue(player.isAbleToShoot());
    }
}
