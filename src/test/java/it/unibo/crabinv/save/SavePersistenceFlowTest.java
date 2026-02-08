package it.unibo.crabinv.save;

import it.unibo.crabinv.Controller.save.SaveControllerImpl;
import it.unibo.crabinv.Controller.save.SessionController;
import it.unibo.crabinv.Controller.save.SessionControllerImpl;
import it.unibo.crabinv.Model.powerUpsShop.PowerUpType;
import it.unibo.crabinv.Model.save.GameSession;
import it.unibo.crabinv.Model.save.PlayerMemorial;
import it.unibo.crabinv.Model.save.Save;
import it.unibo.crabinv.Model.save.SessionRecord;
import it.unibo.crabinv.persistence.json.SaveRepositoryGson;
import it.unibo.crabinv.persistence.repository.SaveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

class SavePersistenceFlowTest {

    @TempDir
    Path tempDir;

    @Test
    void testFullSaveFlow_creation_load_powerups_session_gameOver_memorial_and_persistence() throws IOException {
        // Arrange: repository + create a new Save
        SaveRepository repository = new SaveRepositoryGson(tempDir);
        SaveControllerImpl saveController = new SaveControllerImpl(repository);

        Save save = saveController.createSave();

        // Assert: save created correctly
        Assertions.assertNotNull(save, "Save must be created");
        Assertions.assertNotNull(save.getSaveId(), "Save UUID must not be null");
        Assertions.assertTrue(save.getCreationTimeStamp() > 0, "Save creation timestamp must be > 0");
        Assertions.assertNotNull(save.getUserProfile(), "UserProfile must not be null");
        Assertions.assertNotNull(save.getPlayerMemorial(), "PlayerMemorial must not be null");

        // Act + Assert: modify powerups in UserProfile
        save.getUserProfile().updatePowerUp(PowerUpType.SPEED_UP, 3);
        save.getUserProfile().updatePowerUp(PowerUpType.FIRERATE_UP, 2);
        save.getUserProfile().updatePowerUp(PowerUpType.HEALTH_UP, 5);

        Assertions.assertEquals(3, save.getUserProfile().getPowerUpLevel(PowerUpType.SPEED_UP));
        Assertions.assertEquals(2, save.getUserProfile().getPowerUpLevel(PowerUpType.FIRERATE_UP));
        Assertions.assertEquals(5, save.getUserProfile().getPowerUpLevel(PowerUpType.HEALTH_UP));

        // Act: create/load a GameSession through SessionController
        SessionController sessionController = new SessionControllerImpl(save);
        GameSession gameSession = sessionController.newGameSession();

        Assertions.assertNotNull(gameSession, "GameSession must be created/loaded");
        Assertions.assertNotNull(save.getGameSession(), "GameSession must be bound to Save");

        // Mutate session a bit to have meaningful values
        gameSession.advanceLevel();
        gameSession.addCurrency(42);

        long sessionStartTs = gameSession.getStartingTimeStamp();
        int expectedLastLevel = gameSession.getCurrentLevel();
        int expectedLastCurrency = gameSession.getCurrency();

        // Act: close with game over and add to memorial
        PlayerMemorial memorialBefore = save.getPlayerMemorial();
        int memorialSizeBefore = memorialBefore.getMemorialList().size();

        gameSession.markGameOver();
        sessionController.gameOverGameSession();

        // Assert: session closed, memorial updated
        Assertions.assertNull(save.getGameSession(), "After gameOverGameSession(), GameSession must be closed (null)");

        PlayerMemorial memorialAfter = save.getPlayerMemorial();
        Assertions.assertEquals(
                memorialSizeBefore + 1,
                memorialAfter.getMemorialList().size(),
                "PlayerMemorial must contain one more record after game over"
        );

        SessionRecord record = memorialAfter.getMemorialRecord(sessionStartTs);
        Assertions.assertNotNull(record, "Memorial must contain a record for the ended session");
        Assertions.assertEquals(sessionStartTs, record.getStartingTimeStamp(), "Record timestamp must match session start");
        Assertions.assertEquals(expectedLastLevel, record.getLastLevel(), "Record last level must match session last level");
        Assertions.assertEquals(expectedLastCurrency, record.getLastCurrency(), "Record last currency must match session last currency");

        // Act: persist Save and reload it
        UUID saveId = save.getSaveId();
        repository.saveSaveFile(save);
        Save loaded = repository.loadSaveFile(saveId);

        // Assert: loaded Save is consistent and data persisted
        Assertions.assertNotNull(loaded, "Loaded save must not be null");
        Assertions.assertEquals(saveId, loaded.getSaveId(), "Loaded save must preserve UUID");
        Assertions.assertEquals(save.getCreationTimeStamp(), loaded.getCreationTimeStamp(), "Loaded save must preserve creation timestamp");

        // PowerUps persisted
        Assertions.assertEquals(3, loaded.getUserProfile().getPowerUpLevel(PowerUpType.SPEED_UP));
        Assertions.assertEquals(2, loaded.getUserProfile().getPowerUpLevel(PowerUpType.FIRERATE_UP));
        Assertions.assertEquals(5, loaded.getUserProfile().getPowerUpLevel(PowerUpType.HEALTH_UP));

        // Memorial persisted
        SessionRecord loadedRecord = loaded.getPlayerMemorial().getMemorialRecord(sessionStartTs);
        Assertions.assertNotNull(loadedRecord, "Loaded memorial must contain the previously saved session record");
        Assertions.assertEquals(expectedLastLevel, loadedRecord.getLastLevel());
        Assertions.assertEquals(expectedLastCurrency, loadedRecord.getLastCurrency());

        // Session remains closed after persistence (expected: null serialized/deserialized)
        Assertions.assertNull(loaded.getGameSession(), "Loaded save should not have an active GameSession after game over");
    }
}