package it.unibo.crabinv.controller.save;

import it.unibo.crabinv.model.save.Save;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Contract for a controller to manage the Save-related Operations.
 */
public interface SaveController {

    /**
     * Checks the save files (and if there are any) and loads the correct save.
     *
     * @return the loaded save
     */
    Save saveControlAndLoad() throws IOException;

    /**
     * Creates a new default save file.
     *
     * @return the newly created save file
     */
    Save createSave() throws IOException;

    /**
     * Selects the save lo load from a saveList.
     *
     * @param saveList the saveList containing all the found valid files
     * @return the selected save
     */
    Save selectSave(List<Save> saveList);

    /**
     * Updates the SaveFile.
     *
     * @param save the up-to-date Save to be saved
     * @throws IOException if an I/O error occurs
     */
    void updateSave(Save save) throws IOException;

    /**
     * Loads a selected save file to be used by the game.
     *
     * @param saveId the identifier of the file to load
     * @return the loaded save
     */
    Save loadSave(UUID saveId) throws IOException;

    /**
     * Deletes the selected file.
     *
     * @param saveId the identifier of the file to delete
     */
    void deleteSave(UUID saveId) throws IOException;
}
