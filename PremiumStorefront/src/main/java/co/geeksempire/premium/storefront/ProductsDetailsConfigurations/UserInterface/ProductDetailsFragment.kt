/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/2/21, 11:42 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.BuiltInBrowserConfigurations.UserInterface.showBuiltInBrowser
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.DataStructure.CategoriesDataKeys
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetails
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.DevelopersConfigurations.DataStructure.DevelopersDataKey
import co.geeksempire.premium.storefront.DevelopersConfigurations.NetworkConnection.DeveloperDataInterface
import co.geeksempire.premium.storefront.DevelopersConfigurations.NetworkConnection.RetrieveDeveloperInformation
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.DeveloperIntroductionPage
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.Extensions.startFavoriteProcess
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoriteProductQueryInterface
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoritedProcess
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.YoutubeConfigurations.SetupYoutubePlayer
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.YoutubeConfigurations.YouTubeInterface
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.Notifications.doVibrate
import co.geeksempire.premium.storefront.Utils.UI.Colors.*
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import co.geeksempire.premium.storefront.databinding.ProductDetailsLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.geeksempire.balloon.optionsmenu.library.Utils.calculatePercentage
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class ProductDetailsFragment : Fragment() {

    var instanceOfProductDetailsFragment: ProductDetailsFragment? = null

    var instanceOfFragmentInterface: FragmentInterface? = null

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(requireActivity() as AppCompatActivity)
    }

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(requireContext())
    }

    val favoritedProcess: FavoritedProcess by lazy {
        FavoritedProcess(requireActivity() as AppCompatActivity)
    }

    var isShowing = false

    var dominantColor = Color.GRAY
    var vibrantColor = Color.GRAY

    lateinit var productDetailsLayoutBinding: ProductDetailsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        productDetailsLayoutBinding = ProductDetailsLayoutBinding.inflate(layoutInflater)

        return productDetailsLayoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {

            themePreferences.checkThemeLightDark().collect {

                applyColors(it)

                applyShadowEffectsForContentBackground(it)

                applyNegativeSpaceEffectsForFavorite(it)

            }

        }

        applyNegativeSpaceEffectsForCategoryIcon()

        applyNegativeSpaceEffectsForCategoryName()

        arguments?.apply {

            val productId = getString(ProductDataKey.ProductId)
            val applicationPackageName = getString(ProductDataKey.ProductPackageName)

            val productIconLink = getString(ProductDataKey.ProductIcon)
            productIconLink?.let { productIcon ->

                Glide.with(requireContext())
                    .asDrawable()
                    .load(productIcon)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(CircleCrop())
                    .listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(exception: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {



                            return true
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                            resource?.let {

                                requireActivity().runOnUiThread {

                                    dominantColor = extractDominantColor(resource)
                                    vibrantColor = extractVibrantColor(resource)

                                    lifecycleScope.launch {

                                        themePreferences.checkThemeLightDark().collect {

                                            applyGlowingEffectsForRatingBackground(themeType = it, glowingColor = vibrantColor)

                                            if (getString(ProductDataKey.ProductCoverImage) == null) {

                                                productDetailsLayoutBinding.applicationIconBlurView.setSecondOverlayColor(when (it) {
                                                    ThemeType.ThemeLight -> {
                                                        requireContext().getColor(R.color.light_transparent_high)
                                                    }
                                                    ThemeType.ThemeDark -> {
                                                        requireContext().getColor(R.color.dark_transparent_high)
                                                    }
                                                    else -> requireContext().getColor(R.color.light_transparent_high)
                                                })

                                                val gradientFeaturedBackground = GradientDrawable(GradientDrawable.Orientation.TL_BR, intArrayOf(dominantColor, vibrantColor))
                                                productDetailsLayoutBinding.applicationFeaturedImageView.background = gradientFeaturedBackground

                                                val temporaryPadding = dpToInteger(requireContext(), 3)
                                                productDetailsLayoutBinding.applicationFeaturedImageView.setPadding(temporaryPadding, temporaryPadding, temporaryPadding, temporaryPadding)

                                                Glide.with(requireContext())
                                                    .load(requireContext().getString(R.string.choicePremiumStorefront))
                                                    .into(productDetailsLayoutBinding.applicationFeaturedImageView)

                                            } else {

                                                Glide.with(requireContext())
                                                    .asDrawable()
                                                    .load(getString(ProductDataKey.ProductCoverImage))
                                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                    .into(productDetailsLayoutBinding.applicationFeaturedImageView)

                                            }

                                        }

                                    }

                                    productDetailsLayoutBinding.applicationRatingTextView.setTextColor(vibrantColor)
                                    productDetailsLayoutBinding.applicationRatingTextView.setShadowLayer(productDetailsLayoutBinding.applicationRatingTextView.shadowRadius, productDetailsLayoutBinding.applicationRatingTextView.shadowDx, productDetailsLayoutBinding.applicationRatingTextView.shadowDy, vibrantColor)

                                    productDetailsLayoutBinding.applicationIconBlurView.setOverlayColor(setColorAlpha(dominantColor, 222f))

                                    productDetailsLayoutBinding.applicationIconImageView.setImageDrawable(resource)

                                }

                            }

                            return true
                        }

                    })
                    .submit()

            }

            val productDeveloper = getString(ProductDataKey.ProductDeveloper)?:"Unknown"
            productDetailsLayoutBinding.applicationDeveloper.text = Html.fromHtml(productDeveloper, Html.FROM_HTML_MODE_COMPACT)
            getString(ProductDataKey.ProductDeveloper)?.let { developerName ->

                RetrieveDeveloperInformation(developerName)
                    .start(object : DeveloperDataInterface {

                        override fun developerInformation(developerData: HashMap<String, String>) {
                            super.developerInformation(developerData)

                            productDetailsLayoutBinding.applicationDeveloper.setOnClickListener {
                                doVibrate(requireContext(), 258)

                                val developerPageIntent = Intent(requireContext(), DeveloperIntroductionPage::class.java).apply {

                                    putExtra(DevelopersDataKey.DeveloperName, developerData[DevelopersDataKey.DeveloperName])
                                    putExtra(DevelopersDataKey.DeveloperDescription, developerData[DevelopersDataKey.DeveloperDescription])

                                    putExtra(DevelopersDataKey.DeveloperLogo, developerData[DevelopersDataKey.DeveloperLogo])
                                    putExtra(DevelopersDataKey.DeveloperCoverImage, developerData[DevelopersDataKey.DeveloperCoverImage])

                                    putExtra(DevelopersDataKey.DeveloperCountry, developerData[DevelopersDataKey.DeveloperCountry])
                                    putExtra(DevelopersDataKey.DeveloperCountryFlag, developerData[DevelopersDataKey.DeveloperCountryFlag])

                                    putExtra(DevelopersDataKey.DeveloperEmail, developerData[DevelopersDataKey.DeveloperEmail])
                                    putExtra(DevelopersDataKey.DeveloperWebsite, developerData[DevelopersDataKey.DeveloperWebsite])

                                    putExtra(DevelopersDataKey.DeveloperSocialMediaIcon, developerData[DevelopersDataKey.DeveloperSocialMediaIcon])
                                    putExtra(DevelopersDataKey.DeveloperSocialMediaLink, developerData[DevelopersDataKey.DeveloperSocialMediaLink])

                                    developerData[DevelopersDataKey.DeveloperApplications]?.let {
                                        putExtra(DevelopersDataKey.DeveloperApplications, it)
                                    }
                                    developerData[DevelopersDataKey.DeveloperGames]?.let {
                                        putExtra(DevelopersDataKey.DeveloperGames, it)
                                    }
                                    developerData[DevelopersDataKey.DeveloperBooks]?.let {
                                        putExtra(DevelopersDataKey.DeveloperBooks, it)
                                    }
                                    developerData[DevelopersDataKey.DeveloperMovies]?.let {
                                        putExtra(DevelopersDataKey.DeveloperMovies, it)
                                    }

                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }

                                requireContext().startActivity(developerPageIntent,
                                    ActivityOptions.makeCustomAnimation(requireContext(), R.anim.slide_from_right, 0).toBundle())

                            }

                        }

                    })

            }

            Handler(Looper.getMainLooper()).postDelayed({

                gradientText(textView = productDetailsLayoutBinding.applicationDeveloper,
                    gradientColors = intArrayOf(requireContext().getColor(R.color.default_color), requireContext().getColor(R.color.default_color_light)),
                    gradientColorsPositions = floatArrayOf(0f, 0.51f),
                    gradientVerticalEnd = productDetailsLayoutBinding.applicationDeveloper.height.toFloat(),
                    gradientType = Gradient.VerticalGradient)

                productDetailsLayoutBinding.applicationDeveloper.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
                productDetailsLayoutBinding.applicationDeveloper.visibility = View.VISIBLE

            }, 500)

            val developerCountry = requireArguments().getString(ProductDataKey.ProductDeveloperCountry)
            val developerCity = requireArguments().getString(ProductDataKey.ProductDeveloperCity)

            getString(ProductDataKey.ProductCategoryName)?.let { categoryName ->

                productDetailsLayoutBinding.categoryNameTextView.text = Html.fromHtml(categoryName.split(" ").first(), Html.FROM_HTML_MODE_COMPACT)

                Handler(Looper.getMainLooper()).postDelayed({

                    gradientText(textView = productDetailsLayoutBinding.categoryNameTextView,
                        gradientColors = intArrayOf(requireContext().getColor(R.color.default_color_game), requireContext().getColor(R.color.default_color_game_bright)),
                        gradientColorsPositions = floatArrayOf(0f, 1f),
                        gradientHorizontalEnd = productDetailsLayoutBinding.categoryNameTextView.width.toFloat().calculatePercentage(29f),
                        gradientType = Gradient.HorizontalGradient)

                }, 500)

                Glide.with(requireContext())
                    .asDrawable()
                    .load((requireActivity().application as PremiumStorefrontApplication).categoryData.getCategoryIconByName(categoryName.split(" ").first()))
                    .into(productDetailsLayoutBinding.categoryIconImageView)

                productDetailsLayoutBinding.categoryIconImageView.setOnClickListener {

                    requireContext().startActivity(Intent(requireContext(), CategoryDetails::class.java).apply {
                        putExtra(CategoriesDataKeys.CategoryId, getInt(ProductDataKey.ProductCategoryId))
                        putExtra(CategoriesDataKeys.CategoryName, getString(ProductDataKey.ProductCategoryName))
                        putExtra(CategoriesDataKeys.CategoryIcon, (requireActivity().application as PremiumStorefrontApplication).categoryData.getCategoryIconByName(categoryName.split(" ").first()))
                        putExtra(GeneralEndpoints.QueryType.QueryTypeCase, if (requireActivity().javaClass.simpleName.contains("Applications")) {
                            GeneralEndpoints.QueryType.ApplicationsQuery
                        } else {
                            GeneralEndpoints.QueryType.GamesQuery
                        })
                    }, ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, 0).toBundle())

                }

                productDetailsLayoutBinding.categoryNameTextView.setOnClickListener {

                    requireContext().startActivity(Intent(requireContext(), CategoryDetails::class.java).apply {
                        putExtra(CategoriesDataKeys.CategoryId, getInt(ProductDataKey.ProductCategoryId))
                        putExtra(CategoriesDataKeys.CategoryName, getString(ProductDataKey.ProductCategoryName))
                        putExtra(CategoriesDataKeys.CategoryIcon, (requireActivity().application as PremiumStorefrontApplication).categoryData.getCategoryIconByName(categoryName.split(" ").first()))
                        putExtra(GeneralEndpoints.QueryType.QueryTypeCase, if (requireActivity().javaClass.simpleName.contains("Applications")) {
                            GeneralEndpoints.QueryType.ApplicationsQuery
                        } else {
                            GeneralEndpoints.QueryType.GamesQuery
                        })
                    }, ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, 0).toBundle())

                }

            }

            val productName = getString(ProductDataKey.ProductName)?.let { productName ->

                productDetailsLayoutBinding.applicationNameTextView.text = Html.fromHtml(productName, Html.FROM_HTML_MODE_COMPACT)

                productName
            }

            val productSummary = getString(ProductDataKey.ProductSummary)

            val productDescription = getString(ProductDataKey.ProductDescription)?.let { productDescription ->

                productDetailsLayoutBinding.applicationDescriptionTextView.text = Html.fromHtml(productDescription, Html.FROM_HTML_MODE_COMPACT)

                productDescription
            }

            getString(ProductDataKey.ProductYoutubeIntroduction)?.let { applicationYoutubeIntroduction ->

                productDetailsLayoutBinding.applicationYoutubeView.visibility = View.VISIBLE
                productDetailsLayoutBinding.playYoutubeView.visibility = View.VISIBLE

                productDetailsLayoutBinding.playYoutubeView.setOnClickListener {

                    showBuiltInBrowser(
                        context = requireContext(),
                        linkToLoad = applicationYoutubeIntroduction,
                        gradientColorOne = null,
                        gradientColorTwo = null
                    )

                }

                SetupYoutubePlayer(productDetailsLayoutBinding.applicationYoutubeView)
                    .initialize(applicationYoutubeIntroduction, object : YouTubeInterface {

                        override fun youtubeThumbnail(thumbnailImage: Drawable) {
                            super.youtubeThumbnail(thumbnailImage)



                        }

                    })

                applicationYoutubeIntroduction
            }

            instanceOfFragmentInterface?.fragmentCreated(productId.orEmpty(), productName.orEmpty(), productSummary.orEmpty())

            productDetailsLayoutBinding.favoriteView.setOnClickListener {

                startFavoriteProcess(productId!!, productName!!, productDescription!!, productIconLink!!, EntryPreferences.EntryStorefrontApplications)

            }

            productDetailsLayoutBinding.informationDetails.setOnClickListener {

                if (productDetailsLayoutBinding.locationProductDetails.isVisible) {

                    productDetailsLayoutBinding.locationProductDetails.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out))
                    productDetailsLayoutBinding.locationProductDetails.visibility = View.INVISIBLE

                    productDetailsLayoutBinding.locationProductDetailsIcon.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out))
                    productDetailsLayoutBinding.locationProductDetailsIcon.visibility = View.INVISIBLE

                    productDetailsLayoutBinding.emailProductDetails.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out))
                    productDetailsLayoutBinding.emailProductDetails.visibility = View.INVISIBLE

                    productDetailsLayoutBinding.emailProductDetailsIcon.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out))
                    productDetailsLayoutBinding.emailProductDetailsIcon.visibility = View.INVISIBLE

                } else {


                    productDetailsLayoutBinding.locationProductDetails.apply {

                        text = Html.fromHtml("${developerCity}, ${developerCountry}", Html.FROM_HTML_MODE_COMPACT)

                        gradientText(textView = productDetailsLayoutBinding.locationProductDetails,
                            gradientColors = intArrayOf(requireContext().getColor(R.color.default_color), requireContext().getColor(R.color.default_color_light)),
                            gradientColorsPositions = floatArrayOf(0f, 1f),
                            gradientHorizontalEnd = productDetailsLayoutBinding.locationProductDetails.width.toFloat().calculatePercentage(19f),
                            gradientType = Gradient.HorizontalGradient)

                        Handler(Looper.getMainLooper()).postDelayed({

                            productDetailsLayoutBinding.locationProductDetailsIcon.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left_from_right_bounce))
                            productDetailsLayoutBinding.locationProductDetailsIcon.visibility = View.VISIBLE

                            startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left_from_right_bounce))
                            visibility = View.VISIBLE

                        }, 51)

                    }

                    requireArguments().getString(ProductDataKey.ProductDeveloperEmail)?.let { developerEmail ->

                        productDetailsLayoutBinding.emailProductDetails.apply {

                            text = Html.fromHtml("${developerEmail}", Html.FROM_HTML_MODE_COMPACT)

                            gradientText(textView = productDetailsLayoutBinding.emailProductDetails,
                                gradientColors = intArrayOf(requireContext().getColor(R.color.default_color), requireContext().getColor(R.color.default_color_light)),
                                gradientColorsPositions = floatArrayOf(0f, 1f),
                                gradientHorizontalEnd = productDetailsLayoutBinding.emailProductDetails.width.toFloat().calculatePercentage(19f),
                                gradientType = Gradient.HorizontalGradient)

                            Handler(Looper.getMainLooper()).postDelayed({

                                productDetailsLayoutBinding.emailProductDetailsIcon.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left_from_right_bounce))
                                productDetailsLayoutBinding.emailProductDetailsIcon.visibility = View.VISIBLE

                                startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left_from_right_bounce))
                                visibility = View.VISIBLE

                            }, 151)

                        }.setOnClickListener {

                            val messageToSupport = "" +
                                    "\n\n\n" +
                                    "[Contacted From <b>${requireContext().getString(R.string.applicationName)}</b>]"

                            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                                putExtra(Intent.EXTRA_EMAIL, arrayOf(developerEmail))
                                putExtra(Intent.EXTRA_SUBJECT,  "Feedback On ${productName}")
                                putExtra(Intent.EXTRA_TEXT, messageToSupport)
                                type = "text/*"
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            requireContext().startActivity(emailIntent)

                        }

                    }

                }

            }

            productDetailsLayoutBinding.goBackView.setOnClickListener {

                instanceOfProductDetailsFragment?.let { detailsFragment ->

                    (requireActivity()).supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(0, R.anim.fade_out)
                        .remove(detailsFragment)
                        .detach(this@ProductDetailsFragment)
                        .commit()

                }

            }

            Firebase.auth.currentUser?.let { firebaseUser ->

                productId?.let {

                    favoritedProcess.isProductFavorited(firebaseUser.uid, firebaseUser.email!!, it,
                        object : FavoriteProductQueryInterface {

                            override fun favoriteProduct(isProductFavorited: Boolean) {
                                super.favoriteProduct(isProductFavorited)

                                if (isProductFavorited) {

                                    productDetailsLayoutBinding.favoriteView.setImageDrawable(requireContext().getDrawable(R.drawable.favorited_icon))

                                }

                            }

                        })

                }

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        instanceOfFragmentInterface?.fragmentDestroyed()

        isShowing = false

    }

}