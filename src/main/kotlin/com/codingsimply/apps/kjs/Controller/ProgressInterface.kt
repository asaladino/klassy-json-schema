package com.codingsimply.apps.kjs.Controller

import com.codingsimply.apps.kjs.Model.Progress

interface ProgressInterface {

    fun update(progress: Progress)

    fun error(ex: Exception)

    fun finished(progress: Progress)
}