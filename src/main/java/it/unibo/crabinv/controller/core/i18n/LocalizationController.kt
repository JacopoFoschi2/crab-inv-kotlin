package it.unibo.crabinv.controller.core.i18n

import it.unibo.crabinv.model.core.i18n.Localization
import it.unibo.crabinv.model.core.i18n.SupportedLocales
import it.unibo.crabinv.model.core.i18n.TextKeys

/**
 * Provides all the apis to control the localization.
 * Binds the controller to it's model [Localization].
 *
 * @param loc the injected instance of localization
 */
class LocalizationController(
    private val loc: Localization,
) {
    var language: SupportedLocales?
        /**
         * @return the currently selected Locale
         */
        get() = loc.getCurrentLocale()

        /**
         * Sets language.
         *
         * @param locale the language to set
         */
        set(locale) = loc.setLocale(locale)

    /**
     * @param key the key you want to take the string from
     * @return the string associated to the key in input in the set language
     */
    fun getString(key: TextKeys): String = loc.getString(key)
}
