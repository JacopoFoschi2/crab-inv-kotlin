package it.unibo.crabinv.persistence.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import it.unibo.crabinv.model.save.*;
import it.unibo.crabinv.persistence.repository.SaveRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Gson based Implementation of SaveRepository for json file persistence
 */
public class SaveRepositoryGson implements SaveRepository {

    private final Gson gson;
    private final Path saveDirectory;
    private final SaveFactory saveFactory;


    /**
     * LITE Constructor to be used by Main, requires only saveDirectory, uses default SaveFactory
     * @param saveDirectory the directory where the json files will be stored
     * @throws IOException if an I/O error occurs
     */
    public SaveRepositoryGson(Path saveDirectory) throws IOException{
        this(saveDirectory, new SaveFactoryImpl());
    }

    /**
     * FULL Constructor, needed to initialize the Gson.builder and SaveFactory
     *
     * @param saveDirectory the directory where the json files will be stored
     * @throws IOException if an I/O error occurs
     */
    public SaveRepositoryGson(Path saveDirectory, SaveFactory saveFactory) throws IOException {
        this.saveDirectory = (Files.exists(saveDirectory) ? saveDirectory : Files.createDirectories(saveDirectory));
        this.saveFactory = Objects.requireNonNull(saveFactory);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(Save.class, (JsonDeserializer<Save>)
                (json, type, context) ->
                context.deserialize(json, SaveImpl.class));
        builder.registerTypeAdapter(GameSession.class, (JsonDeserializer<GameSession>)
                (json, type, context) ->
                context.deserialize(json, GameSessionImpl.class));
        builder.registerTypeAdapter(UserProfile.class, (JsonDeserializer<UserProfile>)
                (json, type, context) ->
                context.deserialize(json, UserProfileImpl.class));
        builder.registerTypeAdapter(PlayerMemorial.class, (JsonDeserializer<PlayerMemorial>)
                (json, type, context) ->
                context.deserialize(json, PlayerMemorialImpl.class));
        builder.registerTypeAdapter(SessionRecord.class, (JsonDeserializer<SessionRecord>)
                (json, type, context) ->
                context.deserialize(json, SessionRecordImpl.class));
        this.gson = builder.create();
    }

    /**
     * Helper, returns a validated Save object from a file using GSON
     *
     * @param path the file path
     * @return the Save object, or IOException if an I/O error occurs
     */
    private Save pathToSaveHandler(Path path) {
        try (java.io.BufferedReader reader = Files.newBufferedReader(path)) {
            Save rawSaveFile = gson.fromJson(reader, Save.class);
            Objects.requireNonNull(rawSaveFile);
            return saveFactory.restoreSave(
                    rawSaveFile.getSaveId(),
                    rawSaveFile.getCreationTimeStamp(),
                    rawSaveFile.getUserProfile(),
                    rawSaveFile.getPlayerMemorial(),
                    rawSaveFile.getGameSession());
        } catch (IOException error) {
            System.err.println("Cannot load files: " + path);
            return null;
        } catch (RuntimeException error) {
            System.err.println("Invalid or empty file: " + path + ": " + error.getMessage());
            return null;
        }
    }

    /**
     * Helper class, returns the Path to the file containing the UUID selected
     *
     * @param id the UUID selected
     * @return the Path to the corresponding json file
     */
    private Path getFilePath(UUID id) {
        return this.saveDirectory.resolve(id.toString() + ".json");
    }

    @Override
    public Path getSaveDirectory() {
        return this.saveDirectory;
    }

    @Override
    public SaveFactory getSaveFactory() {
        return this.saveFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Save newSave() {
        return saveFactory.createNewSave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveSaveFile(Save save) throws IOException {
        Path filePath = getFilePath(save.getSaveId());
        try (java.io.Writer writer = Files.newBufferedWriter(filePath)) {
            this.gson.toJson(save, writer);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Save> list() throws IOException {
        try (Stream<Path> s = Files.list(saveDirectory)) {
            return s.filter(saveFile -> saveFile.toString().endsWith(".json"))
                    .map(this::pathToSaveHandler)
                    .filter(java.util.Objects::nonNull)
                    .toList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Save loadSaveFile(UUID saveUUID) throws IOException {
        Path filePath = getFilePath(saveUUID);
        return java.util.Optional.ofNullable(pathToSaveHandler(filePath))
                .orElseThrow(() -> new IOException("Cannot find save: " + saveUUID));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(UUID saveUUID) throws IOException {
        Path filePath = getFilePath(saveUUID);
        Files.deleteIfExists(filePath);
    }
}
