/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/21/21 12:08 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.NetworkConnections

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

object NetworkSettingCallback {
    const val WifiSetting: Int = 111
}

interface InterfaceNetworkCheckpoint {

}

class NetworkCheckpoint constructor(var context: Context) : InterfaceNetworkCheckpoint {

    fun networkConnection() : Boolean {

        var networkAvailable = false

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (connectivityManager != null) {

            val activeNetwork: Network? = connectivityManager.activeNetwork
            val networkCapabilities: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(activeNetwork)

            if (networkCapabilities != null) {

                when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {

                        networkAvailable = true

                    }
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {

                        networkAvailable = true

                    }
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {

                        networkAvailable = true

                    }
                }

            }

        }

        return networkAvailable
    }

    fun networkConnectionVpn() : Boolean {

        var networkAvailable = false

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (connectivityManager != null) {

            val activeNetwork: Network? = connectivityManager.activeNetwork
            val networkCapabilities: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(activeNetwork)

            if (networkCapabilities != null) {

                if (networkConnection()
                    && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {

                    networkAvailable = true

                }

            }

        }

        return networkAvailable
    }

}