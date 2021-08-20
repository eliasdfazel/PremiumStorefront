/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 12:50 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.Extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.StorefrontApplications
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForGamesConfigurations.UserInterface.StorefrontGames
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.databinding.MoviesSectionsSwitcherLayoutBinding
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun storefrontMoviesSectionSwitcher(context: AppCompatActivity, sectionsSwitcherLayoutBinding: MoviesSectionsSwitcherLayoutBinding,
                                    themeType: Boolean) {

    moviesSectionSwitcherDesign(context, sectionsSwitcherLayoutBinding, themeType)

    sectionsSwitcherLayoutBinding.applicationsSectionView.setOnClickListener {

        val valueAnimatorMovie = ValueAnimator.ofInt(dpToInteger(context, 57), sectionsSwitcherLayoutBinding.moviesSectionView.width)
        valueAnimatorMovie.duration = 333
        valueAnimatorMovie.startDelay = 333
        valueAnimatorMovie.addUpdateListener { animator ->

            val animatorValue = animator.animatedValue as Int

            sectionsSwitcherLayoutBinding.applicationsSectionView.layoutParams.width = animatorValue
            sectionsSwitcherLayoutBinding.applicationsSectionView.requestLayout()

        }
        valueAnimatorMovie.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {

                applicationsSectionSwitcherDesign(context, sectionsSwitcherLayoutBinding, themeType)

            }

            override fun onAnimationEnd(animation: Animator) {

                val activityOptions = ActivityOptions.makeCustomAnimation(context, R.anim.fade_in_movie, 0)

                val switchIntent = Intent(context, StorefrontApplications::class.java).apply {

                }

                context.startActivity(switchIntent, activityOptions.toBundle())

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })

        val valueAnimatorApplications = ValueAnimator.ofInt(sectionsSwitcherLayoutBinding.moviesSectionView.width, dpToInteger(context, 57))
        valueAnimatorApplications.duration = 333
        valueAnimatorApplications.startDelay = 333
        valueAnimatorApplications.addUpdateListener { animator ->

            val animatorValue = animator.animatedValue as Int

            sectionsSwitcherLayoutBinding.moviesSectionView.layoutParams.width = animatorValue
            sectionsSwitcherLayoutBinding.moviesSectionView.requestLayout()

        }
        valueAnimatorApplications.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

                sectionsSwitcherLayoutBinding.moviesSectionView.text = ""

                valueAnimatorMovie.start()

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })
        valueAnimatorApplications.start()

    }

    sectionsSwitcherLayoutBinding.gamesSectionView.setOnClickListener {

        val valueAnimatorGames = ValueAnimator.ofInt(dpToInteger(context, 57), sectionsSwitcherLayoutBinding.moviesSectionView.width)
        valueAnimatorGames.duration = 333
        valueAnimatorGames.startDelay = 333
        valueAnimatorGames.addUpdateListener { animator ->

            val animatorValue = animator.animatedValue as Int

            sectionsSwitcherLayoutBinding.gamesSectionView.layoutParams.width = animatorValue
            sectionsSwitcherLayoutBinding.gamesSectionView.requestLayout()

        }
        valueAnimatorGames.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {

                gamesSectionSwitcherDesign(context, sectionsSwitcherLayoutBinding, themeType)

            }

            override fun onAnimationEnd(animation: Animator) {

                val activityOptions = ActivityOptions.makeCustomAnimation(context, R.anim.fade_in_movie, 0)

                val switchIntent = Intent(context, StorefrontGames::class.java).apply {

                }

                context.startActivity(switchIntent, activityOptions.toBundle())

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })

        val valueAnimatorMovies = ValueAnimator.ofInt(sectionsSwitcherLayoutBinding.moviesSectionView.width, dpToInteger(context, 57))
        valueAnimatorMovies.duration = 333
        valueAnimatorMovies.startDelay = 333
        valueAnimatorMovies.addUpdateListener { animator ->

            val animatorValue = animator.animatedValue as Int

            sectionsSwitcherLayoutBinding.moviesSectionView.layoutParams.width = animatorValue
            sectionsSwitcherLayoutBinding.moviesSectionView.requestLayout()

        }
        valueAnimatorMovies.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

                sectionsSwitcherLayoutBinding.moviesSectionView.text = ""

                valueAnimatorGames.start()

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })
        valueAnimatorMovies.start()

    }

}

fun applicationsSectionSwitcherDesign(context: AppCompatActivity, sectionsSwitcherLayoutBinding: MoviesSectionsSwitcherLayoutBinding,
                                      themeType: Boolean) {

    sectionsSwitcherLayoutBinding.applicationsSectionView.apply {

        (layoutParams as ConstraintLayout.LayoutParams).width = 0
        requestLayout()

        text = contentDescription
        setTextColor(context.getColor(R.color.white))
        iconTint = ColorStateList.valueOf(context.getColor(R.color.white))
        iconSize = dpToInteger(context, 29)
        iconPadding = dpToInteger(context, 7)
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.transparent))
        backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))

        (layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.51f
        requestLayout()


    }

    sectionsSwitcherLayoutBinding.moviesSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))
        backgroundTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumLight))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumDark))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.premiumLight))
        }

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

}

fun gamesSectionSwitcherDesign(context: AppCompatActivity, sectionsSwitcherLayoutBinding: MoviesSectionsSwitcherLayoutBinding,
                               themeType: Boolean) {

    sectionsSwitcherLayoutBinding.moviesSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))
        backgroundTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumLight))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumDark))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.premiumLight))
        }

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

    sectionsSwitcherLayoutBinding.gamesSectionView.apply {

        (layoutParams as ConstraintLayout.LayoutParams).width = 0
        requestLayout()

        text = contentDescription
        setTextColor(context.getColor(R.color.white))
        iconTint = ColorStateList.valueOf(context.getColor(R.color.white))
        iconSize = dpToInteger(context, 29)
        iconPadding = dpToInteger(context, 7)
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.transparent))
        backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.gamesSectionColor))

        (layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.51f

        requestLayout()

        clearFocus()

    }

}

fun moviesSectionSwitcherDesign(context: AppCompatActivity, sectionsSwitcherLayoutBinding: MoviesSectionsSwitcherLayoutBinding,
                                themeType: Boolean) {

    sectionsSwitcherLayoutBinding.applicationsSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))
        backgroundTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumLight))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumDark))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.premiumLight))
        }

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

    sectionsSwitcherLayoutBinding.gamesSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.gamesSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.gamesSectionColor))
        backgroundTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumLight))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumDark))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.premiumLight))
        }

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

    sectionsSwitcherLayoutBinding.moviesSectionView.apply {

        (layoutParams as ConstraintLayout.LayoutParams).width = 0
        requestLayout()

        text = contentDescription
        setTextColor(context.getColor(R.color.white))
        iconTint = ColorStateList.valueOf(context.getColor(R.color.white))
        iconSize = dpToInteger(context, 29)
        iconPadding = dpToInteger(context, 7)
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.transparent))
        backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))

        (layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.51f

        requestLayout()

        clearFocus()

    }

}