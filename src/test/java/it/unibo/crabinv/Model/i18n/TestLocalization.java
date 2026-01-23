package it.unibo.crabinv.Model.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLocalization {
    private Localization loc;

    @BeforeEach
    public void setup() {
        loc = new Localization(SupportedLocales.ENGLISH);
    }

    @Test
    void testCorrectKeyFetch() {
        String expectedResult = "PLAY";
        Assertions.assertEquals(expectedResult, loc.getString(TextKeys.PLAY));
    }

    @Test
    void testCorrectLocaleSwap() {
        String expectedResult = "GIOCA";
        loc.setLocale(SupportedLocales.ITALIAN);
        Assertions.assertEquals(expectedResult, loc.getString(TextKeys.PLAY));
    }

    @Test
    void testCorrectLocaleSwapped() {
        var expectedResult = SupportedLocales.ITALIAN;
        loc.setLocale(SupportedLocales.ITALIAN);
        Assertions.assertEquals(expectedResult, loc.getCurrentLocale());
        expectedResult = SupportedLocales.ENGLISH;
        loc.setLocale(SupportedLocales.ENGLISH);
        Assertions.assertEquals(expectedResult, loc.getCurrentLocale());
    }
}
