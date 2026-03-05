package it.unibo.crabinv.model.core.i18n

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestLocalization {
    private var loc: Localization? = null

    @BeforeEach
    fun setup() {
        loc = Localization(SupportedLocales.ENGLISH)
    }

    @Test
    fun testCorrectKeyFetch() {
        val expectedResult = "PLAY"
        Assertions.assertEquals(expectedResult, loc!!.getString(TextKeys.PLAY))
    }

    @Test
    fun testCorrectLocaleSwap() {
        val expectedResult = "GIOCA"
        loc!!.setLocale(SupportedLocales.ITALIAN)
        Assertions.assertEquals(expectedResult, loc!!.getString(TextKeys.PLAY))
    }

    @Test
    fun testCorrectLocaleSwapped() {
        var expectedResult = SupportedLocales.ITALIAN
        loc!!.setLocale(SupportedLocales.ITALIAN)
        Assertions.assertEquals(expectedResult, loc!!.getCurrentLocale())
        expectedResult = SupportedLocales.ENGLISH
        loc!!.setLocale(SupportedLocales.ENGLISH)
        Assertions.assertEquals(expectedResult, loc!!.getCurrentLocale())
    }
}
