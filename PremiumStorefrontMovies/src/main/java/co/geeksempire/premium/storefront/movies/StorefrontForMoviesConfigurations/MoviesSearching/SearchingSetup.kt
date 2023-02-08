/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/22/21, 8:14 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesSearching

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.CompleteSearch
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.android.Utils.System.hideKeyboard
import co.geeksempire.premium.storefront.android.Utils.System.showKeyboard
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter.FilterAllMovies
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class SearchingMoviesSetup (private val context: AppCompatActivity) {

    fun searchingSetup(filterAllMovies: FilterAllMovies,
                       textInputSearchView: TextInputLayout,
                       searchView: TextInputEditText,
                       rightActionView: ImageView,
                       middleActionView: ImageView,
                       leftActionView: ImageView,
                       storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot>,
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
            else -> {}
        }

        if (textInputSearchView.isShown) {

            hideSearchView(textInputSearchView,
                searchView,
                middleActionView,
                themeType)

        } else {

            middleActionView.background = context.getDrawable(R.drawable.action_center_glowing_movie)

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

            val slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_from_bottom_bounce_movie)
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

                        (context.application as PremiumStorefrontApplication).firebaseAnalytics.logEvent(Firebase.auth.currentUser?.uid?:"Anonymous", Bundle().apply {
                            putString("SearchQuery", textView.text.toString())
                        })

                        filterAllMovies.searchThroughAllMovies(storefrontAllUnfilteredContents, textView.text.toString())
                            .invokeOnCompletion {

                                hideSearchView(textInputSearchView,
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

    fun hideSearchView(textInputSearchView: TextInputLayout,
                       searchView: TextInputEditText,
                       middleActionView: ImageView,
                       themeType: Boolean) = CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {

        middleActionView.background = null

        val fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out_movie)

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

    fun afterQuickSearch(themeType: Boolean,
                         searchMoviesRevertView: AppCompatButton, searchMoviesAdvancedView: AppCompatButton,
                         searchQuery: String,
                         storefrontLiveData: MoviesStorefrontLiveData,
                         storefrontAllUntouchedContents: ArrayList<DocumentSnapshot>) {

        when (themeType) {
            ThemeType.ThemeLight -> {

                searchMoviesRevertView.background = context.getDrawable(co.geeksempire.premium.storefront.R.drawable.search_after_actions_light)
                searchMoviesRevertView.setTextColor(context.getColor(co.geeksempire.premium.storefront.R.color.dark))

                searchMoviesAdvancedView.background = context.getDrawable(co.geeksempire.premium.storefront.R.drawable.search_after_actions_light)
                searchMoviesAdvancedView.setTextColor(context.getColor(co.geeksempire.premium.storefront.R.color.dark))

            }
            ThemeType.ThemeDark -> {

                searchMoviesRevertView.background = context.getDrawable(co.geeksempire.premium.storefront.R.drawable.search_after_actions_dark)
                searchMoviesRevertView.setTextColor(context.getColor(co.geeksempire.premium.storefront.R.color.light))

                searchMoviesAdvancedView.background = context.getDrawable(co.geeksempire.premium.storefront.R.drawable.search_after_actions_dark)
                searchMoviesAdvancedView.setTextColor(context.getColor(co.geeksempire.premium.storefront.R.color.light))

            }
            else -> {

                searchMoviesRevertView.background = context.getDrawable(co.geeksempire.premium.storefront.R.drawable.search_after_actions_light)
                searchMoviesRevertView.setTextColor(context.getColor(co.geeksempire.premium.storefront.R.color.dark))

                searchMoviesAdvancedView.background = context.getDrawable(co.geeksempire.premium.storefront.R.drawable.search_after_actions_light)
                searchMoviesAdvancedView.setTextColor(context.getColor(co.geeksempire.premium.storefront.R.color.dark))

            }
        }

        Handler(Looper.getMainLooper()).postDelayed({

            val alphaAnimation = AlphaAnimation(0.0f, 1.0f).apply {
                duration = 1000
                fillAfter = true
            }

            searchMoviesRevertView.visibility = View.VISIBLE
            searchMoviesRevertView.startAnimation(alphaAnimation)

            searchMoviesAdvancedView.visibility = View.VISIBLE
            searchMoviesAdvancedView.startAnimation(alphaAnimation)

            val valueAnimatorIncreaseTextSize = ValueAnimator.ofFloat(11f, 21f)
            valueAnimatorIncreaseTextSize.duration = 777
            valueAnimatorIncreaseTextSize.addUpdateListener { animator ->
                val animatorValue = animator.animatedValue as Float

                searchMoviesRevertView.setTextSize(TypedValue.COMPLEX_UNIT_SP,  animatorValue)
                searchMoviesAdvancedView.setTextSize(TypedValue.COMPLEX_UNIT_SP,  animatorValue)

            }
            valueAnimatorIncreaseTextSize.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {

                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }
            })
            valueAnimatorIncreaseTextSize.start()

        }, 777)

        searchMoviesRevertView.setOnClickListener {

            storefrontLiveData.allFilteredMoviesItemData.postValue(Pair(storefrontAllUntouchedContents, false))

            Handler(Looper.getMainLooper()).postDelayed({

                searchMoviesRevertView.apply {
                    visibility = View.INVISIBLE
                    startAnimation(AnimationUtils.loadAnimation(context, co.geeksempire.premium.storefront.R.anim.fade_out))
                }

                searchMoviesAdvancedView.apply {
                    visibility = View.INVISIBLE
                    startAnimation(AnimationUtils.loadAnimation(context, co.geeksempire.premium.storefront.R.anim.fade_out))
                }

            }, 379)

        }

        searchMoviesAdvancedView.setOnClickListener {

            searchMoviesRevertView.apply {
                visibility = View.INVISIBLE
                startAnimation(AnimationUtils.loadAnimation(context, co.geeksempire.premium.storefront.R.anim.fade_out))
            }

            searchMoviesAdvancedView.apply {
                visibility = View.INVISIBLE
                startAnimation(AnimationUtils.loadAnimation(context, co.geeksempire.premium.storefront.R.anim.fade_out))
            }

            Handler(Looper.getMainLooper()).postDelayed({

                context.startActivity(Intent(context, CompleteSearch::class.java).apply {
                    putExtra(CompleteSearch.SearchQuery, searchQuery)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }, ActivityOptions.makeCustomAnimation(context, co.geeksempire.premium.storefront.R.anim.fade_in, co.geeksempire.premium.storefront.R.anim.fade_out).toBundle())

            }, 179)

        }

    }

}