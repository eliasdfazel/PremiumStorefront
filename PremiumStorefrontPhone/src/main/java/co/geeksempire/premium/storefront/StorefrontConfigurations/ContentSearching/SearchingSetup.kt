/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/9/21, 4:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import com.abanabsalan.aban.magazine.Utils.System.showKeyboard
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun Storefront.searchingSetup() {

    val valueAnimatorScalesUpWidth = ValueAnimator.ofInt(dpToInteger(applicationContext, 51), dpToInteger(applicationContext, 313))
    valueAnimatorScalesUpWidth.duration = 777
    valueAnimatorScalesUpWidth.addUpdateListener { animator ->
        val animatorValue = animator.animatedValue as Int

        storefrontLayoutBinding.textInputSearchView.layoutParams.width = animatorValue
        storefrontLayoutBinding.textInputSearchView.requestLayout()
    }
    valueAnimatorScalesUpWidth.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {

            storefrontLayoutBinding.searchView.requestFocus()

            showKeyboard(applicationContext, storefrontLayoutBinding.searchView)

        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }
    })

    val valueAnimatorScalesUpHeight = ValueAnimator.ofInt(dpToInteger(applicationContext, 43), dpToInteger(applicationContext, 77))
    valueAnimatorScalesUpHeight.duration = 333
    valueAnimatorScalesUpHeight.addUpdateListener { animator ->
        val animatorValue = animator.animatedValue as Int

        storefrontLayoutBinding.textInputSearchView.layoutParams.height= animatorValue
        storefrontLayoutBinding.textInputSearchView.requestLayout()
    }
    valueAnimatorScalesUpHeight.addListener(object : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {

            valueAnimatorScalesUpWidth.start()

        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }

    })

    val slideUpAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_in_bottom)
    storefrontLayoutBinding.textInputSearchView.visibility = View.VISIBLE
    storefrontLayoutBinding.textInputSearchView.startAnimation(slideUpAnimation)

    slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {

            valueAnimatorScalesUpHeight.start()

        }

        override fun onAnimationRepeat(animation: Animation?) {}

    })

    storefrontLayoutBinding.searchView.setOnEditorActionListener { textView, actionId, event ->

        when (actionId) {
            EditorInfo.IME_ACTION_SEARCH -> {

                filterAllContent.searchThroughAllContent(storefrontAllUnfilteredContents, textView.text.toString())
                    .invokeOnCompletion {

                    }

            }
        }

        false
    }

}