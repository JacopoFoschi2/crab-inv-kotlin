package it.unibo.crabinv.model.save;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.UUID;

public class SaveFactoryImpl implements SaveFactory {

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

    @Override
    public Save restoreSave(UUID saveId,
                            long creationTimeStamp,
                            UserProfile userProfile,
                            PlayerMemorial playerMemorial,
                            GameSession gameSession) {
        return new SaveImpl(
                Objects.requireNonNull(saveId, "saveId cannot be null"),
                validateCreationTimeStamp(creationTimeStamp),
                Objects.requireNonNull(userProfile, "UserProfile cannot be null"),
                Objects.requireNonNull(playerMemorial, "PlayerMemorial cannot be null"),
                gameSession);  //GameSession CAN be null
    }

    private long validateCreationTimeStamp(long creationTimeStamp) {
        if (creationTimeStamp <= 0) {
            throw new IllegalArgumentException("creationTimeStamp invalid, must be > 0: " + creationTimeStamp);
        }
        return creationTimeStamp;
    }
}
