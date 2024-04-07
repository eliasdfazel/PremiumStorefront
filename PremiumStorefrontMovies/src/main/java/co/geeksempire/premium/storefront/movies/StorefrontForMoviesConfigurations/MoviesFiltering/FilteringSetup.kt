/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 12:56 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering

import android.animation.Animator
import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Utils.UI.Animations.AnimationListener
import co.geeksempire.premium.storefront.Utils.UI.Animations.CircularRevealAnimation
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter.FilterOptionsItem
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter.FilteringOptions
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.FilterAdapter.FilterOptionsAdapter
import co.geeksempire.premium.storefront.movies.databinding.MoviesFilteringLayoutBinding
import co.geeksempire.premium.storefront.movies.databinding.MoviesSortingLayoutBinding
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

fun moviesFilteringSetup(context: AppCompatActivity,
                         moviesSortingLayoutBinding: MoviesSortingLayoutBinding,
                         moviesFilteringLayoutBinding: MoviesFilteringLayoutBinding,
                         rightActionView: ImageView,
                         middleActionView: ImageView,
                         leftActionView: ImageView,
                         filterOptionsAdapter: FilterOptionsAdapter,
                         storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot>,
                         themeType: Boolean = ThemeType.ThemeLight) {

    when (themeType) {
        ThemeType.ThemeLight -> {

            moviesFilteringLayoutBinding.popupBlurryBackground.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

            moviesFilteringLayoutBinding.filtersContainerView.background = context.getDrawable(R.drawable.filtering_container_layer_light_movie)

            moviesFilteringLayoutBinding.filterSelectedView.background = context.getDrawable(R.drawable.filtering_selected_container_layer_light_movie)

        }
        ThemeType.ThemeDark -> {

            moviesFilteringLayoutBinding.popupBlurryBackground.setOverlayColor(context.getColor(R.color.premiumDarkTransparent))

            moviesFilteringLayoutBinding.filtersContainerView.background = context.getDrawable(R.drawable.filtering_container_layer_dark_movie)

            moviesFilteringLayoutBinding.filterSelectedView.background = context.getDrawable(R.drawable.filtering_selected_container_layer_dark_movie)

        }
        else -> {}
    }

    if (moviesSortingLayoutBinding.root.isShown) {

        moviesSortingLayoutBinding.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out_movie))
        moviesSortingLayoutBinding.root.visibility = View.GONE

        leftActionView.background = context.getDrawable(R.drawable.action_center_glowing_movie)

        leftActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.default_color_dark))
        }

        middleActionView.background = context.getDrawable(R.drawable.action_center_glowing_movie)

        middleActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.default_color_dark))
        }

    }

    if (moviesFilteringLayoutBinding.root.isShown) {

        rightActionView.background = null

        moviesFilteringLayoutBinding.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out_movie))
        moviesFilteringLayoutBinding.root.visibility = View.GONE

        rightActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.default_color_dark))
        }

    } else {

        rightActionView.background = context.getDrawable(R.drawable.action_center_glowing_movie)

        rightActionView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.default_color_game_dark))

        val animationListener = object : AnimationListener {

            override fun animationFinished() {
                super.animationFinished()



            }

        }

        val circularRevealAnimation = CircularRevealAnimation (animationListener)
        circularRevealAnimation.startForView(context = context, rootView = moviesFilteringLayoutBinding.root,
            xPosition = ((rightActionView.x) + (rightActionView.width/2)).toInt(),
            yPosition = ((rightActionView.y) + (rightActionView.height/2)).toInt())

    }

    moviesFilteringLayoutBinding.root.post {

        if (filterOptionsAdapter.filterOptionsData.isEmpty()) {

            filterByDirectorsDataProcess(context,
                storefrontAllUnfilteredContents,
                moviesFilteringLayoutBinding,
                filterOptionsAdapter)

        }

        moviesFilteringLayoutBinding.filterDirectorView.setOnClickListener {

            moviesFilteringLayoutBinding.filterSelectedView.animate()
                .translationYBy(-(moviesFilteringLayoutBinding.filterStudioView.y - moviesFilteringLayoutBinding.filterDirectorView.y))
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {

                        filterByDirectorsDataProcess(context,
                            storefrontAllUnfilteredContents,
                            moviesFilteringLayoutBinding,
                            filterOptionsAdapter)

                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}

                }).start()

            moviesFilteringLayoutBinding.filterDirectorView.setTextColor(context.getColor(R.color.white))
            moviesFilteringLayoutBinding.filterStudioView.setTextColor(context.getColor(R.color.default_color_bright))

        }

        moviesFilteringLayoutBinding.filterStudioView.setOnClickListener {

            moviesFilteringLayoutBinding.filterSelectedView.animate()
                .translationYBy((moviesFilteringLayoutBinding.filterDirectorView.y - moviesFilteringLayoutBinding.filterStudioView.y).absoluteValue)
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {

                        filterByStudioDataProcess(context,
                            storefrontAllUnfilteredContents,
                            moviesFilteringLayoutBinding,
                            filterOptionsAdapter)

                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}

                }).start()

            moviesFilteringLayoutBinding.filterDirectorView.setTextColor(context.getColor(R.color.default_color_bright))
            moviesFilteringLayoutBinding.filterStudioView.setTextColor(context.getColor(R.color.white))

        }

    }

}


