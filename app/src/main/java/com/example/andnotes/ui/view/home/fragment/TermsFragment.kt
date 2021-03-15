package com.example.andnotes.ui.view.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.andnotes.BuildConfig
import com.example.andnotes.R
import com.example.andnotes.databinding.TermsFragmentBinding

class TermsFragment: Fragment() {

    lateinit var binding: TermsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.terms_fragment, container, false)

        val wvSettings = binding.webView.settings
        wvSettings.javaScriptEnabled = true // To enable JavaScript. By default is disabled.
        wvSettings.javaScriptCanOpenWindowsAutomatically = true // Java script window.open() method is available

        binding.webView.loadUrl(BuildConfig.TERMS_URL)
        return binding.root
    }
}