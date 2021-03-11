package com.example.andnotes.ui.view.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.andnotes.R
import com.example.andnotes.databinding.SettingsFragmentBinding

class SettingsFragment: Fragment() {

    lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        binding.itemLanguage.setOnClickListener { Toast.makeText(context, "Language selected", Toast.LENGTH_LONG).show() }
        binding.itemAuthor.setOnClickListener { Toast.makeText(context, "Author selected", Toast.LENGTH_LONG).show() }
        return binding.root
    }
}