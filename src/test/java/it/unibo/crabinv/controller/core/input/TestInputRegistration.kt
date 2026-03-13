package it.unibo.crabinv.controller.core.input

import it.unibo.crabinv.model.entities.entity.Delta
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Tests if the registration of the input works correctly.
 */
internal class TestInputRegistration {
    @Test
    fun pressingRightRegistersIncreaseAndReleaseClearsIt() {
        val controller: InputController = InputControllerPlayer(InputMapperImpl())

        Assertions.assertEquals(Delta.NO_ACTION, controller.inputState?.xMovementDelta)

        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.keyCode)
        Assertions.assertEquals(Delta.INCREASE, controller.inputState?.xMovementDelta)

        controller.onKeyReleased(KeyCodeKeyboard.RIGHT.keyCode)
        Assertions.assertEquals(Delta.NO_ACTION, controller.inputState?.xMovementDelta)
    }
}
