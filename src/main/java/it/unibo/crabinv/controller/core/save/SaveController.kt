package it.unibo.crabinv.controller.core.save

import it.unibo.crabinv.model.core.save.Save
import java.io.IOException
import java.util.UUID

/**
 * Contract for a controller to manage the Save-related Operations.
 */
interface SaveController {
    /**
     * Checks the save files (and if there are any) and loads the correct save.
     * @return the loaded save
     */
    @Throws(IOException::class)
    fun saveControlAndLoad(): Save

    /**
     * Creates a new default save file.
     * @return the newly created save file
     */
    @Throws(IOException::class)
    fun createSave(): Save

    /**
     * Selects the save lo load from a saveList.
     * @param saveList the saveList containing all the found valid files
     * @return the selected save
     */
    fun selectSave(saveList: MutableList<Save>): Save

    /**
     * Updates the SaveFile.
     * @param save the up-to-date Save to be saved
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun updateSave(save: Save)

    /**
     * Loads a selected save file to be used by the game.
     * @param saveId the identifier of the file to load
     * @return the loaded save
     */
    @Throws(IOException::class)
    fun loadSave(saveId: UUID): Save

    /**
     * Deletes the selected file.
     * @param saveId the identifier of the file to delete
     */
    @Throws(IOException::class)
    fun deleteSave(saveId: UUID)
}
