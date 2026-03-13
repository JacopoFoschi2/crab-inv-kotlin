package it.unibo.crabinv.controller.core.input

import it.unibo.crabinv.model.entities.entity.Delta
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Tests if the input works correctly.
 */
internal class TestInputControllerPlayer {
    @Test
    fun initialStateIsIdleAndNotShooting() {
        val controller: InputControllerPlayer = newController()

        val snap = controller.inputState

        Assertions.assertFalse(snap.isShooting)
        Assertions.assertEquals(Delta.NO_ACTION, snap.xMovementDelta)
    }

    @Test
    fun pressLeftMovesDecreaseOnX() {
        val controller: InputControllerPlayer = newController()

        controller.onKeyPressed(KeyCodeKeyboard.LEFT.keyCode)
        val snap = controller.inputState

        Assertions.assertEquals(Delta.DECREASE, snap.xMovementDelta)
        Assertions.assertFalse(snap.isShooting)
    }

    @Test
    fun pressRightMovesIncreaseOnX() {
        val controller: InputControllerPlayer = newController()

        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.keyCode)
        val snap = controller.inputState

        Assertions.assertEquals(Delta.INCREASE, snap.xMovementDelta)
        Assertions.assertFalse(snap.isShooting)
    }

    @Test
    fun leftAndRightTogetherCancelOut() {
        val controller: InputControllerPlayer = newController()

        controller.onKeyPressed(KeyCodeKeyboard.LEFT.keyCode)
        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.keyCode)
        val snap = controller.inputState

        Assertions.assertEquals(Delta.NO_ACTION, snap.xMovementDelta)
    }

    @Test
    fun releasingOneOfConflictingKeysResolvesDirection() {
        val controller: InputControllerPlayer = newController()

        controller.onKeyPressed(KeyCodeKeyboard.LEFT.keyCode)
        controller.onKeyPressed(KeyCodeKeyboard.RIGHT.keyCode)
        Assertions.assertEquals(Delta.NO_ACTION, controller.inputState.xMovementDelta)

        controller.onKeyReleased(KeyCodeKeyboard.RIGHT.keyCode)
        Assertions.assertEquals(Delta.DECREASE, controller.inputState.xMovementDelta)
    }

    @Test
    fun shootIsTrueWhileHeldAndFalseWhenReleased() {
        val controller: InputControllerPlayer = newController()

        controller.onKeyPressed(KeyCodeKeyboard.SHOOT.keyCode)
        Assertions.assertTrue(controller.inputState.isShooting)

        controller.onKeyReleased(KeyCodeKeyboard.SHOOT.keyCode)
        Assertions.assertFalse(controller.inputState.isShooting)
    }

    @Test
    fun unmappedKeyIsIgnored() {
        val controller: InputControllerPlayer = newController()

        val unmappedKeyCode = 9999
        controller.onKeyPressed(unmappedKeyCode)

        val snap = controller.inputState
        Assertions.assertFalse(snap.isShooting)
        Assertions.assertEquals(Delta.NO_ACTION, snap.xMovementDelta)
    }

    companion object {
        private fun newController(): InputControllerPlayer {
            val mapper: InputMapper = InputMapperImpl()
            return InputControllerPlayer(mapper)
        }
    }
}
