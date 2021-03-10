package com.example.commons.utility.helper

import android.content.res.Resources

class DensityUtils {

    companion object {
        val Int.dp: Int
            get() = (this / Resources.getSystem().displayMetrics.density).toInt()
        val Int.px: Int
            get() = (this * Resources.getSystem().displayMetrics.density).toInt()
    }

}