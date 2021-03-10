package com.example.andnotes.ui.view.walkthrough.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.andnotes.R
import com.example.andnotes.databinding.WtActivityBinding
import com.example.andnotes.ui.utility.manager.WTManager
import com.example.andnotes.ui.view.walkthrough.adapter.WTAdapter
import com.example.andnotes.ui.view.walkthrough.model.WTItem

class WTActivity: AppCompatActivity() {

    lateinit var wtItemList: ArrayList<WTItem>
    lateinit var adapter: WTAdapter
    lateinit var binding: WtActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.wt_activity)

        wtItemList = WTManager.getWtItemList()
        WTManager.configAutoSlide(binding.viewpager, wtItemList.size)

        adapter = WTAdapter(wtItemList)
        binding.viewpager.adapter = adapter

        binding.circleIndicator.setViewPager(binding.viewpager)
        setContentView(binding.root)
    }

}