package it.unibo.crabinv.Model.i18n;

import java.util.Locale;
import java.util.Locale.Builder;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * {@code Localization} is engineered to handle all localization cases across the application.
 *
 * <h1>Changing localization</h1>
 * <p>Localization changes are to be made with {@code loc.setLocale(locale)} selecting locale from
 * supported list</p>
 *
 * <h1>String fetching</h1>
 * <p>To fetch a string from current locale use {@code loc.getString(key)} selecting key
 * from {@link TextKeys} enum</p>
 */
public class Localization {
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
    public Localization(SUPPORTED_LOCALES locale) {
        setLocale(locale);
    }

    /**
     * Creates new clean Localization
     */
    public Localization() {}

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

    /**
     * Gets currently set Locale
     * @return currently set Locale
     */
    public SUPPORTED_LOCALES getCurrentLocale() {
        return currentLocale;
    }

    /**
     * Gets the currently supported Locales
     * @return an array of the currently supported Locales
     */
    public SUPPORTED_LOCALES[] getSupportedLocales() {
        return SUPPORTED_LOCALES.values();
    }
}