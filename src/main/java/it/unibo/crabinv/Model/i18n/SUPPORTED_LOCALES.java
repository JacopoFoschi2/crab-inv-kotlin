package it.unibo.crabinv.Model.i18n;

import java.util.Locale;

/**
 * Lists all currently supported locales to ensure correct usage
 */
public enum SUPPORTED_LOCALES {
    ENGLISH(new Locale.Builder().setLanguage("en").setRegion("US").build()),
    ITALIAN(new Locale.Builder().setLanguage("it").setRegion("IT").build());

    private final Locale locale;

    SUPPORTED_LOCALES(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}