/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/6/21, 7:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.animation.Animator
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.SortingOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.UI.Animations.AnimationListener
import co.geeksempire.premium.storefront.Utils.UI.Animations.CircularRevealAnimation
import kotlinx.coroutines.launch

fun Storefront.sortingSetup() {

    if (storefrontLayoutBinding.filteringInclude.root.isShown) {

        storefrontLayoutBinding.filteringInclude.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
        storefrontLayoutBinding.filteringInclude.root.visibility = View.GONE

    }

    if (storefrontLayoutBinding.sortingInclude.root.isShown) {

        storefrontLayoutBinding.sortingInclude.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
        storefrontLayoutBinding.sortingInclude.root.visibility = View.GONE

    } else {

        val animationListener = object : AnimationListener {

            override fun animationFinished() {
                super.animationFinished()



            }

        }

        val circularRevealAnimation = CircularRevealAnimation (animationListener)
        circularRevealAnimation.startForView(context = applicationContext, rootView = storefrontLayoutBinding.sortingInclude.root,
            xPosition = ((storefrontLayoutBinding.leftActionView.x) + (storefrontLayoutBinding.leftActionView.width/2)).toInt(),
            yPosition = ((storefrontLayoutBinding.leftActionView.y) + (storefrontLayoutBinding.leftActionView.height/2)).toInt())

    }

    storefrontLayoutBinding.sortingInclude.root.post {

        storefrontLayoutBinding.sortingInclude.sortPriceView.setOnClickListener {

            storefrontLayoutBinding.sortingInclude.sortSelectedView.animate()
                .translationYBy(-(storefrontLayoutBinding.sortingInclude.sortRateView.y - storefrontLayoutBinding.sortingInclude.sortPriceView.y))
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {

                        Handler(Looper.getMainLooper()).postDelayed({

                            filterAllContent.sortAllContentByInput(allContentAdapter.storefrontContents, SortingOptions.SortByPrice)
                                .invokeOnCompletion {

                                    lifecycleScope.launch {

                                        storefrontLayoutBinding.sortingInclude.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                                        storefrontLayoutBinding.sortingInclude.root.visibility = View.GONE

                                    }

                                }

                        }, 531)

                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationRepeat(animation: Animator?) {}

                }).start()

            storefrontLayoutBinding.sortingInclude.sortPriceView.setTextColor(getColor(R.color.white))
            storefrontLayoutBinding.sortingInclude.sortRateView.setTextColor(getColor(R.color.default_color_bright))

        }

        storefrontLayoutBinding.sortingInclude.sortRateView.setOnClickListener {

            storefrontLayoutBinding.sortingInclude.sortSelectedView.animate()
                .translationYBy((storefrontLayoutBinding.sortingInclude.sortPriceView.y - storefrontLayoutBinding.sortingInclude.sortRateView.y).absoluteValue)
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {

                        Handler(Looper.getMainLooper()).postDelayed({

                            filterAllContent.sortAllContentByInput(allContentAdapter.storefrontContents, SortingOptions.SortByRating)
                                .invokeOnCompletion {

                                    lifecycleScope.launch {

                                        storefrontLayoutBinding.sortingInclude.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                                        storefrontLayoutBinding.sortingInclude.root.visibility = View.GONE

                                    }

                                }

                        }, 531)

                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationRepeat(animation: Animator?) {}

                }).start()

            storefrontLayoutBinding.sortingInclude.sortPriceView.setTextColor(getColor(R.color.default_color_bright))
            storefrontLayoutBinding.sortingInclude.sortRateView.setTextColor(getColor(R.color.white))

        }

    }

}