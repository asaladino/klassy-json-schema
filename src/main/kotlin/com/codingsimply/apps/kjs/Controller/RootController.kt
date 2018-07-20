package com.codingsimply.apps.kjs.Controller

import com.codingsimply.apps.kjs.Model.Setting
import com.codingsimply.apps.kjs.Service.GeneratorService
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.*
import javafx.stage.DirectoryChooser

import java.net.URL
import java.util.ResourceBundle
import javafx.stage.FileChooser

class RootController : Initializable {

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
    var statusLabel: Label? = null

    private val generatorService = GeneratorService(this)

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        generatorService.setOnSucceeded {
            startToggleButton?.text = "Start"
        }
    }

    fun start(actionEvent: ActionEvent) {
        generatorService.setting = Setting(
                templateFileTextField?.text,
                schemaFolderTextField?.text,
                outputFolderTextField?.text,
                fileTypeTextField?.text,
                false)
        val toggleButton = actionEvent.source as ToggleButton
        if (toggleButton.isSelected) {
            toggleButton.text = "Cancel"
            generatorService.start()
        } else {
            toggleButton.text = "Start"
            generatorService.cancel()
            generatorService.reset()
        }
    }

    fun openTemplateFileDialog(actionEvent: ActionEvent) {
        val fileChooser = FileChooser()
        fileChooser.title = "Select Template File"
        val file = fileChooser.showOpenDialog((actionEvent.source as Node).scene.window)
        if (file != null) {
            templateFileTextField?.text = file.absolutePath
        }
    }

    fun openSchemaFolderDialog(actionEvent: ActionEvent) {
        val directoryChooser = DirectoryChooser()
        directoryChooser.title = "Select Schema Folder"
        val file = directoryChooser.showDialog((actionEvent.source as Node).scene.window)
        if (file != null) {
            schemaFolderTextField?.text = file.absolutePath
        }
    }

    fun openOutputFolderDialog(actionEvent: ActionEvent) {
        val directoryChooser = DirectoryChooser()
        directoryChooser.title = "Select Output Folder"
        val file = directoryChooser.showDialog((actionEvent.source as Node).scene.window)
        if (file != null) {
            outputFolderTextField?.text = file.absolutePath
        }
    }
}
