package com.example.andnotes.ui.utility.manager

import com.example.andnotes.BuildConfig
import com.example.commons.utility.extensions.display
import org.joda.time.LocalDate

class SettingsManager {

    companion object {

        fun versionDate(): String {
            val version = BuildConfig.VERSION_NAME
            val date = LocalDate.now().display("dd MMMM yyyy")
            return "v$version - $date"
        }

    }
}