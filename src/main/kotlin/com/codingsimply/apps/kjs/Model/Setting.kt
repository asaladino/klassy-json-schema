package com.codingsimply.apps.kjs.Model

data class Setting(var templateFile: String? = null,
                   var schemaFolder: String? = null,
                   var outputFolder: String? = null,
                   var outputType: String? = null,
                   var useCli: Boolean = false)