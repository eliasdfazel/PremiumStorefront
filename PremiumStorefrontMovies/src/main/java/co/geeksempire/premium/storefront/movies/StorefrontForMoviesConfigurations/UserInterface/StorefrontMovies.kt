/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/4/21, 6:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface

import android.app.ActivityOptions
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountData
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountSignIn
import co.geeksempire.premium.storefront.Actions.Operation.ActionCenterOperations
import co.geeksempire.premium.storefront.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoriteProductQueryInterface
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoritedProcess
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSplitActivity
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.Extensions.setupStorefrontMoviesUserInterface
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.Extensions.storefrontMoviesUserInteractionSetup
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations.retrieveFeaturedMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations.retrieveGenreMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.Adapter.FeaturedMoviesAdapter
import co.geeksempire.premium.storefront.movies.databinding.StorefrontMoviesLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.geeksempire.balloon.optionsmenu.library.BalloonOptionsMenu
import java.util.*

class StorefrontMovies : StorefrontSplitActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@StorefrontMovies)
    }

    val moviesStorefrontLiveData: MoviesStorefrontLiveData by lazy {
        ViewModelProvider(this@StorefrontMovies).get(MoviesStorefrontLiveData::class.java)
    }

    val prepareActionCenterUserInterface: PrepareActionCenterUserInterface by lazy {
        PrepareActionCenterUserInterface(context = applicationContext, actionCenterView = storefrontMoviesLayoutBinding.actionCenterView, actionLeftView = storefrontMoviesLayoutBinding.leftActionView, actionMiddleView = storefrontMoviesLayoutBinding.middleActionView, actionRightView = storefrontMoviesLayoutBinding.rightActionView)
    }

    val balloonOptionsMenu: BalloonOptionsMenu by lazy {
        BalloonOptionsMenu(context = this@StorefrontMovies,
            rootView = storefrontMoviesLayoutBinding.rootView)
    }

    val actionCenterOperations: ActionCenterOperations by lazy {
        ActionCenterOperations()
    }

    val favoritedProcess: FavoritedProcess by lazy {
        FavoritedProcess(this@StorefrontMovies)
    }

    val featuredMoviesAdapter: FeaturedMoviesAdapter by lazy {
        FeaturedMoviesAdapter(this@StorefrontMovies)
    }

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@StorefrontMovies, storefrontMoviesLayoutBinding.rootView, networkCheckpoint)
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

            }

        }

        storefrontMoviesLayoutBinding.root.post {

            storefrontMoviesLayoutBinding.featuredContentRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.HORIZONTAL, false)
            storefrontMoviesLayoutBinding.featuredContentRecyclerView.adapter = featuredMoviesAdapter

            val snapHelper: SnapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(storefrontMoviesLayoutBinding.featuredContentRecyclerView)

            moviesStorefrontLiveData.featuredContentItemData.observe(this@StorefrontMovies, {

                featuredMoviesAdapter.featuredMoviesData.clear()
                featuredMoviesAdapter.featuredMoviesData.addAll(it)

                featuredMoviesAdapter.notifyItemRangeInserted(0, featuredMoviesAdapter.featuredMoviesData.size)

                if (storefrontMoviesLayoutBinding.loadingView.isVisible) {

                    storefrontMoviesLayoutBinding.loadingView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                    storefrontMoviesLayoutBinding.loadingView.visibility = View.GONE

                }

                if (storefrontMoviesLayoutBinding.featuredContentRecyclerView.isGone) {

                    storefrontMoviesLayoutBinding.featuredContentRecyclerView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    storefrontMoviesLayoutBinding.featuredContentRecyclerView.visibility = View.VISIBLE

                }

            })

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
        Log.d(this@StorefrontMovies.javaClass.simpleName, "Network Available @ ${this@StorefrontMovies.javaClass.simpleName}")

        retrieveGenreMovies(this@StorefrontMovies, moviesStorefrontLiveData)

        retrieveFeaturedMovies(this@StorefrontMovies, moviesStorefrontLiveData)

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

            openPlayStoreToInstall(context = applicationContext,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

    }

}