package com.codingsimply.apps.kjs

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class KlassyJsonSchema : Application() {

    override fun start(stage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("/fxml/root.fxml"))
        val scene = Scene(root)
        scene.stylesheets.add("/styles/Styles.css")
        stage.title = "Klassy Json Schema"
        stage.scene = scene
        stage.show()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch(KlassyJsonSchema::class.java)
        }
    }
}
