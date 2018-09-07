package com.codingsimply.apps.kjs.controller

import com.codingsimply.apps.kjs.model.Progress

interface ProgressInterface {

    fun update(progress: Progress)

    fun error(e: Exception)

    fun finished(progress: Progress)
}