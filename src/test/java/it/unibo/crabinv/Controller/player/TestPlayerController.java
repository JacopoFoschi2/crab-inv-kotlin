package it.unibo.crabinv.Controller.player;

import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlayerController {
    Player player;
    PlayerController playerController;

    @BeforeEach
    public void setup() {
        player = new Player(1,0,0,1,1);
        playerController = new PlayerController(player, -2, 2);
    }

    @Test
    public void TestMovement() {
        playerController.update(false, Delta.INCREASE);
        Assertions.assertEquals(1, player.getX());
    }

    @Test
    public void TestMovementAndShot() {
        playerController.update(true, Delta.DECREASE);
        Assertions.assertEquals(-1, player.getX());
        Assertions.assertFalse(player.isAbleToShoot());
    }

    @Test
    public void testMoveOutOfBounds() {
        playerController.update(false, Delta.DECREASE);
        playerController.update(false, Delta.DECREASE);
        playerController.update(false, Delta.DECREASE);
        Assertions.assertEquals(-2, player.getX()); // minBound
        playerController.update(false, Delta.INCREASE);
        playerController.update(false, Delta.INCREASE);
        playerController.update(false, Delta.INCREASE);
        playerController.update(false, Delta.INCREASE);
        playerController.update(false, Delta.INCREASE);
        Assertions.assertEquals(2, player.getX()); // maxBound
    }

    @Test
    public void testShootCooldown() {
        playerController.update(true, Delta.NO_ACTION);
        Assertions.assertFalse(player.isAbleToShoot());
        playerController.update(false, Delta.NO_ACTION);
        Assertions.assertTrue(player.isAbleToShoot());
    }
}
