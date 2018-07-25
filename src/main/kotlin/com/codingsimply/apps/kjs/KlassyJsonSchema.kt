package com.codingsimply.apps.kjs

import com.codingsimply.apps.kjs.Controller.RootController
import com.codingsimply.apps.kjs.Model.Setting
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

        val scene = Scene(root)

        scene.stylesheets.add("/styles/Styles.css")
        stage.title = "Klassy Json Schema"
        stage.scene = scene
        var heightOffset = 0
        val os = System.getProperty("os.name")
        if (os != null && os.startsWith("Mac")) {
            heightOffset = 30
        }

        stage.maxHeight = (root as BorderPane).prefHeightProperty().value - heightOffset
        stage.minHeight = root.prefHeightProperty().value - heightOffset
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
