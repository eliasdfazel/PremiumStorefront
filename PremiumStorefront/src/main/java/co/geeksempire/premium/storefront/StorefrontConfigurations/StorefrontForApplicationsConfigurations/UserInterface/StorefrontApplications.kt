/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/21/21, 6:44 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountData
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountSignIn
import co.geeksempire.premium.storefront.Actions.Operation.ActionCenterOperations
import co.geeksempire.premium.storefront.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.CompleteSearch
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoriteProductQueryInterface
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoritedProcess
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterAllContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilteringOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterAdapter.FilterOptionsAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentSearching.SearchingSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.gamesSectionSwitcherDesign
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.setupStorefrontUserInterface
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.storefrontUserInteractionSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations.*
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontActivity
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForGamesConfigurations.UserInterface.StorefrontGames
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.AllContent.Adapter.AllContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.CategoryContent.Adapter.CategoriesAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.FeaturedContent.Adapter.FeaturedContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.NewContent.Adapter.NewContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.OldContent.Adapter.OldContentAdapter
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
import co.geeksempire.premium.storefront.android.Utils.System.hideKeyboard
import co.geeksempire.premium.storefront.databinding.StorefrontLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
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

class StorefrontApplications : StorefrontActivity(), NavigationListener {

