package com.example.display.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.display.R
import com.example.display.databinding.DisplayActivityBinding

class DisplayActivity: AppCompatActivity() {

    lateinit var binding: DisplayActivityBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.display_activity)

        // Nav Controller
        navController = findNavController(R.id.fragment_content)
        // Setup ActionBar
        NavigationUI.setupActionBarWithNavController(this, navController)

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}