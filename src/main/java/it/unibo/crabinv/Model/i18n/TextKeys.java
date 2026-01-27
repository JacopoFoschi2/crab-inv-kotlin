package it.unibo.crabinv.Model.i18n;

/**
 * Provides all currently supported keys of localization.
 * These keys are to be used with {@code getKey()} method of {@link Localization} class.
 */
public enum TextKeys {
    PLAY("play");

    private final String key;

    TextKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}