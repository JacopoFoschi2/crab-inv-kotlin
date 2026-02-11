package it.unibo.crabinv.controller.core.i18n;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.model.core.i18n.Localization;
import it.unibo.crabinv.model.core.i18n.SupportedLocales;
import it.unibo.crabinv.model.core.i18n.TextKeys;

/**
 * Provides all the apis to control the localization.
 */
public class LocalizationController {
    private final Localization loc;

    /**
     * Binds the controller to it's model {@link Localization}.
     *
     * @param loc the injected instance of localization
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") // Localization is injected and owned by the caller.
    public LocalizationController(final Localization loc) {
        this.loc = loc;
    }

    /**
     * Sets language.
     *
     * @param locale the language to set
     */
    public void setLanguage(final SupportedLocales locale) {
        loc.setLocale(locale);
    }

    /**
     * @return the currently selected Locale
     */
    public SupportedLocales getLanguage() {
        return loc.getCurrentLocale();
    }

    /**
     * @param key the key you want to take the string from
     * @return the string associated to the key in input in the set language
     */
    public String getString(final TextKeys key) {
        return loc.getString(key);
    }
}
