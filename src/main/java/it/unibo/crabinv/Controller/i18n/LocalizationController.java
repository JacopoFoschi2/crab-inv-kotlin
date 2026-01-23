package it.unibo.crabinv.Controller.i18n;

import it.unibo.crabinv.Model.i18n.Localization;
import it.unibo.crabinv.Model.i18n.SUPPORTED_LOCALES;

/**
 * Handles all operations related to Localization
 */
public class LocalizationController {
    Localization loc;

    /**
     * Binds the controller to it's model {@link Localization}
     * @param loc the instance of localization
     */
    public LocalizationController(Localization loc) {
        this.loc = loc;
    }

    /**
     * Sets language
     * @param locale the language to set
     */
    public void setLanguage(SUPPORTED_LOCALES locale) {
        loc.setLocale(locale);
    }

    /**
     * @return the currently selected Locale
     */
    public SUPPORTED_LOCALES getLanguage() {
        return loc.getCurrentLocale();
    }
}
