/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/12/21, 6:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.DevelopersConfigurations.NetworkConnection.DeveloperDataInterface
import co.geeksempire.premium.storefront.DevelopersConfigurations.NetworkConnection.RetrieveDeveloperInformation
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import co.geeksempire.premium.storefront.databinding.EntryConfigurationsLayoutBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EntryConfigurations : AppCompatActivity() {

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    lateinit var entryConfigurationsLayoutBinding: EntryConfigurationsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryConfigurationsLayoutBinding = EntryConfigurationsLayoutBinding.inflate(layoutInflater)
        setContentView(entryConfigurationsLayoutBinding.root)

        if (networkCheckpoint.networkConnection()) {

            RetrieveDeveloperInformation("Geeks Empire").start(object : DeveloperDataInterface {})

            lifecycleScope.launch {

                (application as PremiumStorefrontApplication).entryPreferences.entryType().collect {
//                    when (it) {
//                        EntryPreferences.EntryStorefrontApplications -> {
//
//                            startActivity(Intent().apply {
//                                setClass(applicationContext, StorefrontApplications::class.java)
//                            }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())
//
//                            this@EntryConfigurations.finish()
//
//                        }
//                        EntryPreferences.EntryStorefrontGames -> {
//
//                            startActivity(Intent().apply {
//                                setClass(applicationContext, StorefrontGames::class.java)
//                            }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())
//
//                            this@EntryConfigurations.finish()
//
//                        } else -> {
//
//                            startActivity(Intent().apply {
//                                setClass(applicationContext, StorefrontApplications::class.java)
//                            }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())
//
//                            this@EntryConfigurations.finish()
//
//                        }
//                    }
                }

            }

        } else {

            SnackbarBuilder(applicationContext).show (
                rootView = entryConfigurationsLayoutBinding.rootView,
                messageText= getString(R.string.noNetworkConnection),
                messageDuration = Snackbar.LENGTH_INDEFINITE,
                actionButtonText = R.string.retryText,
                snackbarActionHandlerInterface = object : SnackbarActionHandlerInterface {

                    override fun onActionButtonClicked(snackbar: Snackbar) {
                        super.onActionButtonClicked(snackbar)

                        snackbar.dismiss()

                        startActivity(
                            Intent(Settings.ACTION_WIFI_SETTINGS)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                        this@EntryConfigurations.finish()

                    }

                }
            )

        }


    }

}