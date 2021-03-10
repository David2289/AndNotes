package com.example.andnotes.ui.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.andnotes.R
import com.example.andnotes.databinding.SplashActivityBinding
import com.example.andnotes.ui.view.home.activity.HomeActivity
import com.example.andnotes.ui.view.walkthrough.activity.WTActivity
import com.example.commons.utility.helper.Constants
import com.example.commons.utility.helper.SharedPrefUtils

class SplashActivity: AppCompatActivity() {

    lateinit var binding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity)
        binding.animated.addAnimatorUpdateListener { valueAnimator ->
            val progress = (valueAnimator.animatedValue as Float * 100).toInt()
            if (progress > 96) {
                binding.animated.removeAllAnimatorListeners()
                toNextScreen()
            }
        }
    }

    private fun toNextScreen() {
        val wtCompleted = SharedPrefUtils.getBooleanData(this, Constants.SPREF_WT_COMPLETED)
        val intent =
            if (wtCompleted) Intent(this, HomeActivity::class.java)
            else Intent(this, WTActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}