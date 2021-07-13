/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/13/21, 2:06 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.DevelopersConfigurations.DataStructure.DeveloperLiveData
import co.geeksempire.premium.storefront.DevelopersConfigurations.DataStructure.DevelopersDataKey
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.Extensions.setupUserInterfaceDeveloperPage
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.UI.Animations.startTypingAnimation
import co.geeksempire.premium.storefront.Utils.UI.Colors.Gradient
import co.geeksempire.premium.storefront.Utils.UI.Colors.gradientText
import co.geeksempire.premium.storefront.databinding.DeveloperIntroductionLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DeveloperIntroductionPage : AppCompatActivity() {

    val developerLiveData: DeveloperLiveData by lazy {
        ViewModelProvider(this@DeveloperIntroductionPage).get(DeveloperLiveData::class.java)
    }

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@DeveloperIntroductionPage)
    }

    lateinit var developerIntroductionLayoutBinding: DeveloperIntroductionLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        developerIntroductionLayoutBinding = DeveloperIntroductionLayoutBinding.inflate(layoutInflater)
        setContentView(developerIntroductionLayoutBinding.root)


        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {

                setupUserInterfaceDeveloperPage(it)

            }

        }

        if (intent != null) {

            val developerName = intent.getStringExtra(DevelopersDataKey.DeveloperName)!!
            val developerDescription = intent.getStringExtra(DevelopersDataKey.DeveloperDescription)!!

            val developerLogo = intent.getStringExtra(DevelopersDataKey.DeveloperLogo)!!
            val developerCoverImage = intent.getStringExtra(DevelopersDataKey.DeveloperCoverImage)!!

            val developerCountry = intent.getStringExtra(DevelopersDataKey.DeveloperCountry)!!
            val developerCountryFlag = intent.getStringExtra(DevelopersDataKey.DeveloperCountryFlag)!!

            val developerEmail = intent.getStringExtra(DevelopersDataKey.DeveloperEmail)!!
            val developerWebsite = intent.getStringExtra(DevelopersDataKey.DeveloperWebsite)!!

            val developerSocialMedia = intent.getStringExtra(DevelopersDataKey.DeveloperSocialMediaIcon)!!
            val developerSocialMediaLink = intent.getStringExtra(DevelopersDataKey.DeveloperSocialMediaLink)!!

            val productsApplicationsId = if (intent.hasExtra(DevelopersDataKey.DeveloperApplications)) { intent.getStringExtra(DevelopersDataKey.DeveloperApplications)!! } else { null }
            val productsGamesId = if (intent.hasExtra(DevelopersDataKey.DeveloperGames)) { intent.getStringExtra(DevelopersDataKey.DeveloperGames)!! } else { null }
            val productsBooksId = if (intent.hasExtra(DevelopersDataKey.DeveloperBooks)) { intent.getStringExtra(DevelopersDataKey.DeveloperBooks)!! } else { null }
            val developerMoviesId = if (intent.hasExtra(DevelopersDataKey.DeveloperMovies)) { intent.getStringExtra(DevelopersDataKey.DeveloperMovies)!! } else { null }

//            developerLiveData.prepareDeveloperProducts(productsApplicationsId)
//            developerLiveData.prepareDeveloperProducts(productsGamesId)
//            developerLiveData.prepareDeveloperProducts(productsBooksId)
//            developerLiveData.prepareDeveloperProducts(developerMoviesId)

            Glide.with(applicationContext)
                .asDrawable()
                .load(developerCoverImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(developerIntroductionLayoutBinding.developerCoverImageView)

            Glide.with(applicationContext)
                .asDrawable()
                .load(developerLogo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CircleCrop())
                .into(developerIntroductionLayoutBinding.developerLogoImageView)

            developerIntroductionLayoutBinding.developerNameTextView.text = Html.fromHtml(developerName, Html.FROM_HTML_MODE_COMPACT)

            gradientText(textView = developerIntroductionLayoutBinding.developerNameTextView,
                gradientColors = intArrayOf(getColor(R.color.default_color), getColor(R.color.default_color_light)),
                gradientColorsPositions = floatArrayOf(0f, 0.51f),
                gradientVerticalEnd = developerIntroductionLayoutBinding.developerNameTextView.height.toFloat(),
                gradientType = Gradient.VerticalGradient)

            developerIntroductionLayoutBinding.developerDescriptionTextView.startTypingAnimation(developerDescription)

            developerLiveData.developerProducts.observe(this@DeveloperIntroductionPage, {

                if (it.isNotEmpty()) {



                }

            })

        } else {

            this@DeveloperIntroductionPage.finish()

        }

    }

}