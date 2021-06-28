/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/28/21, 2:10 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.animation.Animator
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.ContentFiltering.Filter.FilterOptionsItem
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.StorefrontApplications
import co.geeksempire.premium.storefront.Utils.UI.Animations.AnimationListener
import co.geeksempire.premium.storefront.Utils.UI.Animations.CircularRevealAnimation
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.math.absoluteValue

fun StorefrontApplications.filteringSetup(themeType: Boolean = ThemeType.ThemeLight) {

    when (themeType) {
        ThemeType.ThemeLight -> {

            storefrontLayoutBinding.filteringInclude.popupBlurryBackground.setOverlayColor(getColor(R.color.premiumLightTransparent))

            storefrontLayoutBinding.filteringInclude.filtersContainerView.background = getDrawable(R.drawable.filtering_container_layer_light)

            storefrontLayoutBinding.filteringInclude.filterSelectedView.background = getDrawable(R.drawable.filtering_selected_container_layer_light)

        }
        ThemeType.ThemeDark -> {

            storefrontLayoutBinding.filteringInclude.popupBlurryBackground.setOverlayColor(getColor(R.color.premiumDarkTransparent))

            storefrontLayoutBinding.filteringInclude.filtersContainerView.background = getDrawable(R.drawable.filtering_container_layer_dark)

            storefrontLayoutBinding.filteringInclude.filterSelectedView.background = getDrawable(R.drawable.filtering_selected_container_layer_dark)

        }
    }

    if (storefrontLayoutBinding.sortingInclude.root.isShown) {

        storefrontLayoutBinding.sortingInclude.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
        storefrontLayoutBinding.sortingInclude.root.visibility = View.GONE

        storefrontLayoutBinding.leftActionView.background = applicationContext.getDrawable(R.drawable.action_center_glowing)

        storefrontLayoutBinding.leftActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(getColor(R.color.default_color_dark))
        }

        storefrontLayoutBinding.middleActionView.background = applicationContext.getDrawable(R.drawable.action_center_glowing)

        storefrontLayoutBinding.middleActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(getColor(R.color.default_color_dark))
        }

    }

    if (storefrontLayoutBinding.filteringInclude.root.isShown) {

        storefrontLayoutBinding.rightActionView.background = null

        storefrontLayoutBinding.filteringInclude.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
        storefrontLayoutBinding.filteringInclude.root.visibility = View.GONE

        storefrontLayoutBinding.rightActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(getColor(R.color.default_color_dark))
        }

    } else {

        storefrontLayoutBinding.rightActionView.background = applicationContext.getDrawable(R.drawable.action_center_glowing)

        storefrontLayoutBinding.rightActionView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_game_dark))

        val animationListener = object : AnimationListener {

            override fun animationFinished() {
                super.animationFinished()



            }

        }

        val circularRevealAnimation = CircularRevealAnimation (animationListener)
        circularRevealAnimation.startForView(context = applicationContext, rootView = storefrontLayoutBinding.filteringInclude.root,
            xPosition = ((storefrontLayoutBinding.rightActionView.x) + (storefrontLayoutBinding.rightActionView.width/2)).toInt(),
            yPosition = ((storefrontLayoutBinding.rightActionView.y) + (storefrontLayoutBinding.rightActionView.height/2)).toInt())

    }

    storefrontLayoutBinding.filteringInclude.root.post {

        if (filterOptionsAdapter.filterOptionsData.isEmpty()) {

            filterByCountriesDataProcess()

        }

        storefrontLayoutBinding.filteringInclude.filterCountryView.setOnClickListener {

            storefrontLayoutBinding.filteringInclude.filterSelectedView.animate()
                .translationYBy(-(storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.y - storefrontLayoutBinding.filteringInclude.filterCountryView.y))
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {

                        filterByCountriesDataProcess()

                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationRepeat(animation: Animator?) {}

                }).start()

            storefrontLayoutBinding.filteringInclude.filterCountryView.setTextColor(getColor(R.color.white))
            storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.setTextColor(getColor(R.color.default_color_bright))

        }

        storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.setOnClickListener {

            storefrontLayoutBinding.filteringInclude.filterSelectedView.animate()
                .translationYBy((storefrontLayoutBinding.filteringInclude.filterCountryView.y - storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.y).absoluteValue)
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {

                        filterByCompatibilitiesDataProcess()

                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationRepeat(animation: Animator?) {}

                }).start()

            storefrontLayoutBinding.filteringInclude.filterCountryView.setTextColor(getColor(R.color.default_color_bright))
            storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.setTextColor(getColor(R.color.white))

        }

    }

}

fun StorefrontApplications.filterByCountriesDataProcess() {

    if (storefrontAllUnfilteredContents.isNotEmpty()) {

        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.VERTICAL, false)
        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.adapter = filterOptionsAdapter

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

fun StorefrontApplications.filterByCompatibilitiesDataProcess() {

    if (storefrontAllUnfilteredContents.isNotEmpty()) {

        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.VERTICAL, false)
        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.adapter = filterOptionsAdapter

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