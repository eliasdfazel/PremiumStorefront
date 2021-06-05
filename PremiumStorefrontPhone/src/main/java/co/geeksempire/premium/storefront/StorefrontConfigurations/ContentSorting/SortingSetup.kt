/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/5/21, 11:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.view.View
import android.view.animation.AnimationUtils
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.UI.Animations.AnimationListener
import co.geeksempire.premium.storefront.Utils.UI.Animations.CircularRevealAnimation

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

//    filterAllContent.sortAllContentByInput(allContentAdapter.storefrontContents, SortingOptions.SortByRating)
//        .invokeOnCompletion {
//
//        }

}