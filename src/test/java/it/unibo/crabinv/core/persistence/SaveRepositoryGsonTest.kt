package it.unibo.crabinv.core.persistence

import it.unibo.crabinv.controller.core.save.SaveControllerImpl
import it.unibo.crabinv.core.persistence.json.SaveRepositoryGson
import it.unibo.crabinv.core.persistence.repository.SaveRepository
import it.unibo.crabinv.model.core.save.GameSessionImpl
import it.unibo.crabinv.model.core.save.SessionRecord
import it.unibo.crabinv.model.core.save.SessionRecordImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.IOException
import java.nio.file.Path

/**
 * Tests if the saves happen correctly.
 */
internal class SaveRepositoryGsonTest {
    @TempDir
    private var tempDir: Path? = null

    private var repository: SaveRepository? = null

    @BeforeEach
    @Throws(IOException::class)
    fun setup() {
        repository = SaveRepositoryGson(tempDir)
    }

    @Test
    @Throws(IOException::class)
    fun testPersistenceAndDeepFieldAssertion() {
        val controller = SaveControllerImpl(repository)
        val originalSave = controller.createSave()
        val saveId = originalSave.getSaveId()

        originalSave.getUserProfile().addCurrency(100)

        originalSave.newGameSession()
        val gameSession = originalSave.getGameSession() as GameSessionImpl
        gameSession.subPlayerHealth(1)
        gameSession.addPlayerHealth(1)
        gameSession.markGameWon()

        val testRecord = SessionRecordImpl(gameSession)
        originalSave.getPlayerMemorial().addMemorialRecord(testRecord)

        repository!!.saveSaveFile(originalSave)
        val loadedSave = repository!!.loadSaveFile(saveId)

        Assertions.assertNotNull(loadedSave, "Loaded save cannot be null")

        Assertions.assertEquals(
            originalSave.getSaveId(),
            loadedSave!!.getSaveId(),
            "UUIDs must be identical",
        )
        Assertions.assertEquals(
            originalSave.getCreationTimeStamp(),
            loadedSave.getCreationTimeStamp(),
            "Creation timestamps must match",
        )

        Assertions.assertEquals(
            100,
            loadedSave.getUserProfile().getCurrency(),
            "Currency must be persisted",
        )

        Assertions.assertEquals(
            3,
            loadedSave.getGameSession().getPlayerHealth(),
            "Player health must be persisted",
        )
        Assertions.assertTrue(
            loadedSave.getGameSession().isGameWon(),
            "Game-won flag must be persisted",
        )

        val memorialRecords = loadedSave.getPlayerMemorial().getMemorialList()
        Assertions.assertFalse(memorialRecords.isEmpty(), "Memorial records must not be empty")

        val loadedRecord: SessionRecord = memorialRecords.first()

        Assertions.assertEquals(
            testRecord.startingTimeStamp,
            loadedRecord.getStartingTimeStamp(),
            "Starting timestamp must be preserved",
        )
        Assertions.assertEquals(
            testRecord.lastLevel,
            loadedRecord.getLastLevel(),
            "Last level must be preserved",
        )
        Assertions.assertEquals(
            testRecord.lastCurrency,
            loadedRecord.getLastCurrency(),
            "Last currency must be preserved",
        )
        Assertions.assertEquals(
            testRecord.isGameWon,
            loadedRecord.isGameWon(),
            "Game won flag must be preserved",
        )
    }

    @Test
    @Throws(IOException::class)
    fun testListSaves() {
        val first = repository!!.newSave()
        val second = repository!!.newSave()

        repository!!.saveSaveFile(first)
        repository!!.saveSaveFile(second)

        val saves = repository!!.list()

        Assertions.assertEquals(
            2,
            saves.size,
            "Expected exactly 2 save files in the temporary folder",
        )
    }

    @Test
    @Throws(IOException::class)
    fun testDeleteSaveFile() {
        val save = repository!!.newSave()
        repository!!.saveSaveFile(save)

        repository!!.delete(save.getSaveId())

        Assertions.assertThrows(
            IOException::class.java,
            { repository!!.loadSaveFile(save.getSaveId()) },
            "Loading a deleted file must throw IOException",
        )
    }
}
