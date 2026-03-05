package it.unibo.crabinv.model.core.i18n

import java.util.MissingResourceException
import java.util.ResourceBundle

/**
 * `Localization` is engineered to handle all localization cases across the application.
 *
 * <h1>Changing localization</h1>
 *
 *
 * Localization changes are to be made with `loc.setLocale(locale)` selecting locale from
 * supported list
 *
 * <h1>String fetching</h1>
 *
 *
 * To fetch a string from current locale use `loc.getString(key)` selecting key
 * from [TextKeys] enum
 */
class Localization {
    private var messages: ResourceBundle? = null
    private var currentLocale: SupportedLocales? = null

    /**
     * Creates new Localization instance based on input locale.
     *
     * @param locale is to be chosen from SupportedLocales
     */
    constructor(locale: SupportedLocales) {
        setLocale(locale)
    }

    /**
     * Default constructor to create a new clean localization.
     */
    constructor()

    /**
     * Changes currently loaded locale.
     *
     * @param locale is to be chosen from SupportedLocales
     */
    fun setLocale(locale: SupportedLocales?) {
        currentLocale = locale
        messages = ResourceBundle.getBundle("i18n.messages", currentLocale!!.locale)
    }

    /**
     * Gets string based on the current locale.
     *
     * @param key the key of the wanted string. Take it from TextKeys
     * @return the wanted string
     */
    fun getString(key: TextKeys): String =
        try {
            messages!!.getString(key.key)
        } catch (_: MissingResourceException) {
            "KEY_" + key.key + " MISSING FROM SELECTED LOCALE"
        }

    /**
     * Gets currently set Locale.
     *
     * @return currently set Locale
     */
    fun getCurrentLocale(): SupportedLocales = currentLocale!!
}
