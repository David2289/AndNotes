package com.example.andnotes.ui.view.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.andnotes.R
import com.example.andnotes.databinding.AboutFragmentBinding

/**
 * Opened on About button is clicked from Home Screen (Fail try)
 */
class AboutFragment: Fragment() {

    lateinit var binding: AboutFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.about_fragment, container, false)
//        (activity as HomeActivity).configToolbarLogo(R.drawable.ic_arrow_left_white, fun() {
//            Navigation.findNavController(binding.root).navigate(R.id.action_about_to_home)
//            (activity as HomeActivity).hideToolbarLogo()
//        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
//        if ((activity as HomeActivity).isToolbarLogoVisible()) {
//            (activity as HomeActivity).hideToolbarLogo()
//        }
    }

}