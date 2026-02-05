package it.unibo.crabinv.Model.player;

import it.unibo.crabinv.Model.entity.Delta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlayer {
    Player player;

    @BeforeEach
    public void setup() {
        player = new Player(1,0,0,1,1);
    }

    @Test
    public void TestPlayerMovement() {
        player.move(Delta.INCREASE, -1, 1);
        Assertions.assertEquals(1, player.getX());
    }

    @Test
    public void TestOutOfBounds() {
        player.move(Delta.INCREASE, -1, 1);
        Assertions.assertEquals(1, player.getX());
        player.move(Delta.DECREASE, -1, 1);
        Assertions.assertEquals(0, player.getX());
        player.move(Delta.DECREASE, -1, 1);
        Assertions.assertEquals(-1, player.getX());
        player.move(Delta.DECREASE, -1, 1);
        Assertions.assertEquals(-1, player.getX());
    }

    @Test
    public void TestNoMovement() {
        player.move(Delta.NO_ACTION, -1, 1);
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
