package com.revakovsky.thenytimesbooks.core

import android.content.Context
import com.revakovsky.thenytimesbooks.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringProvider @Inject constructor(
    private val context: Context,
) {

    fun provideOfflineText(): String = context.getString(R.string.your_device_is_offline)

}