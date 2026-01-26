package it.unibo.crabinv.Model.persistence.repository;

import it.unibo.crabinv.Model.save.Save;

import java.util.List;

public interface SaveRepository {

    Save newSave();

    void save(Save save) throws java.io.IOException;

    List<Save> list() throws java.io.IOException;

    void load(java.util.UUID saveUUID) throws java.io.IOException;

    void delete(java.util.UUID saveUUDI) throws java.io.IOException;
}
