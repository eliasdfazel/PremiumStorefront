/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/1/21, 5:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Notifications

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

fun doVibrate(context: Context, millisecondVibrate: Long) {

    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(millisecondVibrate, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(millisecondVibrate)
    }
}