package it.unibo.crabinv.controller.entities.player

import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.model.core.engine.GameEngine
import it.unibo.crabinv.model.entities.entity.Delta
import it.unibo.crabinv.model.entities.player.Player
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

internal class TestPlayerController {
    private var player: Player? = null
    private var playerController: PlayerController? = null

    @Mock
    private val audioMock: AudioController = Mockito.mock(AudioController::class.java)

    @Mock
    private val engineMock: GameEngine = Mockito.mock(GameEngine::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)

        player =
            Player
                .builder()
                .x(DEFAULT_COORDINATE.toDouble())
                .y(DEFAULT_COORDINATE.toDouble())
                .maxHealth(INITIAL_HEALTH)
                .health(INITIAL_HEALTH)
                .radius(DEFAULT_RADIUS.toDouble())
                .speed(DEFAULT_SPEED.toDouble())
                .fireRate(DEFAULT_FIRE_RATE)
                .shootingCounter(DEFAULT_COUNTER)
                .minBound(MIN_BOUND.toDouble())
                .maxBound(MAX_BOUND.toDouble())
                .build()
        playerController = PlayerController(player, audioMock, engineMock)
    }

    @Test
    fun testMovement() {
        val expectedX = 1
        playerController!!.update(false, Delta.INCREASE)
        Assertions.assertEquals(expectedX.toDouble(), player!!.x)
    }

    @Test
    fun testMovementAndShot() {
        val expectedX = -1
        playerController!!.update(true, Delta.DECREASE)
        Assertions.assertEquals(expectedX.toDouble(), player!!.x)
        Assertions.assertFalse(player!!.isAbleToShoot)
    }

    @Test
    fun testMoveOutOfBounds() {
        val expectedMinBound = -2
        val expectedMaxBound = 2
        playerController!!.update(false, Delta.DECREASE)
        playerController!!.update(false, Delta.DECREASE)
        playerController!!.update(false, Delta.DECREASE)
        Assertions.assertEquals(expectedMinBound.toDouble(), player!!.x) // minBound
        playerController!!.update(false, Delta.INCREASE)
        playerController!!.update(false, Delta.INCREASE)
        playerController!!.update(false, Delta.INCREASE)
        playerController!!.update(false, Delta.INCREASE)
        playerController!!.update(false, Delta.INCREASE)
        Assertions.assertEquals(expectedMaxBound.toDouble(), player!!.x) // maxBound
    }

    @Test
    fun testShootCooldown() {
        playerController!!.update(true, Delta.NO_ACTION)
        Assertions.assertFalse(player!!.isAbleToShoot)
        playerController!!.update(false, Delta.NO_ACTION)
        Assertions.assertTrue(player!!.isAbleToShoot)
    }

    @Test
    fun testDamage() {
        val damage1 = 1
        val damage2 = 2
        playerController!!.takeDamage(damage1)
        Assertions.assertEquals(INITIAL_HEALTH - damage1, player!!.health)
        playerController!!.takeDamage(damage2)
        Assertions.assertFalse(player!!.isAlive)
    }

    companion object {
        const val DEFAULT_COORDINATE: Int = 0
        const val INITIAL_HEALTH: Int = 3
        const val DEFAULT_RADIUS: Int = 10
        const val DEFAULT_SPEED: Int = 1
        const val DEFAULT_FIRE_RATE: Int = 1
        const val DEFAULT_COUNTER: Int = 0
        const val MIN_BOUND: Int = -2
        const val MAX_BOUND: Int = 2
    }
}
