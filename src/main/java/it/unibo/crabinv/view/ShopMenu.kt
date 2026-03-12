package it.unibo.crabinv.view

import it.unibo.crabinv.SceneManager
import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.i18n.LocalizationController
import it.unibo.crabinv.controller.core.save.SaveControllerImpl
import it.unibo.crabinv.core.persistence.repository.SaveRepository
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.i18n.TextKeys
import it.unibo.crabinv.model.core.save.Save
import it.unibo.crabinv.model.core.save.UserProfile
import it.unibo.crabinv.model.powerups.PowerUp
import it.unibo.crabinv.model.powerups.Shop
import it.unibo.crabinv.model.powerups.ShopLogic
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import java.io.IOException

/**
 * It's the view of the Shop Menu that makes the player purchase the power up.
 * Part of this was adapted from LLM by Mosè Barbieri
 * @param sceneManager the scene manager needed to go to specific screens
 * @param loc the local variable needed for the localization
 * @param audio the audio needed for the sounds of the buttons
 * @param save the save needed to save all the purchases
 * @param repo the repository of the save
 * @param powerUps the powerUps to see which ones are available
 */
class ShopMenu(
    private val sceneManager: SceneManager,
    private val loc: LocalizationController,
    private val audio: AudioController,
    private val save: Save,
    private val repo: SaveRepository?,
    private val powerUps: List<PowerUp>,
) {
    private val profile: UserProfile = save.getUserProfile()
    private val shop: Shop = ShopLogic()
    private val currencyLabel = Label()

    val view: Pane
        /**
         * It's the getter of the view of the shop.
         * @return the view of the shop
         */
        get() {
            val root = StackPane()
            val mainColumn =
                VBox(ViewParameters.DEFAULT_SPACING.toDouble())
            mainColumn.alignment = Pos.CENTER
            mainColumn.padding = Insets(ViewParameters.DEFAULT_INSETS_PANE.toDouble())

            val title = Label(loc.getString(TextKeys.SHOP))
            title.styleClass.add("menu-title")

            val descriptionLabel = Label()
            descriptionLabel.isWrapText = true
            descriptionLabel.styleClass.add("shop-description")

            val descriptionBox = VBox(descriptionLabel)
            descriptionBox.padding = Insets(ViewParameters.DEFAULT_INSETS_DESCRIPTION.toDouble())
            descriptionBox.alignment = Pos.CENTER
            descriptionBox.styleClass.add("shop-description-box")

            updateCurrency()
            val powerUpsBox = FlowPane()
            powerUpsBox.hgap = ViewParameters.DEFAULT_SPACING.toDouble()
            powerUpsBox.vgap = ViewParameters.DEFAULT_SPACING.toDouble()
            powerUpsBox.alignment = Pos.CENTER
            powerUpsBox.prefWrapLength = ViewParameters.DEFAULT_WIDTH.toDouble()

            val headerBox =
                VBox(ViewParameters.DEFAULT_HEADER_SPACING.toDouble())
            headerBox.alignment = Pos.CENTER
            headerBox.children.addAll(title, descriptionBox, currencyLabel)
            mainColumn.children.add(headerBox)
            mainColumn.children.add(powerUpsBox)
            for (p in powerUps) {
                powerUpsBox.children.add(
                    createPowerUpCard(p, descriptionLabel),
                )
            }

            val backButton =
                createMenuButton(
                    TextKeys.RETURN,
                ) { sceneManager.showMainMenu() }

            mainColumn.children.add(backButton)

            root.children.add(mainColumn)
            return root
        }

    private fun createPowerUpCard(
        powerUp: PowerUp,
        descriptionLabel: Label,
    ): VBox {
        val card = VBox(ViewParameters.DEFAULT_CARD_SPACING.toDouble())
        card.alignment = Pos.CENTER
        card.styleClass.add("powerup-card")
        card.padding = Insets(ViewParameters.DEFAULT_INSETS_POWERUP_CARD.toDouble())

        val type = powerUp.powerUpType

        val name =
            Label(
                loc.getString(TextKeys.valueOf(type.name)),
            )
        name.styleClass.add("powerup-title")

        val level = Label()
        val cost =
            Label(
                loc.getString(TextKeys.COST) + ": " + powerUp.cost,
            )
        cost.styleClass.add("powerup-cost")

        updateLevelLabel(level, powerUp)

        val buyButton = Button(loc.getString(TextKeys.BUY))
        buyButton.styleClass.add("app-button-small")

        buyButton
            .focusedProperty()
            .addListener(
                ChangeListener { _: ObservableValue<out Boolean>, _: Boolean, focused: Boolean ->
                    if (focused) {
                        audio.playSFX(SFXTracks.MENU_HOVER)
                        descriptionLabel.text = loc.getString(type.description)
                    }
                },
            )

        buyButton.onAction =
            EventHandler { `_`: ActionEvent? ->
                audio.playSFX(SFXTracks.MENU_SELECT)
                if (shop.purchase(profile, powerUp)) {
                    updateCurrency()
                    updateLevelLabel(level, powerUp)
                    try {
                        SaveControllerImpl(repo).updateSave(save)
                    } catch (e: IOException) {
                        throw IllegalCallerException(e)
                    }
                }
            }

        card.children.addAll(name, level, cost, buyButton)
        return card
    }

    private fun createMenuButton(
        key: TextKeys,
        action: Runnable,
    ): Button {
        val button = Button(loc.getString(key))
        button.styleClass.add("app-button")

        button
            .focusedProperty()
            .addListener(
                ChangeListener { _: ObservableValue<out Boolean>, _: Boolean, focused: Boolean ->
                    if (focused) {
                        audio.playSFX(SFXTracks.MENU_HOVER)
                    }
                },
            )

        button.onAction =
            EventHandler { `_`: ActionEvent? ->
                audio.playSFX(SFXTracks.MENU_SELECT)
                action.run()
            }

        return button
    }

    private fun updateCurrency() {
        currencyLabel.text = loc.getString(TextKeys.CURRENCY) + ": " + profile.getCurrency()
    }

    private fun updateLevelLabel(
        label: Label,
        p: PowerUp,
    ) {
        val lvl = profile.getPowerUpLevel(p.powerUpType)
        label.text = "Lv " + lvl + " / " + p.maxLevel
    }
}
