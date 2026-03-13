package it.unibo.crabinv.controller.core.save

import it.unibo.crabinv.core.persistence.repository.SaveRepository
import it.unibo.crabinv.model.core.save.Save
import java.io.IOException
import java.util.Objects
import java.util.UUID

/**
 * Implementation of [SaveController].
 * @param saveRepository the [SaveRepository] used by the [SaveControllerImpl]
 */
class SaveControllerImpl(
    saveRepository: SaveRepository?,
) : SaveController {
    @Throws(IOException::class)
    override fun saveControlAndLoad(): Save {
        val saveList = this.saveRepository.list()
        return if (saveList.isEmpty()) {
            createSave()
        } else if (saveList.size == 1) {
            saveList.first()
        } else {
            selectSave(saveList)
        }
    }

    @Throws(IOException::class)
    override fun createSave(): Save {
        val newSaveFile = this.saveRepository.newSave()
        this.saveRepository.saveSaveFile(newSaveFile)
        return newSaveFile
    }

    override fun selectSave(saveList: MutableList<Save>): Save {
        return saveList.last()
        // Placeholder for user-directed save selection if implemented
    }

    @Throws(IOException::class)
    override fun updateSave(save: Save) {
        this.saveRepository.saveSaveFile(save)
    }

    /**
     * {@inheritDoc}
     */
    @Throws(IOException::class)
    override fun loadSave(saveId: UUID): Save = this.saveRepository.loadSaveFile(saveId)

    @Throws(IOException::class)
    override fun deleteSave(saveId: UUID) {
        this.saveRepository.delete(saveId)
    }

    val saveRepository: SaveRepository = Objects.requireNonNull<SaveRepository>(saveRepository)
}
