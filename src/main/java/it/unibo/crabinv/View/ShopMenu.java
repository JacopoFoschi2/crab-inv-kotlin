package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.PowerUpsShop.*;
import it.unibo.crabinv.Model.audio.SFXTracks;
import it.unibo.crabinv.Model.i18n.TextKeys;
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

        currencyLabel = new Label();
        updateCurrency();

        mainColumn.getChildren().addAll(title, currencyLabel);

        for (PowerUp p : powerUps) {
            mainColumn.getChildren().add(createPowerUpRow(p));
        }

        Button backButton = createMenuButton(
                TextKeys.RETURN,
                sceneManager::showMainMenu
        );

        mainColumn.getChildren().add(backButton);
        root.getChildren().add(mainColumn);
        return root;
    }

    private HBox createPowerUpRow(PowerUp powerUp) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER);

        Label name = new Label(powerUp.getPowerUpName());
        Label level = new Label();
        Label cost = new Label(
                loc.getString(TextKeys.COST) + ": " + powerUp.getCost()
        );

        updateLevelLabel(level, powerUp);

        Button buyButton = new Button(loc.getString(TextKeys.BUY));
        buyButton.getStyleClass().add("app-button");

        buyButton.focusedProperty().addListener((_, _, focused) -> {
            if (focused) {
                audio.playSFX(SFXTracks.MENU_HOVER);
            }
        });

        buyButton.setOnAction(_ -> {
            audio.playSFX(SFXTracks.MENU_SELECT);
            if (shop.purchase(profile, powerUp)) {
                updateCurrency();
                updateLevelLabel(level, powerUp);
            }
        });

        row.getChildren().addAll(name, level, cost, buyButton);
        return row;
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