    val updatingDataIO: UpdatingDataIO by lazy {
        UpdatingDataIO(applicationContext)
    }

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@StorefrontApplications)
    }

    val generalEndpoints: GeneralEndpoints = GeneralEndpoints()

    val storefrontLiveData: StorefrontLiveData by lazy {
        ViewModelProvider(this@StorefrontApplications)[StorefrontLiveData::class.java]
    }

    val allContent: AllContent by lazy {
        AllContent(this@StorefrontApplications, storefrontLiveData, GeneralEndpoints.QueryType.ApplicationsQuery)
    }

    val prepareActionCenterUserInterface: PrepareActionCenterUserInterface by lazy {
        PrepareActionCenterUserInterface(context = applicationContext, actionCenterView = storefrontLayoutBinding.actionCenterView, actionLeftView = storefrontLayoutBinding.leftActionView, actionMiddleView = storefrontLayoutBinding.middleActionView, actionRightView = storefrontLayoutBinding.rightActionView)
    }

    val balloonOptionsMenu: BalloonOptionsMenu by lazy {
        BalloonOptionsMenu(context = this@StorefrontApplications,
            rootView = storefrontLayoutBinding.rootView)
    }

    val actionCenterOperations: ActionCenterOperations by lazy {
        ActionCenterOperations()
    }

    val filterAllContent: FilterAllContent by lazy {
        FilterAllContent(storefrontLiveData)
    }

    val featuredContentAdapter: FeaturedContentAdapter by lazy {
        FeaturedContentAdapter(context = this@StorefrontApplications,
            contentDetailsContainer = storefrontLayoutBinding.contentDetailsContainer,
            productDetailsFragment = productDetailsFragment,
            fragmentInterface = this@StorefrontApplications)
    }

    val allContentAdapter: AllContentAdapter by lazy {
        AllContentAdapter(context = this@StorefrontApplications,
            contentDetailsContainer = storefrontLayoutBinding.contentDetailsContainer,
            productDetailsFragment = productDetailsFragment,
            fragmentInterface = this@StorefrontApplications)
    }

    val allMoreContentAdapter: AllContentAdapter by lazy {
        AllContentAdapter(context = this@StorefrontApplications,
            contentDetailsContainer = storefrontLayoutBinding.contentDetailsContainer,
            productDetailsFragment = productDetailsFragment,
            fragmentInterface = this@StorefrontApplications)
    }

    val oldContentAdapter: OldContentAdapter by lazy {
        OldContentAdapter(context = this@StorefrontApplications,
            contentDetailsContainer = storefrontLayoutBinding.contentDetailsContainer,
            productDetailsFragment = productDetailsFragment,
            fragmentInterface = this@StorefrontApplications)
    }

    val newContentAdapter: NewContentAdapter by lazy {
        NewContentAdapter(context = this@StorefrontApplications,
            contentDetailsContainer = storefrontLayoutBinding.contentDetailsContainer,
            productDetailsFragment = productDetailsFragment,
            fragmentInterface = this@StorefrontApplications)
    }

    val categoriesAdapter: CategoriesAdapter by lazy {
        CategoriesAdapter(context = this@StorefrontApplications,
            filterAllContent = filterAllContent,
            allFilteredContentItemData = storefrontLiveData.allFilteredContentItemData,
            storefrontAllUnfilteredContents = storefrontAllUnfilteredContents,
            storefrontAllUntouchedContents = storefrontAllUntouchedContents,
            categoryIndicatorTextView = storefrontLayoutBinding.categoryIndicatorTextView,
            categoriesRecyclerView = storefrontLayoutBinding.categoriesRecyclerView,
            balloonOptionsMenu = balloonOptionsMenu)
    }

    val favoritedProcess: FavoritedProcess by lazy {
        FavoritedProcess(this@StorefrontApplications)
    }

    val searchingSetup: SearchingSetup by lazy {
        SearchingSetup(this@StorefrontApplications)
    }

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@StorefrontApplications, storefrontLayoutBinding.rootView, networkCheckpoint)
    }

    val productDetailsFragment: ProductDetailsFragment by lazy {
        ProductDetailsFragment()
    }

    val filterOptionsAdapter: FilterOptionsAdapter by lazy {
        FilterOptionsAdapter(this@StorefrontApplications,
            filterAllContent = filterAllContent,
            storefrontAllUnfilteredContents = storefrontAllUnfilteredContents,
            filteringInclude = storefrontLayoutBinding.filteringInclude,
            filterOptionsType = FilteringOptions.FilterByCountry)
    }

    val storefrontAllUntouchedContents: ArrayList<DocumentSnapshot> = ArrayList<DocumentSnapshot>()
    val storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot> = ArrayList<DocumentSnapshot>()

    val firebaseRemoteConfiguration = Firebase.remoteConfig

    private val swipeGestureListener: SwipeGestureListener by lazy {
        SwipeGestureListener(applicationContext, this@StorefrontApplications)
    }

    /* Start - Sign In */
    val accountSignIn: AccountSignIn by lazy {
        AccountSignIn(this@StorefrontApplications, this@StorefrontApplications)
    }

    val accountSelector: ActivityResultLauncher<Any?> = registerForActivityResult(accountSignIn.createProcess()) {



    }
    /* End - Sign In */

    val firebaseAuthentication = Firebase.auth
    var firebaseUser = firebaseAuthentication.currentUser

    lateinit var storefrontLayoutBinding: StorefrontLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storefrontLayoutBinding = StorefrontLayoutBinding.inflate(layoutInflater)
        setContentView(storefrontLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@StorefrontApplications

        setupStorefrontUserInterface(context = this@StorefrontApplications,
            themePreferences = themePreferences,
            allContentAdapter = allContentAdapter, allMoreContentAdapter = allMoreContentAdapter, featuredContentAdapter = featuredContentAdapter, newContentAdapter = newContentAdapter, oldContentAdapter = oldContentAdapter,
            categoriesAdapter = categoriesAdapter,
            rootView = storefrontLayoutBinding.rootView,
            allContentBackground = storefrontLayoutBinding.allContentBackground, brandingBackground = storefrontLayoutBinding.brandingBackground,
            profileView = storefrontLayoutBinding.profileView, preferencesView = storefrontLayoutBinding.preferencesView, favoritesView = storefrontLayoutBinding.favoritesView,
            categoryIndicatorTextView = storefrontLayoutBinding.categoryIndicatorTextView,
            prepareActionCenterUserInterface = prepareActionCenterUserInterface)

        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {

                storefrontUserInteractionSetup(context = this@StorefrontApplications, rootView = storefrontLayoutBinding.rootView,
                    firebaseUser = firebaseUser, accountSelector = accountSelector,
                    profileView = storefrontLayoutBinding.profileView, preferencesView = storefrontLayoutBinding.preferencesView, favoritesView = storefrontLayoutBinding.favoritesView,
                    sectionsSwitcherLayoutBinding = storefrontLayoutBinding.sectionsSwitcherContainer, themeType = it)

                actionCenterOperations.setupForApplicationsStorefront(this@StorefrontApplications, it)

            }

        }

        storefrontLayoutBinding.root.post {

            storefrontLayoutBinding.loadingView.visibility = View.VISIBLE

            storefrontLayoutBinding.featuredContentRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.HORIZONTAL, false)
            storefrontLayoutBinding.featuredContentRecyclerView.adapter = featuredContentAdapter

            PagerSnapHelper().attachToRecyclerView(storefrontLayoutBinding.featuredContentRecyclerView)

            storefrontLayoutBinding.allContentRecyclerView.layoutManager = RecycleViewSmoothLayoutGrid(applicationContext, columnCount(applicationContext, 307), RecyclerView.VERTICAL,false)
            storefrontLayoutBinding.allContentRecyclerView.adapter = allContentAdapter

            PagerSnapHelper().attachToRecyclerView(storefrontLayoutBinding.allContentRecyclerView)

            storefrontLayoutBinding.oldContentRecyclerView.layoutManager = GridLayoutManager(applicationContext, columnCount(applicationContext, percentageOfDisplayX(applicationContext, 87f), 113, dpToInteger(applicationContext, 19).toFloat()), RecyclerView.VERTICAL, false)
            storefrontLayoutBinding.oldContentRecyclerView.adapter = oldContentAdapter

            storefrontLayoutBinding.newContentRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.HORIZONTAL, false)
            storefrontLayoutBinding.newContentRecyclerView.adapter = newContentAdapter

            PagerSnapHelper().attachToRecyclerView(storefrontLayoutBinding.newContentRecyclerView)

            storefrontLayoutBinding.categoriesRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.VERTICAL, false)
            storefrontLayoutBinding.categoriesRecyclerView.adapter = categoriesAdapter

            storefrontLiveData.allContentItems.observe(this@StorefrontApplications) {

                if (it.isNotEmpty()) {

                    storefrontLayoutBinding.loadingView.visibility = View.GONE

                    storefrontAllUntouchedContents.clear()
                    storefrontAllUntouchedContents.addAll(it)

                    storefrontAllUnfilteredContents.clear()
                    storefrontAllUnfilteredContents.addAll(it)

                    allContentAdapter.storefrontContents.clear()
                    allContentAdapter.storefrontContents.addAll(it.subList(0, it.size / 3))

                    allContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.allContentRecyclerView.visibility = View.VISIBLE

                    retrieveOldContent(
                        this@StorefrontApplications,
                        storefrontLiveData,
                        generalEndpoints,
                        GeneralEndpoints.QueryType.ApplicationsQuery
                    )

                } else {


                }

            }

            storefrontLiveData.allFilteredContentItemData.observe(this@StorefrontApplications) {

                if (it.first.isNotEmpty()) {

                    allContentAdapter.storefrontContents.clear()
                    allContentAdapter.storefrontContents.addAll(it.first)

                    allContentAdapter.notifyDataSetChanged()

                    storefrontAllUnfilteredContents.clear()
                    storefrontAllUnfilteredContents.addAll(storefrontAllUntouchedContents)

                    if (it.first.size < storefrontAllUntouchedContents.size) {

                        lifecycleScope.launch {

                            themePreferences.checkThemeLightDark().collect {

                                searchingSetup.afterQuickSearch(
                                    themeType = it,
                                    revertView = storefrontLayoutBinding.searchRevertView,
                                    advancedSearchView = storefrontLayoutBinding.searchAdvancedView,
                                    searchQuery = storefrontLayoutBinding.searchView.text.toString(),
                                    storefrontLiveData = storefrontLiveData,
                                    storefrontAllUntouchedContents = storefrontAllUntouchedContents
                                )

                            }
                            
                        }

                    }

                } else {
                    //Nothing Found

                    startActivity(
                        Intent(
                            this@StorefrontApplications,
                            CompleteSearch::class.java
                        ).apply {
                            putExtra(
                                CompleteSearch.SearchQuery,
                                storefrontLayoutBinding.searchView.text.toString()
                            )
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        },
                        ActivityOptions.makeCustomAnimation(
                            applicationContext,
                            R.anim.fade_in,
                            R.anim.fade_out
                        ).toBundle()
                    )

                }

                lifecycleScope.launch {

                    themePreferences.checkThemeLightDark().collect {

                        prepareActionCenterUserInterface.resetActionCenterIcon(it)

                    }

                }

            }

            storefrontLiveData.featuredContentItems.observe(this@StorefrontApplications) {

                if (it.isNotEmpty()) {

                    featuredContentAdapter.storefrontContents.clear()
                    featuredContentAdapter.storefrontContents.addAll(it)

                    featuredContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.featuredContentRecyclerView.visibility = View.VISIBLE
                    storefrontLayoutBinding.featuredContentRecyclerView.startAnimation(
                        AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
                    )

                    PopupShortcutsCreator(applicationContext)
                        .startConfiguration()

                } else {


                }

            }

            storefrontLiveData.newContentItemDataWordpress.observe(this@StorefrontApplications) {

                if (it.isNotEmpty()) {

                    newContentAdapter.storefrontContents.clear()
                    newContentAdapter.storefrontContents.addAll(it)

                    newContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.newContentRecyclerView.visibility = View.VISIBLE
                    storefrontLayoutBinding.newContentRecyclerView.startAnimation(
                        AnimationUtils.loadAnimation(
                            applicationContext,
                            R.anim.fade_in
                        )
                    )

                } else {


                }

            }

            storefrontLiveData.oldContentItemDataWordpress.observe(this@StorefrontApplications) {

                if (it.isNotEmpty()) {

                    oldContentAdapter.storefrontContents.clear()
                    oldContentAdapter.storefrontContents.addAll(it)

                    oldContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.oldContentRecyclerView.visibility = View.VISIBLE
                    storefrontLayoutBinding.oldContentRecyclerView.startAnimation(
                        AnimationUtils.loadAnimation(
                            applicationContext,
                            R.anim.fade_in
                        )
                    )

                } else {


                }

            }

            storefrontLiveData.categoriesItems.observe(this@StorefrontApplications) {

                if (it.isNotEmpty()) {

                    categoriesAdapter.storefrontCategories.clear()
                    categoriesAdapter.storefrontCategories.addAll(it)

                    categoriesAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.categoriesRecyclerView.visibility = View.VISIBLE

                    storefrontLayoutBinding.categoryIndicatorTextView.text =
                        getString(R.string.allApplications)
                    storefrontLayoutBinding.categoryIndicatorTextView.visibility = View.VISIBLE

                    (application as PremiumStorefrontApplication).categoryData.clearData()
                    (application as PremiumStorefrontApplication).categoryData.prepareAllCategoriesData(
                        it
                    )

                } else {


                }

                if (getFileStreamPath(IO.UpdateApplicationsDataKey).exists()) {

                    updatingDataIO.startUpdatingApplicationsDataPeriodic()

                } else {

                    updatingDataIO.startUpdatingApplicationsData()

                }

            }

            storefrontLayoutBinding.nestedScrollView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY > oldScrollY) {//Scrolling Down

                    balloonOptionsMenu.removeBalloonOption()

                } else if (scrollY < oldScrollY) {//Scrolling Up

                    balloonOptionsMenu.removeBalloonOption()

                }

            }

            storefrontLayoutBinding.categoriesRecyclerView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY > oldScrollY) {
                    Log.d(this@StorefrontApplications.javaClass.simpleName, "Scrolling Down")

                    balloonOptionsMenu.removeBalloonOption()

                } else if (scrollY < oldScrollY) {
                    Log.d(this@StorefrontApplications.javaClass.simpleName, "Scrolling Up")

                    balloonOptionsMenu.removeBalloonOption()

                }

            }

        }

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

        InApplicationUpdateProcess(this@StorefrontApplications, storefrontLayoutBinding.rootView)
            .initialize(object : UpdateResponse {

                override fun newUpdateAvailable() {
                    super.newUpdateAvailable()



                }

                override fun latestVersionAlreadyInstalled() {
                    super.latestVersionAlreadyInstalled()



                }

            })

        NavigationOperations(this@StorefrontApplications)
            .listenBackPressed(this@StorefrontApplications)

    }

    override fun onResume() {
        super.onResume()

        accountSignIn.firebaseUser = Firebase.auth.currentUser

        accountSignIn.firebaseUser?.let {

            Glide.with(applicationContext)
                .load(it.photoUrl)
                .transform(CircleCrop())
                .into(storefrontLayoutBinding.profileView)

            favoritedProcess.isFavoriteProductsExist(accountSignIn.firebaseUser!!.uid, it.email!!,
                object : FavoriteProductQueryInterface {

                    override fun favoriteProductsExist(isFavoriteProductsExist: Boolean) {
                        super.favoriteProductsExist(isFavoriteProductsExist)

                        storefrontLayoutBinding.favoritesView.visibility = if (isFavoriteProductsExist) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }

                    }

                })

        }

    }

    override fun onPause() {
        super.onPause()

        accountSignIn.firebaseUser?.reload()

        lifecycleScope.launch {

            (this@StorefrontApplications.application as PremiumStorefrontApplication).entryPreferences.entryType(EntryPreferences.EntryStorefrontApplications)

        }

    }

    override fun backNavigation() {

        if (productDetailsFragment.isShowing) {

            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(0, R.anim.fade_out)
                .remove(productDetailsFragment)
                .detach(productDetailsFragment)
                .commitNow()

        } else {

            startActivity(Intent(Intent.ACTION_MAIN).apply {
                this.addCategory(Intent.CATEGORY_HOME)
                this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, 0).toBundle())

        }

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
                    GestureListenerConstants.SWIPE_LEFT -> {
                        Log.d(this@StorefrontApplications.javaClass.simpleName, "Swipe Left")

                        val valueAnimatorGames = ValueAnimator.ofInt(dpToInteger(applicationContext, 57), storefrontLayoutBinding.sectionsSwitcherContainer.applicationsSectionView.width)
                        valueAnimatorGames.duration = 333
                        valueAnimatorGames.startDelay = 333
                        valueAnimatorGames.addUpdateListener { animator ->

                            val animatorValue = animator.animatedValue as Int

                            storefrontLayoutBinding.sectionsSwitcherContainer.gamesSectionView.layoutParams.width = animatorValue
                            storefrontLayoutBinding.sectionsSwitcherContainer.gamesSectionView.requestLayout()

                        }
                        valueAnimatorGames.addListener(object : Animator.AnimatorListener {

                            override fun onAnimationStart(animation: Animator) {

                                lifecycleScope.launch {

                                    themePreferences.checkThemeLightDark().collect {

                                        gamesSectionSwitcherDesign(this@StorefrontApplications, storefrontLayoutBinding.sectionsSwitcherContainer, it)

                                    }

                                }

                            }

                            override fun onAnimationEnd(animation: Animator) {

                                val activityOptions = ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0)

                                val switchIntent = Intent(this@StorefrontApplications, StorefrontGames::class.java).apply {

                                }

                                startActivity(switchIntent, activityOptions.toBundle())

                            }

                            override fun onAnimationCancel(animation: Animator) {

                            }

                            override fun onAnimationRepeat(animation: Animator) {

                            }

                        })

                        val valueAnimatorApplications = ValueAnimator.ofInt(storefrontLayoutBinding.sectionsSwitcherContainer.applicationsSectionView.width, dpToInteger(applicationContext, 57))
                        valueAnimatorApplications.duration = 333
                        valueAnimatorApplications.startDelay = 333
                        valueAnimatorApplications.addUpdateListener { animator ->

                            val animatorValue = animator.animatedValue as Int

                            storefrontLayoutBinding.sectionsSwitcherContainer.applicationsSectionView.layoutParams.width = animatorValue
                            storefrontLayoutBinding.sectionsSwitcherContainer.applicationsSectionView.requestLayout()

                        }
                        valueAnimatorApplications.addListener(object : Animator.AnimatorListener {

                            override fun onAnimationStart(animation: Animator) {

                            }

                            override fun onAnimationEnd(animation: Animator) {

                                storefrontLayoutBinding.sectionsSwitcherContainer.applicationsSectionView.text = ""

                                valueAnimatorGames.start()

                            }

                            override fun onAnimationCancel(animation: Animator) {

                            }

                            override fun onAnimationRepeat(animation: Animator) {

                            }

                        })
                        valueAnimatorApplications.start()

                    }
                }
            }
            else -> {}
        }

    }

    override fun networkAvailable() {
        Log.d(this@StorefrontApplications.javaClass.simpleName, "Network Available @ ${this@StorefrontApplications.javaClass.simpleName}")

        retrieveCategories(this@StorefrontApplications,
            generalEndpoints, storefrontLiveData, GeneralEndpoints.QueryType.ApplicationsQuery)

        retrieveFeaturedContent(this@StorefrontApplications,
            storefrontLiveData, generalEndpoints, GeneralEndpoints.QueryType.ApplicationsQuery)

        allContent.retrieveAllApplicationsContent()

        retrieveNewContent(this@StorefrontApplications,
            storefrontLiveData, generalEndpoints, GeneralEndpoints.QueryType.ApplicationsQuery)

    }

    override fun networkLost() {
        Log.d(this@StorefrontApplications.javaClass.simpleName, "No Network @ ${this@StorefrontApplications.javaClass.simpleName}")

        hideKeyboard(applicationContext, storefrontLayoutBinding.searchView)

    }

    override fun userCreated(accountData: AccountData) {
        super.userCreated(accountData)

        val messageText = "Your Details On www.GeeksEmpire.co \n" +
                "Username: ${accountData.usernameId} | Password: ${accountData.userPassword}"

        SnackbarBuilder(applicationContext).show (
            rootView = storefrontLayoutBinding.rootView,
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
            .into(storefrontLayoutBinding.profileView)

        favoritedProcess.isFavoriteProductsExist(accountSignIn.firebaseUser!!.uid, accountSignIn.firebaseUser!!.email!!,
            object : FavoriteProductQueryInterface {

                override fun favoriteProductsExist(isFavoriteProductsExist: Boolean) {
                    super.favoriteProductsExist(isFavoriteProductsExist)

                    storefrontLayoutBinding.favoritesView.visibility = if (isFavoriteProductsExist) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                }

            })

    }

    override fun fragmentCreated(applicationPackageName: String, applicationName: String, applicationSummary: String) {
        super.fragmentCreated(applicationPackageName, applicationName, applicationSummary)

        actionCenterOperations.setupForApplicationsDetails(context = this@StorefrontApplications,
            applicationPackageName = applicationPackageName?:packageName,
            applicationName = applicationName?:getString(R.string.applicationName),
            applicationSummary = applicationSummary?:getString(R.string.applicationSummary))

        lifecycleScope.launch {
            themePreferences.checkThemeLightDark().collect {
                prepareActionCenterUserInterface.setupIconsForDetails(it)
            }
        }

    }

    override fun fragmentDestroyed() {
        super.fragmentDestroyed()

        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {

                prepareActionCenterUserInterface.setupIconsForStorefront(it)

                actionCenterOperations.setupForApplicationsStorefront(this@StorefrontApplications, it)

            }

        }

        if (!storefrontLayoutBinding.favoritesView.isShown) {

            accountSignIn.firebaseUser?.let {

                favoritedProcess.isFavoriteProductsExist(it.uid, it.email!!,
                    object : FavoriteProductQueryInterface {

                        override fun favoriteProductsExist(isFavoriteProductsExist: Boolean) {
                            super.favoriteProductsExist(isFavoriteProductsExist)

                            storefrontLayoutBinding.favoritesView.visibility = if (isFavoriteProductsExist) {
                                View.VISIBLE
                            } else {
                                View.GONE
                            }

                        }

                    })

            }

        }

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