/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/12/21, 9:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import co.geeksempire.premium.storefront.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSplitActivity
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.movies.Actions.Operation.ActionCenterOperationsMovies
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.Extensions.setupMoviesDetailsUserInterface
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter.MovieDetailsPagerAdapter
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataKey
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkEndpoints.MoviesQueryEndpoints
import co.geeksempire.premium.storefront.movies.databinding.MoviesDetailsLayoutBinding
import com.google.firebase.firestore.Source
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

    val prepareActionCenterUserInterface: PrepareActionCenterUserInterface by lazy {
        PrepareActionCenterUserInterface(context = applicationContext, actionCenterView = moviesDetailsLayoutBinding.actionCenterView, actionLeftView = moviesDetailsLayoutBinding.leftActionView, actionMiddleView = moviesDetailsLayoutBinding.middleActionView, actionRightView = moviesDetailsLayoutBinding.rightActionView)
    }

    val actionCenterOperationsMovies: ActionCenterOperationsMovies by lazy {
        ActionCenterOperationsMovies()
    }

    val generalEndpoints = GeneralEndpoints()

    val moviesQueryEndpoints: MoviesQueryEndpoints by lazy {
        MoviesQueryEndpoints(generalEndpoints)
    }

    val moviesStorefrontLiveData: MoviesStorefrontLiveData by lazy {
        ViewModelProvider(this@MoviesDetails).get(MoviesStorefrontLiveData::class.java)
    }

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@MoviesDetails, moviesDetailsLayoutBinding.rootView, networkCheckpoint)
    }

    var moviePrimaryGenre: String = ""
    var movieProductId: String = ""

    var movieSelectedPosition: Int = 0

    lateinit var moviesDetailsLayoutBinding: MoviesDetailsLayoutBinding

    companion object {

        fun openMoviesDetails(context: Context,
                              moviePrimaryGenre: String, movieProductId: String) {

            context.startActivity(Intent(context, MoviesDetails::class.java).apply {
                putExtra(MoviesDataKey.MoviePrimaryGenre, moviePrimaryGenre)
                putExtra(MoviesDataKey.MovieProductId, movieProductId)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }, ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, 0).toBundle())

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

            val rotation = -11f * position * -1.13f

            page.pivotX = (width * 0.7f)
            page.pivotY = height.toFloat()

            page.rotation = rotation

        }

        if (intent.hasExtra(MoviesDataKey.MovieProductId) && intent.hasExtra(MoviesDataKey.MoviePrimaryGenre)) {

            moviePrimaryGenre = intent.getStringExtra(MoviesDataKey.MoviePrimaryGenre)?:"Comedy"
            movieProductId = intent.getStringExtra(MoviesDataKey.MovieProductId)?:"3889"

            moviesStorefrontLiveData.allMoviesOfGenre.observe(this@MoviesDetails, {

                if (it.isNotEmpty()) {

                    movieDetailsPagerAdapter.moviesDetailsList.addAll(it)

                    movieDetailsPagerAdapter.notifyItemRangeInserted(movieDetailsPagerAdapter.itemCount, movieDetailsPagerAdapter.moviesDetailsList.size)

                }

            })

            (application as PremiumStorefrontApplication)
                .firestoreDatabase
                .document(moviesQueryEndpoints.storefrontSpecificMovieEndpoint(moviePrimaryGenre, movieProductId))
                .get(Source.CACHE).addOnSuccessListener { documentSnapshot ->

                    documentSnapshot.data?.let {

                        movieDetailsPagerAdapter.moviesDetailsList.clear()

                        movieDetailsPagerAdapter.moviesDetailsList.add(documentSnapshot)

                        movieDetailsPagerAdapter.notifyItemInserted(0)

                        val moviesDataStructure = MoviesDataStructure(it)

                        actionCenterOperationsMovies.setupForMoviesDetails(this@MoviesDetails, movieProductId, moviesDataStructure.movieName(), moviesDataStructure.movieSummary())

                        (application as PremiumStorefrontApplication)
                            .firestoreDatabase
                            .collection(moviesQueryEndpoints.storefrontMoviesGenreCollectionsEndpoint(moviePrimaryGenre))
                            .get(Source.CACHE).addOnSuccessListener { querySnapshot ->

                                moviesStorefrontLiveData.processAllMoviesOfGenre(querySnapshot, movieProductId)

                            }.addOnFailureListener {



                            }

                    }

                }.addOnFailureListener {



                }

            moviesDetailsLayoutBinding.moviesViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    movieSelectedPosition = position

                    moviesDetailsLayoutBinding.favoriteView.setOnClickListener {

                        if (movieDetailsPagerAdapter.moviesDetailsList.isNotEmpty()) {

                            movieDetailsPagerAdapter.moviesDetailsList[position].data?.let {

                                val moviesDataStructure = MoviesDataStructure(it)



                            }

                        }

                    }

                }

            })

        } else {

            this@MoviesDetails.finish()

        }

    }

    override fun onStart() {
        super.onStart()

        moviesDetailsLayoutBinding.goBackView.setOnClickListener {

            overridePendingTransition(0, R.anim.fade_out)
            this@MoviesDetails.finish()

        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {

        overridePendingTransition(0, R.anim.fade_out)
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