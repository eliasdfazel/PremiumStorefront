/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 8:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.youtubeplayer.ui.utils

import android.annotation.SuppressLint

object TimeUtilities {

    /**
     * Transform the time in seconds in a string with format "M:SS".
     */
    @SuppressLint("DefaultLocale")
    @JvmStatic
    fun formatTime(timeInSeconds: Float): String {
        val minutes = (timeInSeconds / 60).toInt()
        val seconds = (timeInSeconds % 60).toInt()
        return String.format("%d:%02d", minutes, seconds)
    }
}
