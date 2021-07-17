/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/17/21, 11:38 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.DevelopersConfigurations.DataStructure.DeveloperLiveData
import co.geeksempire.premium.storefront.DevelopersConfigurations.DataStructure.DevelopersDataKey
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.Extensions.setupUserInterfaceDeveloperPage
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.Products.Applications.ApplicationsShowcase
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.Products.Games.GamesShowcase
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.UI.Animations.startTypingAnimation
import co.geeksempire.premium.storefront.Utils.UI.Colors.Gradient
import co.geeksempire.premium.storefront.Utils.UI.Colors.gradientText
import co.geeksempire.premium.storefront.databinding.DeveloperIntroductionLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DeveloperIntroductionPage : AppCompatActivity() {

    val developerLiveData: DeveloperLiveData by lazy {
        ViewModelProvider(this@DeveloperIntroductionPage).get(DeveloperLiveData::class.java)
    }

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@DeveloperIntroductionPage)
    }

    val productDetailsFragment: ProductDetailsFragment by lazy {
        ProductDetailsFragment()
    }

    lateinit var applicationsShowcase: ApplicationsShowcase

    lateinit var gamesShowcase: GamesShowcase

    var applicationsProductsAvailable = false
    var gamesProductsAvailable = false
    var booksProductsAvailable = false
    var moviesProductsAvailable = false

    lateinit var developerIntroductionLayoutBinding: DeveloperIntroductionLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        developerIntroductionLayoutBinding = DeveloperIntroductionLayoutBinding.inflate(layoutInflater)
        setContentView(developerIntroductionLayoutBinding.root)

        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {

                setupUserInterfaceDeveloperPage(it)

                applicationsShowcase = ApplicationsShowcase(this@DeveloperIntroductionPage, developerIntroductionLayoutBinding.productShowcaseRecyclerView, developerIntroductionLayoutBinding.contentDetailsContainer, productDetailsFragment, it)

                gamesShowcase = GamesShowcase(this@DeveloperIntroductionPage, developerIntroductionLayoutBinding.productShowcaseRecyclerView, developerIntroductionLayoutBinding.contentDetailsContainer, productDetailsFragment, it)

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

            val developerSocialMediaIcon = intent.getStringExtra(DevelopersDataKey.DeveloperSocialMediaIcon)!!
            val developerSocialMediaLink = intent.getStringExtra(DevelopersDataKey.DeveloperSocialMediaLink)!!

            val productsApplicationsId = if (intent.hasExtra(DevelopersDataKey.DeveloperApplications)) { intent.getStringExtra(DevelopersDataKey.DeveloperApplications) } else { null }
            val productsGamesId = if (intent.hasExtra(DevelopersDataKey.DeveloperGames)) { intent.getStringExtra(DevelopersDataKey.DeveloperGames) } else { null }
            val productsBooksId = if (intent.hasExtra(DevelopersDataKey.DeveloperBooks)) { intent.getStringExtra(DevelopersDataKey.DeveloperBooks) } else { null }
            val developerMoviesId = if (intent.hasExtra(DevelopersDataKey.DeveloperMovies)) { intent.getStringExtra(DevelopersDataKey.DeveloperMovies) } else { null }

            Glide.with(applicationContext)
                .load(developerSocialMediaIcon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            developerIntroductionLayoutBinding.developerSocialMediaImageView.setImageDrawable(resource)

                        }

                        return false
                    }

                })
                .submit()

            productsApplicationsId?.let { developerLiveData.prepareDeveloperProductsApplications(it) }
            productsGamesId?.let { developerLiveData.prepareDeveloperProductsGames(it) }
