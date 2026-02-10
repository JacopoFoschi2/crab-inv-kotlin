package it.unibo.crabinv.controller.input;

import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.input.InputSnapshot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestInputControllerPlayer {

    private static InputControllerPlayer newController() {
        InputMapper mapper = new InputMapperImpl();
        return new InputControllerPlayer(mapper);
    }

    @Test
    void initialStateIsIdleAndNotShooting() {
        InputControllerPlayer controller = newController();

        InputSnapshot snap = controller.getInputState();

        assertFalse(snap.isShooting());
        assertEquals(Delta.NO_ACTION, snap.getXMovementDelta());
    }

    @Test
    void pressLeftMovesDecreaseOnX() {
        InputControllerPlayer controller = newController();

        controller.onKeyPressed(KeyCodeKeyboard.LEFT.getKeyCode());
        InputSnapshot snap = controller.getInputState();

        assertEquals(Delta.DECREASE, snap.getXMovementDelta());
        assertFalse(snap.isShooting());
    }

    @Test
    void pressRightMovesIncreaseOnX() {
        InputControllerPlayer controller = newController();

        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.getKeyCode());
        InputSnapshot snap = controller.getInputState();

        assertEquals(Delta.INCREASE, snap.getXMovementDelta());
        assertFalse(snap.isShooting());
    }

    @Test
    void leftAndRightTogetherCancelOut() {
        InputControllerPlayer controller = newController();

        controller.onKeyPressed(KeyCodeKeyboard.LEFT.getKeyCode());
        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.getKeyCode());
        InputSnapshot snap = controller.getInputState();

        assertEquals(Delta.NO_ACTION, snap.getXMovementDelta());
    }

    @Test
    void releasingOneOfConflictingKeysResolvesDirection() {
        InputControllerPlayer controller = newController();

        controller.onKeyPressed(KeyCodeKeyboard.LEFT.getKeyCode());
        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.getKeyCode());
        assertEquals(Delta.NO_ACTION, controller.getInputState().getXMovementDelta());

        controller.onKeyReleased(KeyCodeKeyboard.RIGHT.getKeyCode());
        assertEquals(Delta.DECREASE, controller.getInputState().getXMovementDelta());
    }

    @Test
    void shootIsTrueWhileHeldAndFalseWhenReleased() {
        InputControllerPlayer controller = newController();

        controller.onKeyPressed(KeyCodeKeyboard.SHOOT.getKeyCode());
        assertTrue(controller.getInputState().isShooting());

        controller.onKeyReleased(KeyCodeKeyboard.SHOOT.getKeyCode());
        assertFalse(controller.getInputState().isShooting());
    }

    @Test
    void unmappedKeyIsIgnored() {
        InputControllerPlayer controller = newController();

        int unmappedKeyCode = 9999;
        controller.onKeyPressed(unmappedKeyCode);

        InputSnapshot snap = controller.getInputState();
        assertFalse(snap.isShooting());
        assertEquals(Delta.NO_ACTION, snap.getXMovementDelta());
    }
}