package it.unibo.crabinv.controller.entities.player;

import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.model.core.GameEngine;
import it.unibo.crabinv.model.core.collisions.CollisionGroups;
import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.entities.entity.EntitySprites;
import it.unibo.crabinv.model.entities.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TestPlayerController {
    static final int INITIAL_HEALTH = 3;
    private Player player;
    private PlayerController playerController;
    @Mock
    private AudioController audioMock;
    @Mock
    private GameEngine engineMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        player = Player.builder()
                .x(0)
                .y(0)
                .maxHealth(INITIAL_HEALTH)
                .health(INITIAL_HEALTH)
                .collisionGroup(CollisionGroups.FRIENDLY)
                .radius(10)
                .speed(1)
                .fireRate(1)
                .shootingCounter(0)
                .minBound(-2)
                .maxBound(2)
                .sprite(EntitySprites.PLAYER)
                .build();
        playerController = new PlayerController(player, audioMock, engineMock);
    }

    @Test
    void testMovement() {
        final int expectedX = 1;
        playerController.update(false, Delta.INCREASE);
        Assertions.assertEquals(expectedX, player.getX());
    }

    @Test
    void testMovementAndShot() {
        final int expectedX = -1;
        playerController.update(true, Delta.DECREASE);
        Assertions.assertEquals(expectedX, player.getX());
        Assertions.assertFalse(player.isAbleToShoot());
    }

    @Test
    void testMoveOutOfBounds() {
        final int expectedMinBound = -2;
        final int expectedMaxBound = 2;
        playerController.update(false, Delta.DECREASE);
        playerController.update(false, Delta.DECREASE);
        playerController.update(false, Delta.DECREASE);
        Assertions.assertEquals(expectedMinBound, player.getX()); // minBound
        playerController.update(false, Delta.INCREASE);
        playerController.update(false, Delta.INCREASE);
        playerController.update(false, Delta.INCREASE);
        playerController.update(false, Delta.INCREASE);
        playerController.update(false, Delta.INCREASE);
        Assertions.assertEquals(expectedMaxBound, player.getX()); // maxBound
    }

    @Test
    void testShootCooldown() {
        playerController.update(true, Delta.NO_ACTION);
        Assertions.assertFalse(player.isAbleToShoot());
        playerController.update(false, Delta.NO_ACTION);
        Assertions.assertTrue(player.isAbleToShoot());
    }

    @Test
    void testDamage() {
        final int damage1 = 1;
        final int damage2 = 2;
        playerController.takeDamage(damage1);
        Assertions.assertEquals(INITIAL_HEALTH - damage1, player.getHealth());
        playerController.takeDamage(damage2);
        Assertions.assertFalse(player.isAlive());
    }
}
