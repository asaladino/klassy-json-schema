package com.codingsimply.apps.kjs.utility

import com.codingsimply.apps.kjs.model.Setting
import com.google.common.base.CaseFormat
import com.google.gson.internal.LinkedTreeMap
import java.nio.file.Path
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import java.nio.file.Files
import java.nio.file.Paths

class ClassCreateUtility(private val setting: Setting) {

    private val template: JtwigTemplate = JtwigTemplate.fileTemplate(setting.templateFile)

    fun writeClass(contents: LinkedTreeMap<*, *>, className: String, isDataModel: Boolean, file: Path?) {
        saveClass(contents, className, file, isDataModel)
        if (contents["properties"] != null) {
            for ((key, value) in (contents["properties"] as LinkedTreeMap<*, *>).entries) {
                val propertyName = key as String
                val propertyMeta = value as LinkedTreeMap<*, *>?
                val type = propertyMeta?.get("type") as String?
                if (type != null) {
                    if (propertyMeta != null && type == "object") {
                        val propertyClassName = classNameFromProperty(propertyName)
                        writeClass(propertyMeta, propertyClassName, false, null)
                    }
                    if (propertyMeta != null && type == "array") {
                        val arrayItems = propertyMeta["items"] as LinkedTreeMap<*, *>?
                        val arrayType = arrayItems?.get("type") as String?
                        if (arrayItems != null && arrayType != null && arrayType == "object") {
                            val propertyClassName = classNameFromProperty(propertyName)
                            writeClass(arrayItems, propertyClassName, false, null)
                        }
                    }
                }
            }
        }
    }

    private fun saveClass(contents: LinkedTreeMap<*, *>, className: String, file: Path?, isDataModel: Boolean) {
        val path = Paths.get(setting.outputFolder, className + setting.filenameSuffix + "." + setting.outputType)
        if (!path.toFile().exists() || isDataModel) {
            if((setting.generateSecondaryClasses && !isDataModel) || isDataModel) {
                val model = JtwigModel.newModel()
                        .with("className", className)
                        .with("isDataModel", isDataModel)
                        .with("setting", setting)
                        .with("contents", contents)
                        .with("dataModelSlug", file?.let { dataModelSluggedFromFile(it) })
                template.render(model, Files.newOutputStream(path))
            }
        }
    }

    private fun classNameFromProperty(propertyName: String): String {
        return propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1)
    }

    fun classNameFromFile(file: Path): String {
        val className = dataModelSluggedFromFile(file).replace("-", "_")
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, className)
    }

    private fun dataModelSluggedFromFile(file: Path): String {
        return file.toFile().name.replace(".json", "")
    }
}