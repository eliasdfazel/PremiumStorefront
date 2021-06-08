/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/8/21, 11:57 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun Storefront.searchingSetup() {

    val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

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

            inputMethodManager.showSoftInput(
                storefrontLayoutBinding.searchView,
                InputMethodManager.SHOW_IMPLICIT
            )

        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }
    })

    val valueAnimatorScalesUpHeight = ValueAnimator.ofInt(dpToInteger(applicationContext, 51), dpToInteger(applicationContext, 77))
    valueAnimatorScalesUpHeight.duration = 777
    valueAnimatorScalesUpHeight.addUpdateListener { animator ->
        val animatorValue = animator.animatedValue as Int

//        storefrontLayoutBinding.textInputSearchView.layoutParams.height =
        storefrontLayoutBinding.textInputSearchView.layoutParams.width = animatorValue
        storefrontLayoutBinding.textInputSearchView.requestLayout()
    }
    valueAnimatorScalesUpHeight.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {

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

            valueAnimatorScalesUpWidth.start()
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