package com.codingsimply.apps.kjs.repository

import com.codingsimply.apps.kjs.model.Setting
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.toList

class JsonSchemaRepository(private val setting: Setting) {

    fun findAllFiles(): List<Path> {
        return Files.list(Paths.get(setting.schemaFolder)).filter {
            return@filter it.toFile().name.endsWith(".json")
        }.toList()
    }

    fun fileAsJson(file: Path): LinkedTreeMap<String, Any> {
        val contents = String(Files.readAllBytes(file), Charset.defaultCharset())
        return Gson().fromJson<LinkedTreeMap<String, Any>>(contents, object: TypeToken<LinkedTreeMap<String, Any>>() {}.type)
    }
}