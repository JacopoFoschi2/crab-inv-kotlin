package it.unibo.crabinv.model.powerUpsShop;

import it.unibo.crabinv.model.save.UserProfile;

public class ShopLogic implements Shop {
    @Override
    public boolean purchase(UserProfile profile, PowerUp item) {

        if (profile.getCurrency() >= item.getCost() &&
                profile.getPowerUpLevel(item.getPowerUpType()) < item.getMaxLevel()){
            int powerupLevel = profile.getPowerUpLevel(item.getPowerUpType());
            powerupLevel++;
            profile.subCurrency(item.getCost());
            profile.updatePowerUp(item.getPowerUpType(), powerupLevel);
            return true;
        }
        else{
            return false;
        }
    }
}
