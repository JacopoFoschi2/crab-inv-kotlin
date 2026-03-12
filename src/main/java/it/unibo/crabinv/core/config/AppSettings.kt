package it.unibo.crabinv.core.config

import it.unibo.crabinv.model.core.i18n.SupportedLocales

/**
 * Helper class that copies the current state of volume and localization to
 * be then read by [SettingsFileManager] to save the state before app closing.
 * It has the explicit purpose of streamlining the saving procedure by making it easier.
 * @param bgmVolume the volume of the background music
 * @param sfxVolume the volume of the sound effects
 * @param isBGMMuted tells if the bgm is muted or not
 * @param isSFXMuted tells if the sfx are muted or not
 * @param locales the currently set locale
 */
class AppSettings(
    val bgmVolume: Int,
    val sfxVolume: Int,
    val isBGMMuted: Boolean,
    val isSFXMuted: Boolean,
    locales: SupportedLocales?,
) {
    val locales: SupportedLocales?

    /**
     * Constructs the record while ensuring that parameters are correct.
     */
    init {
        var locales = locales
        require(bgmVolume in 0..100) { "bgmVolume must be between 0 and 100" }
        require(sfxVolume in 0..100) { "sfxVolume must be between 0 and 100" }
        if (locales == null) {
            locales = SupportedLocales.ENGLISH
        }
        this.locales = locales
    }
}
