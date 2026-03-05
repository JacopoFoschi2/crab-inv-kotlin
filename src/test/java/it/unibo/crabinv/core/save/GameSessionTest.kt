package it.unibo.crabinv.core.save

import it.unibo.crabinv.model.core.save.GameSessionImpl
import it.unibo.crabinv.model.core.save.PlayerBaseStats
import it.unibo.crabinv.model.core.save.StartingSaveValues
import it.unibo.crabinv.model.powerups.PowerUpType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Tests the game session.
 */
internal class GameSessionTest {
    private var session: GameSessionImpl? = null

    @BeforeEach
    fun init() {
        session =
            GameSessionImpl(
                StartingSaveValues.CURRENCY.intValue,
                PlayerBaseStats.getIntValueOf(PowerUpType.HEALTH_UP).toDouble(),
                PlayerBaseStats.getDoubleValueOf(PowerUpType.SPEED_UP),
                PlayerBaseStats.getIntValueOf(PowerUpType.FIRERATE_UP).toDouble(),
            )
    }

    @Test
    fun testInitialState() {
        val addCurrency = 50
        val subCurrency = 80
        val subHealth = 5
        Assertions.assertEquals(1, session!!.currentLevel)
        Assertions.assertEquals(0, session!!.currency)
        session!!.addCurrency(addCurrency)
        Assertions.assertEquals(addCurrency, session!!.currency)
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
        ) { session!!.subCurrency(subCurrency) }
        Assertions.assertEquals(PlayerBaseStats.getIntValueOf(PowerUpType.HEALTH_UP), session!!.playerHealth)
        session!!.subPlayerHealth(subHealth)
        Assertions.assertEquals(0, session!!.playerHealth)
        session!!.advanceLevel()
        Assertions.assertEquals(2, session!!.currentLevel)
        Assertions.assertEquals(3, session!!.nextLevel)
        Assertions.assertFalse(session!!.isGameOver)
        session!!.markGameOver()
        Assertions.assertTrue(session!!.isGameOver)
    }
}
