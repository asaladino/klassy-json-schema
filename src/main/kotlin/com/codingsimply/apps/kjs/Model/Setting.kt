package com.codingsimply.apps.kjs.Model

import javafx.application.Application

data class Setting(var templateFile: String,
                   var schemaFolder: String,
                   var outputFolder: String,
                   var outputType: String,
                   var generateSecondaryClasses: Boolean,
                   var useCli: Boolean) {
    companion object {
        fun build(parameters: Application.Parameters): Setting {
            return Setting(
                    parameters.named["templateFile"] ?: "",
                    parameters.named["schemaFolder"] ?: "",
                    parameters.named["outputFolder"] ?: "",
                    parameters.named["outputType"] ?: "",
                    parameters.named["generateSecondaryClasses"]?.toBoolean() ?: true,
                    parameters.named["useCli"]?.toBoolean() ?: false
            )
        }
    }
}