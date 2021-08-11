/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 10:06 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSplitActivity
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.Extensions.setupMoviesDetailsUserInterface
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter.MovieDetailsPagerAdapter
import co.geeksempire.premium.storefront.movies.databinding.MoviesDetailsLayoutBinding
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class MoviesDetails : StorefrontSplitActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@MoviesDetails)
    }

    val movieDetailsPagerAdapter: MovieDetailsPagerAdapter by lazy {
        MovieDetailsPagerAdapter(this@MoviesDetails)
    }

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@MoviesDetails, moviesDetailsLayoutBinding.rootView, networkCheckpoint)
    }

    lateinit var moviesDetailsLayoutBinding: MoviesDetailsLayoutBinding

    companion object {

        fun openMoviesDetails(context: Context) {

            context.startActivity(Intent(context, MoviesDetails::class.java).apply {

                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesDetailsLayoutBinding = MoviesDetailsLayoutBinding.inflate(layoutInflater)
        setContentView(moviesDetailsLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@MoviesDetails

        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {

                setupMoviesDetailsUserInterface(it)

            }

        }

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

            openPlayStoreToInstallApplications(context = applicationContext,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

    }
}