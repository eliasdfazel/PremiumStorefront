/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/18/21, 5:23 AM
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
import co.geeksempire.premium.storefront.AccountManager.UserInterface.AccountInformation
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoriteProductQueryInterface
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoritedProcess
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontDynamicActivity
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import co.geeksempire.premium.storefront.Utils.Operations.NavigationListener
import co.geeksempire.premium.storefront.Utils.Operations.NavigationOperations
import co.geeksempire.premium.storefront.Utils.UI.Animations.ShadowAnimation
import co.geeksempire.premium.storefront.movies.Actions.Operation.ActionCenterOperationsMovies
import co.geeksempire.premium.storefront.movies.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.Extensions.setupMoviesDetailsUserInterface
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter.MovieDetailsPagerAdapter
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataKey
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkEndpoints.MoviesQueryEndpoints
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations.retrieveGenreMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.GenreContent.GenreData
import co.geeksempire.premium.storefront.movies.databinding.MoviesDetailsLayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Source
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

fun openMoviesDetails(context: Context,
                      moviePrimaryGenre: String, movieProductId: String) {

    context.startActivity(Intent(context, MoviesDetails::class.java).apply {
        putExtra(MoviesDataKey.MoviePrimaryGenre, moviePrimaryGenre)
        putExtra(MoviesDataKey.MovieProductId, movieProductId)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }, ActivityOptions.makeCustomAnimation(context, co.geeksempire.premium.storefront.movies.R.anim.fade_in_movie, 0).toBundle())

}

class MoviesDetails : StorefrontDynamicActivity(), NavigationListener {

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

    val genresData: GenreData = GenreData()

    val shadowAnimationUtils = ShadowAnimation()

