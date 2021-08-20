/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 5:28 AM
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
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontDynamicActivity
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.StorefrontApplications
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForGamesConfigurations.UserInterface.StorefrontGames
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import co.geeksempire.premium.storefront.databinding.SectionsSwitcherLayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun startMoviesSwitching(context: AppCompatActivity, activityRootView: ViewGroup, sectionsSwitcherLayoutBinding: SectionsSwitcherLayoutBinding, themeType: Boolean) {

    val splitInstallManager = SplitInstallManagerFactory.create(context)

    if (checkInstalledModules(splitInstallManager, "PremiumStorefrontMovies")) {

        completeMoviesSwitching(context, sectionsSwitcherLayoutBinding, themeType)

    } else {

        if (sectionsSwitcherLayoutBinding.installLoadingView.isGone) {

            sectionsSwitcherLayoutBinding.moviesSectionView.icon = null

            sectionsSwitcherLayoutBinding.installLoadingView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            sectionsSwitcherLayoutBinding.installLoadingView.visibility = View.VISIBLE

        }

        val splitInstallRequest = SplitInstallRequest.newBuilder()
            .addModule("PremiumStorefrontMovies")
            .build()

        val dynamicModuleInstaller = splitInstallManager.startInstall(splitInstallRequest)

        splitInstallManager.registerListener {

            when (it.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    Log.d("Dynamic Feature", "${it.bytesDownloaded()} | ${it.totalBytesToDownload()}")



                }
                SplitInstallSessionStatus.INSTALLED -> {
                    Log.d("Dynamic Feature", "Installed")

                    sectionsSwitcherLayoutBinding.moviesSectionView.icon = context.getDrawable(R.drawable.movies_icon)

                    sectionsSwitcherLayoutBinding.installLoadingView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                    sectionsSwitcherLayoutBinding.installLoadingView.visibility = View.GONE

                    SnackbarBuilder(context).show (
                        rootView = activityRootView,
                        messageText= context.getString(R.string.moviesDynamicFeatureInstalled),
                        messageDuration = Snackbar.LENGTH_INDEFINITE,
                        actionButtonText = android.R.string.ok,
                        backgroundColor = when (themeType) {ThemeType.ThemeLight -> { context.getColor(R.color.premiumDark) }ThemeType.ThemeDark -> { context.getColor(R.color.premiumLight) }else -> { context.getColor(R.color.premiumDark) } },
                        messageTextColor = when (themeType) {ThemeType.ThemeLight -> { context.getColor(R.color.light) }ThemeType.ThemeDark -> { context.getColor(R.color.dark) }else -> { context.getColor(R.color.light) } },
                        snackbarActionHandlerInterface = object : SnackbarActionHandlerInterface {

                            override fun onActionButtonClicked(snackbar: Snackbar) {
                                super.onActionButtonClicked(snackbar)

                                snackbar.dismiss()

                                completeMoviesSwitching(context, sectionsSwitcherLayoutBinding, themeType)

                            }

                        }
                    )

                }

            }

        }

        dynamicModuleInstaller.addOnSuccessListener { sessionId ->
            Log.d("Dynamic Module", "Dynamic Module: Movies Section Installed Successfully")


        }.addOnFailureListener { exception ->



        }

    }

}


fun completeMoviesSwitching(context: AppCompatActivity, sectionsSwitcherLayoutBinding: SectionsSwitcherLayoutBinding, themeType: Boolean) {

    val valueAnimatorMovies = when (context) {
        is StorefrontApplications -> {

            ValueAnimator.ofInt(dpToInteger(context, 57), sectionsSwitcherLayoutBinding.applicationsSectionView.width)

        }
        is StorefrontGames -> {

            ValueAnimator.ofInt(dpToInteger(context, 57), sectionsSwitcherLayoutBinding.gamesSectionView.width)

        }
        else -> ValueAnimator.ofInt(dpToInteger(context, 57), sectionsSwitcherLayoutBinding.applicationsSectionView.width)
    }
    valueAnimatorMovies.duration = 333
    valueAnimatorMovies.startDelay = 333
    valueAnimatorMovies.addUpdateListener { animator ->

        val animatorValue = animator.animatedValue as Int

        sectionsSwitcherLayoutBinding.moviesSectionView.layoutParams.width = animatorValue
        sectionsSwitcherLayoutBinding.moviesSectionView.requestLayout()

    }
    valueAnimatorMovies.addListener(object : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator) {

            moviesSectionSwitcherDesign(context, sectionsSwitcherLayoutBinding, themeType)

        }

        override fun onAnimationEnd(animation: Animator) {

            val activityOptions = ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, 0)

            val switchIntent = Intent().apply {
                setClassName(context.packageName, StorefrontDynamicActivity.MoviesModule.EntryConfigurations)
            }

            context.startActivity(switchIntent, activityOptions.toBundle())

        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }

    })

    when (context) {
        is StorefrontApplications -> {

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

        }
        is StorefrontGames -> {

            val valueAnimatorGames = ValueAnimator.ofInt(sectionsSwitcherLayoutBinding.gamesSectionView.width, dpToInteger(context, 57))
            valueAnimatorGames.duration = 333
            valueAnimatorGames.startDelay = 333
            valueAnimatorGames.addUpdateListener { animator ->

                val animatorValue = animator.animatedValue as Int

                sectionsSwitcherLayoutBinding.gamesSectionView.layoutParams.width = animatorValue
                sectionsSwitcherLayoutBinding.gamesSectionView.requestLayout()

            }
            valueAnimatorGames.addListener(object : Animator.AnimatorListener {

                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {

                    sectionsSwitcherLayoutBinding.gamesSectionView.text = ""

                    valueAnimatorMovies.start()

                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }

            })
            valueAnimatorGames.start()

        }
    }

}

fun moviesSectionSwitcherDesign(context: AppCompatActivity, sectionsSwitcherLayoutBinding: SectionsSwitcherLayoutBinding, themeType: Boolean) {

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

fun checkInstalledModules(splitInstallManager: SplitInstallManager, moduleName: String) : Boolean {

    var moduleInstalled = false

    installedModuleLoop@ for (installedModule in splitInstallManager.installedModules) {

        if (installedModule == moduleName) {

            moduleInstalled = true

            break@installedModuleLoop
        } else {

            moduleInstalled = false

        }

    }

    return moduleInstalled
}