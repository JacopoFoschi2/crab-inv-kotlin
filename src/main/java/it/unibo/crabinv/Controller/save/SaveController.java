package it.unibo.crabinv.Controller.save;

import it.unibo.crabinv.Model.save.*;
import it.unibo.crabinv.persistence.repository.SaveRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface SaveController {

    /**
     * Returns the SaveRepository used by the SaveController
     * @return the SaveRepository in use
     */
    SaveRepository getSaveRepository();

    /**
     * Checks the save files (and if there are any) and loads the correct save
     * @return the loaded save
     */
    Save saveControlAndLoad() throws IOException;

    /**
     * Creates a new default save file
     * @return the newly created save file
     */
    Save createSave() throws IOException;

    /**
     * Selects the save lo load from a saveList
     * @param saveList the saveList containing all the found valid files
     * @return the selected save
     */
    Save selectSave(List<Save> saveList);

    /**
     * Updates the SaveFile
     * @param save the up to date Save to be saved
     * @exception IOException if an I/O error occours
     */
    void updateSave (Save save) throws IOException;

    /**
     * Loads a selected save file to be used by the game
     * @return the loaded save
     */
    Save loadSave(UUID saveId) throws IOException;

    /**
     * Deletes the selected file
     */
    void deleteSave(UUID saveId) throws IOException;


}
