package it.unibo.crabinv.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestLocalization {
    private final Localization loc = Localization.getInstance();

    @Test
    void testCorrectKeyFetch() {
        String expectedResult = "PLAY";
        Assertions.assertEquals(expectedResult, loc.getString(TextKeys.PLAY));
    }

    @Test
    void testCorrectLocaleSwap() {
        String expectedResult = "GIOCA";
        loc.setLocale(Localization.SUPPORTED_LOCALES.ITALIAN);
        Assertions.assertEquals(expectedResult, loc.getString(TextKeys.PLAY));
    }

    @Test
    void testCorrectLocaleSwapped() {
        var expectedResult = Localization.SUPPORTED_LOCALES.ITALIAN;
        loc.setLocale(Localization.SUPPORTED_LOCALES.ITALIAN);
        Assertions.assertEquals(expectedResult, loc.getCurrentLocale());
        expectedResult = Localization.SUPPORTED_LOCALES.ENGLISH;
        loc.setLocale(Localization.SUPPORTED_LOCALES.ENGLISH);
        Assertions.assertEquals(expectedResult, loc.getCurrentLocale());
    }
}
