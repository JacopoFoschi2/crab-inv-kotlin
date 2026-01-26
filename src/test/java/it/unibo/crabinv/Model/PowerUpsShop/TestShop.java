package it.unibo.crabinv.Model.PowerUpsShop;
import it.unibo.crabinv.Model.save.UserProfile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestShop {
    @Test
    public void testSuccefulPurchase() {
        Shop shop = new ShopLogic();
        UserProfile profile = new UserProfileLogic(100);
        PowerUp powerUp = new PowerUpLogic("shield",50, 3);

        boolean result = shop.purchase(profile,powerUp);

        assertTrue(result);
        assertEquals(50,profile.getCurrentPlayerCurrency());
        assertEquals(1, profile.getPowerUpLevel(powerUp.getPowerUpName()));

    }

    @Test
    public void testFailPurchaseForCurrency() {
        Shop shop = new ShopLogic();
        UserProfile profile = new UserProfileLogic(20);
        PowerUp powerUp = new PowerUpLogic("shield",50, 3);

        boolean result = shop.purchase(profile,powerUp);

        assertFalse(result);

        assertEquals(20,profile.getCurrentPlayerCurrency());
        assertEquals(0, profile.getPowerUpLevel(powerUp.getPowerUpName()));

    }
    @Test
    public void testFailPurchaseForMaxLevel() {
        Shop shop = new ShopLogic();
        UserProfile profile = new UserProfileLogic(100);
        PowerUp powerUp = new PowerUpLogic("shield",50, 3);

        profile.updatePowerUp(powerUp.getPowerUpName(),3);

        boolean result = shop.purchase(profile,powerUp);

        assertFalse(result);
        assertEquals(100,profile.getCurrentPlayerCurrency());
        assertEquals(3, profile.getPowerUpLevel(powerUp.getPowerUpName()));
    }
}
