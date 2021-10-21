/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/21/21, 9:19 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.ContentSearching

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterAllContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.android.Utils.System.hideKeyboard
import co.geeksempire.premium.storefront.android.Utils.System.showKeyboard
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun searchingSetup(context: AppCompatActivity, filterAllContent: FilterAllContent,
                   textInputSearchView: TextInputLayout,
                   searchView: TextInputEditText,
                   rightActionView: ImageView,
                   middleActionView: ImageView,
                   leftActionView: ImageView,
                   storefrontAllUnfilteredContents: ArrayList<StorefrontContentsData>,
                   themeType: Boolean = ThemeType.ThemeLight) {

    leftActionView.background = null
    rightActionView.background = null

    when (themeType) {
        ThemeType.ThemeLight -> {

            textInputSearchView.boxBackgroundColor = context.getColor(R.color.premiumLight)

            searchView.setTextColor(context.getColor(R.color.default_color_dark))
            searchView.setHintTextColor(context.getColor(R.color.default_color_game_dark))

        }
        ThemeType.ThemeDark -> {

            textInputSearchView.boxBackgroundColor = context.getColor(R.color.premiumDark)

            searchView.setTextColor(context.getColor(R.color.default_color_light))
            searchView.setHintTextColor(context.getColor(R.color.default_color_game_light))

        }
    }

    if (textInputSearchView.isShown) {

        hideSearchView(context,
            textInputSearchView,
            searchView,
            middleActionView,
            themeType)

    } else {

        middleActionView.background = context.getDrawable(R.drawable.action_center_glowing)

        middleActionView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.default_color_game_dark))

        val valueAnimatorScalesUpWidth = ValueAnimator.ofInt(dpToInteger(context, 51), dpToInteger(context, 313))
        valueAnimatorScalesUpWidth.duration = 777
        valueAnimatorScalesUpWidth.addUpdateListener { animator ->
            val animatorValue = animator.animatedValue as Int

            textInputSearchView.layoutParams.width = animatorValue
            textInputSearchView.requestLayout()
        }
        valueAnimatorScalesUpWidth.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

                searchView.requestFocus()

                showKeyboard(context, searchView)

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        val valueAnimatorScalesUpHeight = ValueAnimator.ofInt(dpToInteger(context, 43), dpToInteger(context, 77))
        valueAnimatorScalesUpHeight.duration = 333
        valueAnimatorScalesUpHeight.addUpdateListener { animator ->
            val animatorValue = animator.animatedValue as Int

            textInputSearchView.layoutParams.height = animatorValue
            textInputSearchView.requestLayout()
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

        val slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_from_bottom_bounce)
        textInputSearchView.visibility = View.VISIBLE
        textInputSearchView.startAnimation(slideUpAnimation)

        slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                valueAnimatorScalesUpHeight.start()
                valueAnimatorScalesUpWidth.start()

            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })

        searchView.setOnEditorActionListener { textView, actionId, event ->

            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {

                    (context.application as PremiumStorefrontApplication).firebaseAnalytics.logEvent(
                        Firebase.auth.currentUser?.uid?:"Anonymous", Bundle().apply {
                        putString("SearchQuery", textView.text.toString())
                    })

                    filterAllContent.searchThroughAllContent(storefrontAllUnfilteredContents, textView.text.toString())
                        .invokeOnCompletion {

                            hideSearchView(context,
                                textInputSearchView,
                                searchView,
                                middleActionView,
                                themeType)

                        }

                }
            }

            false
        }

    }

}

fun hideSearchView(context: AppCompatActivity,
                   textInputSearchView: TextInputLayout,
                   searchView: TextInputEditText,
                   middleActionView: ImageView,
                   themeType: Boolean) = CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {

    middleActionView.background = null

    val fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)

    textInputSearchView.visibility = View.INVISIBLE
    textInputSearchView.startAnimation(fadeOutAnimation)

    fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {

            textInputSearchView.layoutParams.width = dpToInteger(context, 51)
            textInputSearchView.layoutParams.height = dpToInteger(context, 51)
            textInputSearchView.requestLayout()

        }

        override fun onAnimationRepeat(animation: Animation?) {}

    })

    middleActionView.imageTintList = when (themeType) {
        ThemeType.ThemeLight -> {

            ColorStateList.valueOf(context.getColor(R.color.default_color_dark))

        }
        ThemeType.ThemeDark -> {

            ColorStateList.valueOf(context.getColor(R.color.default_color_bright))

        } else -> ColorStateList.valueOf(context.getColor(R.color.default_color_dark))
    }

    hideKeyboard(context, searchView)

}