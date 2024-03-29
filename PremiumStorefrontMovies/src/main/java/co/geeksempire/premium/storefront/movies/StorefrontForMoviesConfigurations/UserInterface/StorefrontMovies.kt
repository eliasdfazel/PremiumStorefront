/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/22/21, 8:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.geeksempire.layoutmanager.Curve.CurveLayoutManager
import co.geeksempire.geeksempire.layoutmanager.Curve.FanLayoutManagerSettings
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountData
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountSignIn
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.CompleteSearch
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoriteProductQueryInterface
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoritedProcess
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontDynamicActivity
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForGamesConfigurations.UserInterface.StorefrontGames
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.IO.IO
import co.geeksempire.premium.storefront.Utils.IO.UpdatingDataIO
import co.geeksempire.premium.storefront.Utils.InApplicationUpdate.InApplicationUpdateProcess
import co.geeksempire.premium.storefront.Utils.InApplicationUpdate.UpdateResponse
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.Notifications.RemoteSubscriptions
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import co.geeksempire.premium.storefront.Utils.Notifications.SubscriptionInterface
import co.geeksempire.premium.storefront.Utils.Operations.NavigationListener
import co.geeksempire.premium.storefront.Utils.Operations.NavigationOperations
import co.geeksempire.premium.storefront.Utils.PopupShortcuts.PopupShortcutsCreator
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import co.geeksempire.premium.storefront.Utils.UI.Gesture.GestureConstants
import co.geeksempire.premium.storefront.Utils.UI.Gesture.GestureListenerConstants
import co.geeksempire.premium.storefront.Utils.UI.Gesture.SwipeGestureListener
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutGrid
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import co.geeksempire.premium.storefront.movies.Actions.Operation.ActionCenterOperationsMovies
import co.geeksempire.premium.storefront.movies.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.Extensions.gamesSectionSwitcherDesign
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.Extensions.setupStorefrontMoviesUserInterface
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.Extensions.storefrontMoviesUserInteractionSetup
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter.FilterAllMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter.FilteringOptions
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.FilterAdapter.FilterOptionsAdapter
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesSearching.SearchingMoviesSetup
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations.retrieveAllMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations.retrieveFeaturedMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations.retrieveGenreMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations.retrieveNewContent
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.AllMovies.Adapter.AllMoviesAdapter
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.FeaturedMovies.Adapter.FeaturedMoviesAdapter
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.GenreContent.Adapter.GenresAdapter
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.GenreContent.GenreData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.NewMovies.Adapter.NewMoviesAdapter
import co.geeksempire.premium.storefront.movies.databinding.StorefrontMoviesLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.launch
import net.geeksempire.balloon.optionsmenu.library.BalloonOptionsMenu
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger
import net.geeksempire.balloon.optionsmenu.library.Utils.percentageOfDisplayX
import java.util.*

class StorefrontMovies : StorefrontDynamicActivity(), NavigationListener {

