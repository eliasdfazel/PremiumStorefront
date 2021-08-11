/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 2:01 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSplitActivity
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.Extensions.setupMoviesDetailsUserInterface
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter.MovieDetailsPagerAdapter
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataKey
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

    var movieGenre: String = ""
    var movieId: String = ""

    lateinit var moviesDetailsLayoutBinding: MoviesDetailsLayoutBinding

    companion object {

        fun openMoviesDetails(context: Context,
                              movieGenre: String, movieId: String) {

            context.startActivity(Intent(context, MoviesDetails::class.java).apply {

                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }, ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, 0).toBundle())

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

                movieDetailsPagerAdapter.themeType = it

            }

        }

        moviesDetailsLayoutBinding.moviesViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        moviesDetailsLayoutBinding.moviesViewPager.adapter = movieDetailsPagerAdapter

        moviesDetailsLayoutBinding.moviesViewPager.setPageTransformer { page, position ->

            val width = page.width
            val height = page.height

            val rotation = -13f * position * -1.25f

            page.pivotX = (width * 1.7f)
            page.pivotY = height.toFloat()

            page.rotation = rotation

        }

        if (intent.hasExtra(MoviesDataKey.MovieId) && intent.hasExtra(MoviesDataKey.MovieGenres)) {

            movieGenre = intent.getStringExtra(MoviesDataKey.MovieGenres)?:"Comedy"
            movieId = intent.getStringExtra(MoviesDataKey.MovieId)?:"3889"



        } else {

            this@MoviesDetails.finish()

        }

    }

    override fun onStart() {
        super.onStart()

        moviesDetailsLayoutBinding.goBackView.setOnClickListener {

            overridePendingTransition(0, R.anim.slide_out_right)
            this@MoviesDetails.finish()

        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {

        overridePendingTransition(0, R.anim.slide_out_right)
        this@MoviesDetails.finish()

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