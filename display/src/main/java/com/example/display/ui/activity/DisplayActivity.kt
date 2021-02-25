package com.example.display.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.display.R
import com.example.display.databinding.DisplayActivityBinding

class DisplayActivity: AppCompatActivity() {

    lateinit var binding: DisplayActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.display_activity)
        setContentView(binding.root)
    }

}