package com.codingsimply.apps.kjs.controller

import com.codingsimply.apps.kjs.model.Progress
import com.codingsimply.apps.kjs.model.Setting
import com.codingsimply.apps.kjs.service.GeneratorService
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.*
import javafx.stage.DirectoryChooser

import java.net.URL
import java.util.ResourceBundle
import javafx.stage.FileChooser
import java.io.File

class RootController : Initializable, ProgressInterface {

    @FXML
    var templateFileTextField: TextField? = null

    @FXML
    var schemaFolderTextField: TextField? = null

    @FXML
    var outputFolderTextField: TextField? = null

    @FXML
    var fileTypeTextField: TextField? = null

    @FXML
    var generatorProgressBar: ProgressBar? = null

    @FXML
    var startToggleButton: ToggleButton? = null

    @FXML
    var generateSecondaryClassesCheckbox: CheckBox? = null

    @FXML
    var menuBar: MenuBar? = null

    @FXML
    var statusLabel: Label? = null

    private var setting: Setting? = null

    private val generatorService = GeneratorService(this)

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        val os = System.getProperty("os.name")
        if (os != null && os.startsWith("Mac")) {
            menuBar?.useSystemMenuBarProperty()?.set(true)
        }
    }

    fun start(actionEvent: ActionEvent) {
        generatorService.setting = Setting(
                templateFileTextField?.text ?: "",
                schemaFolderTextField?.text ?: "",
                outputFolderTextField?.text ?: "",
                fileTypeTextField?.text ?: "",
                generateSecondaryClassesCheckbox?.isSelected ?: true,
                false)
        val toggleButton = actionEvent.source as ToggleButton
        if (generatorService.state == Worker.State.SUCCEEDED) {
            generatorService.cancel()
            generatorService.reset()
        }
        if (toggleButton.isSelected) {
            toggleButton.text = "Cancel"
            generatorService.start()
        } else {
            toggleButton.text = "Start"
        }
    }

    fun openTemplateFileDialog(actionEvent: ActionEvent) {
        val fileChooser = FileChooser()
        fileChooser.title = "Select Template File"
        val fileLocation = File(templateFileTextField?.text)
        fileChooser.initialFileName = fileLocation.name
        fileChooser.initialDirectory = fileLocation.parentFile
        val file = fileChooser.showOpenDialog((actionEvent.source as Node).scene.window)
        if (file != null) {
            templateFileTextField?.text = file.absolutePath
        }
    }

    fun openSchemaFolderDialog(actionEvent: ActionEvent) {
        val directoryChooser = DirectoryChooser()
        directoryChooser.title = "Select Schema Folder"
        val fileLocation = File(schemaFolderTextField?.text)
        directoryChooser.initialDirectory = fileLocation.parentFile
        val file = directoryChooser.showDialog((actionEvent.source as Node).scene.window)
        if (file != null) {
            schemaFolderTextField?.text = file.absolutePath
        }
    }

    fun openOutputFolderDialog(actionEvent: ActionEvent) {
        val directoryChooser = DirectoryChooser()
        directoryChooser.title = "Select Output Folder"
        val fileLocation = File(outputFolderTextField?.text)
        directoryChooser.initialDirectory = fileLocation.parentFile
        val file = directoryChooser.showDialog((actionEvent.source as Node).scene.window)
        if (file != null) {
            outputFolderTextField?.text = file.absolutePath
        }
    }

    fun loadSetting(setting: Setting) {
        this.setting = setting
        setTooltipValue(templateFileTextField, setting.templateFile)
        setTooltipValue(schemaFolderTextField, setting.schemaFolder)
        setTooltipValue(outputFolderTextField, setting.outputFolder)
        setTooltipValue(fileTypeTextField, setting.outputType)
        update(Progress("Not doing anything", 0, 1))
        generateSecondaryClassesCheckbox?.isSelected = setting.generateSecondaryClasses
    }

    private fun setTooltipValue(control: TextInputControl?, text: String) {
        control?.text = text
        control?.tooltip?.text = text
    }

    override fun update(progress: Progress) {
        Platform.runLater {
            val percent = (progress.position.toDouble() / progress.total.toDouble())
            generatorProgressBar?.progress = percent
            statusLabel?.text = progress.description
            statusLabel?.tooltip?.text = progress.description
        }
    }

    override fun error(e: Exception) {
        Platform.runLater {
            statusLabel?.styleClass?.add("error")
            statusLabel?.text = e.localizedMessage
            statusLabel?.tooltip?.text = e.localizedMessage
            startToggleButton?.text = "Start"
            startToggleButton?.isSelected = false
        }
    }

    override fun finished(progress: Progress) {
        Platform.runLater {
            startToggleButton?.text = "Start"
            startToggleButton?.isSelected = false
            statusLabel?.text = "All done"
            statusLabel?.tooltip?.text = "All done"
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun close(actionEvent: ActionEvent) {
        System.exit(0)
    }
}
