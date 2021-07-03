/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/3/21, 10:57 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForGamesConfigurations.UserInterface

import android.app.ActivityOptions
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountData
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountSignIn
import co.geeksempire.premium.storefront.Actions.Operation.ActionCenterOperations
import co.geeksempire.premium.storefront.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoriteProductQueryInterface
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoritedProcess
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.Adapters.AllContent.Adapter.AllContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.Adapters.CategoryContent.Adapter.CategoriesAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.Adapters.FeaturedContent.Adapter.FeaturedContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.Adapters.NewContent.Adapter.NewContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterAllContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterAdapter.FilterOptionsAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.setupStorefrontUserInterface
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.storefrontUserInteractionSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations.*
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontActivity
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.Utils.IO.IO
import co.geeksempire.premium.storefront.Utils.IO.UpdatingDataIO
import co.geeksempire.premium.storefront.Utils.InApplicationUpdate.InApplicationUpdateProcess
import co.geeksempire.premium.storefront.Utils.InApplicationUpdate.UpdateResponse
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.Notifications.*
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import co.geeksempire.premium.storefront.Utils.UI.Display.displayY
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutGrid
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import co.geeksempire.premium.storefront.databinding.StorefrontLayoutBinding
import com.abanabsalan.aban.magazine.Utils.System.hideKeyboard
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.android.synthetic.main.storefront_layout.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.geeksempire.balloon.optionsmenu.library.BalloonOptionsMenu
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger
import java.util.*
import kotlin.collections.ArrayList

class StorefrontGames : StorefrontActivity() {

