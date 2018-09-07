package com.codingsimply.apps.kjs

import com.codingsimply.apps.kjs.controller.RootController
import com.codingsimply.apps.kjs.model.Setting
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class KlassyJsonSchema : Application() {

    override fun start(stage: Stage) {
        val loader = FXMLLoader()
        val root = loader.load<Parent>(javaClass.getResourceAsStream("/fxml/root.fxml"))
        val controller = loader.getController<RootController>()
        controller.loadSetting(Setting.build(parameters))

        stage.scene = Scene(root)
        stage.scene.stylesheets.add("/styles/Styles.css")
        stage.title = "Klassy Json Schema"
        stage.maxHeight = (root as BorderPane).prefHeightProperty().value
        stage.minHeight = root.prefHeightProperty().value
        stage.minWidth = root.prefWidthProperty().value
        stage.show()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch(KlassyJsonSchema::class.java, *args)
        }
    }
}
