/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 8:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.youtubeplayer.player.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

internal class NetworkListener : BroadcastReceiver() {

    var onNetworkUnavailable = { }
    var onNetworkAvailable = { }

    override fun onReceive(context: Context, intent: Intent) {
        if (Utils.isOnline(context))
            onNetworkAvailable()
        else
            onNetworkUnavailable()
    }
}
