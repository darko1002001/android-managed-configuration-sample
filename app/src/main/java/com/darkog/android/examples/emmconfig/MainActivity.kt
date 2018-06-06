package com.darkog.android.examples.emmconfig

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.*
import android.widget.Toast
import com.darkog.android.examples.emmconfig.R.layout.activity_main
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        buttonSearch.setOnClickListener { loadUrl(editTextSearch.text.toString()) }
        configureWebView()
    }

    private fun loadUrl(url: String) {
        Single.just(url)
                .map { it.normalizeUrl() }
                .subscribe({ item ->
                    editTextSearch.setText(item)
                    webView.loadUrl(item)
                }, { throwable ->
                    Toast.makeText(this, getString(R.string.toast_message_invalid_url), Toast.LENGTH_LONG).show()
                })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView() {
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
            }
        }
    }
}
