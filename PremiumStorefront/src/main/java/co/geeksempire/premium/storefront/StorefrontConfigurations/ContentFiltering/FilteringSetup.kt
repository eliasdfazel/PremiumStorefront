/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 6:05 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering

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
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterOptionsItem
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilteringOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterAdapter.FilterOptionsAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.Utils.UI.Animations.AnimationListener
import co.geeksempire.premium.storefront.Utils.UI.Animations.CircularRevealAnimation
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import co.geeksempire.premium.storefront.databinding.FilteringLayoutBinding
import co.geeksempire.premium.storefront.databinding.SortingLayoutBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.math.absoluteValue

fun filteringSetup(context: AppCompatActivity,
                   sortingInclude: SortingLayoutBinding,
                   filteringInclude: FilteringLayoutBinding,
                   rightActionView: ImageView,
                   middleActionView: ImageView,
                   leftActionView: ImageView,
                   filterOptionsAdapter: FilterOptionsAdapter,
                   storefrontAllUnfilteredContents: ArrayList<StorefrontContentsData>,
                   themeType: Boolean = ThemeType.ThemeLight) {

    when (themeType) {
        ThemeType.ThemeLight -> {

            filteringInclude.popupBlurryBackground.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

            filteringInclude.filtersContainerView.background = context.getDrawable(R.drawable.filtering_container_layer_light)

            filteringInclude.filterSelectedView.background = context.getDrawable(R.drawable.filtering_selected_container_layer_light)

        }
        ThemeType.ThemeDark -> {

            filteringInclude.popupBlurryBackground.setOverlayColor(context.getColor(R.color.premiumDarkTransparent))

            filteringInclude.filtersContainerView.background = context.getDrawable(R.drawable.filtering_container_layer_dark)

            filteringInclude.filterSelectedView.background = context.getDrawable(R.drawable.filtering_selected_container_layer_dark)

        }
    }

    if (sortingInclude.root.isShown) {

        sortingInclude.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
        sortingInclude.root.visibility = View.GONE

        leftActionView.background = context.getDrawable(R.drawable.action_center_glowing)

        leftActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.default_color_dark))
        }

        middleActionView.background = context.getDrawable(R.drawable.action_center_glowing)

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

    if (filteringInclude.root.isShown) {

        rightActionView.background = null

        filteringInclude.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
        filteringInclude.root.visibility = View.GONE

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

        rightActionView.background = context.getDrawable(R.drawable.action_center_glowing)

        rightActionView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.default_color_game_dark))

        val animationListener = object : AnimationListener {

            override fun animationFinished() {
                super.animationFinished()



            }

        }

        val circularRevealAnimation = CircularRevealAnimation (animationListener)
        circularRevealAnimation.startForView(context = context, rootView = filteringInclude.root,
            xPosition = ((rightActionView.x) + (rightActionView.width/2)).toInt(),
            yPosition = ((rightActionView.y) + (rightActionView.height/2)).toInt())

    }

    filteringInclude.root.post {

        if (filterOptionsAdapter.filterOptionsData.isEmpty()) {

            filterByCountriesDataProcess(context,
                storefrontAllUnfilteredContents,
                filteringInclude,
                filterOptionsAdapter)

        }

        filteringInclude.filterCountryView.setOnClickListener {

            filteringInclude.filterSelectedView.animate()
                .translationYBy(-(filteringInclude.filterCompatibilitiesView.y - filteringInclude.filterCountryView.y))
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {

                        filterByCountriesDataProcess(context,
                            storefrontAllUnfilteredContents,
                            filteringInclude,
                            filterOptionsAdapter)

                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationRepeat(animation: Animator?) {}

                }).start()

            filteringInclude.filterCountryView.setTextColor(context.getColor(R.color.white))
            filteringInclude.filterCompatibilitiesView.setTextColor(context.getColor(R.color.default_color_bright))

        }

        filteringInclude.filterCompatibilitiesView.setOnClickListener {

            filteringInclude.filterSelectedView.animate()
                .translationYBy((filteringInclude.filterCountryView.y - filteringInclude.filterCompatibilitiesView.y).absoluteValue)
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {

                        filterByCompatibilitiesDataProcess(context,
                            storefrontAllUnfilteredContents,
                            filteringInclude,
                            filterOptionsAdapter)

                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationRepeat(animation: Animator?) {}

                }).start()

            filteringInclude.filterCountryView.setTextColor(context.getColor(R.color.default_color_bright))
            filteringInclude.filterCompatibilitiesView.setTextColor(context.getColor(R.color.white))

        }

    }

}

fun filterByCountriesDataProcess(context: Context,
                                 storefrontAllUnfilteredContents: ArrayList<StorefrontContentsData>,
                                 filteringInclude: FilteringLayoutBinding,
                                 filterOptionsAdapter: FilterOptionsAdapter) {

    if (storefrontAllUnfilteredContents.isNotEmpty()) {

        filteringInclude.filteringOptionsRecyclerView.layoutManager = RecycleViewSmoothLayoutList(context, RecyclerView.VERTICAL, false)
        filteringInclude.filteringOptionsRecyclerView.adapter = filterOptionsAdapter

        filterOptionsAdapter.filterOptionsType = FilteringOptions.FilterByCountry

        CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

            var lastLabel = "-666"

            filterOptionsAdapter.filterOptionsData.clear()

            storefrontAllUnfilteredContents.sortedBy {

                it.productAttributes[ProductsContentKey.AttributesDeveloperCountryKey]

            }.asFlow()
                .map {

                    it.productAttributes[ProductsContentKey.AttributesDeveloperCountryKey]
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

                filterOptionsAdapter.notifyDataSetChanged()

            }

        }

    }

}

fun filterByCompatibilitiesDataProcess(context: Context,
                                       storefrontAllUnfilteredContents: ArrayList<StorefrontContentsData>,
                                       filteringInclude: FilteringLayoutBinding,
                                       filterOptionsAdapter: FilterOptionsAdapter) {

    if (storefrontAllUnfilteredContents.isNotEmpty()) {

        filteringInclude.filteringOptionsRecyclerView.layoutManager = RecycleViewSmoothLayoutList(context, RecyclerView.VERTICAL, false)
        filteringInclude.filteringOptionsRecyclerView.adapter = filterOptionsAdapter

        filterOptionsAdapter.filterOptionsType = FilteringOptions.FilterByCountry

        CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

            var lastLabel = "-666"

            filterOptionsAdapter.filterOptionsData.clear()

            storefrontAllUnfilteredContents.sortedBy {

                it.productAttributes[ProductsContentKey.AttributesAndroidCompatibilitiesKey]

            }.asFlow()
                .map {

                    it.productAttributes[ProductsContentKey.AttributesAndroidCompatibilitiesKey]
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

                filterOptionsAdapter.notifyDataSetChanged()

            }

        }

    }

}