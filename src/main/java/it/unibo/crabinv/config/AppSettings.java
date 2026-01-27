package it.unibo.crabinv.config;

import it.unibo.crabinv.Model.i18n.SupportedLocales;

/**
 * Helper class that copies the current state of volume and localization to
 * be then read by {@link SettingsFileManager} to save the state
 * before app closing.
 * It has the explicit purpose of streamlining the saving procedure by making it easier.
 */
public record AppSettings(
        int bgmVolume,
        int sfxVolume,
        boolean isBGMMuted,
        boolean isSFXMuted,
        SupportedLocales locales) {
    /**
     * Constructs the record while ensuring that parameters are correct
     */
    public AppSettings (int bgmVolume, int sfxVolume, boolean isBGMMuted, boolean isSFXMuted, SupportedLocales locales) {
        if (bgmVolume < 0 || bgmVolume > 100) {
            throw new IllegalArgumentException("bgmVolume must be between 0 and 100");
        }
        if (sfxVolume < 0 || sfxVolume > 100) {
            throw new IllegalArgumentException("sfxVolume must be between 0 and 100");
        }
        this.bgmVolume = bgmVolume;
        this.sfxVolume = sfxVolume;
        this.isBGMMuted = isBGMMuted;
        this.isSFXMuted = isSFXMuted;
        this.locales = locales;
    }
}
