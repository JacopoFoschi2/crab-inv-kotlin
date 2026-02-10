package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.core.i18n.LocalizationController;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.core.i18n.TextKeys;
import it.unibo.crabinv.Model.save.Save;
import it.unibo.crabinv.Model.save.SessionRecord;
import it.unibo.crabinv.SceneManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.util.List;

public class MemorialScreen {

    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Save save;
    private final ListView<SessionRecord> listView = new ListView<>();
    private Button btnReturn;
    private int lastSelectedIdx = -1;

    public MemorialScreen(SceneManager sceneManager,
                          LocalizationController loc,
                          AudioController audio,
                          Save save) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.save = save;
    }

    public Pane getView() {
        List<SessionRecord> records = this.save.getPlayerMemorial().getMemorialList();
        listView.setItems(FXCollections.observableArrayList(records));
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(SessionRecord rec, boolean empty) {
                super.updateItem(rec, empty);
                if (empty || rec == null) {
                    setText(null);
                } else {
                    String status = rec.getWonGame() ? "Vinto" : "Perso";
                    setText(String.format(
                            "%tF %tT – Livello %d – Monete %d – %s",
                            rec.getStartingTimeStamp(),
                            rec.getStartingTimeStamp(),
                            rec.getLastLevel(),
                            rec.getLastCurrency(),
                            status));
                }
            }
        });

        listView.getSelectionModel().clearSelection();
        listView.setFocusTraversable(true);
        listView.setMouseTransparent(true);

        listView.setPrefWidth(500);
        listView.setMaxWidth(Region.USE_PREF_SIZE);
        listView.setPadding(new Insets(10, 0, 10, 0));

        btnReturn = new Button(loc.getString(TextKeys.RETURN));
        btnReturn.getStyleClass().add("app-button");
        btnReturn.setOnAction(_ -> {
            sceneManager.showMainMenu();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });

        listView.setOnKeyPressed(e -> {
            int size = listView.getItems().size();
            int idx = listView.getSelectionModel().getSelectedIndex();

            if (e.getCode() == KeyCode.ESCAPE) {
                btnReturn.requestFocus();
                e.consume();
                return;
            }

            if (idx == -1 && (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN)) {
                listView.getSelectionModel().selectFirst();
                e.consume();
                return;
            }

            switch (e.getCode()) {
                case UP -> {
                    if (idx > 0) {
                        listView.getSelectionModel().selectPrevious();
                    }
                    e.consume();
                }
                case DOWN -> {
                    if (idx < size - 1) {
                        listView.getSelectionModel().selectNext();
                    }
                    e.consume();
                }
                default -> {}
            }
            lastSelectedIdx = listView.getSelectionModel().getSelectedIndex();
        });

        btnReturn.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                e.consume();
                return;
            }

            if (e.getCode() == KeyCode.UP) {
                if (lastSelectedIdx >= 0 && lastSelectedIdx < listView.getItems().size()) {
                    listView.getSelectionModel().select(lastSelectedIdx);
                } else {

                    listView.getSelectionModel().selectFirst();
                }
                listView.requestFocus();
                e.consume();
            }
        });

        VBox listContainer = new VBox(listView);
        listContainer.setAlignment(Pos.CENTER);
        listContainer.setPrefSize(500, 300);
        VBox.setVgrow(listView, Priority.NEVER);

        StackPane root = new StackPane();

        Label title = new Label(loc.getString(TextKeys.RUN_LOG));
        title.getStyleClass().add("title");
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(20, 0, 0, 0));

        VBox mainColumn = new VBox(25);
        mainColumn.setAlignment(Pos.CENTER);
        mainColumn.getChildren().add(listContainer);
        StackPane.setAlignment(mainColumn, Pos.CENTER);

        StackPane.setAlignment(btnReturn, Pos.BOTTOM_CENTER);
        StackPane.setMargin(btnReturn, new Insets(0, 0, 60, 0));

        root.getChildren().addAll(title, mainColumn, btnReturn);

        listView.focusedProperty().addListener((obs, oldV, newV) -> {
            if (newV) audio.playSFX(SFXTracks.MENU_HOVER);
        });
        btnReturn.focusedProperty().addListener((obs, oldV, newV) -> {
            if (newV) audio.playSFX(SFXTracks.MENU_HOVER);
        });

        return root;

    }
}
