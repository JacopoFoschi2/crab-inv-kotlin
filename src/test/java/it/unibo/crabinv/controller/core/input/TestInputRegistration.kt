package it.unibo.crabinv.controller.core.input;

import it.unibo.crabinv.model.entities.entity.Delta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests if the registration of the input works correctly.
 */
class TestInputRegistration {

    @Test
    void pressingRightRegistersIncreaseAndReleaseClearsIt() {
        final InputController controller = new InputControllerPlayer(new InputMapperImpl());

        Assertions.assertEquals(Delta.NO_ACTION, controller.getInputState().getXMovementDelta());

        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.getKeyCode());
        Assertions.assertEquals(Delta.INCREASE, controller.getInputState().getXMovementDelta());

        controller.onKeyReleased(KeyCodeKeyboard.RIGHT.getKeyCode());
        Assertions.assertEquals(Delta.NO_ACTION, controller.getInputState().getXMovementDelta());
    }
}
