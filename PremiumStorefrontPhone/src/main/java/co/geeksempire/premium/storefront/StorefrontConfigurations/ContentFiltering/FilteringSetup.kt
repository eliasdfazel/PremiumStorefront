/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/9/21, 5:51 AM
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
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterOptionsItem
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontFeaturedContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.UI.Animations.AnimationListener
import co.geeksempire.premium.storefront.Utils.UI.Animations.CircularRevealAnimation
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.math.absoluteValue

fun Storefront.filteringSetup() {

    if (storefrontLayoutBinding.sortingInclude.root.isShown) {

        storefrontLayoutBinding.sortingInclude.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
        storefrontLayoutBinding.sortingInclude.root.visibility = View.GONE

    }

    if (storefrontLayoutBinding.filteringInclude.root.isShown) {

        storefrontLayoutBinding.filteringInclude.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
        storefrontLayoutBinding.filteringInclude.root.visibility = View.GONE

        storefrontLayoutBinding.rightActionView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_dark))

    } else {

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

fun Storefront.filterByCountriesDataProcess() {

    if (storefrontAllUnfilteredContents.isNotEmpty()) {

        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.VERTICAL, false)
        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.adapter = filterOptionsAdapter

        CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

            var lastLabel = "-666"

            filterOptionsAdapter.filterOptionsData.clear()

            storefrontAllUnfilteredContents.sortedBy {

                it.productAttributes[StorefrontFeaturedContentKey.AttributesDeveloperCountryKey]

            }.asFlow()
                .map {

                    it.productAttributes[StorefrontFeaturedContentKey.AttributesDeveloperCountryKey]
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

fun Storefront.filterByCompatibilitiesDataProcess() {

    if (storefrontAllUnfilteredContents.isNotEmpty()) {

        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.VERTICAL, false)
        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.adapter = filterOptionsAdapter

        CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

            var lastLabel = "-666"

            filterOptionsAdapter.filterOptionsData.clear()

            storefrontAllUnfilteredContents.sortedBy {

                it.productAttributes[StorefrontFeaturedContentKey.AttributesAndroidCompatibilitiesKey]

            }.asFlow()
                .map {

                    it.productAttributes[StorefrontFeaturedContentKey.AttributesAndroidCompatibilitiesKey]
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