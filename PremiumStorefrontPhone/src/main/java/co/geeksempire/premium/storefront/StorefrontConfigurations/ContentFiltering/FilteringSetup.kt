/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/5/21, 10:55 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

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

    } else {

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

    val viewTranslateY = (storefrontLayoutBinding.filteringInclude.filterCountryView.y - storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.y).absoluteValue

    storefrontLayoutBinding.filteringInclude.root.post {

        if (filterOptionsAdapter.filterOptionsData.isEmpty()) {

            filterByCountriesDataProcess()

        }

        storefrontLayoutBinding.filteringInclude.filterCountryView.setOnClickListener {

            storefrontLayoutBinding.filteringInclude.filterSelectedView.animate()
                .translationY(-viewTranslateY)
                .apply {
                    interpolator = OvershootInterpolator()
                }.start()

            filterByCountriesDataProcess()

        }

        storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.setOnClickListener {

            storefrontLayoutBinding.filteringInclude.filterSelectedView.animate()
                .translationY((storefrontLayoutBinding.filteringInclude.filterCountryView.y - storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.y).absoluteValue)
                .apply {
                    interpolator = OvershootInterpolator()
                }.start()

            filterByCompatibilitiesDataProcess()

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