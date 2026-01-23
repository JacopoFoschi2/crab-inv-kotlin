package it.unibo.crabinv.Model.i18n;

import java.util.Locale;

/**
 * Lists all currently supported locales to ensure correct usage
 */
public enum SupportedLocales {
    ENGLISH(new Locale.Builder().setLanguage("en").setRegion("US").build(), "/flags/flag_en.png", "ENGLISH"),
    ITALIAN(new Locale.Builder().setLanguage("it").setRegion("IT").build(), "/flags/flag_it.png", "ITALIANO"),;

    private final Locale locale;
    private final String imagePath;
    private final String localizedName;

    SupportedLocales(Locale locale, String imagePath, String localizedName) {
        this.locale = locale;
        this.imagePath = imagePath;
        this.localizedName = localizedName;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLocalizedName() {
        return localizedName;
    }
}