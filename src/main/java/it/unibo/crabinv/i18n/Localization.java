package it.unibo.crabinv.i18n;

import java.util.Locale;
import java.util.Locale.Builder;
import java.util.ResourceBundle;

public class Localization {
    private static Localization localization;
    private ResourceBundle messages;
    private SUPPORTED_LOCALES currentLocale;

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
        if (localization == null) {
            localization = new Localization(SUPPORTED_LOCALES.ENGLISH);
        }
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
    public String getString(String key) {
        return messages.getString(key);
    }
}
