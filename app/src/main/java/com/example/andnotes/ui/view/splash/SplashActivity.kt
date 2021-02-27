package com.example.andnotes.ui.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.andnotes.R
import com.example.andnotes.databinding.SplashActivityBinding
import com.example.andnotes.ui.view.home.activity.HomeActivity

class SplashActivity: AppCompatActivity() {

    lateinit var binding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity)
        binding.animated.addAnimatorUpdateListener { valueAnimator ->
            val progress = (valueAnimator.animatedValue as Float * 100).toInt()
            if (progress > 96) {
                binding.animated.removeAllAnimatorListeners()
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}