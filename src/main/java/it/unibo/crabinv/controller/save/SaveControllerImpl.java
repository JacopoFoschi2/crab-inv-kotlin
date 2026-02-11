package it.unibo.crabinv.controller.save;

import it.unibo.crabinv.model.save.Save;
import it.unibo.crabinv.persistence.repository.SaveRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link SaveController}.
 *
 * @param saveRepository the {@link SaveRepository} used by the {@link SaveControllerImpl}
 */
public record SaveControllerImpl(SaveRepository saveRepository) implements SaveController {

    /**
     * Constructor for {@link SaveControllerImpl}.
     *
     * @param saveRepository the {@link SaveRepository} to be used
     */
    public SaveControllerImpl(final SaveRepository saveRepository) {
        this.saveRepository = Objects.requireNonNull(saveRepository);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SaveRepository saveRepository() {
        return this.saveRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Save saveControlAndLoad() throws IOException {
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
    public final Save createSave() throws IOException {
        final Save newSaveFile = this.saveRepository.newSave();
        this.saveRepository.saveSaveFile(newSaveFile);
        return newSaveFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Save selectSave(final List<Save> saveList) {
        return saveList.getLast();
        // Placeholder for user-directed save selection if implemented
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateSave(final Save save) throws IOException {
        this.saveRepository.saveSaveFile(save);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Save loadSave(final UUID saveId) throws IOException {
        return this.saveRepository.loadSaveFile(saveId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void deleteSave(final UUID saveId) throws IOException {
        this.saveRepository.delete(saveId);
    }
}