fun filterByDirectorsDataProcess(context: Context,
                                 storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot>,
                                 filteringInclude: MoviesFilteringLayoutBinding,
                                 filterOptionsAdapter: FilterOptionsAdapter) {

    if (storefrontAllUnfilteredContents.isNotEmpty()) {

        filteringInclude.filteringOptionsRecyclerView.layoutManager = RecycleViewSmoothLayoutList(context, RecyclerView.VERTICAL, false)
        filteringInclude.filteringOptionsRecyclerView.adapter = filterOptionsAdapter

        filterOptionsAdapter.filterOptionsType = FilteringOptions.FilterByDirector

        CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

            var lastLabel = "-666"

            filterOptionsAdapter.filterOptionsData.clear()

            storefrontAllUnfilteredContents.sortedBy {

                it.data?.let { documentSnapshot ->
                    val moviesDataStructure = MoviesDataStructure(documentSnapshot)

                    moviesDataStructure.movieDirectors()
                }

            }.asFlow()
                .map {

                    it.data?.let { documentSnapshot ->
                        val moviesDataStructure = MoviesDataStructure(documentSnapshot)

                        moviesDataStructure.movieDirectors()
                    }

                }
                .flowOn(Dispatchers.IO)
                .onCompletion {

                }
                .collect { countryName ->


                    countryName?.let {

                        if (countryName != lastLabel) {

                            filterOptionsAdapter.filterOptionsData.add(FilterOptionsItem(it, null))

                        }

                        lastLabel = countryName

                    }

                }

            withContext(SupervisorJob() + Dispatchers.Main) {

                if (filterOptionsAdapter.filterOptionsData.isNotEmpty()) {

                    filterOptionsAdapter.notifyDataSetChanged()

                }

            }

        }

    }

}

fun filterByStudioDataProcess(context: Context,
                                       storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot>,
                                       filteringInclude: MoviesFilteringLayoutBinding,
                                       filterOptionsAdapter: FilterOptionsAdapter) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

    if (storefrontAllUnfilteredContents.isNotEmpty()) {

        val filterOptionsData: ArrayList<FilterOptionsItem> = ArrayList<FilterOptionsItem>()

        filterOptionsAdapter.filterOptionsData.clear()

        storefrontAllUnfilteredContents.forEachIndexed { index, documentSnapshot ->

            documentSnapshot.data?.let { documentSnapshot ->

                val moviesDataStructure = MoviesDataStructure(documentSnapshot)

                val csvMoviesStudio = moviesDataStructure.movieStudios().split(",")

                csvMoviesStudio.forEach {

                    filterOptionsData.add(FilterOptionsItem(it, null))

                }

            }

        }

        filterOptionsAdapter.filterOptionsData.addAll(filterOptionsData.toSet().toList())

        filterOptionsData.clear()


        withContext(SupervisorJob() + Dispatchers.Main) {

            filterOptionsAdapter.filterOptionsType = FilteringOptions.FilterByStudio

            filteringInclude.filteringOptionsRecyclerView.layoutManager = RecycleViewSmoothLayoutList(context, RecyclerView.VERTICAL, false)
            filteringInclude.filteringOptionsRecyclerView.adapter = filterOptionsAdapter

            if (filterOptionsAdapter.filterOptionsData.isNotEmpty()) {

                filterOptionsAdapter.notifyDataSetChanged()

            }

        }

    }

}