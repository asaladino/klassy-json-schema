package com.codingsimply.apps.kjs.Model

import javafx.application.Application

data class Setting(var templateFile: String? = null,
                   var schemaFolder: String? = null,
                   var outputFolder: String? = null,
                   var outputType: String? = null,
                   var useCli: Boolean? = false) {
    companion object {
        fun build(parameters: Application.Parameters): Setting {
            return Setting(
                    parameters.named["templateFile"],
                    parameters.named["schemaFolder"],
                    parameters.named["outputFolder"],
                    parameters.named["outputType"],
                    parameters.named["useCli"]?.toBoolean()
            )
        }
    }
}