package com.codingsimply.apps.kjs.Service

import com.codingsimply.apps.kjs.Controller.ProgressInterface
import com.codingsimply.apps.kjs.Model.Progress
import com.codingsimply.apps.kjs.Model.Setting
import com.codingsimply.apps.kjs.Repository.JsonSchemaRepository
import com.codingsimply.apps.kjs.Utility.ClassCreateUtility
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
        try {
            val jsonSchemaRepository = JsonSchemaRepository(setting!!)
            val files = jsonSchemaRepository.findAllFiles()
            val classCreateUtility = ClassCreateUtility(setting!!)
            var progress = 1
            for (file in files) {
                val className = classCreateUtility.classNameFromFile(file)
                val json = jsonSchemaRepository.fileAsJson(file)
                classCreateUtility.writeClass(json, className, true)
                progressInterface.update(Progress(className, progress++, files.size))
            }
            progressInterface.finished(Progress("Done", 0, 0))
        } catch (e: Exception) {
            progressInterface.error(e)
        }
    }
}