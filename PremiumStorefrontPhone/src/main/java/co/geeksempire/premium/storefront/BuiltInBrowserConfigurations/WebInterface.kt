/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/20/21, 9:34 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.BuiltInBrowserConfigurations

import android.webkit.JavascriptInterface
import co.geeksempire.premium.storefront.R

/**
 * Android
 **/
class WebInterface (private val context: BuiltInBrowser) {

    @JavascriptInterface
    fun getApplicationName() : String {

        return context.getString(R.string.applicationName)
    }

}