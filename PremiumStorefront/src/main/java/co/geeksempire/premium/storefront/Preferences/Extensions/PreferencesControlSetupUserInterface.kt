/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/21/21, 6:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.Extensions

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.lifecycle.coroutineScope
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Preferences.Operations.ToggleTheme
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.UI.Animations.ShadowAnimation
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun PreferencesControl.preferencesControlSetupUserInterface() {

    if (!BuildConfig.DEBUG) {
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }

    val toggleTheme = ToggleTheme(this@preferencesControlSetupUserInterface, themePreferences, preferencesControlLayoutBinding)
    toggleTheme.initialThemeToggleAction()

    Firebase.auth.currentUser?.let {

        preferencesControlLayoutBinding.profileNameView.text = Html.fromHtml(it.displayName, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(applicationContext)
            .asDrawable()
            .load(it.photoUrl)
            .transform(CircleCrop())
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        runOnUiThread {

                            preferencesControlLayoutBinding.profileImageView.icon = resource

                            val dominantColor = extractVibrantColor(resource)

                            preferencesControlLayoutBinding.profileImageColorView.backgroundTintList = ColorStateList.valueOf(setColorAlpha(dominantColor, 111f))

                        }

                    }

                    return false
                }

            })
            .submit()

    }

    ShadowAnimation().apply {

        textShadowValueAnimatorLoop(view = preferencesControlLayoutBinding.rateItView,
            startValue = 0f, endValue = preferencesControlLayoutBinding.rateItView.shadowRadius,
            startDuration = 1357, endDuration = 579,
            shadowColor = preferencesControlLayoutBinding.rateItView.shadowColor, shadowX = 0f, shadowY = 0f,
            numberOfLoop = 13)

    }

    toggleLightDark()

}

fun PreferencesControl.toggleLightDark() {

    lifecycle.coroutineScope.launch {

        themePreferences.checkThemeLightDark().collect {

            when (it) {
                ThemeType.ThemeLight -> {

                    window.statusBarColor = getColor(R.color.premiumLight)
                    window.navigationBarColor = getColor(R.color.premiumLight)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                        window.insetsController?.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                        )

                        window.insetsController?.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

                    } else {

                        @Suppress("DEPRECATION")
                        window.decorView.systemUiVisibility =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                            } else {
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            }

                    }

                    preferencesControlLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))
                    preferencesControlLayoutBinding.blurryBackground.setOverlayColor(getColor(R.color.premiumLightTransparent))

                    preferencesControlLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

                    preferencesControlLayoutBinding.profileImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.white))

                    preferencesControlLayoutBinding.profileNameView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.white))
                    preferencesControlLayoutBinding.profileNameView.setTextColor(getColor(R.color.premiumDark))

                    preferencesControlLayoutBinding.themeToggleView.background = getDrawable(R.drawable.preferences_item_background_light)
                    preferencesControlLayoutBinding.supportView.background = getDrawable(R.drawable.preferences_item_background_light)
                    preferencesControlLayoutBinding.whatNewView.background = getDrawable(R.drawable.preferences_item_background_light)

                    preferencesControlLayoutBinding.submissionRequestView.apply {
                        background = getDrawable(R.drawable.preferences_item_small_background_light)
                        setImageDrawable(getDrawable(R.drawable.developer_submission_icon_light))
                    }

                    preferencesControlLayoutBinding.updateItView.apply {
                        backgroundTintList = ColorStateList.valueOf(getColor(R.color.premiumLight))
                        iconTint = ColorStateList.valueOf(getColor(R.color.premiumDark))
                        strokeColor = ColorStateList.valueOf(getColor(R.color.white_transparent))
                    }

                    preferencesControlLayoutBinding.rateItView.apply {
                        backgroundTintList = ColorStateList.valueOf(getColor(R.color.premiumLight))
                        strokeColor = ColorStateList.valueOf(getColor(R.color.white_transparent))
                    }

                    preferencesControlLayoutBinding.shareItView.apply {
                        backgroundTintList = ColorStateList.valueOf(getColor(R.color.premiumLight))
                        iconTint = ColorStateList.valueOf(getColor(R.color.premiumDark))
                        strokeColor = ColorStateList.valueOf(getColor(R.color.white_transparent))
                    }

                }
                ThemeType.ThemeDark -> {

                    window.statusBarColor = getColor(R.color.premiumDark)
                    window.navigationBarColor = getColor(R.color.premiumDark)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                        window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                        window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

                    } else {

                        @Suppress("DEPRECATION")
                        window.decorView.systemUiVisibility = 0

                    }

                    preferencesControlLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))
                    preferencesControlLayoutBinding.blurryBackground.setOverlayColor(getColor(R.color.premiumDarkTransparent))

                    preferencesControlLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

                    preferencesControlLayoutBinding.profileImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.black))

                    preferencesControlLayoutBinding.profileNameView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.black))
                    preferencesControlLayoutBinding.profileNameView.setTextColor(getColor(R.color.premiumLight))

                    preferencesControlLayoutBinding.themeToggleView.background = getDrawable(R.drawable.preferences_item_background_dark)
                    preferencesControlLayoutBinding.supportView.background = getDrawable(R.drawable.preferences_item_background_dark)
                    preferencesControlLayoutBinding.whatNewView.background = getDrawable(R.drawable.preferences_item_background_dark)

                    preferencesControlLayoutBinding.submissionRequestView.apply {
                        background = getDrawable(R.drawable.preferences_item_small_background_dark)
                        setImageDrawable(getDrawable(R.drawable.developer_submission_icon_dark))
                    }

                    preferencesControlLayoutBinding.updateItView.apply {
                        backgroundTintList = ColorStateList.valueOf(getColor(R.color.premiumDark))
                        iconTint = ColorStateList.valueOf(getColor(R.color.premiumLight))
                        strokeColor = ColorStateList.valueOf(getColor(R.color.black_transparent))
                    }

                    preferencesControlLayoutBinding.rateItView.apply {
                        backgroundTintList = ColorStateList.valueOf(getColor(R.color.premiumDark))
                        strokeColor = ColorStateList.valueOf(getColor(R.color.black_transparent))
                    }

                    preferencesControlLayoutBinding.shareItView.apply {
                        backgroundTintList = ColorStateList.valueOf(getColor(R.color.premiumDark))
                        iconTint = ColorStateList.valueOf(getColor(R.color.premiumLight))
                        strokeColor = ColorStateList.valueOf(getColor(R.color.black_transparent))
                    }

                }

            }

        }
    }

}