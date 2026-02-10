package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.core.i18n.LocalizationController;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.core.i18n.SupportedLocales;
import it.unibo.crabinv.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

import static it.unibo.crabinv.View.ViewParameters.DEFAULT_SPACING;

/**
 * Provides the languageSelection GUI.
 */
public class LanguageSelection {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;

    /**
     * Ensures the GUI uses the global configuration.
     *
     * @param sceneManager the sceneManager used to change scenes
     * @param loc the localizationController used to fetch strings
     * @param audio the audioController used to play sounds
     */
    public LanguageSelection(final SceneManager sceneManager, final LocalizationController loc, final AudioController audio) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
    }

    /**
     * @return the view ready to be set inside a stage
     */
    public Pane getView() {
        final StackPane pane = new StackPane();
        final VBox mainColumn = new VBox(DEFAULT_SPACING);
        final Label title = new Label("SELECT LANGUAGE");
        title.getStyleClass().add("title");
        final HBox languageSelection = new HBox(DEFAULT_SPACING);
        final double widthOfButton = sceneManager.getWidth() / (SupportedLocales.values().length + 1);
        for (SupportedLocales supportedLocale : SupportedLocales.values()) {
            languageSelection.getChildren().add(generateLanguageButton(widthOfButton, supportedLocale));
        }
        languageSelection.setAlignment(Pos.CENTER);
        mainColumn.getChildren().addAll(title, languageSelection);
        mainColumn.setAlignment(Pos.TOP_CENTER);
        pane.getChildren().add(mainColumn);
        return pane;
    }

    /**
     * @param width the width of the flag
     * @param locale the locale the button should represent
     * @return a button that shows the flag it represents and it's localised name
     */
    private Button generateLanguageButton(final double width, final SupportedLocales locale) {
        var path = Objects.requireNonNull(getClass().getResourceAsStream(locale.getImagePath()));
        final Image flag = new Image(path);
        final ImageView flagImg = new ImageView(flag);
        flagImg.setFitWidth(width);
        flagImg.setFitHeight((width / 3) * 2);
        final Label language = new Label(locale.getLocalizedName());
        language.getStyleClass().add("label");
        final VBox composition = new VBox(flagImg, language);
        composition.setAlignment(Pos.CENTER);
        final Button languageButton = new Button();
        languageButton.setGraphic(composition);
        languageButton.focusedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                audio.playSFX(SFXTracks.MENU_HOVER);
            }
        });
        languageButton.setOnAction(_ -> {
            audio.playSFX(SFXTracks.MENU_SELECT);
            loc.setLanguage(locale);
            sceneManager.showMainMenu();
        });
        languageButton.getStyleClass().add("language-button");
        return languageButton;
    }
}
