package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.entities.entity.Delta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestInputRegistration {

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
