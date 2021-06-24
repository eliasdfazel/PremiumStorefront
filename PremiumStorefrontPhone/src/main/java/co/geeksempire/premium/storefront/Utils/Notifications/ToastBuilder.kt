/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/24/21, 11:44 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Notifications

import android.content.Context
import android.widget.Toast
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun showToast(context: Context, toastContent: String, toastGravity: Int) {

    Toast.makeText(context, toastContent, Toast.LENGTH_LONG).apply {
        setGravity(toastGravity, 0, dpToInteger(context, 29))
    }.show()

}