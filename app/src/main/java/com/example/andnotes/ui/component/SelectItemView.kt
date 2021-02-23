package com.example.andnotes.ui.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.andnotes.R
import com.example.andnotes.databinding.SelectItemViewBinding
import com.example.andnotes.ui.utility.manager.SelectItemManager

class SelectItemView: ConstraintLayout {

    var binding: SelectItemViewBinding
    var descContentExpandedHeight = 0
    var descContentCollapsedHeight = 0
    var descContentExpanded = false

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {

        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.select_item_view, this, true)

        binding.descContent.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                descContentExpandedHeight = binding.descContent.height
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                SelectItemManager.maxLines(binding.desc, 1)
            }
        })
        binding.ward.setOnClickListener {
            if (descContentCollapsedHeight == 0) {
                descContentCollapsedHeight = binding.descContent.height
            }
            if (descContentExpanded) {
                descContentExpanded = false
                SelectItemManager.collapseDetail(binding, descContentCollapsedHeight, descContentExpandedHeight)
            } else {
                descContentExpanded = true
                SelectItemManager.expandDetail(binding, descContentCollapsedHeight, descContentExpandedHeight)
            }
        }

        if (attrs == null) return
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectItemView)
        val title = typedArray.getString(R.styleable.SelectItemView_title)
        val desc = typedArray.getString(R.styleable.SelectItemView_desc)
        configUI(title, desc)
        typedArray.recycle()

    }

    private fun configUI(title:String?, desc: String?) {
        title?.let { binding.title.text = it }
        desc?.let { binding.desc.text = it }
    }

}