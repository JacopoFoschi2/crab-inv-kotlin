package it.unibo.crabinv.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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
}
