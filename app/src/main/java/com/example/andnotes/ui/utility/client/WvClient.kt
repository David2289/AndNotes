package com.example.andnotes.ui.utility.client

import android.webkit.WebView
import android.webkit.WebViewClient

class WvClient: WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let { view?.loadUrl(url) }
        return false
    }

}