package it.unibo.crabinv.model.powerups

import it.unibo.crabinv.model.core.save.UserProfile

/**
 * It's the logic of the shop.
 */
class ShopLogic : Shop {
    override fun purchase(
        profile: UserProfile,
        item: PowerUp,
    ): Boolean {
        if (profile.getCurrency() >= item.cost &&
            profile.getPowerUpLevel(item.powerUpType) < item.maxLevel
        ) {
            var powerupLevel = profile.getPowerUpLevel(item.powerUpType)
            powerupLevel++
            profile.subCurrency(item.cost)
            profile.updatePowerUp(item.powerUpType, powerupLevel)
            return true
        } else {
            return false
        }
    }
}
