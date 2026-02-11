package it.unibo.crabinv.model.save;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link SaveFactory}.
 */
public class SaveFactoryImpl implements SaveFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Save createNewSave() {
        return new SaveImpl(
                UUID.randomUUID(),
                Instant.now().toEpochMilli(),
                new UserProfileImpl(new LinkedHashMap<>()),
                new PlayerMemorialImpl(),
                null
        );
    }

    /**
     * {@inheritDoc}
     *
     * <p> {@code GameSession} parameter CAN be null
     */
    @Override
    public Save restoreSave(final UUID saveId,
                            final long creationTimeStamp,
                            final UserProfile userProfile,
                            final PlayerMemorial playerMemorial,
                            final GameSession gameSession) {
        return new SaveImpl(
                Objects.requireNonNull(saveId, "saveId cannot be null"),
                validateCreationTimeStamp(creationTimeStamp),
                Objects.requireNonNull(userProfile, "UserProfile cannot be null"),
                Objects.requireNonNull(playerMemorial, "PlayerMemorial cannot be null"),
                gameSession);
    }

    /**
     * Validates the {@code creationTimeStamp}.
     *
     * @param creationTimeStamp to validate
     * @return the {@code creationTimeStamp} if it is valid
     * @throws IllegalArgumentException if the {@code creationTimeStamp} is invalid
     */
    private long validateCreationTimeStamp(final long creationTimeStamp) {
        if (creationTimeStamp <= 0) {
            throw new IllegalArgumentException("creationTimeStamp invalid, must be > 0: " + creationTimeStamp);
        }
        return creationTimeStamp;
    }
}