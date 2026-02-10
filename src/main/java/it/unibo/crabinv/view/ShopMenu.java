package it.unibo.crabinv.view;

import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.core.i18n.LocalizationController;
import it.unibo.crabinv.controller.save.SaveControllerImpl;
import it.unibo.crabinv.model.powerUpsShop.*;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.core.i18n.TextKeys;
import it.unibo.crabinv.model.save.Save;
import it.unibo.crabinv.model.save.UserProfile;
import it.unibo.crabinv.SceneManager;

import it.unibo.crabinv.persistence.repository.SaveRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;

public class ShopMenu {

    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Save save;
    private final SaveRepository repo;
    private final UserProfile profile;
    private final Shop shop = new ShopLogic();
    private final List<PowerUp> powerUps;
    private Label currencyLabel;

    public ShopMenu(SceneManager sceneManager,
                    LocalizationController loc,
                    AudioController audio,
                    Save save,
                    SaveRepository repo,
                    List<PowerUp> powerUps) {

        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.save = save;
        this.repo = repo;
        this.profile = save.getUserProfile();
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
        descriptionBox.setMinHeight(220);
        descriptionBox.getStyleClass().add("shop-description-box");

        currencyLabel = new Label();
        updateCurrency();
        FlowPane powerUpsBox = new FlowPane();
        powerUpsBox.setHgap(20);
        powerUpsBox.setVgap(20);
        powerUpsBox.setAlignment(Pos.CENTER);
        powerUpsBox.setPrefWrapLength(600);
        VBox headerBox = new VBox(25);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(title, descriptionBox, currencyLabel);
        mainColumn.getChildren().add(headerBox);
        mainColumn.getChildren().add(powerUpsBox);
        for (PowerUp p : powerUps) {
            powerUpsBox.getChildren().add(
                    createPowerUpCard(p, descriptionLabel)
            );
        }

        Button backButton = createMenuButton(
                TextKeys.RETURN,
                sceneManager::showMainMenu
        );

        mainColumn.getChildren().add(backButton);

        root.getChildren().add(mainColumn);
        return root;
    }


    private VBox createPowerUpCard(PowerUp powerUp , Label descriptionLabel) {
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
        buyButton.getStyleClass().add("app-button-small");

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
                try {
                    new SaveControllerImpl(repo).updateSave(save);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
        int lvl = profile.getPowerUpLevel(p.getPowerUpType());
        label.setText("Lv " + lvl + " / " + p.getMaxLevel());
    }
}
