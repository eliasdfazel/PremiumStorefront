/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/24/21, 7:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.NetworkConnections

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.databinding.OfflineIndicatorBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

interface NetworkConnectionListenerInterface {
    fun networkAvailable()
    fun networkLost()
}

class NetworkConnectionListener constructor (private var appCompatActivity: AppCompatActivity,
                                             private var rootView: ConstraintLayout,
                                             private var networkCheckpoint: NetworkCheckpoint) :  ConnectivityManager.NetworkCallback() {

    var networkConnectionListenerInterface: NetworkConnectionListenerInterface? = null

    private val connectivityManager = appCompatActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var offlineIndicator: OfflineIndicatorBinding

    init {
        connectivityManager.registerDefaultNetworkCallback(this@NetworkConnectionListener)

        offlineIndicator = OfflineIndicatorBinding.inflate(appCompatActivity.layoutInflater, rootView, false)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)

        if (!appCompatActivity.isFinishing) {
            appCompatActivity.runOnUiThread {

                networkConnectionListenerInterface?.let {
                    it.networkAvailable()
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    if (networkCheckpoint.networkConnection()) {
                        Log.d(this@NetworkConnectionListener.javaClass.simpleName, "Network Available")

                        try { rootView.removeView(offlineIndicator.root) } catch (e: Exception) { e.printStackTrace() }

                    }
                }, 555)
            }
        }

    }

    override fun onLost(network: Network) {
        super.onLost(network)

        if (!appCompatActivity.isFinishing) {
            appCompatActivity.runOnUiThread {

                networkConnectionListenerInterface?.let {
                    it.networkLost()
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    if (!networkCheckpoint.networkConnection()) {
                        Log.d(this@NetworkConnectionListener.javaClass.simpleName, "Network Lost")

                        try { rootView.addView(offlineIndicator.root) } catch (e: Exception){ e.printStackTrace() }

                        Handler(Looper.getMainLooper()).postDelayed(Runnable {
                            Glide.with(appCompatActivity)
                                .asGif()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(R.drawable.no_internet_connection)
                                .into(offlineIndicator.offlineWait)

                            offlineIndicator.offlineWait.setOnClickListener {

                                appCompatActivity.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                                appCompatActivity.finish()
                            }

                        }, 555)

                    }
                }, 555)
            }
        }

    }

    fun unregisterDefaultNetworkCallback() {

        connectivityManager.unregisterNetworkCallback(this@NetworkConnectionListener)
    }
}