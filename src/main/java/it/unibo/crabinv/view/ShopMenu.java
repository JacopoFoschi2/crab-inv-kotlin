package it.unibo.crabinv.view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.SceneManager;
import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.core.i18n.LocalizationController;
import it.unibo.crabinv.controller.save.SaveControllerImpl;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.core.i18n.TextKeys;
import it.unibo.crabinv.model.powerups.PowerUp;
import it.unibo.crabinv.model.powerups.PowerUpType;
import it.unibo.crabinv.model.powerups.Shop;
import it.unibo.crabinv.model.powerups.ShopLogic;
import it.unibo.crabinv.model.save.Save;
import it.unibo.crabinv.model.save.UserProfile;
import it.unibo.crabinv.persistence.repository.SaveRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

/**
 * It's the view of the Shop Menu that makes the player purchase the power up.
 * Part of this was adapted from LLM by Mosè Barbieri
 */
public final class ShopMenu {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Save save;
    private final SaveRepository repo;
    private final UserProfile profile;
    private final Shop shop = new ShopLogic();
    private final List<PowerUp> powerUps;
    private final Label currencyLabel = new Label();

    /**
     * It's the constructor of the ShopMenu.
     *
     * @param sceneManager the scene manager needed to go to specific screens
     * @param loc the local variable needed for the localization
     * @param audio the audio needed for the sounds of the buttons
     * @param save the save needed to save all the purchases
     * @param repo the repository of the save
     * @param powerUps the powerUps to see which ones are available
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") //dependencies are injected and owned by caller
    public ShopMenu(final SceneManager sceneManager,
                    final LocalizationController loc,
                    final AudioController audio,
                    final Save save,
                    final SaveRepository repo,
                    final List<PowerUp> powerUps) {

        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.save = save;
        this.repo = repo;
        this.profile = save.getUserProfile();
        this.powerUps = powerUps;
    }

    /**
     * It's the getter of the view of the shop.
     *
     * @return the view of the shop
     */
    public Pane getView() {
        final StackPane root = new StackPane();
        final VBox mainColumn = new VBox(ViewParameters.DEFAULT_SPACING);
        mainColumn.setAlignment(Pos.CENTER);
        mainColumn.setPadding(new Insets(ViewParameters.DEFAULT_INSETS_PANE));

        final Label title = new Label(loc.getString(TextKeys.SHOP));
        title.getStyleClass().add("menu-title");

        final Label descriptionLabel = new Label();
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("shop-description");

        final VBox descriptionBox = new VBox(descriptionLabel);
        descriptionBox.setPadding(new Insets(ViewParameters.DEFAULT_INSETS_DESCRIPTION));
        descriptionBox.setAlignment(Pos.CENTER);
        descriptionBox.getStyleClass().add("shop-description-box");

        updateCurrency();
        final FlowPane powerUpsBox = new FlowPane();
        powerUpsBox.setHgap(ViewParameters.DEFAULT_SPACING);
        powerUpsBox.setVgap(ViewParameters.DEFAULT_SPACING);
        powerUpsBox.setAlignment(Pos.CENTER);
        powerUpsBox.setPrefWrapLength(ViewParameters.DEFAULT_WIDTH);

        final VBox headerBox = new VBox(ViewParameters.DEFAULT_HEADER_SPACING);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(title, descriptionBox, currencyLabel);
        mainColumn.getChildren().add(headerBox);
        mainColumn.getChildren().add(powerUpsBox);
        for (final PowerUp p : powerUps) {
            powerUpsBox.getChildren().add(
                    createPowerUpCard(p, descriptionLabel)
            );
        }

        final Button backButton = createMenuButton(
                TextKeys.RETURN,
                sceneManager::showMainMenu
        );

        mainColumn.getChildren().add(backButton);

        root.getChildren().add(mainColumn);
        return root;
    }

    private VBox createPowerUpCard(final PowerUp powerUp, final Label descriptionLabel) {
        final VBox card = new VBox(ViewParameters.DEFAULT_CARD_SPACING);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("powerup-card");
        card.setPadding(new Insets(ViewParameters.DEFAULT_INSETS_POWERUP_CARD));

        final PowerUpType type = powerUp.getPowerUpType();

        final Label name = new Label(
                loc.getString(TextKeys.valueOf(type.name()))
        );
        name.getStyleClass().add("powerup-title");

        final Label level = new Label();
        final Label cost = new Label(
                loc.getString(TextKeys.COST) + ": " + powerUp.getCost()
        );
        cost.getStyleClass().add("powerup-cost");

        updateLevelLabel(level, powerUp);

        final Button buyButton = new Button(loc.getString(TextKeys.BUY));
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
                } catch (final IOException e) {
                    throw new IllegalCallerException(e);
                }
            }
        });

        card.getChildren().addAll(name, level, cost, buyButton);
        return card;
    }

    private Button createMenuButton(final TextKeys key, final Runnable action) {
        final Button button = new Button(loc.getString(key));
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

    private void updateLevelLabel(final Label label, final PowerUp p) {
        final int lvl = profile.getPowerUpLevel(p.getPowerUpType());
        label.setText("Lv " + lvl + " / " + p.getMaxLevel());
    }
}
