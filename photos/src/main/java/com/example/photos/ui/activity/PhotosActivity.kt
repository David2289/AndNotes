package com.example.photos.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.photos.R
import com.example.photos.databinding.PhotosActivityBinding

class PhotosActivity: AppCompatActivity() {

    lateinit var binding: PhotosActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.photos_activity)
        setContentView(binding.root)
    }

}