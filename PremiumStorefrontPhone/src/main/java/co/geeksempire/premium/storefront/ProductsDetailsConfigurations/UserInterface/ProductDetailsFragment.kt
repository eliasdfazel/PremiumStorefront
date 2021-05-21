/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/21/21, 2:32 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.geeksempire.premium.storefront.BuiltInBrowserConfigurations.BuiltInBrowser
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.YoutubeConfigurations.SetupYoutubePlayer
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
import co.geeksempire.premium.storefront.databinding.ProductDetailsLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ProductDetailsFragment : Fragment() {

    var storefrontInstance: Storefront? = null

    var isShowing = false

    lateinit var productDetailsLayoutBinding: ProductDetailsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as Storefront).prepareActionCenterUserInterface.setupIconsForDetails()

    }

    override fun onCreateView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        productDetailsLayoutBinding = ProductDetailsLayoutBinding.inflate(layoutInflater)

        return productDetailsLayoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyEffects()

        arguments?.apply {

            val applicationId = getString(ProductDataKey.ProductId)
            val applicationPackageName = getString(ProductDataKey.ProductPackageName)

            getString(ProductDataKey.ProductIcon)?.let { productIcon ->

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

                                val dominantColor = extractDominantColor(requireContext(), resource)
                                val vibrantColor = extractVibrantColor(requireContext(), resource)

                                requireActivity().runOnUiThread {

                                    productDetailsLayoutBinding.applicationIconBlurView.setOverlayColor(dominantColor)
                                    productDetailsLayoutBinding.applicationIconBlurView.setSecondOverlayColor(setColorAlpha(requireContext().getColor(R.color.light_transparent_high), 123f))

                                    productDetailsLayoutBinding.applicationIconImageView.setImageDrawable(resource)

                                    if (getString(ProductDataKey.ProductCoverImage) == null) {

                                        val gradientFeaturedBackground = GradientDrawable(GradientDrawable.Orientation.TL_BR, intArrayOf(dominantColor, vibrantColor))

                                        productDetailsLayoutBinding.applicationFeaturedImageView.background = gradientFeaturedBackground

                                    } else {

                                        getString(ProductDataKey.ProductCoverImage)?.let { productCoverImage ->

                                            Glide.with(requireContext())
                                                .asDrawable()
                                                .load(productCoverImage)
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(productDetailsLayoutBinding.applicationFeaturedImageView)

                                        }

                                    }

                                }

                            }

                            return true
                        }

                    })
                    .submit()

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

            val applicationYoutubeIntroduction = getString(ProductDataKey.ProductYoutubeIntroduction)?.let { applicationYoutubeIntroduction ->

                productDetailsLayoutBinding.applicationYoutubeView.visibility = View.VISIBLE
                productDetailsLayoutBinding.playYoutubeView.visibility = View.VISIBLE

                productDetailsLayoutBinding.playYoutubeView.setOnClickListener {

                    BuiltInBrowser.show(
                        context = requireContext(),
                        linkToLoad = applicationYoutubeIntroduction,
                        gradientColorOne = null,
                        gradientColorTwo = null
                    )

                }

                SetupYoutubePlayer(productDetailsLayoutBinding.applicationYoutubeView)
                    .initialize(applicationYoutubeIntroduction)

                applicationYoutubeIntroduction
            }

            storefrontInstance?.actionCenterOperations?.setupForProductDetails(
                applicationPackageName = applicationPackageName?:requireContext().packageName,
                applicationName = productName?:requireContext().getString(R.string.applicationName),
                applicationSummary = productSummary?:requireContext().getString(R.string.applicationSummary))

            productDetailsLayoutBinding.goBackView.setOnClickListener {

                storefrontInstance?.apply {

                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .remove(productDetailsFragment)
                        .commit()

                    storefrontLayoutBinding.contentDetailsContainer.visibility = View.GONE

                    prepareActionCenterUserInterface.setupIconsForStorefront()

                }

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        isShowing = false

    }

}