package com.example.andnotes.ui.view.home.manager

import android.widget.ImageView
import com.example.commons.ui.utility.manager.BaseManager

class TopicsManager: BaseManager() {

    companion object {
        fun pulseAnim(imageView: ImageView) {
            BaseManager.pulseAnim(imageView)
        }
    }

}