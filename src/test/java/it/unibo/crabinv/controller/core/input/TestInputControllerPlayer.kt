package it.unibo.crabinv.controller.core.input;

import it.unibo.crabinv.model.core.input.InputSnapshot;
import it.unibo.crabinv.model.entities.entity.Delta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests if the input works correctly.
 */
class TestInputControllerPlayer {

    private static InputControllerPlayer newController() {
        final InputMapper mapper = new InputMapperImpl();
        return new InputControllerPlayer(mapper);
    }

    @Test
    void initialStateIsIdleAndNotShooting() {
        final InputControllerPlayer controller = newController();

        final InputSnapshot snap = controller.getInputState();

        assertFalse(snap.isShooting());
        assertEquals(Delta.NO_ACTION, snap.getXMovementDelta());
    }

    @Test
    void pressLeftMovesDecreaseOnX() {
        final InputControllerPlayer controller = newController();

        controller.onKeyPressed(KeyCodeKeyboard.LEFT.getKeyCode());
        final InputSnapshot snap = controller.getInputState();

        assertEquals(Delta.DECREASE, snap.getXMovementDelta());
        assertFalse(snap.isShooting());
    }

    @Test
    void pressRightMovesIncreaseOnX() {
        final InputControllerPlayer controller = newController();

        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.getKeyCode());
        final InputSnapshot snap = controller.getInputState();

        assertEquals(Delta.INCREASE, snap.getXMovementDelta());
        assertFalse(snap.isShooting());
    }

    @Test
    void leftAndRightTogetherCancelOut() {
        final InputControllerPlayer controller = newController();

        controller.onKeyPressed(KeyCodeKeyboard.LEFT.getKeyCode());
        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.getKeyCode());
        final InputSnapshot snap = controller.getInputState();

        assertEquals(Delta.NO_ACTION, snap.getXMovementDelta());
    }

    @Test
    void releasingOneOfConflictingKeysResolvesDirection() {
        final InputControllerPlayer controller = newController();

        controller.onKeyPressed(KeyCodeKeyboard.LEFT.getKeyCode());
        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.getKeyCode());
        assertEquals(Delta.NO_ACTION, controller.getInputState().getXMovementDelta());

        controller.onKeyReleased(KeyCodeKeyboard.RIGHT.getKeyCode());
        assertEquals(Delta.DECREASE, controller.getInputState().getXMovementDelta());
    }

    @Test
    void shootIsTrueWhileHeldAndFalseWhenReleased() {
        final InputControllerPlayer controller = newController();

        controller.onKeyPressed(KeyCodeKeyboard.SHOOT.getKeyCode());
        assertTrue(controller.getInputState().isShooting());

        controller.onKeyReleased(KeyCodeKeyboard.SHOOT.getKeyCode());
        assertFalse(controller.getInputState().isShooting());
    }

    @Test
    void unmappedKeyIsIgnored() {
        final InputControllerPlayer controller = newController();

        final int unmappedKeyCode = 9999;
        controller.onKeyPressed(unmappedKeyCode);

        final InputSnapshot snap = controller.getInputState();
        assertFalse(snap.isShooting());
        assertEquals(Delta.NO_ACTION, snap.getXMovementDelta());
    }
}
