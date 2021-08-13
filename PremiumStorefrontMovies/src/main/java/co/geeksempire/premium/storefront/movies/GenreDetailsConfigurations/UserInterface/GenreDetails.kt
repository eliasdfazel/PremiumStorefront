/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/13/21, 6:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.UserInterface

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSplitActivity
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.databinding.GenreDetailsLayoutBinding
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class GenreDetails : StorefrontSplitActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@GenreDetails)
    }

    lateinit var genreDetailsLayoutBinding: GenreDetailsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreDetailsLayoutBinding = GenreDetailsLayoutBinding.inflate(layoutInflater)
        setContentView(genreDetailsLayoutBinding.root)

        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {



            }

        }

        genreDetailsLayoutBinding.root.post {



        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {

        overridePendingTransition(0, R.anim.fade_out)
        this@GenreDetails.finish()

    }

    override fun networkAvailable() {
        Log.d(this@GenreDetails.javaClass.simpleName, "Network Available @ ${this@GenreDetails.javaClass.simpleName}")


    }

    override fun networkLost() {
        Log.d(this@GenreDetails.javaClass.simpleName, "No Network @ ${this@GenreDetails.javaClass.simpleName}")


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

            openPlayStoreToInstallApplications(context = applicationContext,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

    }

}