package it.unibo.crabinv.controller.core.input

import it.unibo.crabinv.model.core.input.InputSnapshot
import it.unibo.crabinv.model.core.input.InputSnapshotImpl
import it.unibo.crabinv.model.entities.entity.Delta
import java.util.Objects

/**
 * Implementation of the [InputController] for controlling the [it.unibo.crabinv.model.entities.player.Player].
 */
class InputControllerPlayer(
    mapper: InputMapper?,
) : InputController {
    private val pressedKeys: MutableSet<Int?> = HashSet<Int?>()
    private val mapper: InputMapper = Objects.requireNonNull<InputMapper>(mapper)

    override fun onKeyPressed(keyCode: Int) {
        val validKey =
            mapper.mapToShoot(keyCode) ||
                mapper.mapToXDelta(keyCode) != Delta.NO_ACTION ||
                mapper.mapToPause(keyCode) ||
                mapper.mapToUnPause(keyCode)
        if (validKey) {
            this.pressedKeys.add(keyCode)
        }
    }

    override fun onKeyReleased(keyCode: Int) {
        this.pressedKeys.remove(keyCode)
    }

    override val inputState: InputSnapshot
        get() {
            val isPausePressed =
                pressedKeys
                    .stream()
                    .anyMatch { inputCode: Int? -> mapper.mapToPause(inputCode!!) }
            val isUnPausePressed =
                pressedKeys
                    .stream()
                    .anyMatch { inputCode: Int? -> mapper.mapToUnPause(inputCode!!) }
            val isShooting =
                pressedKeys
                    .stream()
                    .anyMatch { inputCode: Int? -> mapper.mapToShoot(inputCode!!) }
            val isInputRight =
                pressedKeys
                    .stream()
                    .map { inputCode: Int? ->
                        mapper.mapToXDelta(
                            inputCode!!,
                        )
                    }.anyMatch { delta: Delta? -> delta == Delta.INCREASE }
            val isInputLeft =
                pressedKeys
                    .stream()
                    .map { inputCode: Int? ->
                        mapper.mapToXDelta(
                            inputCode!!,
                        )
                    }.anyMatch { delta: Delta? -> delta == Delta.DECREASE }
            var xMovementDelta =
                Delta.NO_ACTION
            if (isInputRight && !isInputLeft) {
                xMovementDelta = Delta.INCREASE
            } else if (isInputLeft && !isInputRight) {
                xMovementDelta = Delta.DECREASE
            }
            return InputSnapshotImpl(isShooting, xMovementDelta, isPausePressed, isUnPausePressed)
        }
}
