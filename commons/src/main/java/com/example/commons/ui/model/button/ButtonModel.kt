package com.example.commons.ui.model.button

data class ButtonModel(
        var title: Int,
        var endIcon: Int? = null,
        var onClick: () -> Unit
)