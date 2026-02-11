package it.unibo.crabinv.view;

import it.unibo.crabinv.SceneManager;
import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.core.i18n.LocalizationController;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.core.i18n.TextKeys;
import it.unibo.crabinv.model.save.PlayerMemorial;
import it.unibo.crabinv.model.save.Save;
import it.unibo.crabinv.model.save.SessionRecord;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Creates the View to show the data of {@link PlayerMemorial}.
 *
 * <p>Initial generation with AI, adapted to work correctly
 */
public class MemorialScreen {

    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Save save;
    private final ListView<SessionRecord> listView = new ListView<>();
    private Button btnReturn;
    private int lastSelectedIdx = -1;

    /**
     * Constructor for the {@link MemorialScreen}.
     *
     * @param sceneManager the {@link SceneManager} used by the {@link MemorialScreen}
     * @param loc          the {@link LocalizationController} used by the {@link MemorialScreen}
     * @param audio        the {@link AudioController} used by the {@link MemorialScreen}
     * @param save         the {@link Save} used by the {@link MemorialScreen}
     */
    public MemorialScreen(final SceneManager sceneManager,
                          final LocalizationController loc,
                          final AudioController audio,
                          final Save save) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.save = save;
    }

    /**
     * Getter for the view of the {@link MemorialScreen}.
     *
     * @return the {@link Pane} of the {@link MemorialScreen}
     */
    public final Pane getView() {
        final List<SessionRecord> records = this.save.getPlayerMemorial().getMemorialList();
        listView.setItems(FXCollections.observableArrayList(records));
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(final SessionRecord rec, final boolean empty) {
                super.updateItem(rec, empty);
                if (empty || rec == null) {
                    setText(null);
                } else {
                    final String status = rec.getWonGame() ? "Vinto" : "Perso";
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

        listView.setPrefWidth(ViewParameters.DEFAULT_WIDTH);
        listView.setMaxWidth(Region.USE_PREF_SIZE);
        listView.setPadding(
                new Insets(ViewParameters.DEFAULT_INSETS_DESCRIPTION,
                        0,
                        ViewParameters.DEFAULT_INSETS_DESCRIPTION,
                        0));
        btnReturn = new Button(loc.getString(TextKeys.RETURN));
        btnReturn.getStyleClass().add("app-button");
        btnReturn.setOnAction(_ -> {
            sceneManager.showMainMenu();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });

        listView.setOnKeyPressed(e -> {
            final int size = listView.getItems().size();
            final int idx = listView.getSelectionModel().getSelectedIndex();

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
                default -> {
                }
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
        final VBox listContainer = new VBox(listView);
        listContainer.setAlignment(Pos.CENTER);
        listContainer.setPrefSize(ViewParameters.DEFAULT_WIDTH, ViewParameters.DEFAULT_WIDTH);
        VBox.setVgrow(listView, Priority.NEVER);
        final StackPane root = new StackPane();
        final Label title = new Label(loc.getString(TextKeys.RUN_LOG));
        title.getStyleClass().add("title");
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(ViewParameters.DEFAULT_INSETS_PANE, 0, 0, 0));
        final VBox mainColumn = new VBox(ViewParameters.DEFAULT_HEADER_SPACING);
        mainColumn.setAlignment(Pos.CENTER);
        mainColumn.getChildren().add(listContainer);
        StackPane.setAlignment(mainColumn, Pos.CENTER);
        StackPane.setAlignment(btnReturn, Pos.BOTTOM_CENTER);
        StackPane.setMargin(btnReturn, new Insets(0, 0, ViewParameters.DEFAULT_INSETS, 0));
        root.getChildren().addAll(title, mainColumn, btnReturn);
        listView.focusedProperty().addListener((obs, oldV, newV) -> {
            if (newV) {
                audio.playSFX(SFXTracks.MENU_HOVER);
            }
        });
        btnReturn.focusedProperty().addListener((obs, oldV, newV) -> {
            if (newV) {
                audio.playSFX(SFXTracks.MENU_HOVER);
            }
        });
        return root;
    }
}
