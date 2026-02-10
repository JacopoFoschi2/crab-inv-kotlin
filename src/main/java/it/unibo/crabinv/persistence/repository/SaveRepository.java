package it.unibo.crabinv.persistence.repository;

import it.unibo.crabinv.model.save.*;

import java.nio.file.Path;
import java.util.List;

/**
 * Manages the persistence of Save objects.
 */
public interface SaveRepository {

    /**
     * Retrieves the SaveDirectory where to manipulate save files
     * @return the SaveDirectory in use
     */
    Path getSaveDirectory();

    /**
     * Retrieves the SaveFactory used for the save file actions
     * @return the SaveFactory in use
     */
    SaveFactory getSaveFactory();

    /**
     * Creates and returns a Save
     * @return the newly created Save
     */
    Save newSave();

    /**
     * Saves (Persists) the Save specified
     * @param save the Save to save
     * @throws java.io.IOException if an I/O error occurs
     */
    void saveSaveFile(Save save) throws java.io.IOException;

    /**
     * Lists all the Save objects found
     * @return the list of all Save objects found
     * @throws java.io.IOException if an I/O error occurs
     */
    List<Save> list() throws java.io.IOException;

    /**
     * Load the Save specified by the UUID passed
     * @param saveUUID the identifier to select the Save
     * @return the Save identified
     * @throws java.io.IOException if an I/O error occurs
     */
    Save loadSaveFile(java.util.UUID saveUUID) throws java.io.IOException;

    /**
     * Deletes the Save specified by the UUID passed
     * @param saveUUID the identifier to select the Save
     * @throws java.io.IOException if an I/O error occurs
     */
    void delete(java.util.UUID saveUUID) throws java.io.IOException;
}
