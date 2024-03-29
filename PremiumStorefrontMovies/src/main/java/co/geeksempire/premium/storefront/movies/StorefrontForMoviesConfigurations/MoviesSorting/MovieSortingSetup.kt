/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 1:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesSorting

import android.animation.Animator
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Utils.UI.Animations.AnimationListener
import co.geeksempire.premium.storefront.Utils.UI.Animations.CircularRevealAnimation
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter.FilterAllMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter.SortingOptions
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.AllMovies.Adapter.AllMoviesAdapter
import co.geeksempire.premium.storefront.movies.databinding.MoviesFilteringLayoutBinding
import co.geeksempire.premium.storefront.movies.databinding.MoviesSortingLayoutBinding
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

fun moviesSortingSetup(context: AppCompatActivity, filterAllMovies: FilterAllMovies,
                       sortingInclude: MoviesSortingLayoutBinding,
                       filteringInclude: MoviesFilteringLayoutBinding,
                       rightActionView: ImageView,
                       middleActionView: ImageView,
                       leftActionView: ImageView,
                       allMoviesAdapter: AllMoviesAdapter,
                       themeType: Boolean = ThemeType.ThemeLight) {

    when (themeType) {
        ThemeType.ThemeLight -> {

            sortingInclude.popupBlurryBackground.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

            sortingInclude.sortsContainerView.background = context.getDrawable(R.drawable.sorting_container_layer_light_movie)

            sortingInclude.sortSelectedView.background = context.getDrawable(R.drawable.sorting_selected_container_layer_light_movie)

        }
        ThemeType.ThemeDark -> {

            sortingInclude.popupBlurryBackground.setOverlayColor(context.getColor(R.color.premiumDarkTransparent))

            sortingInclude.sortsContainerView.background = context.getDrawable(R.drawable.sorting_container_layer_dark_movie)

            sortingInclude.sortSelectedView.background = context.getDrawable(R.drawable.sorting_selected_container_layer_dark_movie)

        }
        else -> {}
    }

    if (filteringInclude.root.isShown) {

        filteringInclude.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out_movie))
        filteringInclude.root.visibility = View.GONE

        rightActionView.background = null

        rightActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.default_color_dark))
        }

        middleActionView.background = null

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

    if (sortingInclude.root.isShown) {

        leftActionView.background = null

        sortingInclude.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out_movie))
        sortingInclude.root.visibility = View.GONE

        leftActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.default_color_dark))
        }

    } else {

        leftActionView.background = context.getDrawable(R.drawable.action_center_glowing_movie)

        leftActionView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.default_color_game_dark))

        val animationListener = object : AnimationListener {

            override fun animationFinished() {
                super.animationFinished()



            }

        }

        val circularRevealAnimation = CircularRevealAnimation (animationListener)
        circularRevealAnimation.startForView(context = context, rootView = sortingInclude.root,
            xPosition = ((leftActionView.x) + (leftActionView.width/2)).toInt(),
            yPosition = ((leftActionView.y) + (leftActionView.height/2)).toInt())

    }

    sortingInclude.root.post {

        sortingInclude.sortPriceView.setOnClickListener {

            sortingInclude.sortSelectedView.animate()
                .translationYBy(-(sortingInclude.sortRateView.y - sortingInclude.sortPriceView.y))
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {

                        Handler(Looper.getMainLooper()).postDelayed({

                            filterAllMovies.sortAllMoviesByInput(allMoviesAdapter.storefrontMoviesContents, SortingOptions.SortByPrice)
                                .invokeOnCompletion {

                                    context.lifecycleScope.launch {

                                        sortingInclude.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out_movie))
                                        sortingInclude.root.visibility = View.GONE

                                    }

                                }

                        }, 531)

                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}

                }).start()

            sortingInclude.sortPriceView.setTextColor(context.getColor(R.color.white))
            sortingInclude.sortRateView.setTextColor(context.getColor(R.color.default_color_bright))

        }

        sortingInclude.sortRateView.setOnClickListener {

            sortingInclude.sortSelectedView.animate()
                .translationYBy((sortingInclude.sortPriceView.y - sortingInclude.sortRateView.y).absoluteValue)
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {

                        Handler(Looper.getMainLooper()).postDelayed({

                            filterAllMovies.sortAllMoviesByInput(allMoviesAdapter.storefrontMoviesContents, SortingOptions.SortByRating)
                                .invokeOnCompletion {

                                    context.lifecycleScope.launch {

                                        sortingInclude.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out_movie))
                                        sortingInclude.root.visibility = View.GONE

                                    }

                                }

                        }, 531)

                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}

                }).start()

            sortingInclude.sortPriceView.setTextColor(context.getColor(R.color.default_color_bright))
            sortingInclude.sortRateView.setTextColor(context.getColor(R.color.white))

        }

    }

}