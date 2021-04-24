/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/24/21 11:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Operations

import android.os.Handler
import android.os.Looper

fun delay(delayTime: Long = 333) {

    Handler(Looper.getMainLooper()).postDelayed(Runnable {



    }, delayTime)

}