    val updatingDataIO: UpdatingDataIO by lazy {
        UpdatingDataIO(applicationContext)
    }

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@StorefrontMovies)
    }

    val generalEndpoints: GeneralEndpoints = GeneralEndpoints()

    val moviesStorefrontLiveData: MoviesStorefrontLiveData by lazy {
        ViewModelProvider(this@StorefrontMovies).get(MoviesStorefrontLiveData::class.java)
    }

    val prepareActionCenterUserInterface: PrepareActionCenterUserInterface by lazy {
        PrepareActionCenterUserInterface(context = applicationContext, actionCenterView = storefrontMoviesLayoutBinding.actionCenterView, actionLeftView = storefrontMoviesLayoutBinding.leftActionView, actionMiddleView = storefrontMoviesLayoutBinding.middleActionView, actionRightView = storefrontMoviesLayoutBinding.rightActionView)
    }

    val actionCenterOperationsMovies: ActionCenterOperationsMovies by lazy {
        ActionCenterOperationsMovies()
    }

    val filterAllMovies: FilterAllMovies by lazy {
        FilterAllMovies(moviesStorefrontLiveData)
    }

    val balloonOptionsMenu: BalloonOptionsMenu by lazy {
        BalloonOptionsMenu(context = this@StorefrontMovies,
            rootView = storefrontMoviesLayoutBinding.rootView)
    }

    val favoritedProcess: FavoritedProcess by lazy {
        FavoritedProcess(this@StorefrontMovies)
    }

    val allMoviesAdapter: AllMoviesAdapter by lazy {
        AllMoviesAdapter(this@StorefrontMovies)
    }

    val featuredMoviesAdapter: FeaturedMoviesAdapter by lazy {
        FeaturedMoviesAdapter(this@StorefrontMovies)
    }

    val newMoviesAdapter: NewMoviesAdapter by lazy {
        NewMoviesAdapter(context = this@StorefrontMovies)
    }

    val genresAdapter: GenresAdapter by lazy {
        GenresAdapter(context = this@StorefrontMovies,
            filterAllMovies = filterAllMovies,
            allFilteredContentItemData = moviesStorefrontLiveData.allFilteredMoviesItemData,
            storefrontAllUnfilteredContents = storefrontAllUnfilteredContents,
            storefrontAllUntouchedContents = storefrontAllUntouchedContents,
            genreIndicatorTextView = storefrontMoviesLayoutBinding.genreIndicatorTextView,
            categoriesRecyclerView = storefrontMoviesLayoutBinding.genresRecyclerView,
            balloonOptionsMenu = balloonOptionsMenu)
    }

    val genresData: GenreData = GenreData()

    val searchingMoviesSetup: SearchingMoviesSetup by lazy {
        SearchingMoviesSetup(this@StorefrontMovies)
    }

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@StorefrontMovies, storefrontMoviesLayoutBinding.rootView, networkCheckpoint)
    }

    val filterOptionsAdapter: FilterOptionsAdapter by lazy {
        FilterOptionsAdapter(this@StorefrontMovies,
            filterAllMovies = filterAllMovies,
            storefrontAllUnfilteredContents = storefrontAllUnfilteredContents,
            moviesFilteringLayoutBinding = storefrontMoviesLayoutBinding.moviesFilteringInclude,
            filterOptionsType = FilteringOptions.FilterByDirector)
    }

    val storefrontAllUntouchedContents: ArrayList<DocumentSnapshot> = ArrayList<DocumentSnapshot>()
    val storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot> = ArrayList<DocumentSnapshot>()

    val firebaseRemoteConfiguration = Firebase.remoteConfig

    private val swipeGestureListener: SwipeGestureListener by lazy {
        SwipeGestureListener(applicationContext, this@StorefrontMovies)
    }

    /* Start - Sign In */
    val accountSignIn: AccountSignIn by lazy {
        AccountSignIn(this@StorefrontMovies, this@StorefrontMovies)
    }

    val accountSelector: ActivityResultLauncher<Any?> = registerForActivityResult(accountSignIn.createProcess()) {



    }
    /* End - Sign In */

    val firebaseAuthentication = Firebase.auth
    var firebaseUser = firebaseAuthentication.currentUser

    lateinit var storefrontMoviesLayoutBinding: StorefrontMoviesLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storefrontMoviesLayoutBinding = StorefrontMoviesLayoutBinding.inflate(layoutInflater)
        setContentView(storefrontMoviesLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@StorefrontMovies

        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {

                storefrontMoviesUserInteractionSetup(context = this@StorefrontMovies, firebaseUser = firebaseUser, accountSelector = accountSelector,
                    profileView = storefrontMoviesLayoutBinding.profileView, preferencesView = storefrontMoviesLayoutBinding.preferencesView, favoritesView = storefrontMoviesLayoutBinding.favoritesView,
                    moviesSectionsSwitcherLayoutBinding = storefrontMoviesLayoutBinding.moviesSectionsSwitcherContainer,
                    themeType = it)

                setupStorefrontMoviesUserInterface(it)

                actionCenterOperationsMovies.setupForMoviesStorefront(this@StorefrontMovies, it)

            }

        }

        storefrontMoviesLayoutBinding.root.post {

            val featuredMoviesLayoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.HORIZONTAL, false)
            storefrontMoviesLayoutBinding.featuredContentRecyclerView.layoutManager = featuredMoviesLayoutManager
            storefrontMoviesLayoutBinding.featuredContentRecyclerView.adapter = featuredMoviesAdapter

            val curveLayoutManager = CurveLayoutManager(applicationContext,
                FanLayoutManagerSettings.newBuilder(applicationContext).apply {
                    withFanRadius(true)
                    withSelectedAnimation(false)
                    withViewWidthDp(279f)
                    withViewHeightDp(439f)
                }.build())
            storefrontMoviesLayoutBinding.newContentRecyclerView.layoutManager = curveLayoutManager
            storefrontMoviesLayoutBinding.newContentRecyclerView.adapter = newMoviesAdapter

            storefrontMoviesLayoutBinding.genresRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.VERTICAL, false)
            storefrontMoviesLayoutBinding.genresRecyclerView.adapter = genresAdapter

            storefrontMoviesLayoutBinding.allContentRecyclerView.layoutManager = RecycleViewSmoothLayoutGrid(applicationContext, columnCount(applicationContext, percentageOfDisplayX(applicationContext, 87f), 159, dpToInteger(applicationContext, 19).toFloat()), RecyclerView.VERTICAL, false)
            storefrontMoviesLayoutBinding.allContentRecyclerView.adapter = allMoviesAdapter

            moviesStorefrontLiveData.featuredContentItemData.observe(this@StorefrontMovies) {

                if (it.isNotEmpty()) {

                    featuredMoviesAdapter.featuredMoviesData.clear()
                    featuredMoviesAdapter.featuredMoviesData.addAll(it)

                    featuredMoviesAdapter.notifyItemRangeInserted(
                        0,
                        featuredMoviesAdapter.featuredMoviesData.size
                    )

                    if (storefrontMoviesLayoutBinding.loadingView.isVisible) {

                        storefrontMoviesLayoutBinding.loadingView.startAnimation(
                            AnimationUtils.loadAnimation(
                                applicationContext,
                                R.anim.fade_out_movie
                            )
                        )
                        storefrontMoviesLayoutBinding.loadingView.visibility = View.GONE

                    }

                    if (storefrontMoviesLayoutBinding.featuredContentRecyclerView.isGone) {

                        storefrontMoviesLayoutBinding.featuredContentRecyclerView.startAnimation(
                            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in_movie)
                        )
                        storefrontMoviesLayoutBinding.featuredContentRecyclerView.visibility =
                            View.VISIBLE

                        storefrontMoviesLayoutBinding.newMovieBackground.visibility = View.VISIBLE
                        storefrontMoviesLayoutBinding.newMovieBlurryBackground.visibility =
                            View.VISIBLE

                        storefrontMoviesLayoutBinding.dividerTopImageView.visibility = View.VISIBLE

                    }

                }

            }

            moviesStorefrontLiveData.newContentItemData.observe(this@StorefrontMovies) {

                if (it.isNotEmpty()) {

                    newMoviesAdapter.storefrontMoviesContents.clear()
                    newMoviesAdapter.storefrontMoviesContents.addAll(it)

                    newMoviesAdapter.notifyItemRangeInserted(
                        0,
                        newMoviesAdapter.storefrontMoviesContents.size
                    )

                    if (storefrontMoviesLayoutBinding.newContentRecyclerView.isGone) {

                        storefrontMoviesLayoutBinding.newContentRecyclerView.startAnimation(
                            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in_movie)
                        )
                        storefrontMoviesLayoutBinding.newContentRecyclerView.visibility =
                            View.VISIBLE

                    }

                    if (storefrontMoviesLayoutBinding.randomMovieSelection.isGone) {

                        storefrontMoviesLayoutBinding.randomMovieSelection.startAnimation(
                            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in_movie)
                        )
                        storefrontMoviesLayoutBinding.randomMovieSelection.visibility = View.VISIBLE

                    }

                    storefrontMoviesLayoutBinding.dividerNewContentImageView.visibility =
                        View.VISIBLE

                    setSelectedBlurryBackground()

                }

            }

            moviesStorefrontLiveData.allMoviesItemData.observe(this@StorefrontMovies) {

                if (it.isNotEmpty()) {

                    storefrontAllUntouchedContents.addAll(it)

                    storefrontAllUnfilteredContents.addAll(it)

                    allMoviesAdapter.storefrontMoviesContents.addAll(it)

                    allMoviesAdapter.notifyDataSetChanged()

                    if (storefrontMoviesLayoutBinding.allContentRecyclerView.isGone) {
                        storefrontMoviesLayoutBinding.allContentRecyclerView.visibility =
                            View.VISIBLE
                    }

                }

            }

            moviesStorefrontLiveData.genresMoviesItemData.observe(this@StorefrontMovies) {

                if (it.isNotEmpty()) {

                    genresAdapter.storefrontGenres.clear()
                    genresAdapter.storefrontGenres.addAll(it)

                    genresAdapter.notifyItemRangeInserted(0, genresAdapter.storefrontGenres.size)

                    storefrontMoviesLayoutBinding.genresRecyclerView.visibility = View.VISIBLE

                    storefrontMoviesLayoutBinding.genreIndicatorTextView.text =
                        getString(R.string.allMovies)
                    storefrontMoviesLayoutBinding.genreIndicatorTextView.visibility = View.VISIBLE

                    genresData.clearData()
                    genresData.prepareAllGenresData(it)

                } else {


                }

                if (getFileStreamPath(IO.UpdateApplicationsDataKey).exists()) {

                    updatingDataIO.startUpdatingMoviesDataPeriodic()

                } else {

                    updatingDataIO.startUpdatingMoviesData()

                }

                retrieveFeaturedMovies(this@StorefrontMovies, moviesStorefrontLiveData)

            }

            moviesStorefrontLiveData.allFilteredMoviesItemData.observe(this@StorefrontMovies) {

                if (it.first.isNotEmpty()) {

                    allMoviesAdapter.storefrontMoviesContents.clear()
                    allMoviesAdapter.storefrontMoviesContents.addAll(it.first)

                    allMoviesAdapter.notifyDataSetChanged()

                    storefrontAllUnfilteredContents.clear()
                    storefrontAllUnfilteredContents.addAll(storefrontAllUntouchedContents)

                    storefrontMoviesLayoutBinding.nestedScrollView.smoothScrollTo(
                        0,
                        storefrontMoviesLayoutBinding.nestedScrollView.height
                    )

                    if (it.first.size < storefrontAllUntouchedContents.size) {

                        lifecycleScope.launch {

                            themePreferences.checkThemeLightDark().collect {

                                searchingMoviesSetup.afterQuickSearch(
                                    themeType = it,
                                    searchMoviesRevertView = storefrontMoviesLayoutBinding.searchRevertView,
                                    searchMoviesAdvancedView = storefrontMoviesLayoutBinding.searchAdvancedView,
                                    searchQuery = storefrontMoviesLayoutBinding.searchView.text.toString(),
                                    storefrontLiveData = moviesStorefrontLiveData,
                                    storefrontAllUntouchedContents = storefrontAllUntouchedContents
                                )

                            }

                        }

                    }

                } else {
                    //Nothing Found

                    startActivity(
                        Intent(this@StorefrontMovies, CompleteSearch::class.java).apply {
                            putExtra(
                                CompleteSearch.SearchQuery,
                                storefrontMoviesLayoutBinding.searchView.text.toString()
                            )
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        },
                        ActivityOptions.makeCustomAnimation(
                            applicationContext,
                            R.anim.fade_in_movie,
                            R.anim.fade_out_movie
                        ).toBundle()
                    )

                }

                lifecycleScope.launch {

                    themePreferences.checkThemeLightDark().collect {

                        prepareActionCenterUserInterface.resetActionCenterIcon(it)

                    }

                }

            }

            storefrontMoviesLayoutBinding.featuredContentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {

                            val snappedItemPosition = featuredMoviesLayoutManager.findLastVisibleItemPosition()

                            (storefrontMoviesLayoutBinding.featuredContentRecyclerView.layoutManager as RecycleViewSmoothLayoutList).findViewByPosition(snappedItemPosition)
                                ?.findViewWithTag<AppCompatTextView>("movieNameTextView")?.requestFocus()


                        }
                    }

                }

            })

            storefrontMoviesLayoutBinding.newContentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {

                            val snappedItemPosition = curveLayoutManager.findCurrentCenterViewPosition()

                            setSelectedBlurryBackground(snappedItemPosition)

                        }
                    }

                }

            })

            storefrontMoviesLayoutBinding.randomMovieSelection.setOnClickListener {

                val repeatCount = IntRange(1, 3).random()
                val animationFrames = IntRange(0, 50).random()

                storefrontMoviesLayoutBinding.randomMovieSelection.repeatCount = repeatCount
                storefrontMoviesLayoutBinding.randomMovieSelection.setMaxFrame(animationFrames)
                storefrontMoviesLayoutBinding.randomMovieSelection.playAnimation()

                val randomScrollPosition = IntRange(0, storefrontMoviesLayoutBinding.newContentRecyclerView.size).random()

                storefrontMoviesLayoutBinding.randomMovieSelection.addAnimatorListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {

                        curveLayoutManager.smoothScrollToPosition(
                            storefrontMoviesLayoutBinding.newContentRecyclerView, RecyclerView.State(),
                            randomScrollPosition
                        )

                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}

                })

            }

            storefrontMoviesLayoutBinding.nestedScrollView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY > oldScrollY) {//Scrolling Down

                    balloonOptionsMenu.removeBalloonOption()

                } else if (scrollY < oldScrollY) {//Scrolling Up

                    balloonOptionsMenu.removeBalloonOption()

                }

            }

            storefrontMoviesLayoutBinding.genresRecyclerView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY > oldScrollY) {
                    Log.d(this@StorefrontMovies.javaClass.simpleName, "Scrolling Down")

                    balloonOptionsMenu.removeBalloonOption()

                } else if (scrollY < oldScrollY) {
                    Log.d(this@StorefrontMovies.javaClass.simpleName, "Scrolling Up")

                    balloonOptionsMenu.removeBalloonOption()

                }

            }

        }

        PopupShortcutsCreator(applicationContext)
            .startConfiguration()

    }

    override fun onStart() {
        super.onStart()

        RemoteSubscriptions()
            .subscribe("PremiumStorefront", object : SubscriptionInterface {

                override fun subscriptionSucceed() {
                    super.subscriptionSucceed()
                }

                override fun subscriptionFailed() {
                    super.subscriptionFailed()
                }

            })

        Firebase.auth.currentUser?.let {

            RemoteSubscriptions()
                .subscribe(it.uid, object : SubscriptionInterface {

                    override fun subscriptionSucceed() {
                        super.subscriptionSucceed()
                    }

                    override fun subscriptionFailed() {
                        super.subscriptionFailed()
                    }

                })

        }

        if (BuildConfig.VERSION_NAME.uppercase(Locale.getDefault()).contains("Beta".uppercase(Locale.getDefault()))) {

            RemoteSubscriptions()
                .subscribe("Beta", object : SubscriptionInterface {

                    override fun subscriptionSucceed() {
                        super.subscriptionSucceed()
                    }

                    override fun subscriptionFailed() {
                        super.subscriptionFailed()
                    }

                })

        }

        InApplicationUpdateProcess(this@StorefrontMovies, storefrontMoviesLayoutBinding.rootView)
            .initialize(object : UpdateResponse {

                override fun newUpdateAvailable() {
                    super.newUpdateAvailable()



                }

                override fun latestVersionAlreadyInstalled() {
                    super.latestVersionAlreadyInstalled()



                }

            })

        NavigationOperations(this@StorefrontMovies)
            .listenBackPressed(this@StorefrontMovies)

    }

    override fun onResume() {
        super.onResume()

        accountSignIn.firebaseUser = Firebase.auth.currentUser

        accountSignIn.firebaseUser?.let {

            Glide.with(applicationContext)
                .load(it.photoUrl)
                .transform(CircleCrop())
                .into(storefrontMoviesLayoutBinding.profileView)

            favoritedProcess.isFavoriteProductsExist(accountSignIn.firebaseUser!!.uid, it.email!!,
                object : FavoriteProductQueryInterface {

                    override fun favoriteProductsExist(isFavoriteProductsExist: Boolean) {
                        super.favoriteProductsExist(isFavoriteProductsExist)

                        storefrontMoviesLayoutBinding.favoritesView.visibility = if (isFavoriteProductsExist) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }

                    }

                })

        }

        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {

                prepareActionCenterUserInterface.setupIconsForStorefront(it)

                actionCenterOperationsMovies.setupForMoviesStorefront(this@StorefrontMovies, it)

            }

        }

    }

    override fun onPause() {
        super.onPause()

        lifecycleScope.launch {

            (this@StorefrontMovies.application as PremiumStorefrontApplication).entryPreferences.entryType(EntryPreferences.EntryStorefrontMovies)

        }

    }

    override fun backNavigation() {

        startActivity(Intent(Intent.ACTION_MAIN).apply {
            this.addCategory(Intent.CATEGORY_HOME)
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, 0).toBundle())

    }

    override fun dispatchTouchEvent(motionEvent: MotionEvent?): Boolean {

        motionEvent?.let {
            swipeGestureListener.onTouchEvent(it)
        }

        return if (motionEvent != null) {
            super.dispatchTouchEvent(motionEvent)
        } else {
            false
        }
    }

    override fun onSwipeGesture(gestureConstants: GestureConstants, downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent, initVelocityX: Float, initVelocityY: Float) {
        super.onSwipeGesture(gestureConstants, downMotionEvent, moveMotionEvent, initVelocityX, initVelocityY)

        when (gestureConstants) {
            is GestureConstants.SwipeHorizontal -> {
                when (gestureConstants.horizontalDirection) {
                    GestureListenerConstants.SWIPE_RIGHT -> {

                        val valueAnimatorGames = ValueAnimator.ofInt(dpToInteger(applicationContext, 57), storefrontMoviesLayoutBinding.moviesSectionsSwitcherContainer.moviesSectionView.width)
                        valueAnimatorGames.duration = 333
                        valueAnimatorGames.startDelay = 333
                        valueAnimatorGames.addUpdateListener { animator ->

                            val animatorValue = animator.animatedValue as Int

                            storefrontMoviesLayoutBinding.moviesSectionsSwitcherContainer.gamesSectionView.layoutParams.width = animatorValue
                            storefrontMoviesLayoutBinding.moviesSectionsSwitcherContainer.gamesSectionView.requestLayout()

                        }
                        valueAnimatorGames.addListener(object : Animator.AnimatorListener {

                            override fun onAnimationStart(animation: Animator) {

                                lifecycleScope.launch {

                                    themePreferences.checkThemeLightDark().collect {

                                        gamesSectionSwitcherDesign(this@StorefrontMovies, storefrontMoviesLayoutBinding.moviesSectionsSwitcherContainer, it)

                                    }

                                }

                            }

                            override fun onAnimationEnd(animation: Animator) {

                                val activityOptions = ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in_movie, 0)

                                val switchIntent = Intent(applicationContext, StorefrontGames::class.java).apply {

                                }

                                startActivity(switchIntent, activityOptions.toBundle())

                            }

                            override fun onAnimationCancel(animation: Animator) {

                            }

                            override fun onAnimationRepeat(animation: Animator) {

                            }

                        })

                        val valueAnimatorMovies = ValueAnimator.ofInt(storefrontMoviesLayoutBinding.moviesSectionsSwitcherContainer.moviesSectionView.width, dpToInteger(applicationContext, 57))
                        valueAnimatorMovies.duration = 333
                        valueAnimatorMovies.startDelay = 333
                        valueAnimatorMovies.addUpdateListener { animator ->

                            val animatorValue = animator.animatedValue as Int

                            storefrontMoviesLayoutBinding.moviesSectionsSwitcherContainer.moviesSectionView.layoutParams.width = animatorValue
                            storefrontMoviesLayoutBinding.moviesSectionsSwitcherContainer.moviesSectionView.requestLayout()

                        }
                        valueAnimatorMovies.addListener(object : Animator.AnimatorListener {

                            override fun onAnimationStart(animation: Animator) {

                            }

                            override fun onAnimationEnd(animation: Animator) {

                                storefrontMoviesLayoutBinding.moviesSectionsSwitcherContainer.moviesSectionView.text = ""

                                valueAnimatorGames.start()

                            }

                            override fun onAnimationCancel(animation: Animator) {

                            }

                            override fun onAnimationRepeat(animation: Animator) {

                            }

                        })
                        valueAnimatorMovies.start()

                    }
                }
            }
            else -> {

            }
        }

    }

    override fun networkAvailable() {
        Log.d(this@StorefrontMovies.javaClass.simpleName, "Network Available @ ${this@StorefrontMovies.javaClass.simpleName}")

        retrieveAllMovies(this@StorefrontMovies, moviesStorefrontLiveData)

        retrieveGenreMovies(this@StorefrontMovies, moviesStorefrontLiveData)

        retrieveNewContent(this@StorefrontMovies, moviesStorefrontLiveData, generalEndpoints)

    }

    override fun networkLost() {
        Log.d(this@StorefrontMovies.javaClass.simpleName, "No Network @ ${this@StorefrontMovies.javaClass.simpleName}")


    }

    override fun userCreated(accountData: AccountData) {
        super.userCreated(accountData)

        val messageText = "Your Details On www.GeeksEmpire.co \n" +
                "Username: ${accountData.usernameId} | Password: ${accountData.userPassword}"

        SnackbarBuilder(applicationContext).show (
            rootView = storefrontMoviesLayoutBinding.rootView,
            messageText = messageText,
            messageDuration = Snackbar.LENGTH_INDEFINITE,
            actionButtonText = R.string.copyText,
            snackbarActionHandlerInterface = object : SnackbarActionHandlerInterface {

                override fun onActionButtonClicked(snackbar: Snackbar) {
                    super.onActionButtonClicked(snackbar)

                    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                    val clipData = ClipData.newPlainText("Geeks Empire Account Data", messageText)

                    clipboardManager.setPrimaryClip(clipData)

                    snackbar.dismiss()

                    Toast.makeText(applicationContext, "Copied!", Toast.LENGTH_LONG).show()

                }

            }
        )

    }

    override fun signInProcessSucceed(authenticationResult: AuthResult) {
        super.signInProcessSucceed(authenticationResult)

        firebaseUser = authenticationResult.user
        firebaseUser?.reload()

        Glide.with(applicationContext)
            .load(authenticationResult.user?.photoUrl)
            .transform(CircleCrop())
            .into(storefrontMoviesLayoutBinding.profileView)

        favoritedProcess.isFavoriteProductsExist(accountSignIn.firebaseUser!!.uid, accountSignIn.firebaseUser!!.email!!,
            object : FavoriteProductQueryInterface {

                override fun favoriteProductsExist(isFavoriteProductsExist: Boolean) {
                    super.favoriteProductsExist(isFavoriteProductsExist)

                    storefrontMoviesLayoutBinding.favoritesView.visibility = if (isFavoriteProductsExist) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                }

            })

    }

    override fun fragmentCreated(applicationPackageName: String, applicationName: String, applicationSummary: String) {
        super.fragmentCreated(applicationPackageName, applicationName, applicationSummary)
    }

    override fun fragmentDestroyed() {
        super.fragmentDestroyed()
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

    private fun setSelectedBlurryBackground(snappedItemPosition: Int = 0) {

        Glide.with(applicationContext)
            .asDrawable()
            .load(newMoviesAdapter.storefrontMoviesContents[snappedItemPosition].moviePosterLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .addListener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        runOnUiThread {

                            storefrontMoviesLayoutBinding.newMovieBackground.setImageDrawable(resource)

                            storefrontMoviesLayoutBinding.newMovieBlurryBackground.setBlurRadius(23f)

                            try {

                                storefrontMoviesLayoutBinding.newMovieBlurryBackground.refreshDrawableState()

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }

                    }

                    return true
                }

            })
            .submit()

    }

}