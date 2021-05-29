package com.example.commons.ui.component.extensions

import android.app.Dialog
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.commons.R
import com.example.commons.ui.component.listitem.ListStdItemView
import com.example.commons.ui.model.button.ButtonModel

class DialogExtensions {

    companion object {
        fun Dialog.setup(
                title: Int = 0,
                desc: Int = 0,
                buttonMdl1: ButtonModel?,
                buttonMdl2: ButtonModel? = null,
                buttonMdl3: ButtonModel? = null
        ): Dialog {
            this.setContentView(R.layout.dialog_expanded_buttons)
            val titleTV = this.findViewById<TextView>(R.id.title)
            val descTV = this.findViewById<TextView>(R.id.desc)
            val btn1Btn = this.findViewById<ListStdItemView>(R.id.button_1)
            val btn2Btn = this.findViewById<ListStdItemView>(R.id.button_2)
            val btn3Btn = this.findViewById<ListStdItemView>(R.id.button_3)
            if (title != 0) {
                titleTV.text = context.getString(title)
            }
            if (desc != 0) {
                descTV.text = context.getString(desc)
            }
            buttonMdl1?.let {
                btn1Btn.visibility = View.VISIBLE
                btn1Btn.title(it.title)
                it.endIcon?.let { btn1Btn.configEndIcon(it) }
                btn1Btn.setOnClickListener { _ -> it.onClick }
            }
            buttonMdl2?.let {
                btn2Btn.visibility = View.VISIBLE
                btn2Btn.title(it.title)
                it.endIcon?.let { btn2Btn.configEndIcon(it) }
                btn2Btn.setOnClickListener { _ -> it.onClick }
            }
            buttonMdl3?.let {
                btn3Btn.visibility = View.VISIBLE
                btn3Btn.title(it.title)
                it.endIcon?.let { btn3Btn.configEndIcon(it) }
                btn3Btn.setOnClickListener { _ -> it.onClick }
            }
            return this
        }
    }

}