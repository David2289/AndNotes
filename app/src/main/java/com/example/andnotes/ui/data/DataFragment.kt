package com.example.andnotes.ui.data

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.andnotes.R
import com.example.andnotes.databinding.DataFragmentBinding

class DataFragment: Fragment() {

    lateinit var binding: DataFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.data_fragment, container, false)
        binding.linkedinLink.setOnClickListener {
            val link = getString(R.string.data_social_network_linkedin_link)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
        binding.githubLink.setOnClickListener {
            val link = getString(R.string.data_social_network_github_link)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
        return binding.root
    }

}