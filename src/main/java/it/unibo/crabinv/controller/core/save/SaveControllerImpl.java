package it.unibo.crabinv.controller.core.save;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.model.core.save.Save;
import it.unibo.crabinv.core.persistence.repository.SaveRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link SaveController}.
 *
 * @param saveRepository the {@link SaveRepository} used by the {@link SaveControllerImpl}
 */
@SuppressFBWarnings("EI_EXPOSE")//exposes internal representation by design
public record SaveControllerImpl(SaveRepository saveRepository) implements SaveController {

    /**
     * Constructor for {@link SaveControllerImpl}.
     *
     * @param saveRepository the {@link SaveRepository} to be used
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") //dependencies are injected and owned by callergit
    public SaveControllerImpl(final SaveRepository saveRepository) {
        this.saveRepository = Objects.requireNonNull(saveRepository);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Save saveControlAndLoad() throws IOException {
        final List<Save> saveList = this.saveRepository.list();
        if (saveList.isEmpty()) {
            return createSave();
        } else if (saveList.size() == 1) {
            return saveList.getFirst();
        } else {
            return selectSave(saveList);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Save createSave() throws IOException {
        final Save newSaveFile = this.saveRepository.newSave();
        this.saveRepository.saveSaveFile(newSaveFile);
        return newSaveFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Save selectSave(final List<Save> saveList) {
        return saveList.getLast();
        // Placeholder for user-directed save selection if implemented
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSave(final Save save) throws IOException {
        this.saveRepository.saveSaveFile(save);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Save loadSave(final UUID saveId) throws IOException {
        return this.saveRepository.loadSaveFile(saveId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSave(final UUID saveId) throws IOException {
        this.saveRepository.delete(saveId);
    }
}
