package com.scarecrowragdoll.ggsel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.*

class MainActivity : AppCompatActivity() {
    lateinit var myWebView: WebView
    lateinit var cookieManager: CookieManager
    lateinit var webStorage: WebStorage

    var url = "https://ggsel.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = baseContext.getResources().getConfiguration().locales.get(0).language

        myWebView = WebView(baseContext)
        val settings: WebSettings = myWebView.getSettings()
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        myWebView.webViewClient = WebViewClient()
        cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)

        if (!language.equals("ru"))
            url = "${url}/${language}"

        webStorage = WebStorage.getInstance()
        myWebView.loadUrl(url + "?utm_source=prila")
        setContentView(myWebView)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        webStorage.getOrigins {
            return@getOrigins
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack()
            cookieManager.flush()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}