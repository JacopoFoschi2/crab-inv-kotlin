package it.unibo.crabinv.Model.persistence.repository;

import it.unibo.crabinv.Model.save.Save;

import java.util.List;

/**
 * Manages the persistence of Save objects.
 */
public interface SaveRepository {

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
    void save(Save save) throws java.io.IOException;

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
    Save load(java.util.UUID saveUUID) throws java.io.IOException;

    /**
     * Deletes the Save specified by the UUID passed
     * @param saveUUID the identifier to select the Save
     * @throws java.io.IOException if an I/O error occurs
     */
    void delete(java.util.UUID saveUUID) throws java.io.IOException;
}
