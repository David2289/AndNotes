package com.example.commons.utility.extensions

import android.app.Dialog
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.commons.R
import com.example.commons.ui.component.listitem.ListStdItemView
import com.example.commons.ui.model.button.ButtonModel


fun Dialog.setupOneButtons(title: Int = 0,
                           desc: Int = 0,
                           btnModel: ButtonModel)
: Dialog {
    this.setContentView(R.layout.dialog_wrapped_buttons)
    val titleTV = this.findViewById<TextView>(R.id.title)
    val descTV = this.findViewById<TextView>(R.id.desc)
    this.findViewById<Button>(R.id.button_1)
        .setup(
            title = btnModel.title,
            onClick = btnModel.onClick
        )
    this.findViewById<Button>(R.id.button_2).setup(visible = false)
    if (title != 0) {
        titleTV.text = context.getString(title)
    }
    if (desc != 0) {
        descTV.text = context.getString(desc)
    }
    return this
}


fun Dialog.setupTwoButtons(title: Int = 0,
                           desc: Int = 0,
                           btnModel1: ButtonModel,
                           btnModel2: ButtonModel)
: Dialog {
    this.setContentView(R.layout.dialog_wrapped_buttons)
    val titleTV = this.findViewById<TextView>(R.id.title)
    val descTV = this.findViewById<TextView>(R.id.desc)
    this.findViewById<Button>(R.id.button_1)
        .setup(
            title = btnModel1.title,
            onClick = btnModel1.onClick
        )
    this.findViewById<Button>(R.id.button_2)
        .setup(
            title = btnModel2.title,
            onClick = btnModel2.onClick
        )
    if (title != 0) {
        titleTV.text = context.getString(title)
    }
    if (desc != 0) {
        descTV.text = context.getString(desc)
    }
    return this
}


fun Dialog.setupListButtons(title: Int = 0,
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
        it.endIcon?.let { icon -> btn1Btn.configEndIcon(icon) }
        btn1Btn.setOnClickListener {
                _ ->
            it.onClick()
        }
    }
    buttonMdl2?.let {
        btn2Btn.visibility = View.VISIBLE
        btn2Btn.title(it.title)
        it.endIcon?.let { icon -> btn2Btn.configEndIcon(icon) }
        btn2Btn.setOnClickListener { _ -> it.onClick() }
    }
    buttonMdl3?.let {
        btn3Btn.visibility = View.VISIBLE
        btn3Btn.title(it.title)
        it.endIcon?.let { icon -> btn3Btn.configEndIcon(icon) }
        btn3Btn.setOnClickListener { _ -> it.onClick() }
    }
    return this
}
