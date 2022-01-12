package com.example.commons.ui.utility.helper

import android.animation.ValueAnimator
import android.view.View
import android.widget.ImageView

abstract class AnimUtils {

    companion object {

        private const val ANIM_DURATION_FAST = 180L
        private const val ANIM_DURATION_SLOW = 560L

        fun pulseAnim(imageView: ImageView) {
            val animator = ValueAnimator.ofFloat(1.0f, 1.2f)
            animator.addUpdateListener { valAnimator ->
                imageView.scaleX = valAnimator.animatedValue as Float
                imageView.scaleY = valAnimator.animatedValue as Float
            }
            animator.duration = ANIM_DURATION_FAST
            animator.repeatMode = ValueAnimator.REVERSE
            animator.repeatCount = 1
            animator.start()
        }

        fun rotateAnim(imageView: View) {
            val animator = ValueAnimator.ofFloat(0f, 360f)
            animator.addUpdateListener { valAnimator ->
                imageView.rotation = valAnimator.animatedValue as Float
            }
            animator.duration = ANIM_DURATION_SLOW
            animator.start()
        }

    }
}