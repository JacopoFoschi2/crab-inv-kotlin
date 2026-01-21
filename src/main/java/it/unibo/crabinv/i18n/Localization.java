package it.unibo.crabinv.i18n;

import java.util.Locale;
import java.util.Locale.Builder;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localization {
    private static final Localization localization = new Localization(SUPPORTED_LOCALES.ENGLISH);
    private ResourceBundle messages;
    private SUPPORTED_LOCALES currentLocale;

    /**
     * Lists all currently supported locales to ensure correct usage
     */
    public enum SUPPORTED_LOCALES {
        ENGLISH(new Builder().setLanguage("en").setRegion("US").build()),
        ITALIAN(new Builder().setLanguage("it").setRegion("IT").build());

        private final Locale locale;

        SUPPORTED_LOCALES(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }
    }

    /**
     * Creates new Localization instance based on input locale
     * @param locale is to be chosen from SUPPORTED_LOCALES
     */
    private Localization(SUPPORTED_LOCALES locale) {
        setLocale(locale);
    }

    public static Localization getInstance() {
        return localization;
    }

    /**
     * Changes currently loaded locale
     * @param locale is to be chosen from SUPPORTED_LOCALES
     */
    public void setLocale(SUPPORTED_LOCALES locale) {
        currentLocale = locale;
        messages = ResourceBundle.getBundle("i18n.messages", currentLocale.getLocale());
    }

    /**
     * Gets string based on the current locale
     * @param key the key of the wanted string. Take it from TextKeys
     * @return the wanted string
     */
    public String getString(TextKeys key) {
        try {
            return messages.getString(key.getKey());
        } catch (MissingResourceException e) {
            return "KEY_" + key.getKey() + " MISSING FROM SELECTED LOCALE";
        }
    }
}