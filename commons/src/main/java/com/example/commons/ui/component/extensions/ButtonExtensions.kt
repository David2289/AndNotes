package com.example.commons.ui.component.extensions

import android.view.View
import android.widget.Button

fun Button.setup(
    visible: Boolean? = null,
    title: Int? = null,
    onClick: (() -> Unit)? = null
)
: Button {
    visible?.let {
        visibility = if (it) View.VISIBLE else View.GONE
    }
    title?.let {
        text = context.getString(it)
    }
    onClick?.let {
        setOnClickListener { it() }
    }
    return this
}