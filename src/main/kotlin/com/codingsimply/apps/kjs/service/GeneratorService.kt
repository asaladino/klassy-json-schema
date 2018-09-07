package com.codingsimply.apps.kjs.service

import com.codingsimply.apps.kjs.controller.ProgressInterface
import com.codingsimply.apps.kjs.model.Progress
import com.codingsimply.apps.kjs.model.Setting
import com.codingsimply.apps.kjs.repository.JsonSchemaRepository
import com.codingsimply.apps.kjs.utility.ClassCreateUtility
import javafx.concurrent.Service
import javafx.concurrent.Task

class GeneratorService(private val progressInterface: ProgressInterface) : Service<Void>() {

    var setting: Setting? = null

    override fun createTask(): Task<Void> {
        return object : Task<Void>() {
            @Throws(Exception::class)
            override fun call(): Void? {
                generate()
                return null
            }
        }
    }

    fun generate() {
        if (isInputValid()) {
            // Find all json schema files.
            val jsonSchemaRepository = JsonSchemaRepository(setting!!)
            val files = jsonSchemaRepository.findAllFiles()
            val classCreateUtility = ClassCreateUtility(setting!!)
            var progress = 1
            try {
                for (file in files) {
                    val className = classCreateUtility.classNameFromFile(file)
                    val json = jsonSchemaRepository.fileAsJson(file)
                    classCreateUtility.writeClass(json, className, true)
                    progressInterface.update(Progress(className, progress++, files.size))
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    private fun isInputValid(): Boolean {
        return true
    }
}