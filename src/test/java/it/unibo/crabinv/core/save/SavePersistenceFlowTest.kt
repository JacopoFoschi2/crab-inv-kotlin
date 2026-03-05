package it.unibo.crabinv.core.save

import it.unibo.crabinv.controller.core.save.SaveControllerImpl
import it.unibo.crabinv.controller.core.save.SessionController
import it.unibo.crabinv.controller.core.save.SessionControllerImpl
import it.unibo.crabinv.core.persistence.json.SaveRepositoryGson
import it.unibo.crabinv.core.persistence.repository.SaveRepository
import it.unibo.crabinv.model.powerups.PowerUpType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.IOException
import java.nio.file.Path

/**
 * Tests if the persistence works correctly.
 */
internal class SavePersistenceFlowTest {
    @TempDir
    private var tempDir: Path? = null

    @Test
    @Throws(IOException::class)
    fun testFullSaveFlowCreationLoadPowerUpsSessionGameOverMemorialAndPersistence() {
        // Arrange: repository + create a new Save
        val repository: SaveRepository = SaveRepositoryGson(tempDir)
        val saveController = SaveControllerImpl(repository)

        val save = saveController.createSave()

        // Assert: save created correctly
        Assertions.assertNotNull(save, "Save must be created")
        Assertions.assertNotNull(save!!.getSaveId(), "Save UUID must not be null")
        Assertions.assertTrue(save.getCreationTimeStamp() > 0, "Save creation timestamp must be > 0")
        Assertions.assertNotNull(save.getUserProfile(), "UserProfile must not be null")
        Assertions.assertNotNull(save.getPlayerMemorial(), "PlayerMemorial must not be null")

        // Act + Assert: modify powerups in UserProfile
        save.getUserProfile().updatePowerUp(PowerUpType.SPEED_UP, MAX_SPEED_UP)
        save.getUserProfile().updatePowerUp(PowerUpType.FIRERATE_UP, MAX_FIRERATE_UP)
        save.getUserProfile().updatePowerUp(PowerUpType.HEALTH_UP, MAX_HEALTH_UP)

        Assertions.assertEquals(MAX_SPEED_UP, save.getUserProfile().getPowerUpLevel(PowerUpType.SPEED_UP))
        Assertions.assertEquals(MAX_FIRERATE_UP, save.getUserProfile().getPowerUpLevel(PowerUpType.FIRERATE_UP))
        Assertions.assertEquals(MAX_HEALTH_UP, save.getUserProfile().getPowerUpLevel(PowerUpType.HEALTH_UP))

        // Act: create/load a GameSession through SessionController
        val sessionController: SessionController = SessionControllerImpl(save)
        val gameSession = sessionController.newGameSession()

        Assertions.assertNotNull(gameSession, "GameSession must be created/loaded")
        Assertions.assertNotNull(save.getGameSession(), "GameSession must be bound to Save")

        // Mutate session a bit to have meaningful values
        val addCurrency = 42
        gameSession!!.advanceLevel()
        gameSession.addCurrency(addCurrency)

        val sessionStartTs = gameSession.getStartingTimeStamp()
        val expectedLastLevel = gameSession.getCurrentLevel()
        val expectedLastCurrency = gameSession.getCurrency()

        // Act: close with game over and add to memorial
        val memorialBefore = save.getPlayerMemorial()
        val memorialSizeBefore = memorialBefore.getMemorialList().size

        gameSession.markGameOver()
        sessionController.gameOverGameSession()

        // Assert: session closed, memorial updated
        Assertions.assertNull(save.getGameSession(), "After gameOverGameSession(), GameSession must be closed (null)")

        val memorialAfter = save.getPlayerMemorial()
        Assertions.assertEquals(
            memorialSizeBefore + 1,
            memorialAfter.getMemorialList().size,
            "PlayerMemorial must contain one more record after game over",
        )

        val record = memorialAfter.getMemorialRecord(sessionStartTs)
        Assertions.assertNotNull(record, "Memorial must contain a record for the ended session")
        Assertions.assertEquals(
            sessionStartTs,
            record!!.getStartingTimeStamp(),
            "Record timestamp must match session start",
        )
        Assertions.assertEquals(
            expectedLastLevel,
            record.getLastLevel(),
            "Record last level must match session last level",
        )
        Assertions.assertEquals(
            expectedLastCurrency,
            record.getLastCurrency(),
            "Record last currency must match session last currency",
        )

        // Act: persist Save and reload it
        val saveId = save.getSaveId()
        repository.saveSaveFile(save)
        val loaded = repository.loadSaveFile(saveId)

        // Assert: loaded Save is consistent and data persisted
        Assertions.assertNotNull(loaded, "Loaded save must not be null")
        Assertions.assertEquals(saveId, loaded.getSaveId(), "Loaded save must preserve UUID")
        Assertions.assertEquals(
            save.getCreationTimeStamp(),
            loaded.getCreationTimeStamp(),
            "Loaded save must preserve creation timestamp",
        )

        // PowerUps persisted
        Assertions.assertEquals(MAX_SPEED_UP, loaded.getUserProfile().getPowerUpLevel(PowerUpType.SPEED_UP))
        Assertions.assertEquals(MAX_FIRERATE_UP, loaded.getUserProfile().getPowerUpLevel(PowerUpType.FIRERATE_UP))
        Assertions.assertEquals(MAX_HEALTH_UP, loaded.getUserProfile().getPowerUpLevel(PowerUpType.HEALTH_UP))

        // Memorial persisted
        val loadedRecord = loaded.getPlayerMemorial().getMemorialRecord(sessionStartTs)
        Assertions.assertNotNull(loadedRecord, "Loaded memorial must contain the previously saved session record")
        Assertions.assertEquals(expectedLastLevel, loadedRecord!!.getLastLevel())
        Assertions.assertEquals(expectedLastCurrency, loadedRecord.getLastCurrency())

        // Session remains closed after persistence (expected: null serialized/deserialized)
        Assertions.assertNull(
            loaded.getGameSession(),
            "Loaded save should not have an active GameSession after game over",
        )
    }

    companion object {
        private const val MAX_SPEED_UP = 3
        private const val MAX_FIRERATE_UP = 2
        private const val MAX_HEALTH_UP = 5
    }
}
