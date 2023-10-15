package com.revakovsky.thenytimesbooks.core

import android.content.res.Configuration

object WindowType {

    private var configuration: Configuration? = null

    fun setConfiguration(configuration: Configuration) {
        this.configuration = configuration
    }

    fun getWindowType(): Type {
        var type: Type = Type.Medium
        configuration?.apply {
            type = when {
                screenWidthDp <= 320 -> Type.Small
                screenWidthDp <= 480 -> Type.Medium
                screenWidthDp <= 600 -> Type.Large
                else -> Type.Expanded
            }
        }
        return type
    }


    sealed class Type {
        object Small : Type()
        object Medium : Type()
        object Large : Type()
        object Expanded : Type()
    }

}
