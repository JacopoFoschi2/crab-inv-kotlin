package it.unibo.crabinv.model.core.i18n;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * {@code Localization} is engineered to handle all localization cases across the application.
 *
 * <h1>Changing localization</h1>
 *
 * <p>Localization changes are to be made with {@code loc.setLocale(locale)} selecting locale from
 * supported list</p>
 *
 * <h1>String fetching</h1>
 *
 * <p>To fetch a string from current locale use {@code loc.getString(key)} selecting key
 * from {@link TextKeys} enum</p>
 */
public final class Localization {
    private ResourceBundle messages;
    private SupportedLocales currentLocale;

    /**
     * Creates new Localization instance based on input locale.
     *
     * @param locale is to be chosen from SupportedLocales
     */
    public Localization(final SupportedLocales locale) {
        setLocale(locale);
    }

    /**
     * Default constructor to create a new clean localization
     */
    public Localization() { }

    /**
     * Changes currently loaded locale.
     *
     * @param locale is to be chosen from SupportedLocales
     */
    public void setLocale(final SupportedLocales locale) {
        currentLocale = locale;
        messages = ResourceBundle.getBundle("i18n.messages", currentLocale.getLocale());
    }

    /**
     * Gets string based on the current locale.
     *
     * @param key the key of the wanted string. Take it from TextKeys
     * @return the wanted string
     */
    public String getString(final TextKeys key) {
        try {
            return messages.getString(key.getKey());
        } catch (final MissingResourceException e) {
            return "KEY_" + key.getKey() + " MISSING FROM SELECTED LOCALE";
        }
    }

    /**
     * Gets currently set Locale.
     *
     * @return currently set Locale
     */
    public SupportedLocales getCurrentLocale() {
        return currentLocale;
    }
}
