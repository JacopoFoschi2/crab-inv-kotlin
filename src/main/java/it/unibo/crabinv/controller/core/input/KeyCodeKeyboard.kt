package it.unibo.crabinv.controller.core.input

/**
 * Enum of keyboard key to be used by the [InputMapper] and the [InputController].
 * @param keyCode the keyCode of the key
 */
enum class KeyCodeKeyboard(
    /**
     * @return the keycode associated with the enum constant
     */
    val keyCode: Int,
) {
    LEFT(37),
    RIGHT(39),
    UP(38),
    DOWN(40),
    SHOOT(32), // SPACEBAR
    PAUSE(27), // ESC
    UNPAUSE(8), ; // BACKSPACE

    companion object {
        /**
         * Returns the [KeyCodeKeyboard] associated with the key code input.
         * @param code the KeyCode of the key to look up
         * @return the corresponding [KeyCodeKeyboard] or null if it doesn't exist
         */
        @JvmStatic
        fun findKeyCode(code: Int): KeyCodeKeyboard? {
            for (k in entries) {
                if (k.keyCode == code) {
                    return k
                }
            }
            return null
        }
    }
}