    val updatingDataIO: UpdatingDataIO by lazy {
        UpdatingDataIO(applicationContext)
    }

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@StorefrontGames)
    }

    val generalEndpoint: GeneralEndpoint = GeneralEndpoint()

    val storefrontLiveData: StorefrontLiveData by lazy {
        ViewModelProvider(this@StorefrontGames).get(StorefrontLiveData::class.java)
    }

    val allContent: AllContent by lazy {
        AllContent(applicationContext, storefrontLiveData, GeneralEndpoint.QueryType.GamesQuery)
    }

    val prepareActionCenterUserInterface: PrepareActionCenterUserInterface by lazy {
        PrepareActionCenterUserInterface(context = applicationContext, actionCenterView = storefrontLayoutBinding.actionCenterView, actionLeftView = storefrontLayoutBinding.leftActionView, actionMiddleView = storefrontLayoutBinding.middleActionView, actionRightView = storefrontLayoutBinding.rightActionView)
    }

    val balloonOptionsMenu: BalloonOptionsMenu by lazy {
        BalloonOptionsMenu(context = this@StorefrontGames,
            rootView = storefrontLayoutBinding.rootView)
    }

    val actionCenterOperations: ActionCenterOperations by lazy {
        ActionCenterOperations()
    }

    val filterAllContent: FilterAllContent by lazy {
        FilterAllContent(storefrontLiveData)
    }

    val featuredContentAdapter: FeaturedContentAdapter by lazy {
        FeaturedContentAdapter(context = this@StorefrontGames,
            contentDetailsContainer = storefrontLayoutBinding.contentDetailsContainer,
            productDetailsFragment = productDetailsFragment,
            fragmentInterface = this@StorefrontGames)
    }

    val allContentAdapter: AllContentAdapter by lazy {
        AllContentAdapter(context = this@StorefrontGames,
            contentDetailsContainer = storefrontLayoutBinding.contentDetailsContainer,
            productDetailsFragment = productDetailsFragment,
            fragmentInterface = this@StorefrontGames)
    }

    val newContentAdapter: NewContentAdapter by lazy {
        NewContentAdapter(context = this@StorefrontGames,
            contentDetailsContainer = storefrontLayoutBinding.contentDetailsContainer,
            productDetailsFragment = productDetailsFragment,
            fragmentInterface = this@StorefrontGames)
    }

    val categoriesAdapter: CategoriesAdapter by lazy {
        CategoriesAdapter(context = this@StorefrontGames,
            filterAllContent = filterAllContent,
            allFilteredContentItemData = storefrontLiveData.allFilteredContentItemData,
            storefrontAllUnfilteredContents = storefrontAllUnfilteredContents,
            storefrontAllUntouchedContents = storefrontAllUntouchedContents,
            categoryIndicatorTextView = categoryIndicatorTextView,
            categoriesRecyclerView = storefrontLayoutBinding.categoriesRecyclerView,
            balloonOptionsMenu = balloonOptionsMenu)
    }

    val favoritedProcess: FavoritedProcess by lazy {
        FavoritedProcess(this@StorefrontGames)
    }

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@StorefrontGames, storefrontLayoutBinding.rootView, networkCheckpoint)
    }

    val productDetailsFragment: ProductDetailsFragment by lazy {
        ProductDetailsFragment()
    }

    val filterOptionsAdapter: FilterOptionsAdapter by lazy {
        FilterOptionsAdapter(this@StorefrontGames,
            filterAllContent = filterAllContent,
            storefrontAllUnfilteredContents = storefrontAllUnfilteredContents,
            filteringInclude = storefrontLayoutBinding.filteringInclude)
    }

    val storefrontAllUntouchedContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()
    val storefrontAllUnfilteredContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    val firebaseRemoteConfiguration = Firebase.remoteConfig

    /* Start - Sign In */
    val accountSignIn: AccountSignIn by lazy {
        AccountSignIn(this@StorefrontGames, this@StorefrontGames)
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

        networkConnectionListener.networkConnectionListenerInterface = this@StorefrontGames

        setupStorefrontUserInterface(context = this@StorefrontGames,
            themePreferences = themePreferences,
            allContentAdapter = allContentAdapter, featuredContentAdapter = featuredContentAdapter, newContentAdapter = newContentAdapter,
            categoriesAdapter = categoriesAdapter,
            rootView = storefrontLayoutBinding.rootView,
            allContentBackground = storefrontLayoutBinding.allContentBackground, brandingBackground = storefrontLayoutBinding.brandingBackground,
            profileView = storefrontLayoutBinding.profileView, preferencesView = storefrontLayoutBinding.preferencesView, favoritesView = storefrontLayoutBinding.favoritesView,
            categoryIndicatorTextView = storefrontLayoutBinding.categoryIndicatorTextView,
            prepareActionCenterUserInterface = prepareActionCenterUserInterface)

        storefrontLayoutBinding.root.post {

            storefrontLayoutBinding.loadingView.visibility = View.VISIBLE

            storefrontUserInteractionSetup(context = this@StorefrontGames, firebaseUser = firebaseUser, accountSelector = accountSelector,
                profileView = storefrontLayoutBinding.profileView, preferencesView = storefrontLayoutBinding.preferencesView, favoritesView = storefrontLayoutBinding.favoritesView,
                sectionSwitcherLayoutBinding = storefrontLayoutBinding.sectionSwitcherContainer)

            lifecycleScope.launch {

                themePreferences.checkThemeLightDark().collect {

                    actionCenterOperations.setupForGamesStorefront(this@StorefrontGames, it)

                }

            }

            storefrontLayoutBinding.featuredContentRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.HORIZONTAL, false)
            storefrontLayoutBinding.featuredContentRecyclerView.adapter = featuredContentAdapter

            storefrontLayoutBinding.allContentRecyclerView.layoutManager = RecycleViewSmoothLayoutGrid(applicationContext, columnCount(applicationContext, 307), RecyclerView.VERTICAL,false)
            storefrontLayoutBinding.allContentRecyclerView.adapter = allContentAdapter

            storefrontLayoutBinding.newContentRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.HORIZONTAL, false)
            storefrontLayoutBinding.newContentRecyclerView.adapter = newContentAdapter

            storefrontLayoutBinding.categoriesRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.VERTICAL, false)
            storefrontLayoutBinding.categoriesRecyclerView.adapter = categoriesAdapter

            storefrontLiveData.allContentItemData.observe(this@StorefrontGames, {

                if (it.isNotEmpty()) {

                    storefrontLayoutBinding.loadingView.visibility = View.GONE

                    storefrontAllUntouchedContents.clear()
                    storefrontAllUntouchedContents.addAll(it)

                    storefrontAllUnfilteredContents.clear()
                    storefrontAllUnfilteredContents.addAll(it)

                    allContentAdapter.storefrontContents.clear()
                    allContentAdapter.storefrontContents.addAll(it)

                    allContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.allContentRecyclerView.visibility = View.VISIBLE

                    retrieveCategories(this@StorefrontGames,
                        generalEndpoint, storefrontLiveData, firebaseRemoteConfiguration, GeneralEndpoint.QueryType.GamesQuery)

                    storefrontLiveData.checkInstalledApplications(applicationContext, allContentAdapter, it)

                } else {



                }

            })

            storefrontLiveData.allContentMoreItemData.observe(this@StorefrontGames, {
                Log.d(this@StorefrontGames.javaClass.simpleName, "More Products Data Loaded")

                storefrontAllUntouchedContents.addAll(it)

                storefrontAllUnfilteredContents.addAll(it)

                if (allContent.allLoadingFinished && allContentAdapter.storefrontContents.isNotEmpty()) {

                    storefrontLayoutBinding.loadMoreView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    storefrontLayoutBinding.loadMoreView.visibility = View.VISIBLE


                }

            })

            storefrontLiveData.presentMoreItemData.observe(this@StorefrontGames, {

                allContentAdapter.storefrontContents.add(it)

                allContentAdapter.notifyItemInserted(allContentAdapter.storefrontContents.size - 1)

                storefrontLayoutBinding.loadMoreView.apply {

                    speed = 1f
                    setMinAndMaxFrame(130, 165)

                    if (!isAnimating) {
                        playAnimation()
                    }

                }

                if (allContentAdapter.storefrontContents.size == storefrontAllUntouchedContents.size) {

                    storefrontLayoutBinding.loadMoreView.visibility = View.GONE

                }

            })

            storefrontLiveData.allFilteredContentItemData.observe(this@StorefrontGames, {

                if (it.first.isNotEmpty()) {

                    allContentAdapter.storefrontContents.clear()
                    allContentAdapter.storefrontContents.addAll(it.first)

                    allContentAdapter.notifyDataSetChanged()

                    storefrontAllUnfilteredContents.clear()
                    storefrontAllUnfilteredContents.addAll(storefrontAllUntouchedContents)

                } else {

                    showToast(applicationContext, getString(R.string.nothingFoundText), Gravity.TOP)

                }

                lifecycleScope.launch {

                    themePreferences.checkThemeLightDark().collect {

                        prepareActionCenterUserInterface.resetActionCenterIcon(it)

                    }

                }

            })

            storefrontLiveData.featuredContentItemData.observe(this@StorefrontGames, {

                if (it.isNotEmpty()) {

                    val numberOfItemsToLoad = displayY(applicationContext) / (dpToInteger(applicationContext, 307))
                    Log.d(this@StorefrontGames.javaClass.simpleName, "Number Of Items To Load | Featured Content: ${numberOfItemsToLoad}")

                    val dataToSetup = it.subList(0, numberOfItemsToLoad)

                    featuredContentAdapter.storefrontContents.clear()
                    featuredContentAdapter.storefrontContents.addAll(it)

                    featuredContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.featuredContentRecyclerView.visibility = View.VISIBLE
                    storefrontLayoutBinding.featuredContentRecyclerView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

                } else {



                }

            })

            storefrontLiveData.newContentItemData.observe(this@StorefrontGames, {

                if (it.isNotEmpty()) {

                    newContentAdapter.storefrontContents.clear()
                    newContentAdapter.storefrontContents.addAll(it)

                    newContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.newContentRecyclerView.visibility = View.VISIBLE
                    storefrontLayoutBinding.newContentRecyclerView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

                } else {



                }

            })

            storefrontLiveData.categoriesItemData.observe(this@StorefrontGames, {

                if (it.isNotEmpty()) {

                    categoriesAdapter.storefrontCategories.clear()
                    categoriesAdapter.storefrontCategories.addAll(it)

                    categoriesAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.categoriesRecyclerView.visibility = View.VISIBLE

                    storefrontLayoutBinding.categoryIndicatorTextView.visibility = View.VISIBLE

                    (application as PremiumStorefrontApplication).categoryData.clearData()
                    (application as PremiumStorefrontApplication).categoryData.prepareAllCategoriesData(it)

                } else {



                }

                if (getFileStreamPath(IO.UpdateGamesDataKey).exists()) {

                    updatingDataIO.startUpdatingGamesDataPeriodic()

                } else {

                    updatingDataIO.startUpdatingGamesData()

                }

            })

            storefrontLayoutBinding.nestedScrollView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY > oldScrollY) {
                    Log.d(this@StorefrontGames.javaClass.simpleName, "Scrolling Down")

                    balloonOptionsMenu.removeBalloonOption()

                } else if (scrollY < oldScrollY) {
                    Log.d(this@StorefrontGames.javaClass.simpleName, "Scrolling Up")

                    balloonOptionsMenu.removeBalloonOption()

                }

            }

            storefrontLayoutBinding.categoriesRecyclerView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY > oldScrollY) {
                    Log.d(this@StorefrontGames.javaClass.simpleName, "Scrolling Down")

                    balloonOptionsMenu.removeBalloonOption()

                } else if (scrollY < oldScrollY) {
                    Log.d(this@StorefrontGames.javaClass.simpleName, "Scrolling Up")

                    balloonOptionsMenu.removeBalloonOption()

                }

            }

            storefrontLayoutBinding.loadMoreView.setOnClickListener {

                storefrontLiveData.loadMoreDataIntoPresenter(storefrontAllUntouchedContents, allContentAdapter.storefrontContents)

                storefrontLayoutBinding.loadMoreView.apply {

                    speed = 1f
                    setMinAndMaxFrame(1, 130)

                    if (!isAnimating) {
                        playAnimation()
                    }

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

        InApplicationUpdateProcess(this@StorefrontGames, storefrontLayoutBinding.rootView)
            .initialize(object : UpdateResponse {

                override fun newUpdateAvailable() {
                    super.newUpdateAvailable()



                }

                override fun latestVersionAlreadyInstalled() {
                    super.latestVersionAlreadyInstalled()



                }

            })

    }

    override fun onResume() {
        super.onResume()

        accountSignIn.firebaseUser = Firebase.auth.currentUser

        accountSignIn.firebaseUser?.let {

            Glide.with(applicationContext)
                .load(it.photoUrl)
                .transform(CircleCrop())
                .into(storefrontLayoutBinding.profileView)

            favoritedProcess.isFavoriteProductsExist(accountSignIn.firebaseUser!!.uid,
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

            (this@StorefrontGames.application as PremiumStorefrontApplication).entryPreferences.entryType(EntryPreferences.EntryStorefrontGames)

        }

    }

    override fun onBackPressed() {

        if (productDetailsFragment.isShowing) {

            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(0, R.anim.fade_out)
                .remove(productDetailsFragment)
                .commitNow()

        } else {

            startActivity(Intent(Intent.ACTION_MAIN).apply {
                this.addCategory(Intent.CATEGORY_HOME)
                this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, 0).toBundle())

        }

    }

    override fun networkAvailable() {
        Log.d(this@StorefrontGames.javaClass.simpleName, "Network Available @ ${this@StorefrontGames.javaClass.simpleName}")

        retrieveFeaturedContent(this@StorefrontGames,
            storefrontLiveData, generalEndpoint, GeneralEndpoint.QueryType.GamesQuery)

        allContent.retrieveAllContent()

        retrieveNewContent(this@StorefrontGames,
            storefrontLiveData, generalEndpoint, GeneralEndpoint.QueryType.GamesQuery)

    }

    override fun networkLost() {
        Log.d(this@StorefrontGames.javaClass.simpleName, "No Network @ ${this@StorefrontGames.javaClass.simpleName}")

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

        favoritedProcess.isFavoriteProductsExist(accountSignIn.firebaseUser!!.uid,
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

        actionCenterOperations.setupForGamesDetails(context = this@StorefrontGames,
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

                actionCenterOperations.setupForGamesStorefront(this@StorefrontGames, it)

            }

        }

        if (!storefrontLayoutBinding.favoritesView.isShown) {

            accountSignIn.firebaseUser?.let {

                favoritedProcess.isFavoriteProductsExist(it.uid,
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

            openPlayStoreToInstall(context = applicationContext,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

    }

}