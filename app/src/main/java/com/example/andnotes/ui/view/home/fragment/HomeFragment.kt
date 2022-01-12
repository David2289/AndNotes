package com.example.andnotes.ui.view.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.andnotes.R
import com.example.andnotes.databinding.HomeFragmentBinding
import com.example.commons.ui.utility.helper.AnimUtils
import com.example.commons.utility.extensions.display
import org.joda.time.*

class HomeFragment: Fragment() {

    lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.star.setOnClickListener { view -> AnimUtils.rotateAnim(view as ImageView) }
        binding.date.text = DateTime.now(DateTimeZone.UTC).display("dd/MM/yyyy HH:mm")
        return binding.root
    }

}