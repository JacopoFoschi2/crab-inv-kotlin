package it.unibo.crabinv.model.core.i18n;

import java.util.Locale;

/**
 * Provides all currently supported locales to ensure correct usage.
 * It also lists the path of the flag representing said language, and it's localised name
 */
public enum SupportedLocales {
    ENGLISH(new Locale.Builder().setLanguage("en").setRegion("US").build(), "/flags/flag_en.png", "ENGLISH"),
    ITALIAN(new Locale.Builder().setLanguage("it").setRegion("IT").build(), "/flags/flag_it.png", "ITALIANO"),;

    private final Locale locale;
    private final String imagePath;
    private final String localizedName;

    /**
     * Constructs the enum of all the languages.
     *
     * @param locale the set locale
     * @param imagePath the imagePath of the flag that represents it
     * @param localizedName the localized name of the language
     */
    SupportedLocales(final Locale locale, final String imagePath, final String localizedName) {
        this.locale = locale;
        this.imagePath = imagePath;
        this.localizedName = localizedName;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @return the image path of the language flag
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @return the localized name of the language
     */
    public String getLocalizedName() {
        return localizedName;
    }

    @Override
    public String toString() {
        return getLocalizedName();
    }
}
