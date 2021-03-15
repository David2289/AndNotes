package com.example.andnotes.ui.view.home.manager

import com.example.andnotes.BuildConfig
import com.example.commons.utility.helper.DateUtils

class SettingsManager {

    companion object {

        fun versionDate(): String {
            val version = BuildConfig.VERSION_NAME
            val date = DateUtils.currentDate("dd MMMM yyyy")
            return "v$version - $date"
        }

    }
}