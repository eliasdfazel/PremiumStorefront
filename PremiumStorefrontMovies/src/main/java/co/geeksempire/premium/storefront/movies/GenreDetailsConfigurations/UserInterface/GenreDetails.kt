/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/16/21, 9:33 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.UserInterface

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import co.geeksempire.geeksempire.layoutmanager.Scale.ScaleLayoutManager
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.DataStructure.CategoriesDataKeys
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSplitActivity
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.UI.Animations.ShadowAnimation
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractMutedColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import co.geeksempire.premium.storefront.Utils.UI.Views.ControlledScrollView.HorizontalSpacingItemDecoration
import co.geeksempire.premium.storefront.Utils.UI.Views.ControlledScrollView.snappedItemPosition
import co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.Extensions.setupGenreDetailsUserInterface
import co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.UserInterface.UniqueSection.Adapter.UniqueMoviesAdapter
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkEndpoints.MoviesQueryEndpoints
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.AllMovies.Adapter.AllMoviesAdapter
import co.geeksempire.premium.storefront.movies.databinding.GenreDetailsLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.Source
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class GenreDetails : StorefrontSplitActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@GenreDetails)
    }

    val generalEndpoints: GeneralEndpoints = GeneralEndpoints()

    val moviesQueryEndpoints: MoviesQueryEndpoints by lazy {
        MoviesQueryEndpoints(generalEndpoints)
    }

    val moviesStorefrontLiveData: MoviesStorefrontLiveData by lazy {
        ViewModelProvider(this@GenreDetails).get(MoviesStorefrontLiveData::class.java)
    }

    val allMoviesAdapter: AllMoviesAdapter by lazy {
        AllMoviesAdapter(this@GenreDetails)
    }

    val uniqueMoviesAdapter: UniqueMoviesAdapter by lazy {
        UniqueMoviesAdapter(this@GenreDetails)
    }

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@GenreDetails, genreDetailsLayoutBinding.rootView, networkCheckpoint)
    }

    val shadowAnimationUtils = ShadowAnimation()

    lateinit var genreDetailsLayoutBinding: GenreDetailsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreDetailsLayoutBinding = GenreDetailsLayoutBinding.inflate(layoutInflater)
        setContentView(genreDetailsLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@GenreDetails

        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {

                setupGenreDetailsUserInterface(it)

            }

        }

        val scaleLayoutManager = ScaleLayoutManager(applicationContext).apply {
            shrinkAmount =  0.23f
        }
        genreDetailsLayoutBinding.uniqueMoviesGenreRecyclerView.layoutManager = scaleLayoutManager
        genreDetailsLayoutBinding.uniqueMoviesGenreRecyclerView.adapter = uniqueMoviesAdapter

        val uniqueMoviesSnapHelper: SnapHelper = PagerSnapHelper()
        uniqueMoviesSnapHelper.attachToRecyclerView(genreDetailsLayoutBinding.uniqueMoviesGenreRecyclerView)

        genreDetailsLayoutBinding.moviesGenreRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.HORIZONTAL, false)
        genreDetailsLayoutBinding.moviesGenreRecyclerView.adapter = allMoviesAdapter
        genreDetailsLayoutBinding.moviesGenreRecyclerView.addItemDecoration(HorizontalSpacingItemDecoration(applicationContext, 19))

        intent?.let { inputData ->

            genreDetailsLayoutBinding.genreNameTextView.text = inputData.getStringExtra(CategoriesDataKeys.CategoryName)

            Glide.with(applicationContext)
                .load(inputData.getStringExtra(CategoriesDataKeys.CategoryIcon))
                .into(genreDetailsLayoutBinding.genreIconImageView)

            moviesStorefrontLiveData.allUniqueMoviesOfGenre.observe(this@GenreDetails, {

                if (it.isNotEmpty()) {

                    uniqueMoviesAdapter.uniqueMoviesData.clear()
                    uniqueMoviesAdapter.uniqueMoviesData.addAll(it)

                    uniqueMoviesAdapter.notifyDataSetChanged()

                    Handler(Looper.getMainLooper()).postDelayed({

                        scaleLayoutManager.smoothScrollToPosition(genreDetailsLayoutBinding.uniqueMoviesGenreRecyclerView, RecyclerView.State(), 1)

                    }, 999)

                    genreDetailsLayoutBinding.uniqueMoviesGenreRecyclerView.startAnimation(AnimationUtils.loadAnimation(applicationContext, co.geeksempire.premium.storefront.R.anim.fade_in))
                    genreDetailsLayoutBinding.uniqueMoviesGenreRecyclerView.visibility = View.VISIBLE

                    genreDetailsLayoutBinding.movieNameTextView.startAnimation(AnimationUtils.loadAnimation(applicationContext, co.geeksempire.premium.storefront.R.anim.fade_in))
                    genreDetailsLayoutBinding.movieNameTextView.visibility = View.VISIBLE

                    genreDetailsLayoutBinding.movieNameBlurryView.startAnimation(AnimationUtils.loadAnimation(applicationContext, co.geeksempire.premium.storefront.R.anim.fade_in))
                    genreDetailsLayoutBinding.movieNameBlurryView.visibility = View.VISIBLE

                    genreDetailsLayoutBinding.loadingView.visibility = View.GONE

                }

            })

            moviesStorefrontLiveData.allMoviesOfGenre.observe(this@GenreDetails, {

                if (it.isNotEmpty()) {

                    allMoviesAdapter.storefrontMoviesContents.clear()
                    allMoviesAdapter.storefrontMoviesContents.addAll(it)

                    allMoviesAdapter.notifyDataSetChanged()

                    genreDetailsLayoutBinding.moviesGenreRecyclerView.startAnimation(AnimationUtils.loadAnimation(applicationContext, co.geeksempire.premium.storefront.R.anim.fade_in))
                    genreDetailsLayoutBinding.moviesGenreRecyclerView.visibility = View.VISIBLE

                }

            })

            genreDetailsLayoutBinding.uniqueMoviesGenreRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {

                            val selectedPosition = snappedItemPosition(genreDetailsLayoutBinding.uniqueMoviesGenreRecyclerView, uniqueMoviesSnapHelper)

                            uniqueMoviesAdapter.uniqueMoviesData[selectedPosition].data?.let {

                                val moviesDataStructure = MoviesDataStructure(it)

                                genreDetailsLayoutBinding.movieNameTextView.text = moviesDataStructure.movieName().replaceFirst(" ", "\n")

                                Glide.with(applicationContext)
                                    .asDrawable()
                                    .load(moviesDataStructure.moviePoster())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .addListener(object : RequestListener<Drawable> {

                                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                                            return true
                                        }

                                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                                            resource?.let {

                                                runOnUiThread {

                                                    genreDetailsLayoutBinding.moviePosterBackground.setImageDrawable(resource)

                                                    val dominantColor = extractDominantColor(applicationContext, resource)
                                                    val vibrantColor = extractVibrantColor(applicationContext, resource)
                                                    val mutedColor = extractMutedColor(applicationContext, resource)

                                                    shadowAnimationUtils.textShadowValueAnimatorLoop(view = genreDetailsLayoutBinding.movieNameTextView,
                                                        startValue = 0f, endValue = genreDetailsLayoutBinding.movieNameTextView.shadowRadius,
                                                        startDuration = 1357, endDuration = 753,
                                                        shadowColor = genreDetailsLayoutBinding.movieNameTextView.shadowColor, shadowX = genreDetailsLayoutBinding.movieNameTextView.shadowDx, shadowY = genreDetailsLayoutBinding.movieNameTextView.shadowDy,
                                                        numberOfLoop = 3)

                                                }

                                            }

                                            return true
                                        }

                                    })
                                    .submit()

                            }

                        }
                    }

                }

            })

        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {

        overridePendingTransition(0, co.geeksempire.premium.storefront.R.anim.fade_out)
        this@GenreDetails.finish()

    }

    override fun networkAvailable() {
        Log.d(this@GenreDetails.javaClass.simpleName, "Network Available @ ${this@GenreDetails.javaClass.simpleName}")

        intent?.let { inputData ->

            if (intent.hasExtra(CategoriesDataKeys.CategoryName)) {

                val genreName = inputData.getStringExtra(CategoriesDataKeys.CategoryName)

                (application as PremiumStorefrontApplication)
                    .firestoreDatabase
                    .collection(moviesQueryEndpoints.storefrontMoviesGenreCollectionsEndpoint(genreName!!))
                    .get(Source.SERVER).addOnSuccessListener { querySnapshot ->

                        moviesStorefrontLiveData.processAllMoviesOfGenre(querySnapshot)

                    }.addOnFailureListener {



                    }

            }

        }
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