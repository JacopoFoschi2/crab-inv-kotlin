package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.i18n.SupportedLocales;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Objects;

public class LanguageSelection {
    public Pane getView(LocalizationController loc) {
        StackPane pane = new StackPane();
        VBox mainColumn = new VBox(20);
        Label title = new Label("SELECT LANGUAGE");
        title.getStyleClass().add("title");
        HBox languageSelection = new HBox(10);
        for (SupportedLocales supportedLocale : SupportedLocales.values()) {
            languageSelection.getChildren().add(generateLanguageButton(200, loc, supportedLocale));
        }
        languageSelection.setAlignment(Pos.CENTER);
        mainColumn.getChildren().addAll(title, languageSelection);
        mainColumn.setAlignment(Pos.TOP_CENTER);
        pane.getChildren().add(mainColumn);
        return pane;
    }

    private Button generateLanguageButton(double width, LocalizationController loc, SupportedLocales locale) {
        var path = Objects.requireNonNull(getClass().getResourceAsStream(locale.getImagePath()));
        Image flag = new Image(path);
        ImageView flagImg = new ImageView(flag);
        flagImg.setFitWidth(width);
        flagImg.setFitHeight((width / 3) * 2);
        Label language = new Label(locale.getLocalizedName());
        VBox composition = new VBox(flagImg, language);
        composition.setAlignment(Pos.CENTER);
        Button languageButton = new Button();
        languageButton.setGraphic(composition);
        languageButton.setOnAction(_ -> loc.setLanguage(locale));
        return languageButton;
    }
}
