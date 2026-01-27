package it.unibo.crabinv.Model.persistence.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import it.unibo.crabinv.Model.persistence.repository.SaveRepository;
import it.unibo.crabinv.Model.Save.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Gson based Implementation of SaveRepository for json file persistence
 */
public class SaveRepositoryGson implements SaveRepository {

    private final Gson gson;
    private final Path saveDirectory;

    /**
     * Constructor, initializes the Gson.builder
     * @param saveDirectory the directory where the json files will be stored
     * @throws IOException if an I/O error occurs
     */
    public SaveRepositoryGson(Path saveDirectory) throws IOException {
        this.saveDirectory = (Files.exists(saveDirectory) ? saveDirectory : Files.createDirectory(saveDirectory));
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(Save.class, (JsonDeserializer<Save>) (json, type, context) ->
                context.deserialize(json, SaveImpl.class));
        builder.registerTypeAdapter(GameSession.class, (JsonDeserializer<GameSession>) (json, type, context) ->
                context.deserialize(json, GameSessionImpl.class));
        builder.registerTypeAdapter(UserProfile.class, (JsonDeserializer<UserProfile>) (json, type, context) ->
                context.deserialize(json, UserProfileImpl.class));
        builder.registerTypeAdapter(PlayerMemorial.class, (JsonDeserializer<PlayerMemorial>) (json, type, context) ->
                context.deserialize(json, PlayerMemorialImpl.class));
        this.gson = builder.create();
    }

    /**
     * Helper class, returns the deserialized json into a Save object
     * @param path the directory path
     * @return the Save object, or IOException if an I/O error occurs
     */
    private Save pathToSaveHandler(Path path) {
        try (java.io.BufferedReader reader = Files.newBufferedReader(path)) {
            return gson.fromJson(reader, Save.class);
        } catch (IOException error) {
            System.err.println("Cannot load files: " + path);
            return null;
        }
    }

    /**
     * Helper class, returns the Path to the file containing the UUID selected
     * @param id the UUID selected
     * @return the Path to the corresponding json file
     */
    private Path getFilePath (UUID id){
        return this.saveDirectory.resolve(id.toString() + ".json");
    }

    /** {@inheritDoc} */
    @Override
    public Save newSave() {
        return SaveImpl.createNewSave();

    }

    /** {@inheritDoc} */
    @Override
    public void save(Save save) throws IOException {
        Path filePath = getFilePath(save.getSaveId());
        try (java.io.Writer writer = Files.newBufferedWriter(filePath)) {
            this.gson.toJson(save, writer);
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<Save> list() throws IOException {
        try (Stream<Path> s = Files.list(saveDirectory)) {
            return s.filter(saveFile -> saveFile.toString().endsWith(".json"))
                    .map(this::pathToSaveHandler)
                    .filter(java.util.Objects::nonNull)
                    .toList();
        }
    }

    /** {@inheritDoc} */
    @Override
    public Save load(UUID saveUUID) throws IOException {
        Path filePath = getFilePath(saveUUID);
        return java.util.Optional.ofNullable(pathToSaveHandler(filePath))
                .orElseThrow(() -> new IOException("Cannot find save: " + saveUUID));
    }

    /** {@inheritDoc} */
    @Override
    public void delete(UUID saveUUID) throws IOException {
        Path filePath = getFilePath(saveUUID);
        Files.deleteIfExists(filePath);
    }
}
