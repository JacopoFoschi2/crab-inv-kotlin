package it.unibo.crabinv.controller.entities.player;

import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.model.core.GameEngine;
import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.entities.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TestPlayerController {
    static final int DEFAULT_COORDINATE = 0;
    static final int INITIAL_HEALTH = 3;
    static final int DEFAULT_RADIUS = 10;
    static final int DEFAULT_SPEED = 1;
    static final int DEFAULT_FIRE_RATE = 1;
    static final int DEFAULT_COUNTER = 0;
    static final int MIN_BOUND = -2;
    static final int MAX_BOUND = 2;
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
                .x(DEFAULT_COORDINATE)
                .y(DEFAULT_COORDINATE)
                .maxHealth(INITIAL_HEALTH)
                .health(INITIAL_HEALTH)
                .radius(DEFAULT_RADIUS)
                .speed(DEFAULT_SPEED)
                .fireRate(DEFAULT_FIRE_RATE)
                .shootingCounter(DEFAULT_COUNTER)
                .minBound(MIN_BOUND)
                .maxBound(MAX_BOUND)
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
