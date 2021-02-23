package com.example.andnotes.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.andnotes.R
import com.example.andnotes.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        setSupportActionBar(binding.toolbarContent.toolbar)

        val fragmentHost = NavHostFragment.create(R.navigation.home)
        loadFragment(fragmentHost)

        bottomNavConfig()

        setContentView(binding.root)
    }

    fun configToolbarLogo(@DrawableRes logo: Int, block: () -> Unit) {
        showToolbarLogo()
        binding.toolbarContent.toolbarLogo.setImageDrawable(ContextCompat.getDrawable(this, logo))
        binding.toolbarContent.toolbarLogo.setOnClickListener { block() }
    }

    fun showToolbarLogo() { binding.toolbarContent.toolbarLogo.visibility = View.VISIBLE }
    fun hideToolbarLogo() { binding.toolbarContent.toolbarLogo.visibility = View.GONE }

    private fun bottomNavConfig() {
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> Log.i("BOTTOMNAV", "home button pressed")
                R.id.item_list -> Log.i("BOTTOMNAV", "list button pressed")
                R.id.item_settings -> Log.i("BOTTOMNAV", "settings button pressed")
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_content, fragment)
        ft.commit()
    }

}