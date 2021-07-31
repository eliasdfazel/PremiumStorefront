/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/31/21, 8:35 AM
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
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSplitActivity
import co.geeksempire.premium.storefront.databinding.SectionsSwitcherLayoutBinding
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun startMoviesSwitching(context: AppCompatActivity, sectionsSwitcherLayoutBinding: SectionsSwitcherLayoutBinding) {

    val splitInstallManager = SplitInstallManagerFactory.create(context)

    val splitInstallRequest = SplitInstallRequest.newBuilder()
        .addModule("PremiumStorefrontMovies")
        .build()

    val dynamicModuleInstaller = splitInstallManager.startInstall(splitInstallRequest)
    dynamicModuleInstaller.addOnSuccessListener { sessionId ->
        Log.d("Dynamic Module", "Dynamic Module: Movies Section Installed Successfully")

        val valueAnimatorMovies = ValueAnimator.ofInt(dpToInteger(context, 57), sectionsSwitcherLayoutBinding.applicationsSectionView.width)
        valueAnimatorMovies.duration = 333
        valueAnimatorMovies.startDelay = 333
        valueAnimatorMovies.addUpdateListener { animator ->

            val animatorValue = animator.animatedValue as Int

            sectionsSwitcherLayoutBinding.moviesSectionView.layoutParams.width = animatorValue
            sectionsSwitcherLayoutBinding.moviesSectionView.requestLayout()

        }
        valueAnimatorMovies.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {

                moviesSectionSwitcherDesign(context, sectionsSwitcherLayoutBinding)

            }

            override fun onAnimationEnd(animation: Animator) {

                val activityOptions = ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, 0)

                val switchIntent = Intent().apply {
                    setClassName(context.packageName, StorefrontSplitActivity.MoviesModule.EntryConfigurations)
                }

                context.startActivity(switchIntent, activityOptions.toBundle())

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })

        val valueAnimatorApplications = ValueAnimator.ofInt(sectionsSwitcherLayoutBinding.applicationsSectionView.width, dpToInteger(context, 57))
        valueAnimatorApplications.duration = 333
        valueAnimatorApplications.startDelay = 333
        valueAnimatorApplications.addUpdateListener { animator ->

            val animatorValue = animator.animatedValue as Int

            sectionsSwitcherLayoutBinding.applicationsSectionView.layoutParams.width = animatorValue
            sectionsSwitcherLayoutBinding.applicationsSectionView.requestLayout()

        }
        valueAnimatorApplications.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

                sectionsSwitcherLayoutBinding.applicationsSectionView.text = ""

                valueAnimatorMovies.start()

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })
        valueAnimatorApplications.start()

    }.addOnFailureListener { exception ->



    }

}

fun moviesSectionSwitcherDesign(context: AppCompatActivity, sectionsSwitcherLayoutBinding: SectionsSwitcherLayoutBinding) {

    sectionsSwitcherLayoutBinding.applicationsSectionView.apply {

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
