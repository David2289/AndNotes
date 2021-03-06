package com.example.commons.ui.utility.manager

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.text.TextUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.addListener
import com.example.commons.databinding.ListCardItemBinding

class ListCardItemManager {

    companion object {

        val ANIM_DURATION = 300L

        fun maxLines(textView: TextView, lines: Int) {
            textView.maxLines = lines
            textView.ellipsize = TextUtils.TruncateAt.END
        }

        fun expandDetail(binding: ListCardItemBinding, descContentCollapsedHeight: Int, descContentExpandedHeight: Int) {
            maxLines(binding.desc, Int.MAX_VALUE)

            val set = ConstraintSet()
            set.clone(binding.mainContent)

            val animator = ValueAnimator.ofPropertyValuesHolder(
                PropertyValuesHolder.ofInt("expandDesc", descContentCollapsedHeight, descContentExpandedHeight),
                PropertyValuesHolder.ofFloat("rotateWard", 0f, 180f)
            )
            animator.addUpdateListener { valAnimator ->
                set.constrainHeight(binding.descContent.id, valAnimator.getAnimatedValue("expandDesc") as Int)
                set.applyTo(binding.mainContent)
                binding.ward.rotation = valAnimator.getAnimatedValue("rotateWard") as Float
            }
            animator.duration = ANIM_DURATION
            animator.start()
        }

        fun collapseDetail(binding: ListCardItemBinding, descContentCollapsedHeight: Int, descContentExpandedHeight: Int) {
            val set = ConstraintSet()
            set.clone(binding.mainContent)

            val animator = ValueAnimator.ofPropertyValuesHolder(
                PropertyValuesHolder.ofInt("collapseDesc", descContentExpandedHeight, descContentCollapsedHeight),
                PropertyValuesHolder.ofFloat("rotateWard", 180f, 0f)
            )
            animator.addUpdateListener {
                set.constrainHeight(binding.descContent.id, animator.getAnimatedValue("collapseDesc") as Int)
                set.applyTo(binding.mainContent)
                binding.ward.rotation = animator.getAnimatedValue("rotateWard") as Float
            }
            animator.addListener(
                onEnd = { maxLines(binding.desc, 1) }
            )
            animator.duration = ANIM_DURATION
            animator.start()
        }
    }

}