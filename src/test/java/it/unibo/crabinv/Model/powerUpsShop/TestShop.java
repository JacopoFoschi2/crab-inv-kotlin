package it.unibo.crabinv.Model.powerUpsShop;

import it.unibo.crabinv.Model.save.UserProfile;
import it.unibo.crabinv.Model.save.UserProfileImpl;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestShop {
    private static final int MAGIC_NUMBER_20 = 20;
    private static final int MAGIC_NUMBER_50 = 50;

    @Test
    public void testSuccefulPurchase() {
        final Shop shop = new ShopLogic();
        final UserProfile profile = new UserProfileImpl(new LinkedHashMap<>());
        final PowerUp powerUp = new PowerUpLogic(PowerUpType.HEALTH_UP, MAGIC_NUMBER_50, 3);

        profile.addCurrency(100);
        final boolean result = shop.purchase(profile, powerUp);

        assertTrue(result);
        assertEquals(MAGIC_NUMBER_50, profile.getCurrency());
        assertEquals(2, profile.getPowerUpLevel(powerUp.getPowerUpType()));

    }

    @Test
    public void testFailPurchaseForCurrency() {
        final Shop shop = new ShopLogic();
        final UserProfile profile = new UserProfileImpl(new LinkedHashMap<>());
        final PowerUp powerUp = new PowerUpLogic(PowerUpType.HEALTH_UP, MAGIC_NUMBER_50, 3);

        profile.addCurrency(MAGIC_NUMBER_20);
        final boolean result = shop.purchase(profile, powerUp);

        assertFalse(result);

        assertEquals(MAGIC_NUMBER_20, profile.getCurrency());
        assertEquals(1, profile.getPowerUpLevel(powerUp.getPowerUpType()));

    }

    @Test
    public void testFailPurchaseForMaxLevel() {
        final Shop shop = new ShopLogic();
        final UserProfile profile = new UserProfileImpl(new LinkedHashMap<>());
        final PowerUp powerUp = new PowerUpLogic(PowerUpType.HEALTH_UP, MAGIC_NUMBER_50, 3);

        profile.addCurrency(100);
        profile.updatePowerUp(powerUp.getPowerUpType(), 3);

        final boolean result = shop.purchase(profile, powerUp);

        assertFalse(result);
        assertEquals(100, profile.getCurrency());
        assertEquals(3, profile.getPowerUpLevel(powerUp.getPowerUpType()));
    }
}
