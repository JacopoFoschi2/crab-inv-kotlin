package it.unibo.crabinv.Model.persistence.json;

import it.unibo.crabinv.Model.persistence.repository.SaveRepository;
import it.unibo.crabinv.Model.save.Save;

import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SaveRepositoryJson implements SaveRepository{

    private final Path saveDirectory;

    public SaveRepositoryJson (Path saveDirectory) throws IOException{
        this.saveDirectory = saveDirectory;
        if (Files.notExists(saveDirectory)) {
            Files.createDirectories(saveDirectory);
        }
    }

    @Override
    public Save newSave() {
        return null;
    }

    @Override
    public void save(Save save) throws IOException {

    }

    @Override
    public List<Save> list() throws IOException {
        return List.of();
    }

    @Override
    public void load(UUID saveUUID) throws IOException {

    }

    @Override
    public void delete(UUID saveUUDI) throws IOException {

    }
}
