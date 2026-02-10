package it.unibo.crabinv.controller.entities.player;

import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.model.core.GameEngineImpl;
import it.unibo.crabinv.model.core.audio.JavaFXSoundManager;
import it.unibo.crabinv.model.core.collisions.CollisionGroups;
import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.entities.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlayerController {
    Player player;
    PlayerController playerController;
    static final int INITIAL_HEALTH = 3;

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
                .minBound(-2)
                .maxBound(2)
                .build();
        playerController = new PlayerController(player, new AudioController(new JavaFXSoundManager()), new GameEngineImpl() {
        });
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

    @Test
    public void testDamage() {
        int damage1 = 1;
        int damage2 = 2;
        playerController.takeDamage(damage1);
        Assertions.assertEquals(INITIAL_HEALTH - damage1, player.getHealth());
        playerController.takeDamage(damage2);
        Assertions.assertFalse(player.isAlive());
    }
}
