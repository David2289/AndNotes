package com.example.display.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.display.R
import com.example.display.databinding.DisplayActivityBinding
import com.example.display.ui.fragment.ListFragment

class DisplayActivity: AppCompatActivity() {

    lateinit var binding: DisplayActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.display_activity)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarContent.toolbar)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_content, ListFragment.newInstance())
        ft.commit()
    }

}