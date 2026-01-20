package it.unibo.crabinv.i18n;

import java.util.Locale;
import java.util.Locale.*;
import java.util.ResourceBundle;

public class Localization {
    private static Localization localization;
    private ResourceBundle messages;
    private Locale currentLocale;
    /**
     * Nested class that lists all the currently supported locales
     * to ensure use of only implemented and correctly formed ones
     */
    public static final class SUPPORTED_LOCALES {
            public static final Locale ENGLISH = new Builder().setLanguage("en").setRegion("US").build();
            public static final Locale ITALIAN = new Builder().setLanguage("it").setRegion("IT").build();
    }

    /**
     * Creates new Localization instance based on input locale
     * @param locale is to be chosen from SUPPORTED_LOCALES
     */
    private Localization(Locale locale) {
        setLocale(locale);
    }

    public static Localization getInstance() {
        if (localization == null) {
            localization = new Localization(Locale.ENGLISH);
        }
        return localization;
    }

    /**
     * Changes currently loaded locale
     * @param locale is to be chosen from SUPPORTED_LOCALES
     */
    public void setLocale(Locale locale) {
        currentLocale = locale;
        messages = ResourceBundle.getBundle("i18n.messages", currentLocale);
    }

    /**
     * Gets string based on the current locale
     * @param key the key of the wanted string. Take it from TextKeys
     * @return the wanted string
     */
    public String get(String key) {
        return messages.getString(key);
    }
}
