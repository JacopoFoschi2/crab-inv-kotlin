package it.unibo.crabinv.Model.i18n;

import java.util.Locale;

/**
 * Lists all currently supported locales to ensure correct usage
 */
public enum SupportedLocales {
    ENGLISH(new Locale.Builder().setLanguage("en").setRegion("US").build(), "/flags/flag_en.png"),
    ITALIAN(new Locale.Builder().setLanguage("it").setRegion("IT").build(), "/flags/flag_it.png"),;

    private final Locale locale;
    private final String imagePath;

    SupportedLocales(Locale locale, String imagePath) {
        this.locale = locale;
        this.imagePath = imagePath;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getImagePath() {
        return imagePath;
    }
}