package it.unibo.crabinv.Model.save;

import java.util.UUID;

public interface SaveFactory {

    /**
     * Creates a new save with identifiers and new components (GameSession will be null by default)
     * @return the newly created save
     */
    Save createNewSave();


    /**
     * Creates a save based on existing data (to be used for loading saves)
     * @param saveId the UUID of the save
     * @param creationTimeStamp the creationTimeStamp of the save
     * @param userProfile the UserProfile of the save
     * @param playerMemorial the PlayerMemorial of the save
     * @param gameSession the GameSession of the save (can be null)
     * @return the loaded save
     */
    Save restoreSave(UUID saveId,
                     long creationTimeStamp,
                     UserProfile userProfile,
                     PlayerMemorial playerMemorial,
                     GameSession gameSession);


}
