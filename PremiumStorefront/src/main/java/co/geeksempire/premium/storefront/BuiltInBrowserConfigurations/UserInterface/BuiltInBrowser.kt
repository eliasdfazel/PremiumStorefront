/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/21/21, 3:48 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.BuiltInBrowserConfigurations.UserInterface

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.BuiltInBrowserConfigurations.Interface.WebInterface
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Operations.NavigationListener
import co.geeksempire.premium.storefront.Utils.Operations.NavigationOperations
import co.geeksempire.premium.storefront.Utils.UI.Display.navigationBarHeight
import co.geeksempire.premium.storefront.Utils.UI.Display.statusBarHeight
import co.geeksempire.premium.storefront.databinding.BrowserViewBinding

fun showBuiltInBrowser(context: Context,
                       linkToLoad: String,
                       gradientColorOne: Int?,
                       gradientColorTwo: Int?) {

    Intent(context, BuiltInBrowser::class.java).apply {
        putExtra(Intent.EXTRA_TEXT, linkToLoad)
        putExtra("GradientColorOne", gradientColorOne)
        putExtra("GradientColorTwo", gradientColorTwo)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(this@apply, ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, 0).toBundle())
    }

}

class BuiltInBrowser : AppCompatActivity(), NavigationListener {

    private lateinit var browserViewBinding: BrowserViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        browserViewBinding = BrowserViewBinding.inflate(layoutInflater)
        setContentView(browserViewBinding.root)

        browserViewBinding.root.setPadding(0, statusBarHeight(applicationContext) , 0, navigationBarHeight(applicationContext))

        val dominantColor = intent.getIntExtra("GradientColorOne", getColor(R.color.default_color))
        val vibrantColor = intent.getIntExtra("GradientColorTwo", getColor(R.color.default_color_game))

        window.setBackgroundDrawable(GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, arrayOf(vibrantColor, dominantColor).toIntArray()))

        if (!BuildConfig.DEBUG) {
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }

        val linkToLoad = intent.getStringExtra(Intent.EXTRA_TEXT)

        if (linkToLoad != null) {

            val progressBarLayerList = getDrawable(R.drawable.web_view_progress_bar_drawable) as LayerDrawable
            val progressBarClipDrawable = progressBarLayerList.findDrawableByLayerId(android.R.id.progress) as ClipDrawable
            progressBarClipDrawable.drawable = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, arrayOf(vibrantColor, dominantColor).toIntArray())

            browserViewBinding.webViewProgressBar.indeterminateDrawable = progressBarLayerList

            browserViewBinding.webView.settings.javaScriptEnabled = true

            browserViewBinding.webView.settings.builtInZoomControls = true
            browserViewBinding.webView.settings.displayZoomControls = false
            browserViewBinding.webView.settings.setSupportZoom(true)

            browserViewBinding.webView.settings.useWideViewPort = true
            browserViewBinding.webView.settings.loadWithOverviewMode = true
            browserViewBinding.webView.setInitialScale(0)

            browserViewBinding.webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

            browserViewBinding.webView.webViewClient = BuiltInWebViewClient()
            browserViewBinding.webView.webChromeClient = BuiltInChromeWebViewClient()
            browserViewBinding.webView.addJavascriptInterface(WebInterface(this@BuiltInBrowser), "Android")
            browserViewBinding.webView.loadUrl(linkToLoad)

        } else {

            this@BuiltInBrowser.finish()

        }

    }

    override fun onStart() {
        super.onStart()

        NavigationOperations(this@BuiltInBrowser)
            .listenBackPressed(this@BuiltInBrowser)

    }

    override fun onPause() {
        super.onPause()
    }

    override fun backNavigation() {

        this@BuiltInBrowser.finish()
        overridePendingTransition(0, android.R.anim.slide_out_right)

    }

    inner class BuiltInWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (request != null) {
                view?.loadUrl(request.url.toString())
            }

            return false
        }

        override fun onPageStarted(webView: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(webView, url, favicon)

            browserViewBinding.webViewProgressBar.visibility = View.VISIBLE

        }

        override fun onPageFinished(webView: WebView?, url: String?) {
            super.onPageFinished(webView, url)

            browserViewBinding.webViewProgressBar.visibility = View.INVISIBLE

        }

    }

    inner class BuiltInChromeWebViewClient : WebChromeClient() {

    }

}