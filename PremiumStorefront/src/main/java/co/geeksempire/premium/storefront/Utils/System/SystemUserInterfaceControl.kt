/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/12/21, 8:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.android.Utils.System

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun showKeyboard(context: Context, toggleView: View) {

    val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(toggleView, InputMethodManager.SHOW_IMPLICIT)

}

fun hideKeyboard(context: Context, toggleView: View) {

    val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(toggleView.windowToken, 0)

}