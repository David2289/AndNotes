package com.example.andnotes.ui.view.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.andnotes.R
import com.example.andnotes.databinding.HomeFragmentBinding

class HomeFragment: Fragment() {

    lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.button.setOnClickListener { Navigation.findNavController(binding.root).navigate(R.id.action_home_to_about) }
        return binding.root
    }

}