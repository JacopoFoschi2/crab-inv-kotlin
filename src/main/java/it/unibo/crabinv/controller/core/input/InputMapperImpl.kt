package it.unibo.crabinv.controller.core.input

import it.unibo.crabinv.controller.core.input.KeyCodeKeyboard.Companion.findKeyCode
import it.unibo.crabinv.model.entities.entity.Delta

/**
 * Uses [KeyCodeKeyboard] to bind keyboard keypresses to actions.
 */
class InputMapperImpl : InputMapper {
    override fun mapToXDelta(inputCode: Int): Delta? {
        val key = findKeyCode(inputCode) ?: return Delta.NO_ACTION
        return when (key) {
            KeyCodeKeyboard.LEFT -> Delta.DECREASE
            KeyCodeKeyboard.RIGHT -> Delta.INCREASE
            else -> Delta.NO_ACTION
        }
    }

    override fun mapToShoot(inputCode: Int): Boolean {
        val key = findKeyCode(inputCode)
        return key == KeyCodeKeyboard.SHOOT
    }

    override fun mapToPause(inputCode: Int): Boolean {
        val key = findKeyCode(inputCode)
        return key == KeyCodeKeyboard.PAUSE
    }

    override fun mapToUnPause(inputCode: Int): Boolean {
        val key = findKeyCode(inputCode)
        return key == KeyCodeKeyboard.UNPAUSE
    }
}
