package it.unibo.crabinv.core.save;

import it.unibo.crabinv.model.core.save.GameSessionImpl;
import it.unibo.crabinv.model.core.save.PlayerBaseStats;
import it.unibo.crabinv.model.core.save.StartingSaveValues;
import it.unibo.crabinv.model.powerups.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the game session.
 */
class GameSessionTest {

    private GameSessionImpl session;

    @BeforeEach
    void init() {
        session = new GameSessionImpl(
                StartingSaveValues.CURRENCY.getIntValue(),
                PlayerBaseStats.getIntValueOf(PowerUpType.HEALTH_UP),
                PlayerBaseStats.getDoubleValueOf(PowerUpType.SPEED_UP),
                PlayerBaseStats.getIntValueOf(PowerUpType.FIRERATE_UP)
                );
    }

    @Test
    void testInitialState() {
        final int addCurrency = 50;
        final int subCurrency = 80;
        final int subHealth = 5;
        assertEquals(1, session.getCurrentLevel());
        assertEquals(0, session.getCurrency());
        session.addCurrency(addCurrency);
        assertEquals(addCurrency, session.getCurrency());
        assertThrows(IllegalArgumentException.class, () -> session.subCurrency(subCurrency));
        assertEquals(PlayerBaseStats.getIntValueOf(PowerUpType.HEALTH_UP), session.getPlayerHealth());
        session.subPlayerHealth(subHealth);
        assertEquals(0, session.getPlayerHealth());
        session.advanceLevel();
        assertEquals(2, session.getCurrentLevel());
        assertEquals(3, session.getNextLevel());
        assertFalse(session.isGameOver());
        session.markGameOver();
        assertTrue(session.isGameOver());
    }
}
