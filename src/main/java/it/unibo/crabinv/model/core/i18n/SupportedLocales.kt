package it.unibo.crabinv.model.core.i18n

import java.util.Locale

/**
 * Provides all currently supported locales to ensure correct usage.
 * It also lists the path of the flag representing said language, and it's localised name
 */
enum class SupportedLocales(
    /**
     * @return the locale
     */
    val locale: Locale,
    /**
     * @return the image path of the language flag
     */
    val imagePath: String,
    /**
     * @return the localized name of the language
     */
    val localizedName: String,
) {
    ENGLISH(
        Locale
            .Builder()
            .setLanguage("en")
            .setRegion("US")
            .build(),
        "/flags/flag_en.png",
        "ENGLISH",
    ),
    ITALIAN(
        Locale
            .Builder()
            .setLanguage("it")
            .setRegion("IT")
            .build(),
        "/flags/flag_it.png",
        "ITALIANO",
    ),
    ;

    override fun toString(): String = this.localizedName
}
