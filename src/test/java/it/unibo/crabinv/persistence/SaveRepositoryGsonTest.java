package it.unibo.crabinv.persistence;

import it.unibo.crabinv.controller.save.SaveControllerImpl;
import it.unibo.crabinv.model.save.GameSessionImpl;
import it.unibo.crabinv.model.save.Save;
import it.unibo.crabinv.model.save.SessionRecord;
import it.unibo.crabinv.model.save.SessionRecordImpl;
import it.unibo.crabinv.persistence.json.SaveRepositoryGson;
import it.unibo.crabinv.persistence.repository.SaveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Tests if the saves happen correctly.
 */
class SaveRepositoryGsonTest {

    @TempDir
    private Path tempDir;

    private SaveRepository repository;

    @BeforeEach
    void setup() throws IOException {
        repository = new SaveRepositoryGson(tempDir);
    }

    @Test
    void testPersistenceAndDeepFieldAssertion() throws IOException {
        final SaveControllerImpl controller = new SaveControllerImpl(repository);
        final Save originalSave = controller.createSave();
        final UUID saveId = originalSave.getSaveId();

        originalSave.getUserProfile().addCurrency(100);

        originalSave.newGameSession();
        final GameSessionImpl gameSession = (GameSessionImpl) originalSave.getGameSession();
        gameSession.subPlayerHealth(1);
        gameSession.addPlayerHealth(1);
        gameSession.markGameWon();

        final SessionRecordImpl testRecord = new SessionRecordImpl(gameSession);
        originalSave.getPlayerMemorial().addMemorialRecord(testRecord);

        repository.saveSaveFile(originalSave);
        final Save loadedSave = repository.loadSaveFile(saveId);

        Assertions.assertNotNull(loadedSave, "Loaded save cannot be null");

        Assertions.assertEquals(originalSave.getSaveId(), loadedSave.getSaveId(),
                "UUIDs must be identical");
        Assertions.assertEquals(originalSave.getCreationTimeStamp(),
                loadedSave.getCreationTimeStamp(),
                "Creation timestamps must match");

        Assertions.assertEquals(100, loadedSave.getUserProfile().getCurrency(),
                "Currency must be persisted");

        Assertions.assertEquals(3, loadedSave.getGameSession().getPlayerHealth(),
                "Player health must be persisted");
        Assertions.assertTrue(loadedSave.getGameSession().isGameWon(),
                "Game-won flag must be persisted");

        final List<SessionRecord> memorialRecords = loadedSave.getPlayerMemorial().getMemorialList();
        Assertions.assertFalse(memorialRecords.isEmpty(), "Memorial records must not be empty");

        final SessionRecord loadedRecord = memorialRecords.getFirst();

        Assertions.assertEquals(testRecord.getStartingTimeStamp(),
                loadedRecord.getStartingTimeStamp(),
                "Starting timestamp must be preserved");
        Assertions.assertEquals(testRecord.getLastLevel(),
                loadedRecord.getLastLevel(),
                "Last level must be preserved");
        Assertions.assertEquals(testRecord.getLastCurrency(),
                loadedRecord.getLastCurrency(),
                "Last currency must be preserved");
        Assertions.assertEquals(testRecord.getWonGame(),
                loadedRecord.getWonGame(),
                "Game won flag must be preserved");
    }

    @Test
    void testListSaves() throws IOException {
        final Save first = repository.newSave();
        final Save second = repository.newSave();

        repository.saveSaveFile(first);
        repository.saveSaveFile(second);

        final List<Save> saves = repository.list();

        Assertions.assertEquals(2, saves.size(),
                "Expected exactly 2 save files in the temporary folder");
    }

    @Test
    void testDeleteSaveFile() throws IOException {
        final Save save = repository.newSave();
        repository.saveSaveFile(save);

        repository.delete(save.getSaveId());

        Assertions.assertThrows(IOException.class,
                () -> repository.loadSaveFile(save.getSaveId()),
                "Loading a deleted file must throw IOException");
    }
}
