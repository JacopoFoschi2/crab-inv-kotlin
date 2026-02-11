package it.unibo.crabinv.model.entities.enemies.wavetypes;

import it.unibo.crabinv.model.entities.enemies.wave.Wave;

/**
 * Contains the available spawn slots for the {@link Wave} spawn.
 */
public enum WaveSlot {
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
    S12(12);

    private final int waveSlot;

    /**
     * Constructor of {@link WaveSlot}.
     *
     * @param waveSlot value of the slot constant
     */
    WaveSlot(final int waveSlot) {
        this.waveSlot = waveSlot;
    }

    /**
     * Returns the corresponding slot number.
     *
     * @return the corresponding slot number.
     */
    public int getWaveSlot() {
        return waveSlot;
    }
}
