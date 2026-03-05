package it.unibo.crabinv.model.powerups

import it.unibo.crabinv.model.core.save.UserProfile
import it.unibo.crabinv.model.core.save.UserProfileImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class TestShop {
    @Test
    fun testSuccessfulPurchase() {
        val shop: Shop = ShopLogic()
        val profile: UserProfile = UserProfileImpl(LinkedHashMap<PowerUpType?, Int?>())
        val powerUp: PowerUp = PowerUpLogic(PowerUpType.HEALTH_UP, MAGIC_NUMBER_50, 3)

        profile.addCurrency(100)
        val result = shop.purchase(profile, powerUp)

        Assertions.assertTrue(result)
        Assertions.assertEquals(MAGIC_NUMBER_50, profile.getCurrency())
        Assertions.assertEquals(2, profile.getPowerUpLevel(powerUp.getPowerUpType()))
    }

    @Test
    fun testFailPurchaseForCurrency() {
        val shop: Shop = ShopLogic()
        val profile: UserProfile = UserProfileImpl(LinkedHashMap<PowerUpType?, Int?>())
        val powerUp: PowerUp = PowerUpLogic(PowerUpType.HEALTH_UP, MAGIC_NUMBER_50, 3)

        profile.addCurrency(MAGIC_NUMBER_20)
        val result = shop.purchase(profile, powerUp)

        Assertions.assertFalse(result)

        Assertions.assertEquals(MAGIC_NUMBER_20, profile.getCurrency())
        Assertions.assertEquals(1, profile.getPowerUpLevel(powerUp.getPowerUpType()))
    }

    @Test
    fun testFailPurchaseForMaxLevel() {
        val shop: Shop = ShopLogic()
        val profile: UserProfile = UserProfileImpl(LinkedHashMap<PowerUpType?, Int?>())
        val powerUp: PowerUp = PowerUpLogic(PowerUpType.HEALTH_UP, MAGIC_NUMBER_50, 3)

        profile.addCurrency(100)
        profile.updatePowerUp(powerUp.getPowerUpType(), 3)

        val result = shop.purchase(profile, powerUp)

        Assertions.assertFalse(result)
        Assertions.assertEquals(100, profile.getCurrency())
        Assertions.assertEquals(3, profile.getPowerUpLevel(powerUp.getPowerUpType()))
    }

    companion object {
        private const val MAGIC_NUMBER_20 = 20
        private const val MAGIC_NUMBER_50 = 50
    }
}
