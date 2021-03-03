package com.example.andnotes.ui.view.home.manager

import android.animation.ValueAnimator
import android.widget.ImageView

class HomeManager {

    companion object {
        fun pulseAnim(imageView: ImageView) {
            val animator = ValueAnimator.ofFloat(1.0f, 1.2f)
            animator.addUpdateListener {
                imageView.scaleX = animator.animatedValue as Float
                imageView.scaleY = animator.animatedValue as Float
            }
            animator.duration = 180
            animator.repeatMode = ValueAnimator.REVERSE
            animator.repeatCount = 1
            animator.start()
        }
    }

}