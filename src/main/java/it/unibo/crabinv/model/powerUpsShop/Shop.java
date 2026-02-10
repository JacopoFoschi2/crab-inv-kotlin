package it.unibo.crabinv.model.powerUpsShop;

import it.unibo.crabinv.model.save.UserProfile;

/**
 * It's the interface that implements the shop and its methods.
 */
public interface Shop {
    /**
     * @param profile variable that keeps track in the profile which power up has been taken
     * @param item the single item that it is present in the shop
     * @return if the purchase went well
     */
    boolean purchase(UserProfile profile, PowerUp item);
}
