package it.unibo.crabinv.Model.PowerUpsShop;

import it.unibo.crabinv.Model.PowerUp;
import it.unibo.crabinv.Model.Shop;
import it.unibo.crabinv.Model.UserProfile;

public class ShopLogic implements Shop {
    @Override
    public void purchase(UserProfile profile, PowerUp item) {
        if (item.getLevel()< item.getMaxLevel()){
            int  currentCost = item.getCost();

            if(profile.getCurrentPlayerCurrency() >= currentCost){
                profile.subtractCurrency(currentCost);
                item.incrementLevel();
                profile.updatePowerUp(item);
            }


        }
    }
}
