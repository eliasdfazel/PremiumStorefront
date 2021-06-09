/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/9/21, 9:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.Extensions

import android.graphics.drawable.Drawable
import android.text.Html
import androidx.lifecycle.coroutineScope
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.Preferences.UserInterface.ToggleTheme
import co.geeksempire.premium.storefront.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.flow.first

fun PreferencesControl.preferencesControlSetupUserInterface() {

    val toggleTheme = ToggleTheme(this@preferencesControlSetupUserInterface, preferencesControlLayoutBinding)
    toggleTheme.initialThemeToggleAction()

    firebaseUser?.let {

        preferencesControlLayoutBinding.profileNameView.text = Html.fromHtml(it.displayName, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(applicationContext)
            .asDrawable()
            .load(it.photoUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    preferencesControlLayoutBinding.profileImageView.icon = resource

                    return false
                }


            })
            .submit()

    }

    toggleLightDark()

}

fun PreferencesControl.toggleLightDark() {

    lifecycle.coroutineScope.launchWhenResumed {

        when (themePreferences.checkThemeLightDark().first()) {
            ThemeType.ThemeLight -> {

                preferencesControlLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))



            }
            ThemeType.ThemeDark -> {

                preferencesControlLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

            }
        }

    }

}