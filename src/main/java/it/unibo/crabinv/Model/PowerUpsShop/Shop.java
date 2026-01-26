package it.unibo.crabinv.Model.PowerUpsShop;

import it.unibo.crabinv.Model.save.UserProfile;

public interface Shop {

    /**
     * @param profile varible that keeps track in the profile which power up has been taken
     * @param item the single item that it is present in the shop
     * @return if the purchase went well
     */
    boolean purchase(UserProfile profile, PowerUp item);
}
