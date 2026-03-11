package it.unibo.crabinv.view

import it.unibo.crabinv.SceneManager
import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.i18n.LocalizationController
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.i18n.TextKeys
import it.unibo.crabinv.model.core.save.Save
import it.unibo.crabinv.model.core.save.SessionRecord
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.util.Callback

/**
 * Creates the View to show the data of [it.unibo.crabinv.model.core.save.PlayerMemorial].
 * Initial generation with AI, adapted to work correctly
 * @param sceneManager the [SceneManager] used by the [MemorialScreen]
 * @param loc the [LocalizationController] used by the [MemorialScreen]
 * @param audio the [AudioController] used by the [MemorialScreen]
 * @param save the [Save] used by the [MemorialScreen]
 */
class MemorialScreen(
    private val sceneManager: SceneManager,
    private val loc: LocalizationController,
    private val audio: AudioController,
    private val save: Save,
) {
    private val listView = ListView<SessionRecord?>()

    val view: Pane
        /**
         * Returns the view of the [MemorialScreen].
         * @return the view of the [MemorialScreen]
         */
        get() {
            val root = StackPane()
            val title = Label(loc.getString(TextKeys.RUN_LOG))
            title.styleClass.add("title")

            val returnButton = createReturnButton()
            setupListView()

            val mainColumn =
                VBox(ViewParameters.DEFAULT_HEADER_SPACING.toDouble())
            mainColumn.alignment = Pos.CENTER

            val listContainer = VBox(listView)
            listContainer.alignment = Pos.CENTER
            listContainer.setPrefSize(ViewParameters.DEFAULT_WIDTH.toDouble(), ViewParameters.DEFAULT_WIDTH.toDouble())
            VBox.setVgrow(listView, Priority.NEVER)

            mainColumn.children.add(listContainer)

            StackPane.setAlignment(title, Pos.TOP_CENTER)
            StackPane.setMargin(
                title,
                Insets(ViewParameters.DEFAULT_INSETS_PANE.toDouble(), 0.0, 0.0, 0.0),
            )
            StackPane.setAlignment(mainColumn, Pos.CENTER)
            StackPane.setAlignment(returnButton, Pos.BOTTOM_CENTER)
            StackPane.setMargin(
                returnButton,
                Insets(0.0, 0.0, ViewParameters.DEFAULT_INSETS.toDouble(), 0.0),
            )

            root.children.addAll(title, mainColumn, returnButton)
            return root
        }

    private fun setupListView() {
        val records = save.getPlayerMemorial().getMemorialList()
        listView.items = FXCollections.observableArrayList<SessionRecord?>(records)
        listView.cellFactory =
            Callback { _: ListView<SessionRecord?>? ->
                object : ListCell<SessionRecord?>() {
                    override fun updateItem(
                        rec: SessionRecord?,
                        empty: Boolean,
                    ) {
                        super.updateItem(rec, empty)
                        text =
                            if (empty || rec == null) {
                                null
                            } else {
                                String.format(
                                    (
                                        "%tF %tT – " +
                                            loc.getString(TextKeys.LEVEL) +
                                            " %d – " +
                                            loc.getString(TextKeys.CURRENCY) +
                                            " %d – %s"
                                    ),
                                    rec.getStartingTimeStamp(),
                                    rec.getStartingTimeStamp(),
                                    rec.getLastLevel(),
                                    rec.getLastCurrency(),
                                    if (rec.isGameWon()) loc.getString(TextKeys.WON) else loc.getString(TextKeys.LOST),
                                )
                            }
                    }
                }
            }

        listView.getSelectionModel().clearSelection()
        listView.isFocusTraversable = true
        listView.prefWidth = ViewParameters.DEFAULT_WIDTH.toDouble()
        listView.maxWidth = Region.USE_PREF_SIZE
        listView.padding =
            Insets(
                ViewParameters.DEFAULT_INSETS_DESCRIPTION.toDouble(),
                0.0,
                ViewParameters.DEFAULT_INSETS_DESCRIPTION.toDouble(),
                0.0,
            )

        listView
            .focusedProperty()
            .addListener(
                ChangeListener { _: ObservableValue<out Boolean>, _: Boolean, newV: Boolean ->
                    if (newV) {
                        audio.playSFX(SFXTracks.MENU_HOVER)
                    }
                },
            )
    }

    private fun createReturnButton(): Button {
        val btnReturn = Button(loc.getString(TextKeys.RETURN))
        btnReturn.styleClass.add("app-button")

        btnReturn.onAction =
            EventHandler { `_`: ActionEvent? ->
                sceneManager.showMainMenu()
                audio.playSFX(SFXTracks.MENU_SELECT)
            }

        btnReturn
            .focusedProperty()
            .addListener(
                ChangeListener { _: ObservableValue<out Boolean>, _: Boolean, newV: Boolean ->
                    if (newV) {
                        audio.playSFX(SFXTracks.MENU_HOVER)
                    }
                },
            )
        return btnReturn
    }
}
