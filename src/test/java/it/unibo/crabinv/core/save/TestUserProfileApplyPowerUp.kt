package it.unibo.crabinv.core.save

import it.unibo.crabinv.model.core.save.PlayerBaseStats
import it.unibo.crabinv.model.core.save.UserProfileImpl
import it.unibo.crabinv.model.powerups.PowerUpType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.EnumMap

internal class TestUserProfileApplyPowerUp {
    @Test
    fun applyMultiplyPowerUpComputesBaseTimesMultiplierTimesLevel() {
        val levels: MutableMap<PowerUpType?, Int?> = EnumMap<PowerUpType?, Int?>(PowerUpType::class.java)
        levels[PowerUpType.SPEED_UP] = MAX_SPEED_UP
        levels[PowerUpType.FIRERATE_UP] = MAX_FIRERATE_UP
        levels[PowerUpType.HEALTH_UP] = MAX_HEALTH_UP

        val profile = UserProfileImpl(levels)

        assertMultiply(profile, PowerUpType.SPEED_UP, MAX_SPEED_UP)
        assertMultiply(profile, PowerUpType.FIRERATE_UP, MAX_FIRERATE_UP)
        assertMultiply(profile, PowerUpType.HEALTH_UP, MAX_HEALTH_UP)
    }

    companion object {
        private const val EPS = 1e-12
        private const val MAX_SPEED_UP = 4
        private const val MAX_FIRERATE_UP = 2
        private const val MAX_HEALTH_UP = 5

        private fun assertMultiply(
            profile: UserProfileImpl,
            type: PowerUpType,
            level: Int,
        ) {
            val expected =
                PlayerBaseStats.getDoubleValueOf(type) * type.statMultiplier * level

            val actual = profile.applyMultiplyPowerUp(type)

            Assertions.assertEquals(expected, actual, EPS, "Wrong result for $type")
        }
    }
}
