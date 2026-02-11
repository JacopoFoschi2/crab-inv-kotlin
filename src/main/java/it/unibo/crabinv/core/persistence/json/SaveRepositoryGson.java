package it.unibo.crabinv.core.persistence.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import it.unibo.crabinv.model.core.save.GameSession;
import it.unibo.crabinv.model.core.save.GameSessionImpl;
import it.unibo.crabinv.model.core.save.PlayerMemorial;
import it.unibo.crabinv.model.core.save.PlayerMemorialImpl;
import it.unibo.crabinv.model.core.save.Save;
import it.unibo.crabinv.model.core.save.SaveFactory;
import it.unibo.crabinv.model.core.save.SaveFactoryImpl;
import it.unibo.crabinv.model.core.save.SaveImpl;
import it.unibo.crabinv.model.core.save.SessionRecord;
import it.unibo.crabinv.model.core.save.SessionRecordImpl;
import it.unibo.crabinv.model.core.save.UserProfile;
import it.unibo.crabinv.model.core.save.UserProfileImpl;
import it.unibo.crabinv.core.persistence.repository.SaveRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Gson-based Implementation of SaveRepository for JSON file persistence.
 */
public class SaveRepositoryGson implements SaveRepository {

    private final Gson gson;
    private final Path saveDirectory;
    private final SaveFactory saveFactory;

    /**
     * LITE Constructor to be used by Main, requires only saveDirectory, uses default SaveFactory.
     *
     * @param saveDirectory the directory where the JSON files will be stored
     * @throws IOException if an I/O error occurs
     */
    public SaveRepositoryGson(final Path saveDirectory) throws IOException {
        this(saveDirectory, new SaveFactoryImpl());
    }

    /**
     * FULL Constructor, needed to initialize the Gson.builder and SaveFactory.
     *
     * @param saveDirectory the directory where the JSON files will be stored
     * @param saveFactory   the {@link SaveFactory} used by the {@link SaveRepositoryGson}
     * @throws IOException if an I/O error occurs
     */
    public SaveRepositoryGson(final Path saveDirectory, final SaveFactory saveFactory) throws IOException {
        this.saveDirectory = Files.exists(saveDirectory) ? saveDirectory : Files.createDirectories(saveDirectory);
        this.saveFactory = Objects.requireNonNull(saveFactory);
        final GsonBuilder builder = new GsonBuilder();
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
     * {@inheritDoc}
     */
    @Override
    public final Path getSaveDirectory() {
        return this.saveDirectory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SaveFactory getSaveFactory() {
        return this.saveFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Save newSave() {
        return saveFactory.createNewSave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void saveSaveFile(final Save save) throws IOException {
        final Path filePath = getFilePath(save.getSaveId());
        try (java.io.Writer writer = Files.newBufferedWriter(filePath)) {
            this.gson.toJson(save, writer);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Save> list() throws IOException {
        try (Stream<Path> s = Files.list(saveDirectory)) {
            return s.filter(saveFile -> saveFile.toString().endsWith(".json"))
                    .map(this::pathToSaveHandler)
                    .filter(Objects::nonNull)
                    .toList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Save loadSaveFile(final UUID saveUUID) throws IOException {
        final Path filePath = getFilePath(saveUUID);
        return java.util.Optional.ofNullable(pathToSaveHandler(filePath))
                .orElseThrow(() -> new IOException("Cannot find save: " + saveUUID));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void delete(final UUID saveUUID) throws IOException {
        final Path filePath = getFilePath(saveUUID);
        Files.deleteIfExists(filePath);
    }

    /**
     * Helper method that returns the Path to the file containing the UUID selected.
     *
     * @param id the UUID selected
     * @return the Path to the corresponding JSON file
     */
    private Path getFilePath(final UUID id) {
        return this.saveDirectory.resolve(id.toString() + ".json");
    }

    /**
     * Helper method that returns a validated Save object from a file using GSON.
     *
     * @param path the file path
     * @return the Save object, or IOException if an I/O error occurs
     */
    private Save pathToSaveHandler(final Path path) {
        try (java.io.BufferedReader reader = Files.newBufferedReader(path)) {
            final Save rawSaveFile = gson.fromJson(reader, Save.class);
            Objects.requireNonNull(rawSaveFile);
            return saveFactory.restoreSave(
                    rawSaveFile.getSaveId(),
                    rawSaveFile.getCreationTimeStamp(),
                    rawSaveFile.getUserProfile(),
                    rawSaveFile.getPlayerMemorial(),
                    rawSaveFile.getGameSession());
        } catch (final IOException | JsonParseException error) {
            return null;
        }
    }
}
