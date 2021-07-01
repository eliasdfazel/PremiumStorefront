/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/1/21, 6:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.UserInterface

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.Preferences.DataStructure.PreferencesLiveData
import co.geeksempire.premium.storefront.Preferences.Extensions.preferencesControlSetupUserInteractions
import co.geeksempire.premium.storefront.Preferences.Extensions.preferencesControlSetupUserInterface
import co.geeksempire.premium.storefront.Preferences.Extensions.toggleLightDark
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.InApplicationReview.ReviewUtils
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
import co.geeksempire.premium.storefront.databinding.PreferencesControlLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PreferencesControl : AppCompatActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@PreferencesControl)
    }

    val preferencesLiveData: PreferencesLiveData by lazy {
        ViewModelProvider(this@PreferencesControl).get(PreferencesLiveData::class.java)
    }

    val reviewUtils: ReviewUtils by lazy {
        ReviewUtils((application as PremiumStorefrontApplication).preferencesIO)
    }

    lateinit var preferencesControlLayoutBinding: PreferencesControlLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesControlLayoutBinding =  PreferencesControlLayoutBinding.inflate(layoutInflater)
        setContentView(preferencesControlLayoutBinding.root)

        preferencesControlLayoutBinding.root.post {

            preferencesControlSetupUserInterface()

            preferencesControlSetupUserInteractions()

            preferencesLiveData.toggleTheme.observe(this@PreferencesControl, Observer { themeType ->

                toggleLightDark()

            })

        }

    }

    override fun onResume() {
        super.onResume()

        Firebase.auth.currentUser?.let {

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

                            val dominantColor = extractVibrantColor(applicationContext, resource)

                            preferencesControlLayoutBinding.profileImageColorView.backgroundTintList = ColorStateList.valueOf(setColorAlpha(dominantColor, 111f))

                            preferencesControlLayoutBinding.profileImageView.icon = resource

                        }

                        return false
                    }

                })
                .submit()

            preferencesControlLayoutBinding.profileNameView.text = Html.fromHtml(it.displayName, Html.FROM_HTML_MODE_COMPACT)

        }

    }

    override fun onBackPressed() {

        this@PreferencesControl.finish()
        overridePendingTransition(0, R.anim.slide_out_right)

    }

}