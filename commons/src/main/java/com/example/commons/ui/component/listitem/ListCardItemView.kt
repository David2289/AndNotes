package com.example.commons.ui.component.listitem

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.commons.R
import com.example.commons.databinding.ListCardItemBinding
import com.example.commons.ui.utility.manager.ListCardItemManager

class ListCardItemView: ConstraintLayout {

    var binding: ListCardItemBinding
    var descContentExpandedHeight = 0
    var descContentCollapsedHeight = 0
    var descContentExpanded = false

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {

        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.list_card_item, this, true)

        binding.descContent.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                descContentExpandedHeight = binding.descContent.height
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                ListCardItemManager.maxLines(binding.desc, 1)
            }
        })
        binding.ward.setOnClickListener {
            if (descContentCollapsedHeight == 0) {
                descContentCollapsedHeight = binding.descContent.height
            }
            if (descContentExpanded) {
                descContentExpanded = false
                ListCardItemManager.collapseDetail(binding, descContentCollapsedHeight, descContentExpandedHeight)
            } else {
                descContentExpanded = true
                ListCardItemManager.expandDetail(binding, descContentCollapsedHeight, descContentExpandedHeight)
            }
        }

        if (attrs == null) return
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ListCardItemView)
        val title = typedArray.getString(R.styleable.ListCardItemView_title)
        val desc = typedArray.getString(R.styleable.ListCardItemView_desc)
        configUI(title, desc)
        typedArray.recycle()

    }

    private fun configUI(title:String?, desc: String?) {
        title?.let { binding.title.text = it }
        desc?.let { binding.desc.text = it }
    }

}