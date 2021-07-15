/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/15/21, 8:26 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.DevelopersConfigurations.DataStructure.DevelopersDataKey
import co.geeksempire.premium.storefront.DevelopersConfigurations.NetworkConnection.DeveloperDataInterface
import co.geeksempire.premium.storefront.DevelopersConfigurations.NetworkConnection.RetrieveDeveloperInformation
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.DeveloperIntroductionPage
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.StorefrontApplications
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForGamesConfigurations.UserInterface.StorefrontGames
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

            lifecycleScope.launch {

                (application as PremiumStorefrontApplication).entryPreferences.entryType().collect {
                    when (it) {
                        EntryPreferences.EntryStorefrontApplications -> {

//                            startActivity(Intent().apply {
//                                setClass(applicationContext, StorefrontApplications::class.java)
//                            }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())
//
//                            this@EntryConfigurations.finish()

                            val productDeveloper = "Geeks Empire"
                            productDeveloper.let { developerName ->

                                RetrieveDeveloperInformation(developerName)
                                    .start(object : DeveloperDataInterface {

                                        override fun developerInformation(developerData: HashMap<String, String>) {
                                            super.developerInformation(developerData)

                                            val developerPageIntent = Intent(applicationContext, DeveloperIntroductionPage::class.java).apply {

                                                putExtra(DevelopersDataKey.DeveloperName, developerData[DevelopersDataKey.DeveloperName])
                                                putExtra(DevelopersDataKey.DeveloperDescription, developerData[DevelopersDataKey.DeveloperDescription])

                                                putExtra(DevelopersDataKey.DeveloperLogo, developerData[DevelopersDataKey.DeveloperLogo])
                                                putExtra(DevelopersDataKey.DeveloperCoverImage, developerData[DevelopersDataKey.DeveloperCoverImage])

                                                putExtra(DevelopersDataKey.DeveloperCountry, developerData[DevelopersDataKey.DeveloperCountry])
                                                putExtra(DevelopersDataKey.DeveloperCountryFlag, developerData[DevelopersDataKey.DeveloperCountryFlag])

                                                putExtra(DevelopersDataKey.DeveloperEmail, developerData[DevelopersDataKey.DeveloperEmail])
                                                putExtra(DevelopersDataKey.DeveloperWebsite, developerData[DevelopersDataKey.DeveloperWebsite])

                                                putExtra(DevelopersDataKey.DeveloperSocialMediaIcon, developerData[DevelopersDataKey.DeveloperSocialMediaIcon])
                                                putExtra(DevelopersDataKey.DeveloperSocialMediaLink, developerData[DevelopersDataKey.DeveloperSocialMediaLink])

                                                developerData[DevelopersDataKey.DeveloperApplications]?.let {
                                                    putExtra(DevelopersDataKey.DeveloperApplications, it)
                                                }
                                                developerData[DevelopersDataKey.DeveloperGames]?.let {
                                                    putExtra(DevelopersDataKey.DeveloperGames, it)
                                                }
                                                developerData[DevelopersDataKey.DeveloperBooks]?.let {
                                                    putExtra(DevelopersDataKey.DeveloperBooks, it)
                                                }
                                                developerData[DevelopersDataKey.DeveloperMovies]?.let {
                                                    putExtra(DevelopersDataKey.DeveloperMovies, it)
                                                }

                                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                            }

                                            startActivity(developerPageIntent,
                                                ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_from_right, 0).toBundle())

                                        }

                                    })

                            }

                        }
                        EntryPreferences.EntryStorefrontGames -> {

                            startActivity(Intent().apply {
                                setClass(applicationContext, StorefrontGames::class.java)
                            }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

                            this@EntryConfigurations.finish()

                        } else -> {

                            startActivity(Intent().apply {
                                setClass(applicationContext, StorefrontApplications::class.java)
                            }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

                            this@EntryConfigurations.finish()

                        }
                    }
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