package it.unibo.crabinv.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestLocalization {
    private Localization loc;

    @BeforeEach
    void setUp() {
        loc = new Localization(Localization.SUPPORTED_LOCALES.ENGLISH);
    }

    @Test
    void testCorrectKeyFetch() {
        String expectedResult = "PLAY";
        Assertions.assertEquals(expectedResult, loc.get(TextKeys.PLAY));
    }

    @Test
    void testCorrectLocaleSwap() {
        String expectedResult = "GIOCA";
        loc.setLocale(Localization.SUPPORTED_LOCALES.ITALIAN);
        Assertions.assertEquals(expectedResult, loc.get(TextKeys.PLAY));
    }
}
