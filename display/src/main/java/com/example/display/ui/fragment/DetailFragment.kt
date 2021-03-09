package com.example.display.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.display.R
import com.example.display.business.model.User
import com.example.display.databinding.DetailFragmentBinding
import com.example.display.ui.utility.helper.Constants
import com.squareup.picasso.Picasso

class DetailFragment: Fragment() {

    lateinit var binding: DetailFragmentBinding
    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        user = arguments?.getParcelable<User>(Constants.BUNDLE_USER) as User
        binding.user = user
        Picasso.get().load(user.avatar).into(binding.photo)
        return binding.root
    }

}