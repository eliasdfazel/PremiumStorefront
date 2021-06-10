/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/10/21, 6:43 AM
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
import androidx.lifecycle.coroutineScope
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.Preferences.UserInterface.ToggleTheme
import co.geeksempire.premium.storefront.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun PreferencesControl.preferencesControlSetupUserInterface() {

    val toggleTheme = ToggleTheme(
        this@preferencesControlSetupUserInterface,
        themePreferences,
        preferencesControlLayoutBinding
    )
    toggleTheme.initialThemeToggleAction()

    firebaseUser?.let {

        preferencesControlLayoutBinding.profileNameView.text =
            Html.fromHtml(it.displayName, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(applicationContext)
            .asDrawable()
            .load(it.photoUrl)
            .transform(CircleCrop())
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {

                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    runOnUiThread {

                        preferencesControlLayoutBinding.profileImageView.icon = resource

                    }

                    return false
                }

            })
            .submit()

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

                    preferencesControlLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))
                    preferencesControlLayoutBinding.blurryBackground.setOverlayColor(getColor(R.color.premiumLightTransparent))

                    preferencesControlLayoutBinding.applicationLogo.imageTintList =
                        ColorStateList.valueOf(getColor(R.color.dark))

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                        window.insetsController?.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                        )

                    } else {

                        @Suppress("DEPRECATION")
                        window.decorView.systemUiVisibility =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                            } else {
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            }

                    }

                }
                ThemeType.ThemeDark -> {

                    window.statusBarColor = getColor(R.color.premiumDark)
                    window.navigationBarColor = getColor(R.color.premiumDark)

                    preferencesControlLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))
                    preferencesControlLayoutBinding.blurryBackground.setOverlayColor(getColor(R.color.premiumDarkTransparent))

                    preferencesControlLayoutBinding.applicationLogo.imageTintList =
                        ColorStateList.valueOf(getColor(R.color.light))

                    preferencesControlLayoutBinding.blurryBackground.setOverlayColor(getColor(R.color.premiumDarkTransparent))

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                        window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

                    } else {

                        @Suppress("DEPRECATION")
                        window.decorView.systemUiVisibility = 0


                    }
                }

            }

        }
    }

}