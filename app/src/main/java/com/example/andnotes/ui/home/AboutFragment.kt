package com.example.andnotes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.andnotes.R
import com.example.andnotes.databinding.AboutFragmentBinding
import com.example.andnotes.ui.main.MainActivity

class AboutFragment: Fragment() {

    lateinit var binding: AboutFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.about_fragment, container, false)
        (activity as MainActivity).configToolbarLogo(R.drawable.ic_arrow_left_white, fun() {
            Navigation.findNavController(binding.root).navigate(R.id.action_about_to_home)
            (activity as MainActivity).hideToolbarLogo()
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        if ((activity as MainActivity).isToolbarLogoVisible()) {
            (activity as MainActivity).hideToolbarLogo()
        }
    }

}