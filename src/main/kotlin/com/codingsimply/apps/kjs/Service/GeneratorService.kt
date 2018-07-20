package com.codingsimply.apps.kjs.Service

import com.codingsimply.apps.kjs.Controller.RootController
import com.codingsimply.apps.kjs.Model.Setting
import javafx.concurrent.Service
import javafx.concurrent.Task

class GeneratorService(val rootController: RootController) : Service<Void>() {

    var setting: Setting? = null

    override fun createTask(): Task<Void> {
        return object : Task<Void>() {
            @Throws(Exception::class)
            override fun call(): Void? {
                return null
            }
        }
    }
}