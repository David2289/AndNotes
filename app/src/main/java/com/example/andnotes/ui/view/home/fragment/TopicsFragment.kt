package com.example.andnotes.ui.view.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.andnotes.R
import com.example.andnotes.databinding.TopicsFragmentBinding
import com.example.commons.ui.utility.helper.AnimUtils

class TopicsFragment: Fragment() {

    lateinit var binding: TopicsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.topics_fragment, container, false)
        binding.image.setOnClickListener { view -> AnimUtils.pulseAnim(view as ImageView) }
        binding.itemDisplay.setOnClickListener{
            context?.let {
                val intent = Intent(Intent.ACTION_VIEW)
                        .setClassName(it, "com.example.display.ui.activity.DisplayActivity")
                startActivity(intent)
            }
        }
        binding.itemPhotos.setOnClickListener {
            context?.let {
                val intent = Intent(Intent.ACTION_VIEW)
                    .setClassName(it, "com.example.photos.ui.activity.PhotosActivity")
                startActivity(intent)
            }
        }
        return binding.root
    }

}