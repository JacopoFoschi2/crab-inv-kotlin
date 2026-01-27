package it.unibo.crabinv.Model.PowerUpsShop;

import it.unibo.crabinv.Model.save.UserProfile;

public class ShopLogic implements Shop {
    @Override
    public boolean purchase(UserProfile profile, PowerUp item) {

        if (profile.getCurrency() >= item.getCost() &&
                profile.getPowerUpLevel(item.getPowerUpName()) < item.getMaxLevel()){
            int powerupLevel = profile.getPowerUpLevel(item.getPowerUpName());
            powerupLevel++;
            profile.subCurrency(item.getCost());
            profile.updatePowerUp(item.getPowerUpName(), powerupLevel);
            return true;
        }
        else{
            return false;
        }
    }
}
