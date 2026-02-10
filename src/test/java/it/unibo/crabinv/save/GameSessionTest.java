package it.unibo.crabinv.save;
import it.unibo.crabinv.model.powerUpsShop.PowerUpType;
import it.unibo.crabinv.model.save.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameSessionTest {

    private GameSessionImpl session;
    @BeforeEach
    void init(){
        session = new GameSessionImpl(
                StartingSaveValues.CURRENCY.getIntValue(),
                PlayerBaseStats.getIntValueOf(PowerUpType.HEALTH_UP),
                PlayerBaseStats.getDoubleValueOf(PowerUpType.SPEED_UP),
                PlayerBaseStats.getIntValueOf(PowerUpType.FIRERATE_UP)
                );
    }

    @Test
    void testInitialState() {
        assertEquals(1, session.getCurrentLevel());
        assertEquals(0, session.getCurrency());
        session.addCurrency(50);
        assertEquals(50, session.getCurrency());
        assertThrows(IllegalArgumentException.class, () -> {session.subCurrency(80);});
        assertEquals(PlayerBaseStats.getIntValueOf(PowerUpType.HEALTH_UP), session.getPlayerHealth());
        session.subPlayerHealth(5);
        assertEquals(0, session.getPlayerHealth());
        session.advanceLevel();
        assertEquals(2, session.getCurrentLevel());
        assertEquals(3, session.getNextLevel());
        assertFalse(session.isGameOver());
        session.markGameOver();
        assertTrue(session.isGameOver());
    }
}
