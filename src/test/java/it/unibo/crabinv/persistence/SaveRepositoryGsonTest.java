package it.unibo.crabinv.persistence;

import it.unibo.crabinv.Model.save.GameSessionImpl;
import it.unibo.crabinv.Model.save.Save;
import it.unibo.crabinv.Model.save.SessionRecordImpl;
import it.unibo.crabinv.persistence.json.SaveRepositoryGson;
import it.unibo.crabinv.persistence.repository.SaveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

class SaveRepositoryGsonTest {

    @TempDir
    Path tempDir;

    private SaveRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        repository = new SaveRepositoryGson(tempDir);
    }

    @Test
    void testPersistenceAndDeepFieldAssertion() throws IOException {
        Save originalSave = repository.newSave();
        UUID saveId = originalSave.getSaveId();

        /* arrange  */
        //UserProfile assign
        originalSave.getUserProfile().addCurrency(100);
        //GameSession assign
        originalSave.getGameSession().subPlayerHealth(1);
        originalSave.getGameSession().addPlayerHealth(1);
        //PlayerMemorial - SessionRecord assign
        long testRecordTimeStamp = Instant.now().toEpochMilli();
        SessionRecordImpl testRecord = new SessionRecordImpl(testRecordTimeStamp, 3, 50);
        originalSave.getPlayerMemorial().addMemorialRecord(testRecord);

        /* Act */
        // Save and load
        repository.saveSaveFile(originalSave);
        Save loadedSave = repository.loadSaveFile(saveId);

        /* Assert */
        // Load assert
        Assertions.assertNotNull(loadedSave, "Loaded save cannot be null");

        //SaveImpl
        Assertions.assertEquals(originalSave.getSaveId(), loadedSave.getSaveId(), "UUIDs must be the same");
        Assertions.assertEquals(
                originalSave.getCreationTimeStamp(),
                loadedSave.getCreationTimeStamp(),
                "Original and loaded timestamps must be the same"
        );
        System.out.println("Created: " + originalSave.getCreationTimeStamp());
        System.out.println("Loaded: " + loadedSave.getCreationTimeStamp());

        //UserProfile
        Assertions.assertEquals(100, loadedSave.getUserProfile().getCurrency());
        //GameSession
        Assertions.assertEquals(1, loadedSave.getGameSession().getPlayerHealth());
        //PlayerMemorial - SessionRecord
        Assertions.assertEquals(testRecordTimeStamp,
                loadedSave.getPlayerMemorial().getMemorialRecord(testRecordTimeStamp).getStartingTimeStamp());

        // Esempio: Verifico che il timestamp di creazione sia identico

    }

    @Test
    void testListSaves() throws IOException {
        // Creiamo e salviamo due file
        repository.saveSaveFile(repository.newSave());
        repository.saveSaveFile(repository.newSave());

        // Verifichiamo che la lista contenga 2 elementi
        List<Save> saves = repository.list();
        Assertions.assertEquals(2, saves.size(), "Expected 2 files inside folder");
    }

    @Test
    void testDeleteSaveSaveFile() throws IOException {
        Save save = repository.newSave();
        repository.saveSaveFile(save);

        // Cancelliamo
        repository.delete(save.getSaveId());

        // Verifichiamo che il caricamento ora fallisca (IOException come definito nel tuo codice)
        Assertions.assertThrows(IOException.class, () -> {
            repository.loadSaveFile(save.getSaveId());
        }, "Loading a deleted file must throw IOException");
    }
}