package it.unibo.crabinv.persistence.repository;

import it.unibo.crabinv.model.save.Save;
import it.unibo.crabinv.model.save.SaveFactory;

import java.nio.file.Path;
import java.util.List;

/**
 * Contract for the management and persistence of {@link Save}.
 */
public interface SaveRepository {

    /**
     * Retrieves the directory in use for the management of {@link Save} files.
     *
     * @return the directory in use
     */
    Path getSaveDirectory();

    /**
     * Retrieves the SaveFactory used for the save file actions.
     *
     * @return the SaveFactory in use
     */
    SaveFactory getSaveFactory();

    /**
     * Creates and returns a {@link Save}.
     *
     * @return the newly created {@link Save}
     */
    Save newSave();

    /**
     * Saves (Persists) the Save specified.
     *
     * @param save the {@link Save} to save
     * @throws java.io.IOException if an I/O error occurs
     */
    void saveSaveFile(Save save) throws java.io.IOException;

    /**
     * Lists all the {@link Save} objects found.
     *
     * @return the list of all {@link Save} objects found
     * @throws java.io.IOException if an I/O error occurs
     */
    List<Save> list() throws java.io.IOException;

    /**
     * Load the Save specified by the UUID passed.
     *
     * @param saveUUID the identifier to select the {@link Save}
     * @return the {@link Save} identified
     * @throws java.io.IOException if an I/O error occurs
     */
    Save loadSaveFile(java.util.UUID saveUUID) throws java.io.IOException;

    /**
     * Deletes the {@link Save} specified by the UUID passed.
     *
     * @param saveUUID the identifier to select the {@link Save}
     * @throws java.io.IOException if an I/O error occurs
     */
    void delete(java.util.UUID saveUUID) throws java.io.IOException;
}
