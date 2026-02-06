package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.core.i18n.LocalizationController;
import it.unibo.crabinv.Model.powerUpsShop.*;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.core.i18n.TextKeys;
import it.unibo.crabinv.Model.save.UserProfile;
import it.unibo.crabinv.SceneManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class ShopMenu {

    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final UserProfile profile;
    private final Shop shop = new ShopLogic();
    private final List<PowerUp> powerUps;
    private Label currencyLabel;

    public ShopMenu(SceneManager sceneManager,
                    LocalizationController loc,
                    AudioController audio,
                    UserProfile profile,
                    List<PowerUp> powerUps) {

        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.profile = profile;
        this.powerUps = powerUps;
    }

    public Pane getView() {
        StackPane root = new StackPane();
        VBox mainColumn = new VBox(20);
        mainColumn.setAlignment(Pos.CENTER);
        mainColumn.setPadding(new Insets(20));

        Label title = new Label(loc.getString(TextKeys.SHOP));
        title.getStyleClass().add("menu-title");

        Label descriptionLabel = new Label();
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("shop-description");

        VBox descriptionBox = new VBox(descriptionLabel);
        descriptionBox.setPadding(new Insets(15));
        descriptionBox.setMaxWidth(400);
        descriptionBox.getStyleClass().add("shop-description-box");


        currencyLabel = new Label();
        updateCurrency();

        VBox powerUpsBox = new VBox(20);
        powerUpsBox.setAlignment(Pos.CENTER);
        VBox headerBox = new VBox(25);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(title, descriptionBox, currencyLabel);
        mainColumn.getChildren().add(headerBox);
        mainColumn.getChildren().add(powerUpsBox);

        for (PowerUp p : powerUps) {
            mainColumn.getChildren().add(createPowerUpRow(p, descriptionLabel));
        }

        Button backButton = createMenuButton(
                TextKeys.RETURN,
                sceneManager::showMainMenu
        );

        mainColumn.getChildren().add(backButton);
        root.getChildren().add(mainColumn);
        return root;
    }

    private VBox createPowerUpRow(PowerUp powerUp , Label descriptionLabel) {
        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("powerup-card");
        card.setPadding(new Insets(15));


        PowerUpType type = powerUp.getPowerUpType();

        Label name = new Label(
                loc.getString(TextKeys.valueOf(type.name()))
        );
        name.getStyleClass().add("powerup-title");

        Label level = new Label();
        Label cost = new Label(
                loc.getString(TextKeys.COST) + ": " + powerUp.getCost()
        );
        cost.getStyleClass().add("powerup-cost");

        updateLevelLabel(level, powerUp);

        Button buyButton = new Button(loc.getString(TextKeys.BUY));
        buyButton.getStyleClass().add("app-button");

        buyButton.focusedProperty().addListener((_, _, focused) -> {
            if (focused) {
                audio.playSFX(SFXTracks.MENU_HOVER);
                descriptionLabel.setText(
                        loc.getString(type.getDescription())
                );
            }
        });

        buyButton.setOnAction(_ -> {
            audio.playSFX(SFXTracks.MENU_SELECT);
            if (shop.purchase(profile, powerUp)) {
                updateCurrency();
                updateLevelLabel(level, powerUp);
            }
        });

        card.getChildren().addAll(name, level, cost, buyButton);
        return card;
    }


    private Button createMenuButton(TextKeys key, Runnable action) {
        Button button = new Button(loc.getString(key));
        button.getStyleClass().add("app-button");

        button.focusedProperty().addListener((_, _, focused) -> {
            if (focused) {
                audio.playSFX(SFXTracks.MENU_HOVER);
            }
        });

        button.setOnAction(_ -> {
            audio.playSFX(SFXTracks.MENU_SELECT);
            action.run();
        });

        return button;
    }

    private void updateCurrency() {
        currencyLabel.setText(
                loc.getString(TextKeys.CURRENCY) + ": " + profile.getCurrency()
        );
    }

    private void updateLevelLabel(Label label, PowerUp p) {
        int lvl = profile.getPowerUpLevel(p.getPowerUpName());
        label.setText("Lv " + lvl + " / " + p.getMaxLevel());
    }
}
