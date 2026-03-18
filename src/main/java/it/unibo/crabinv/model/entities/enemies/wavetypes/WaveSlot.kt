package it.unibo.crabinv.model.entities.enemies.wavetypes

/**
 * Contains the available spawn slots for the [Wave] spawn.
 * @param waveSlot value of the slot constant
 */
enum class WaveSlot(
    /**
     * @return the corresponding slot number.
     */
    val waveSlot: Int,
) {
    S1(1),
    S2(2),
    S3(3),
    S4(4),
    S5(5),
    S6(6),
    S7(7),
    S8(8),
    S9(9),
    S10(10),
    S11(11),
    S12(12),
}
