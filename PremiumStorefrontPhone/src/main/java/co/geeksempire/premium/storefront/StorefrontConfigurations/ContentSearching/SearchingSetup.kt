/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/21/21, 4:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import com.abanabsalan.aban.magazine.Utils.System.hideKeyboard
import com.abanabsalan.aban.magazine.Utils.System.showKeyboard
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun Storefront.searchingSetup(themeType: Boolean = ThemeType.ThemeLight) {

    storefrontLayoutBinding.leftActionView.background = null
    storefrontLayoutBinding.rightActionView.background = null

    when (themeType) {
        ThemeType.ThemeLight -> {

            storefrontLayoutBinding.textInputSearchView.boxBackgroundColor = getColor(R.color.premiumLight)

            storefrontLayoutBinding.searchView.setTextColor(getColor(R.color.default_color_dark))
            storefrontLayoutBinding.searchView.setHintTextColor(getColor(R.color.default_color_game_dark))

        }
        ThemeType.ThemeDark -> {

            storefrontLayoutBinding.textInputSearchView.boxBackgroundColor = getColor(R.color.premiumDark)

            storefrontLayoutBinding.searchView.setTextColor(getColor(R.color.default_color_light))
            storefrontLayoutBinding.searchView.setHintTextColor(getColor(R.color.default_color_game_light))

        }
    }

    if (storefrontLayoutBinding.textInputSearchView.isShown) {

        storefrontLayoutBinding.middleActionView.background = null

        val fadeOutAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)

        storefrontLayoutBinding.textInputSearchView.visibility = View.INVISIBLE
        storefrontLayoutBinding.textInputSearchView.startAnimation(fadeOutAnimation)

        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                storefrontLayoutBinding.textInputSearchView.layoutParams.width = dpToInteger(applicationContext, 51)
                storefrontLayoutBinding.textInputSearchView.layoutParams.height = dpToInteger(applicationContext, 51)
                storefrontLayoutBinding.textInputSearchView.requestLayout()

            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })

        storefrontLayoutBinding.middleActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(getColor(R.color.default_color_bright))

            } else -> ColorStateList.valueOf(getColor(R.color.default_color_dark))
        }

        hideKeyboard(applicationContext, storefrontLayoutBinding.searchView)

    } else {

        storefrontLayoutBinding.middleActionView.background = applicationContext.getDrawable(R.drawable.action_center_glowing)

        storefrontLayoutBinding.middleActionView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_game_dark))

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

            storefrontLayoutBinding.textInputSearchView.layoutParams.height = animatorValue
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

}