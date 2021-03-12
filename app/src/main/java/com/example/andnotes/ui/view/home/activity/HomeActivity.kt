package com.example.andnotes.ui.view.home.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.andnotes.R
import com.example.andnotes.databinding.HomeActivityBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity)
        configUI()
        setContentView(binding.root)
    }

    private fun configUI() {
        // Nav Controller
        val navController = findNavController(R.id.fragment_content)

        // SetUp Bottom Navigation View
        binding.bottomNav.setupWithNavController(navController)

        // Setup ActionBar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home, R.id.topics, R.id.sett))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}