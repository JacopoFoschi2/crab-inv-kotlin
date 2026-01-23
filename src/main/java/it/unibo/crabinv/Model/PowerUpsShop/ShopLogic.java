package it.unibo.crabinv.Model.PowerUpsShop;

import it.unibo.crabinv.Model.Save.UserProfile;

public class ShopLogic implements Shop {
    @Override
    public void purchase(UserProfile profile, PowerUp item) {
        // needed a better implementation with the level of UserProfile
        if (profile.getCurrentPlayerCurrency() >= item.getCost()){
            //need also the control of the currentLevel in UserProfile with item.getMaxLevel()
            profile.subtractCurrency(item.getCost());
        }
    }
}
