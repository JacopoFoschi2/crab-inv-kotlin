package it.unibo.crabinv.Model.powerUpsShop;
import it.unibo.crabinv.Model.save.UserProfile;
import it.unibo.crabinv.Model.save.UserProfileImpl;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestShop {
    @Test
    public void testSuccefulPurchase() {
        Shop shop = new ShopLogic();
        UserProfile profile = new UserProfileImpl(new LinkedHashMap<>());
        PowerUp powerUp = new PowerUpLogic(PowerUpType.HEALTH_UP,50, 3);

        profile.addCurrency(100);
        boolean result = shop.purchase(profile,powerUp);

        assertTrue(result);
        assertEquals(50,profile.getCurrency());
        assertEquals(1, profile.getPowerUpLevel(powerUp.getPowerUpType()));

    }

    @Test
    public void testFailPurchaseForCurrency() {
        Shop shop = new ShopLogic();
        UserProfile profile = new UserProfileImpl(new LinkedHashMap<>());
        PowerUp powerUp = new PowerUpLogic(PowerUpType.HEALTH_UP,50, 3);

        profile.addCurrency(20);
        boolean result = shop.purchase(profile,powerUp);

        assertFalse(result);

        assertEquals(20,profile.getCurrency());
        assertEquals(0, profile.getPowerUpLevel(powerUp.getPowerUpType()));

    }
    @Test
    public void testFailPurchaseForMaxLevel() {
        Shop shop = new ShopLogic();
        UserProfile profile = new UserProfileImpl(new LinkedHashMap<>());
        PowerUp powerUp = new PowerUpLogic(PowerUpType.HEALTH_UP,50, 3);

        profile.addCurrency(100);
        profile.updatePowerUp(powerUp.getPowerUpType(),3);

        boolean result = shop.purchase(profile,powerUp);

        assertFalse(result);
        assertEquals(100,profile.getCurrency());
        assertEquals(3, profile.getPowerUpLevel(powerUp.getPowerUpType()));
    }
}
