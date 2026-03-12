package it.unibo.crabinv.model.powerups;

import it.unibo.crabinv.model.core.save.UserProfile;

/**
 * It's the logic of the shop.
 */
public final class ShopLogic implements Shop {
    @Override
    public boolean purchase(final UserProfile profile, final PowerUp item) {

        if (profile.getCurrency() >= item.getCost()
                &&
                profile.getPowerUpLevel(item.getPowerUpType()) < item.getMaxLevel()) {
            int powerupLevel = profile.getPowerUpLevel(item.getPowerUpType());
            powerupLevel++;
            profile.subCurrency(item.getCost());
            profile.updatePowerUp(item.getPowerUpType(), powerupLevel);
            return true;
        } else {
            return false;
        }
    }
}
