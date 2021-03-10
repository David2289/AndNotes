package com.example.andnotes.ui.view.walkthrough.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.andnotes.R
import com.example.andnotes.databinding.WtActivityBinding
import com.example.andnotes.ui.utility.manager.WTManager
import com.example.andnotes.ui.view.home.activity.HomeActivity
import com.example.andnotes.ui.view.walkthrough.adapter.WTAdapter
import com.example.andnotes.ui.view.walkthrough.model.WTItem
import com.example.commons.utility.helper.Constants
import com.example.commons.utility.helper.SharedPrefUtils

class WTActivity: AppCompatActivity() {

    lateinit var wtItemList: ArrayList<WTItem>
    lateinit var adapter: WTAdapter
    lateinit var binding: WtActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.wt_activity)
        configUI()
        setContentView(binding.root)
    }

    private fun configUI() {
        wtItemList = WTManager.getWtItemList()
        WTManager.configAutoSlide(binding.viewpager, wtItemList.size)

        adapter = WTAdapter(wtItemList)
        binding.viewpager.adapter = adapter

        binding.circleIndicator.setViewPager(binding.viewpager)

        binding.continueButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            SharedPrefUtils.saveData(this, Constants.SPREF_WT_COMPLETED, true)
        }
    }

}