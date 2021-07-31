/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/31/21, 10:18 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSplitActivity
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.Extensions.setupStorefrontMoviesUserInterface
import co.geeksempire.premium.storefront.movies.databinding.StorefrontMoviesLayoutBinding
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class StorefrontMovies : StorefrontSplitActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@StorefrontMovies)
    }

    val prepareActionCenterUserInterface: PrepareActionCenterUserInterface by lazy {
        PrepareActionCenterUserInterface(context = applicationContext, actionCenterView = storefrontMoviesLayoutBinding.actionCenterView, actionLeftView = storefrontMoviesLayoutBinding.leftActionView, actionMiddleView = storefrontMoviesLayoutBinding.middleActionView, actionRightView = storefrontMoviesLayoutBinding.rightActionView)
    }

    lateinit var storefrontMoviesLayoutBinding: StorefrontMoviesLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storefrontMoviesLayoutBinding = StorefrontMoviesLayoutBinding.inflate(layoutInflater)
        setContentView(storefrontMoviesLayoutBinding.root)

        storefrontMoviesLayoutBinding.root.post {

            lifecycleScope.launch {

                themePreferences.checkThemeLightDark().collect {

                    setupStorefrontMoviesUserInterface(it)

                }

            }
        }

    }

    override fun onPause() {
        super.onPause()

        lifecycleScope.launch {

            (this@StorefrontMovies.application as PremiumStorefrontApplication).entryPreferences.entryType(EntryPreferences.EntryStorefrontMovies)

        }

    }

    override fun onBackPressed() {

        startActivity(Intent(Intent.ACTION_MAIN).apply {
            this.addCategory(Intent.CATEGORY_HOME)
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, 0).toBundle())

    }

    override fun networkAvailable() {


    }

    override fun networkLost() {


    }

    override fun messageClicked(inAppMessage: InAppMessage, action: Action) {

        val actionUrl = action.actionUrl

        val dataMessage: HashMap<String, String> = inAppMessage.data as HashMap<String, String>

        if (dataMessage[ProductDataKey.ProductPackageName] != null &&
            dataMessage[ProductDataKey.ProductName] != null &&
            dataMessage[ProductDataKey.ProductSummary] != null) {

            val applicationPackageName = dataMessage[ProductDataKey.ProductPackageName]!!
            val applicationName = dataMessage[ProductDataKey.ProductName]!!
            val applicationSummary = dataMessage[ProductDataKey.ProductSummary]!!

            openPlayStoreToInstall(context = applicationContext,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

    }

}