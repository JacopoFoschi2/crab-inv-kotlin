package it.unibo.crabinv.Model.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLocalization {
    private Localization loc;

    @BeforeEach
    public void setup() {
        loc = new Localization(SUPPORTED_LOCALES.ENGLISH);
    }

    @Test
    void testCorrectKeyFetch() {
        String expectedResult = "PLAY";
        Assertions.assertEquals(expectedResult, loc.getString(TextKeys.PLAY));
    }

    @Test
    void testCorrectLocaleSwap() {
        String expectedResult = "GIOCA";
        loc.setLocale(SUPPORTED_LOCALES.ITALIAN);
        Assertions.assertEquals(expectedResult, loc.getString(TextKeys.PLAY));
    }

    @Test
    void testCorrectLocaleSwapped() {
        var expectedResult = SUPPORTED_LOCALES.ITALIAN;
        loc.setLocale(SUPPORTED_LOCALES.ITALIAN);
        Assertions.assertEquals(expectedResult, loc.getCurrentLocale());
        expectedResult = SUPPORTED_LOCALES.ENGLISH;
        loc.setLocale(SUPPORTED_LOCALES.ENGLISH);
        Assertions.assertEquals(expectedResult, loc.getCurrentLocale());
    }
}
