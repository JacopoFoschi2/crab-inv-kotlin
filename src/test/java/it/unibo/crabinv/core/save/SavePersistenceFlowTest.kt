package it.unibo.crabinv.core.save;

import it.unibo.crabinv.controller.core.save.SaveControllerImpl;
import it.unibo.crabinv.controller.core.save.SessionController;
import it.unibo.crabinv.controller.core.save.SessionControllerImpl;
import it.unibo.crabinv.core.persistence.json.SaveRepositoryGson;
import it.unibo.crabinv.core.persistence.repository.SaveRepository;
import it.unibo.crabinv.model.core.save.GameSession;
import it.unibo.crabinv.model.core.save.PlayerMemorial;
import it.unibo.crabinv.model.core.save.Save;
import it.unibo.crabinv.model.core.save.SessionRecord;
import it.unibo.crabinv.model.powerups.PowerUpType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Tests if the persistence works correctly.
 */
class SavePersistenceFlowTest {
    private static final int MAX_SPEED_UP = 3;
    private static final int MAX_FIRERATE_UP = 2;
    private static final int MAX_HEALTH_UP = 5;
    @TempDir
    private Path tempDir;

    @Test
    void testFullSaveFlowCreationLoadPowerUpsSessionGameOverMemorialAndPersistence() throws IOException {
        // Arrange: repository + create a new Save
        final SaveRepository repository = new SaveRepositoryGson(tempDir);
        final SaveControllerImpl saveController = new SaveControllerImpl(repository);

        final Save save = saveController.createSave();

        // Assert: save created correctly
        Assertions.assertNotNull(save, "Save must be created");
        Assertions.assertNotNull(save.getSaveId(), "Save UUID must not be null");
        Assertions.assertTrue(save.getCreationTimeStamp() > 0, "Save creation timestamp must be > 0");
        Assertions.assertNotNull(save.getUserProfile(), "UserProfile must not be null");
        Assertions.assertNotNull(save.getPlayerMemorial(), "PlayerMemorial must not be null");

        // Act + Assert: modify powerups in UserProfile
        save.getUserProfile().updatePowerUp(PowerUpType.SPEED_UP, MAX_SPEED_UP);
        save.getUserProfile().updatePowerUp(PowerUpType.FIRERATE_UP, MAX_FIRERATE_UP);
        save.getUserProfile().updatePowerUp(PowerUpType.HEALTH_UP, MAX_HEALTH_UP);

        Assertions.assertEquals(MAX_SPEED_UP, save.getUserProfile().getPowerUpLevel(PowerUpType.SPEED_UP));
        Assertions.assertEquals(MAX_FIRERATE_UP, save.getUserProfile().getPowerUpLevel(PowerUpType.FIRERATE_UP));
        Assertions.assertEquals(MAX_HEALTH_UP, save.getUserProfile().getPowerUpLevel(PowerUpType.HEALTH_UP));

        // Act: create/load a GameSession through SessionController
        final SessionController sessionController = new SessionControllerImpl(save);
        final GameSession gameSession = sessionController.newGameSession();

        Assertions.assertNotNull(gameSession, "GameSession must be created/loaded");
        Assertions.assertNotNull(save.getGameSession(), "GameSession must be bound to Save");

        // Mutate session a bit to have meaningful values
        final int addCurrency = 42;
        gameSession.advanceLevel();
        gameSession.addCurrency(addCurrency);

        final long sessionStartTs = gameSession.getStartingTimeStamp();
        final int expectedLastLevel = gameSession.getCurrentLevel();
        final int expectedLastCurrency = gameSession.getCurrency();

        // Act: close with game over and add to memorial
        final PlayerMemorial memorialBefore = save.getPlayerMemorial();
        final int memorialSizeBefore = memorialBefore.getMemorialList().size();

        gameSession.markGameOver();
        sessionController.gameOverGameSession();

        // Assert: session closed, memorial updated
        Assertions.assertNull(save.getGameSession(), "After gameOverGameSession(), GameSession must be closed (null)");

        final PlayerMemorial memorialAfter = save.getPlayerMemorial();
        Assertions.assertEquals(
                memorialSizeBefore + 1,
                memorialAfter.getMemorialList().size(),
                "PlayerMemorial must contain one more record after game over"
        );

        final SessionRecord record = memorialAfter.getMemorialRecord(sessionStartTs);
        Assertions.assertNotNull(record, "Memorial must contain a record for the ended session");
        Assertions.assertEquals(sessionStartTs, record.getStartingTimeStamp(), "Record timestamp must match session start");
        Assertions.assertEquals(expectedLastLevel, record.getLastLevel(), "Record last level must match session last level");
        Assertions.assertEquals(
                expectedLastCurrency,
                record.getLastCurrency(),
                "Record last currency must match session last currency");

        // Act: persist Save and reload it
        final UUID saveId = save.getSaveId();
        repository.saveSaveFile(save);
        final Save loaded = repository.loadSaveFile(saveId);

        // Assert: loaded Save is consistent and data persisted
        Assertions.assertNotNull(loaded, "Loaded save must not be null");
        Assertions.assertEquals(saveId, loaded.getSaveId(), "Loaded save must preserve UUID");
        Assertions.assertEquals(
                save.getCreationTimeStamp(),
                loaded.getCreationTimeStamp(),
                "Loaded save must preserve creation timestamp");

        // PowerUps persisted
        Assertions.assertEquals(MAX_SPEED_UP, loaded.getUserProfile().getPowerUpLevel(PowerUpType.SPEED_UP));
        Assertions.assertEquals(MAX_FIRERATE_UP, loaded.getUserProfile().getPowerUpLevel(PowerUpType.FIRERATE_UP));
        Assertions.assertEquals(MAX_HEALTH_UP, loaded.getUserProfile().getPowerUpLevel(PowerUpType.HEALTH_UP));

        // Memorial persisted
        final SessionRecord loadedRecord = loaded.getPlayerMemorial().getMemorialRecord(sessionStartTs);
        Assertions.assertNotNull(loadedRecord, "Loaded memorial must contain the previously saved session record");
        Assertions.assertEquals(expectedLastLevel, loadedRecord.getLastLevel());
        Assertions.assertEquals(expectedLastCurrency, loadedRecord.getLastCurrency());

        // Session remains closed after persistence (expected: null serialized/deserialized)
        Assertions.assertNull(loaded.getGameSession(), "Loaded save should not have an active GameSession after game over");
    }
}
