/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/18/21, 4:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.Extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.CompleteSearch
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.android.Utils.System.hideKeyboard
import co.geeksempire.premium.storefront.android.Utils.System.showKeyboard
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun CompleteSearch.setupSearchView(themeType: Boolean) {

    when (themeType) {
        ThemeType.ThemeLight -> {

            completeSearchLayoutBinding.textInputSearchView.boxBackgroundColor = getColor(R.color.premiumLight)

            completeSearchLayoutBinding.searchView.setTextColor(getColor(R.color.default_color_dark))
            completeSearchLayoutBinding.searchView.setHintTextColor(getColor(R.color.default_color_game_dark))

            completeSearchLayoutBinding.searchIconCompleteSearch.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_dark))

        }
        ThemeType.ThemeDark -> {

            completeSearchLayoutBinding.textInputSearchView.boxBackgroundColor = getColor(R.color.premiumDark)

            completeSearchLayoutBinding.searchView.setTextColor(getColor(R.color.default_color_light))
            completeSearchLayoutBinding.searchView.setHintTextColor(getColor(R.color.default_color_game_light))

            completeSearchLayoutBinding.searchIconCompleteSearch.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_bright))

        }
        else -> {}
    }

    val hideSearchViewAction = {
        completeSearchLayoutBinding.searchIconCompleteSearch.background = getDrawable(R.drawable.ripple_cancel_search)

        completeSearchLayoutBinding.searchIconCompleteSearch.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_dark))

        val fadeOutAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)

        completeSearchLayoutBinding.textInputSearchView.visibility = View.INVISIBLE
        completeSearchLayoutBinding.textInputSearchView.startAnimation(fadeOutAnimation)

        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                completeSearchLayoutBinding.textInputSearchView.layoutParams.width = dpToInteger(applicationContext, 51)
                completeSearchLayoutBinding.textInputSearchView.layoutParams.height = dpToInteger(applicationContext, 51)
                completeSearchLayoutBinding.textInputSearchView.requestLayout()

            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })

        completeSearchLayoutBinding.searchIconCompleteSearch.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(getColor(R.color.default_color_bright))

            } else -> ColorStateList.valueOf(getColor(R.color.default_color_dark))
        }

        hideKeyboard(applicationContext, completeSearchLayoutBinding.searchView)
    }

    if (completeSearchLayoutBinding.textInputSearchView.isShown) {

        hideSearchViewAction.invoke()

    } else {

        completeSearchLayoutBinding.searchIconCompleteSearch.background = getDrawable(R.drawable.action_center_glowing)

        completeSearchLayoutBinding.searchIconCompleteSearch.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_game_dark))

        val valueAnimatorScalesUpWidth = ValueAnimator.ofInt(dpToInteger(applicationContext, 51), dpToInteger(applicationContext, 313))
        valueAnimatorScalesUpWidth.duration = 777
        valueAnimatorScalesUpWidth.addUpdateListener { animator ->
            val animatorValue = animator.animatedValue as Int

            completeSearchLayoutBinding.textInputSearchView.layoutParams.width = animatorValue
            completeSearchLayoutBinding.textInputSearchView.requestLayout()
        }
        valueAnimatorScalesUpWidth.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

                completeSearchLayoutBinding.searchView.requestFocus()

                showKeyboard(applicationContext, completeSearchLayoutBinding.searchView)

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

            completeSearchLayoutBinding.textInputSearchView.layoutParams.height = animatorValue
            completeSearchLayoutBinding.textInputSearchView.requestLayout()
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

        val slideUpAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up_from_bottom_bounce)
        completeSearchLayoutBinding.textInputSearchView.visibility = View.VISIBLE
        completeSearchLayoutBinding.textInputSearchView.startAnimation(slideUpAnimation)

        slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                valueAnimatorScalesUpHeight.start()
                valueAnimatorScalesUpWidth.start()

            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })

        completeSearchLayoutBinding.searchView.setOnEditorActionListener { textView, actionId, event ->

            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {

                    hideSearchViewAction.invoke()

                    try {

                        completeSearchAdapter?.completeSearchResultsItems!!.clear()
                        completeSearchAdapter?.notifyDataSetChanged()

                        completeSearchLayoutBinding.searchResultsRecyclerView.removeAllViews()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    val searchQuery = textView.text.toString()

                    completeSearchLayoutBinding.searchQueryText.text = searchQuery

                    searchAllProducts.startApplicationsSearch(searchQuery)

                    searchAllProducts.startGamesSearch(searchQuery)

                    searchAllProducts.startMoviesSearch(searchQuery)

                    completeSearchLayoutBinding.waitingView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    completeSearchLayoutBinding.waitingView.visibility = View.VISIBLE

                }
            }

            false
        }

    }

}