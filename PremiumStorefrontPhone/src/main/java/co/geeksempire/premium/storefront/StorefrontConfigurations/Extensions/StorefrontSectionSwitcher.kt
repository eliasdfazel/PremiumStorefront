/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/28/21, 4:32 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.StorefrontApplications
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForGamesConfigurations.UserInterface.StorefrontGames
import co.geeksempire.premium.storefront.databinding.SectionSwitcherLayoutBinding
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun storefrontSectionSwitcher(context: AppCompatActivity, sectionSwitcherLayoutBinding: SectionSwitcherLayoutBinding) {

    when (context) {
        is StorefrontApplications -> {

            applicationsSectionSwitcherDesign(context, sectionSwitcherLayoutBinding)

            sectionSwitcherLayoutBinding.gamesSectionView.setOnClickListener {

                val valueAnimatorGames = ValueAnimator.ofInt(dpToInteger(context, 57), sectionSwitcherLayoutBinding.applicationsSectionView.width)
                valueAnimatorGames.duration = 333
                valueAnimatorGames.startDelay = 333
                valueAnimatorGames.addUpdateListener { animator ->

                    val animatorValue = animator.animatedValue as Int

                    sectionSwitcherLayoutBinding.gamesSectionView.layoutParams.width = animatorValue
                    sectionSwitcherLayoutBinding.gamesSectionView.requestLayout()

                }
                valueAnimatorGames.addListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {

                        gamesSectionSwitcherDesign(context, sectionSwitcherLayoutBinding)

                    }

                    override fun onAnimationEnd(animation: Animator) {

                        val activityOptions = ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, 0)

                        val switchIntent = Intent(context, StorefrontGames::class.java).apply {

                        }

                        context.startActivity(switchIntent, activityOptions.toBundle())

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }

                })

                val valueAnimatorApplications = ValueAnimator.ofInt(sectionSwitcherLayoutBinding.applicationsSectionView.width, dpToInteger(context, 57))
                valueAnimatorApplications.duration = 333
                valueAnimatorApplications.startDelay = 333
                valueAnimatorApplications.addUpdateListener { animator ->

                    val animatorValue = animator.animatedValue as Int

                    sectionSwitcherLayoutBinding.applicationsSectionView.layoutParams.width = animatorValue
                    sectionSwitcherLayoutBinding.applicationsSectionView.requestLayout()

                }
                valueAnimatorApplications.addListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {

                        sectionSwitcherLayoutBinding.applicationsSectionView.text = ""

                        valueAnimatorGames.start()

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }

                })
                valueAnimatorApplications.start()

            }

        }
        is StorefrontGames -> {

            gamesSectionSwitcherDesign(context, sectionSwitcherLayoutBinding)

            sectionSwitcherLayoutBinding.applicationsSectionView.setOnClickListener {

                val valueAnimatorApplications = ValueAnimator.ofInt(sectionSwitcherLayoutBinding.gamesSectionView.width, dpToInteger(context, 57))
                valueAnimatorApplications.duration = 333
                valueAnimatorApplications.startDelay = 333
                valueAnimatorApplications.addUpdateListener { animator ->

                    val animatorValue = animator.animatedValue as Int

                    sectionSwitcherLayoutBinding.gamesSectionView.layoutParams.width = animatorValue
                    sectionSwitcherLayoutBinding.gamesSectionView.requestLayout()

                }
                valueAnimatorApplications.addListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {

                        sectionSwitcherLayoutBinding.gamesSectionView.text = ""

                        valueAnimatorApplications.start()

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }

                })

                val valueAnimatorGames = ValueAnimator.ofInt(dpToInteger(context, 57), sectionSwitcherLayoutBinding.gamesSectionView.width)
                valueAnimatorGames.duration = 333
                valueAnimatorGames.startDelay = 333
                valueAnimatorGames.addUpdateListener { animator ->

                    val animatorValue = animator.animatedValue as Int

                    sectionSwitcherLayoutBinding.applicationsSectionView.layoutParams.width = animatorValue
                    sectionSwitcherLayoutBinding.applicationsSectionView.requestLayout()

                }
                valueAnimatorGames.addListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {

                        applicationsSectionSwitcherDesign(context, sectionSwitcherLayoutBinding)

                    }

                    override fun onAnimationEnd(animation: Animator) {

                        val activityOptions = ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, 0)

                        val switchIntent = Intent(context, StorefrontApplications::class.java).apply {

                        }

                        context.startActivity(switchIntent, activityOptions.toBundle())

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }

                })
                valueAnimatorGames.start()

            }

        }
        else -> {

        }
    }

}

fun applicationsSectionSwitcherDesign(context: AppCompatActivity, sectionSwitcherLayoutBinding: SectionSwitcherLayoutBinding) {

    sectionSwitcherLayoutBinding.applicationsSectionView.apply {

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

    sectionSwitcherLayoutBinding.gamesSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.gamesSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.gamesSectionColor))
        backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.premiumLight))

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

}

fun gamesSectionSwitcherDesign(context: AppCompatActivity, sectionSwitcherLayoutBinding: SectionSwitcherLayoutBinding) {

    sectionSwitcherLayoutBinding.applicationsSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))
        backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.premiumLight))

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

    sectionSwitcherLayoutBinding.gamesSectionView.apply {

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

        println(">>> " + (layoutParams as ConstraintLayout.LayoutParams).width)

        requestLayout()

        clearFocus()

    }

}

