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

class SaveRepositoryGsonTest {

    @TempDir
    Path tempDir;                     // directory temporaneo fornito da JUnit

    private SaveRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        // il costruttore di SaveRepositoryGson accetta la directory di salvataggio
        repository = new SaveRepositoryGson(tempDir);
    }

    @Test
    void testPersistenceAndDeepFieldAssertion() throws IOException {
        // -------------------------------------------------
        // Arrange – creazione di un Save completo
        // -------------------------------------------------
        SaveControllerImpl controller = new SaveControllerImpl(repository);
        Save originalSave = controller.createSave();          // crea un nuovo Save con UUID e timestamp
        UUID saveId = originalSave.getSaveId();

        // UserProfile
        originalSave.getUserProfile().addCurrency(100);

        // GameSession
        originalSave.newGameSession();
        GameSessionImpl gameSession = (GameSessionImpl) originalSave.getGameSession();
        gameSession.subPlayerHealth(1);
        gameSession.addPlayerHealth(1);
        gameSession.markGameWon();

        // PlayerMemorial – aggiunta di un SessionRecord
        SessionRecordImpl testRecord = new SessionRecordImpl(gameSession);
        originalSave.getPlayerMemorial().addMemorialRecord(testRecord);

        // -------------------------------------------------
        // Act – salvataggio e ricarica
        // -------------------------------------------------
        repository.saveSaveFile(originalSave);
        Save loadedSave = repository.loadSaveFile(saveId);

        // -------------------------------------------------
        // Assert – verifica di tutti i campi (deep)
        // -------------------------------------------------
        Assertions.assertNotNull(loadedSave, "Loaded save cannot be null");

        // Save (UUID e timestamp)
        Assertions.assertEquals(originalSave.getSaveId(), loadedSave.getSaveId(),
                "UUIDs must be identical");
        Assertions.assertEquals(originalSave.getCreationTimeStamp(),
                loadedSave.getCreationTimeStamp(),
                "Creation timestamps must match");

        // UserProfile
        Assertions.assertEquals(100, loadedSave.getUserProfile().getCurrency(),
                "Currency must be persisted");

        // GameSession
        Assertions.assertEquals(3, loadedSave.getGameSession().getPlayerHealth(),
                "Player health must be persisted");
        Assertions.assertTrue(loadedSave.getGameSession().isGameWon(),
                "Game-won flag must be persisted");

        // PlayerMemorial – SessionRecord
        List<SessionRecord> memorialRecords = loadedSave.getPlayerMemorial().getMemorialList();
        Assertions.assertFalse(memorialRecords.isEmpty(), "Memorial records must not be empty");

        SessionRecord loadedRecord = memorialRecords.get(0);

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
        // -------------------------------------------------
        // Arrange – creazione di due salvataggi distinti
        // -------------------------------------------------
        Save first = repository.newSave();
        Save second = repository.newSave();

        repository.saveSaveFile(first);
        repository.saveSaveFile(second);

        // -------------------------------------------------
        // Act – lista dei salvataggi presenti nella cartella
        // -------------------------------------------------
        List<Save> saves = repository.list();

        // -------------------------------------------------
        // Assert
        // -------------------------------------------------
        Assertions.assertEquals(2, saves.size(),
                "Expected exactly 2 save files in the temporary folder");
    }

    @Test
    void testDeleteSaveFile() throws IOException {
        // -------------------------------------------------
        // Arrange – creazione e salvataggio di un file
        // -------------------------------------------------
        Save save = repository.newSave();
        repository.saveSaveFile(save);

        // -------------------------------------------------
        // Act – cancellazione
        // -------------------------------------------------
        repository.delete(save.getSaveId());

        // -------------------------------------------------
        // Assert – il caricamento deve fallire con IOException
        // -------------------------------------------------
        Assertions.assertThrows(IOException.class,
                () -> repository.loadSaveFile(save.getSaveId()),
                "Loading a deleted file must throw IOException");
    }
}
