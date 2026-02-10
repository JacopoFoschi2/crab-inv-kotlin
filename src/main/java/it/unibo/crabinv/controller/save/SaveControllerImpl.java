package it.unibo.crabinv.controller.save;

import it.unibo.crabinv.model.save.Save;
import it.unibo.crabinv.persistence.repository.SaveRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SaveControllerImpl implements SaveController {

    private final SaveRepository saveRepository;

    public SaveControllerImpl(SaveRepository saveRepository) {
        this.saveRepository = Objects.requireNonNull(saveRepository);
    }

    @Override
    public SaveRepository getSaveRepository() {
        return this.saveRepository;
    }

    @Override
    public Save saveControlAndLoad() throws IOException {
        List<Save> saveList = this.saveRepository.list();
        if (saveList.isEmpty()) {
            return createSave();
        } else if (saveList.size() == 1) {
            return saveList.getFirst();
        } else {
            return selectSave(saveList);
        }
    }

    @Override
    public Save createSave() throws IOException {
        Save newSaveFile = this.saveRepository.newSave();
        this.saveRepository.saveSaveFile(newSaveFile);
        return newSaveFile;

    }

    @Override
    public Save selectSave(List<Save> saveList) {
        return saveList.getLast();
        // Placeholder for user-directed save selection if implemented
    }

    @Override
    public void updateSave(Save save) throws IOException {
        this.saveRepository.saveSaveFile(save);
    }

    @Override
    public Save loadSave(UUID saveId) throws IOException {
        return this.saveRepository.loadSaveFile(saveId);
    }

    @Override
    public void deleteSave(UUID saveId) throws IOException {
        this.saveRepository.delete(saveId);
    }
}
