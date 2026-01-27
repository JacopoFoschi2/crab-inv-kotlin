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
        SupportedLocales locales) { }