//            developerLiveData.prepareDeveloperProductsBooks(productsBooksId)
//            developerLiveData.prepareDeveloperProductsMovies(developerMoviesId)

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

            Glide.with(applicationContext)
                .asDrawable()
                .load(developerCountryFlag)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CircleCrop())
                .into(developerIntroductionLayoutBinding.developerCountryFlag)

            developerIntroductionLayoutBinding.developerCountryFlag.contentDescription = developerCountry

            developerIntroductionLayoutBinding.developerNameTextView.text = Html.fromHtml(developerName, Html.FROM_HTML_MODE_COMPACT)

            Handler(Looper.getMainLooper()).postDelayed({

                lifecycleScope.launch {

                    themePreferences.checkThemeLightDark().collect {

                        when (it) {
                            ThemeType.ThemeLight -> {

                                gradientText(textView = developerIntroductionLayoutBinding.developerNameTextView,
                                    gradientColors = intArrayOf(getColor(R.color.premiumDark), getColor(R.color.black)),
                                    gradientColorsPositions = floatArrayOf(0f, 0.91f),
                                    gradientVerticalEnd = developerIntroductionLayoutBinding.developerNameTextView.height.toFloat(),
                                    gradientType = Gradient.VerticalGradient)

                                developerIntroductionLayoutBinding.developerNameTextView.setShadowLayer(developerIntroductionLayoutBinding.developerNameTextView.shadowRadius, developerIntroductionLayoutBinding.developerNameTextView.shadowDx, developerIntroductionLayoutBinding.developerNameTextView.shadowDy, getColor(R.color.dark_transparent))

                            }
                            ThemeType.ThemeDark -> {

                                gradientText(textView = developerIntroductionLayoutBinding.developerNameTextView,
                                    gradientColors = intArrayOf(getColor(R.color.premiumLight), getColor(R.color.white)),
                                    gradientColorsPositions = floatArrayOf(0f, 0.99f),
                                    gradientVerticalEnd = developerIntroductionLayoutBinding.developerNameTextView.height.toFloat(),
                                    gradientType = Gradient.VerticalGradient)

                                developerIntroductionLayoutBinding.developerNameTextView.setShadowLayer(developerIntroductionLayoutBinding.developerNameTextView.shadowRadius, developerIntroductionLayoutBinding.developerNameTextView.shadowDx, developerIntroductionLayoutBinding.developerNameTextView.shadowDy, getColor(R.color.light_transparent))

                            }
                        }

                        developerIntroductionLayoutBinding.developerNameTextView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                        developerIntroductionLayoutBinding.developerNameTextView.visibility = View.VISIBLE

                    }
                }

            }, 500)

            developerIntroductionLayoutBinding.developerDescriptionTextView.startTypingAnimation(developerDescription)

            developerLiveData.developerProductsApplications.observe(this@DeveloperIntroductionPage, {

                if (it.isNotEmpty()) {

                    if (developerIntroductionLayoutBinding.comingSoonView.isShown) {
                        developerIntroductionLayoutBinding.comingSoonView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                        developerIntroductionLayoutBinding.comingSoonView.visibility = View.GONE
                    }

                    applicationsProductsAvailable = true

                    developerIntroductionLayoutBinding.productApplications.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    developerIntroductionLayoutBinding.productApplications.visibility = View.VISIBLE

                    developerIntroductionLayoutBinding.productApplications.setOnClickListener { view ->

                        if (::applicationsShowcase.isLateinit) {

                            if (developerIntroductionLayoutBinding.productShowcaseRecyclerView.isGone) {
                                developerIntroductionLayoutBinding.productShowcaseRecyclerView.visibility = View.VISIBLE
                            }

                            applicationsShowcase.prepareToPresent(it)

                            if (applicationsProductsAvailable) {
                                developerIntroductionLayoutBinding.productApplications.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                                developerIntroductionLayoutBinding.productApplications.visibility = View.GONE
                            }

                            if (gamesProductsAvailable) {
                                developerIntroductionLayoutBinding.productGames.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                                developerIntroductionLayoutBinding.productGames.visibility = View.GONE
                            }

                            if (booksProductsAvailable) {
                                developerIntroductionLayoutBinding.productBooks.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                                developerIntroductionLayoutBinding.productBooks.visibility = View.GONE
                            }

                            if (moviesProductsAvailable) {
                                developerIntroductionLayoutBinding.productMovies.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                                developerIntroductionLayoutBinding.productMovies.visibility = View.GONE
                            }

                        }

                    }

                }

            })

            developerLiveData.developerProductsGames.observe(this@DeveloperIntroductionPage, {

                if (it.isNotEmpty()) {

                    if (developerIntroductionLayoutBinding.comingSoonView.isShown) {
                        developerIntroductionLayoutBinding.comingSoonView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                        developerIntroductionLayoutBinding.comingSoonView.visibility = View.GONE
                    }

                    gamesProductsAvailable = true

                    developerIntroductionLayoutBinding.productGames.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    developerIntroductionLayoutBinding.productGames.visibility = View.VISIBLE

                    developerIntroductionLayoutBinding.productGames.setOnClickListener { view ->

                        if (::gamesShowcase.isLateinit) {

                            if (developerIntroductionLayoutBinding.productShowcaseRecyclerView.isGone) {
                                developerIntroductionLayoutBinding.productShowcaseRecyclerView.visibility = View.VISIBLE
                            }

                            gamesShowcase.prepareToPresent(it)

                            if (applicationsProductsAvailable) {
                                developerIntroductionLayoutBinding.productApplications.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                                developerIntroductionLayoutBinding.productApplications.visibility = View.GONE
                            }

                            if (gamesProductsAvailable) {
                                developerIntroductionLayoutBinding.productGames.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                                developerIntroductionLayoutBinding.productGames.visibility = View.GONE
                            }

                            if (booksProductsAvailable) {
                                developerIntroductionLayoutBinding.productBooks.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                                developerIntroductionLayoutBinding.productBooks.visibility = View.GONE
                            }

                            if (moviesProductsAvailable) {
                                developerIntroductionLayoutBinding.productMovies.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                                developerIntroductionLayoutBinding.productMovies.visibility = View.GONE
                            }

                        }

                    }

                }

            })

            developerWebsiteInteraction(developerWebsite)
            developerEmailInteraction(developerEmail)
            developerSocialMediaInteraction(developerSocialMediaLink)

        } else {

            this@DeveloperIntroductionPage.finish()

        }

    }

    override fun onStart() {
        super.onStart()

        developerIntroductionLayoutBinding.goBackView.setOnClickListener {

            backButtonAction()

        }

    }

    override fun onBackPressed() {

        backButtonAction()

    }

    private fun developerWebsiteInteraction(developerWebsite: String) {

        developerIntroductionLayoutBinding.developerWebsiteImageView.setOnClickListener {

            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(developerWebsite)))

        }

    }

    private fun developerEmailInteraction(developerEmail: String) {

        developerIntroductionLayoutBinding.developerEmailImageView.setOnClickListener {

            val messageToSupport = "" +
                    "\n\n\n" +
                    "[Contacted From <b>Premium Storefront</b> " +
                    "\n" +
                    "${getString(R.string.premiumStorefrontPlayStoreLink)}]"

            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, arrayOf(developerEmail))
                putExtra(Intent.EXTRA_SUBJECT,  "Contact From Premium Storefront")
                putExtra(Intent.EXTRA_TEXT, messageToSupport)
                type = "text/*"
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(emailIntent)

        }

    }

    private fun developerSocialMediaInteraction(developerSocialMedia: String) {

        developerIntroductionLayoutBinding.developerSocialMediaImageView.setOnClickListener {

            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(developerSocialMedia)))

        }

    }

    private fun backButtonAction() {

        if (productDetailsFragment.isShowing) {

            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(0, R.anim.fade_out)
                .remove(productDetailsFragment)
                .commitNow()

        } else if (developerIntroductionLayoutBinding.productShowcaseRecyclerView.isShown) {

            developerIntroductionLayoutBinding.productShowcaseRecyclerView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
            developerIntroductionLayoutBinding.productShowcaseRecyclerView.visibility = View.GONE

            if (applicationsProductsAvailable) {
                developerIntroductionLayoutBinding.productApplications.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                developerIntroductionLayoutBinding.productApplications.visibility = View.VISIBLE
            }

            if (gamesProductsAvailable) {
                developerIntroductionLayoutBinding.productGames.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                developerIntroductionLayoutBinding.productGames.visibility = View.VISIBLE
            }

            if (booksProductsAvailable) {
                developerIntroductionLayoutBinding.productBooks.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                developerIntroductionLayoutBinding.productBooks.visibility = View.VISIBLE
            }

            if (moviesProductsAvailable) {
                developerIntroductionLayoutBinding.productMovies.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                developerIntroductionLayoutBinding.productMovies.visibility = View.VISIBLE
            }

        } else {

            overridePendingTransition(0, R.anim.slide_out_right)
            this@DeveloperIntroductionPage.finish()

        }

    }

}