    val favoritedProcess: FavoritedProcess by lazy {
        FavoritedProcess(this@MoviesDetails)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesDetailsLayoutBinding = MoviesDetailsLayoutBinding.inflate(layoutInflater)
        setContentView(moviesDetailsLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@MoviesDetails

        moviesDetailsLayoutBinding.moviesViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        moviesDetailsLayoutBinding.moviesViewPager.adapter = movieDetailsPagerAdapter

        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {

                setupMoviesDetailsUserInterface(it)

                movieDetailsPagerAdapter.themeType = it

            }

        }

        moviesDetailsLayoutBinding.moviesViewPager.setPageTransformer { page, position ->

            page.apply {

                val width = width
                val height = height

                val rotationAmount = (-11f) * (position) * (-1.13f)

                pivotX = (width * 0.7f)
                pivotY = height.toFloat()

                scaleX = 0.975f
                scaleY = 0.975f

                rotation = rotationAmount

                val minimumScale = 0.93f

                when {
                    position < -1 -> {

                        alpha = 0f

                    }
                    position <= 1 -> {

                        val scaleFactor = minimumScale.coerceAtLeast(1 - abs(position))

                        val verticalMargin = height * (1 - scaleFactor) / 2
                        val horizontalMargin = width * (1 - scaleFactor) / 2

                        translationX = if (position < 0) {
                            horizontalMargin - verticalMargin / 2
                        } else {
                            horizontalMargin + verticalMargin / 2
                        }

                        scaleX = scaleFactor
                        scaleY = scaleFactor

                    }
                    else -> {

                        alpha = 0f

                    }
                }
            }


        }

        if (intent.hasExtra(MoviesDataKey.MovieProductId) && intent.hasExtra(MoviesDataKey.MoviePrimaryGenre)) {

            moviePrimaryGenre = intent.getStringExtra(MoviesDataKey.MoviePrimaryGenre)?:"Comedy"
            movieProductId = intent.getStringExtra(MoviesDataKey.MovieProductId)?:"3889"

            moviesStorefrontLiveData.allMoviesOfGenre.observe(this@MoviesDetails, {

                if (it.isNotEmpty()) {

                    movieDetailsPagerAdapter.moviesDetailsList.addAll(it)

                    movieDetailsPagerAdapter.notifyItemRangeInserted(movieDetailsPagerAdapter.itemCount, it.size)

                }

            })

            moviesStorefrontLiveData.genresMoviesItemData.observe(this@MoviesDetails, {

                if (it.isNotEmpty()) {

                    lifecycleScope.launch {

                        genresData.clearData()
                        genresData.prepareAllGenresData(it).await()

                        withContext(Dispatchers.Main) {

                            movieDetailsPagerAdapter.notifyItemRangeChanged(0, movieDetailsPagerAdapter.itemCount)

                        }

                    }

                } else {



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

                        actionCenterOperationsMovies.setupForMoviesDetails(this@MoviesDetails, moviesDataStructure.movieId(), moviesDataStructure.movieName(), moviesDataStructure.movieSummary())

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

                    movieDetailsPagerAdapter.moviesDetailsList[position].data?.let {

                        val moviesDataStructure = MoviesDataStructure(it)

                        Firebase.auth.currentUser?.let { firebaseUser ->

                            favoritedProcess.isProductFavorited(firebaseUser.uid, firebaseUser.email!!, moviesDataStructure.movieProductId(),
                                object : FavoriteProductQueryInterface {

                                    override fun favoriteProduct(isProductFavorited: Boolean) {
                                        super.favoriteProduct(isProductFavorited)

                                        if (isProductFavorited) {

                                            moviesDetailsLayoutBinding.favoriteView.setImageDrawable(getDrawable(R.drawable.favorited_icon_movie))

                                        }

                                    }

                                })

                        }

                        moviesDetailsLayoutBinding.favoriteView.setOnClickListener {

                            if (movieDetailsPagerAdapter.moviesDetailsList.isNotEmpty()) {

                                movieDetailsPagerAdapter.moviesDetailsList[position].data?.let {

                                    startFavoriteProcess(moviesDataStructure.movieProductId(), moviesDataStructure.movieName(), moviesDataStructure.movieDescription(), moviesDataStructure.moviePoster(), EntryPreferences.EntryStorefrontMovies)

                                }

                            }

                        }

                        actionCenterOperationsMovies.setupForMoviesDetails(this@MoviesDetails, moviesDataStructure.movieId(), moviesDataStructure.movieName(), moviesDataStructure.movieSummary())

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

            overridePendingTransition(0, R.anim.fade_out_movie)
            this@MoviesDetails.finish()

        }

        NavigationOperations(this@MoviesDetails)
            .listenBackPressed(this@MoviesDetails)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun backNavigation() {

        overridePendingTransition(0, R.anim.fade_out_movie)
        this@MoviesDetails.finish()

    }

    override fun networkAvailable() {

        retrieveGenreMovies(this@MoviesDetails, moviesStorefrontLiveData)

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

    fun startFavoriteProcess(productId: String, productName: String, productDescription: String, productIcon: String, favoriteType: String = EntryPreferences.EntryStorefrontMovies) {

        if (Firebase.auth.currentUser != null) {

            favoritedProcess.isProductFavorited(Firebase.auth.currentUser!!.uid, Firebase.auth.currentUser!!.email!!, productId,
                object : FavoriteProductQueryInterface {

                    override fun favoriteProduct(isProductFavorited: Boolean) {
                        super.favoriteProduct(isProductFavorited)

                        if (isProductFavorited) {

                            if (networkCheckpoint.networkConnection()) {

                                favoritedProcess.remove(userUniqueIdentifier = Firebase.auth.currentUser!!.uid, userEmailAddress = Firebase.auth.currentUser!!.uid , productId)

                                moviesDetailsLayoutBinding.favoriteView.setImageDrawable(applicationContext.getDrawable(R.drawable.favorite_icon_movie))

                            } else {

                                SnackbarBuilder(applicationContext).show (
                                    rootView = moviesDetailsLayoutBinding.rootView,
                                    messageText= getString(R.string.noNetworkConnection),
                                    messageDuration = Snackbar.LENGTH_INDEFINITE,
                                    actionButtonText = R.string.retryText,
                                    snackbarActionHandlerInterface = object :
                                        SnackbarActionHandlerInterface {

                                        override fun onActionButtonClicked(snackbar: Snackbar) {
                                            super.onActionButtonClicked(snackbar)

                                            if (networkCheckpoint.networkConnection()) {

                                                favoritedProcess.remove(userUniqueIdentifier = Firebase.auth.currentUser!!.uid, userEmailAddress = Firebase.auth.currentUser!!.uid, productId)

                                                moviesDetailsLayoutBinding.favoriteView.setImageDrawable(getDrawable(R.drawable.favorite_icon_movie))

                                                snackbar.dismiss()

                                            } else {



                                            }

                                        }

                                    }
                                )

                            }

                        } else {

                            if (networkCheckpoint.networkConnection()) {

                                favoritedProcess.add(userUniqueIdentifier = Firebase.auth.currentUser!!.uid,
                                    userEmailAddress = Firebase.auth.currentUser!!.email!!,
                                    productIdToFavorite = productId,
                                    productName = productName,
                                    productDescription = productDescription,
                                    productIcon = productIcon,
                                    productType = favoriteType)

                                moviesDetailsLayoutBinding.favoriteView.setImageDrawable(getDrawable(R.drawable.favorited_icon_movie))

                            } else {

                                SnackbarBuilder(applicationContext).show (
                                    rootView = moviesDetailsLayoutBinding.rootView,
                                    messageText= getString(R.string.noNetworkConnection),
                                    messageDuration = Snackbar.LENGTH_INDEFINITE,
                                    actionButtonText = R.string.retryText,
                                    snackbarActionHandlerInterface = object : SnackbarActionHandlerInterface {

                                        override fun onActionButtonClicked(snackbar: Snackbar) {
                                            super.onActionButtonClicked(snackbar)

                                            if (networkCheckpoint.networkConnection()) {

                                                favoritedProcess.add(userUniqueIdentifier = Firebase.auth.currentUser!!.uid,
                                                    userEmailAddress = Firebase.auth.currentUser!!.email!!,
                                                    productIdToFavorite = productId,
                                                    productName = productName,
                                                    productDescription = productDescription,
                                                    productIcon = productIcon,
                                                    productType = favoriteType)

                                                moviesDetailsLayoutBinding.favoriteView.setImageDrawable(getDrawable(R.drawable.favorited_icon_movie))

                                                snackbar.dismiss()

                                            } else {



                                            }

                                        }

                                    }
                                )

                            }

                        }

                    }

                })


        } else {

            startActivity(Intent(applicationContext, AccountInformation::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_in_right_movie, 0).toBundle())

        }

    }

}