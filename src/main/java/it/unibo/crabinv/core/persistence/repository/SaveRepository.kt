package it.unibo.crabinv.core.persistence.repository

import it.unibo.crabinv.model.core.save.Save
import it.unibo.crabinv.model.core.save.SaveFactory
import java.io.IOException
import java.nio.file.Path
import java.util.UUID

/**
 * Contract for the management and persistence of [Save].
 */
interface SaveRepository {
    /**
     * Retrieves the directory in use for the management of [Save] files.
     * @return the directory in use
     */
    val saveDirectory: Path

    /**
     * Retrieves the SaveFactory used for the save file actions.
     * @return the SaveFactory in use
     */
    val saveFactory: SaveFactory

    /**
     * Creates and returns a [Save].
     * @return the newly created [Save]
     */
    fun newSave(): Save

    /**
     * Saves (Persists) the Save specified.
     * @param save the [Save] to save
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun saveSaveFile(save: Save)

    /**
     * Lists all the [Save] objects found.
     * @return the list of all [Save] objects found
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun list(): MutableList<Save>

    /**
     * Load the Save specified by the UUID passed.
     * @param saveUUID the identifier to select the [Save]
     * @return the [Save] identified
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun loadSaveFile(saveUUID: UUID): Save

    /**
     * Deletes the [Save] specified by the UUID passed.
     * @param saveUUID the identifier to select the [Save]
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun delete(saveUUID: UUID)
}
