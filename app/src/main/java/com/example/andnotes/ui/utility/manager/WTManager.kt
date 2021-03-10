package com.example.andnotes.ui.utility.manager

import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.ViewPager2
import com.example.andnotes.R
import com.example.andnotes.ui.view.walkthrough.model.WTItem

class WTManager {

    companion object {

        private const val DURATION_SLIDE_SHOWN = 4000L

        private lateinit var handler: Handler
        private lateinit var runnable: Runnable
        private var runnableEnqueued = false
        private var currentPage = 0

        fun getWtItemList(): ArrayList<WTItem> {
            val list: ArrayList<WTItem> = ArrayList();
            list.add(
                WTItem(
                    R.drawable.img_andstudio_logo,
                    R.string.wt_page1_title,
                    R.string.wt_page1_description
                )
            )
            list.add(
                WTItem(
                    R.drawable.img_jetpack_logo,
                    R.string.wt_page2_title,
                    R.string.wt_page2_description
                )
            )
            list.add(
                WTItem(
                    R.drawable.img_reactivex_logo,
                    R.string.wt_page3_title,
                    R.string.wt_page3_description
                )
            )
            return list
        }


        fun configAutoSlide(viewpager: ViewPager2, itemListSize: Int) {
            handler = Handler(Looper.getMainLooper())
            runnable = Runnable {
                runnableEnqueued = false
                val newPage = if (currentPage + 1 == itemListSize) 0 else currentPage + 1
                // 2. OnPageSelected method will be called again
                viewpager.setCurrentItem(newPage, true)
            }

            // 1.
            // When the adapter is attached, the first page will be indexed, then the ViewPager will call onPageSelected method with position 0
            // When onPageSelected is called for first time, tha handler is associated with the runnable to enqueue for four seconds.
            viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPage = position
                    if (!runnableEnqueued) {
                        handler.postDelayed(runnable, DURATION_SLIDE_SHOWN)
                        runnableEnqueued = true
                    }
                    // 3. When user slides the pager, it will stop
                    else {
                        handler.removeCallbacks(runnable)
                    }
                }
            })
        }
    }

}