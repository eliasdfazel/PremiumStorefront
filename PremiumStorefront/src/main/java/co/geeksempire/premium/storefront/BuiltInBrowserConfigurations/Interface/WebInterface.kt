/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/22/21, 2:15 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.BuiltInBrowserConfigurations.Interface

import android.webkit.JavascriptInterface
import co.geeksempire.premium.storefront.BuiltInBrowserConfigurations.UserInterface.BuiltInBrowser
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