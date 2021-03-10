package com.example.andnotes.ui.view.walkthrough.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WTItem (
    @DrawableRes val imageRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val descRes: Int,
        )