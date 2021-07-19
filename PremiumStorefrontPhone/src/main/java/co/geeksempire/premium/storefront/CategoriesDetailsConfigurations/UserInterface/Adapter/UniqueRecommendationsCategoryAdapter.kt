/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/19/21, 2:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetails
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.ViewHolder.ProductsOfCategoryViewHolder
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.Extensions.openProductsDetails
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.Utils.Data.shareApplication
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class UniqueRecommendationsCategoryAdapter (val context: CategoryDetails, var themeType: Boolean = ThemeType.ThemeLight) : RecyclerView.Adapter<ProductsOfCategoryViewHolder>() {

    val storefrontContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : ProductsOfCategoryViewHolder {

        return ProductsOfCategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.products_of_category_item, viewGroup, false))
    }

    override fun onBindViewHolder(productsOfCategoryViewHolder: ProductsOfCategoryViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(productsOfCategoryViewHolder, position, payloads)

        if (!storefrontContents[position].installViewText.equals(context.getString(R.string.installNowText))) {

            productsOfCategoryViewHolder.installView.icon = context.getDrawable(R.drawable.share_icon)
            productsOfCategoryViewHolder.installView.iconSize = dpToInteger(context, 23)

        }

        productsOfCategoryViewHolder.installView.text = storefrontContents[position].installViewText

        when (themeType) {
            ThemeType.ThemeLight -> {

                productsOfCategoryViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                productsOfCategoryViewHolder.productNameTextView.setShadowLayer(productsOfCategoryViewHolder.productNameTextView.shadowRadius, productsOfCategoryViewHolder.productNameTextView.shadowDx, productsOfCategoryViewHolder.productNameTextView.shadowDy, context.getColor(R.color.white))

                productsOfCategoryViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.dark))

                productsOfCategoryViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_solid_icon_light))

                productsOfCategoryViewHolder.productRatingStarsView.background = context.getDrawable(R.drawable.application_rating_glowing_frame_light)
                productsOfCategoryViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.white))

                productsOfCategoryViewHolder.blurryBackground.setSecondOverlayColor(context.getColor(R.color.light_transparent))
                productsOfCategoryViewHolder.blurryBackground.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

            }
            ThemeType.ThemeDark -> {

                productsOfCategoryViewHolder.productNameTextView.setTextColor(context.getColor(R.color.light))
                productsOfCategoryViewHolder.productNameTextView.setShadowLayer(productsOfCategoryViewHolder.productNameTextView.shadowRadius, productsOfCategoryViewHolder.productNameTextView.shadowDx, productsOfCategoryViewHolder.productNameTextView.shadowDy, context.getColor(R.color.black))

                productsOfCategoryViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.light))

                productsOfCategoryViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_solid_icon_dark))

                productsOfCategoryViewHolder.productRatingStarsView.background = context.getDrawable(R.drawable.application_rating_glowing_frame_dark)
                productsOfCategoryViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black_transparent))

                productsOfCategoryViewHolder.blurryBackground.setSecondOverlayColor(context.getColor(R.color.dark_transparent))
                productsOfCategoryViewHolder.blurryBackground.setOverlayColor(context.getColor(R.color.premiumDarkTransparent))

            }
            else -> {

                productsOfCategoryViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                productsOfCategoryViewHolder.productNameTextView.setShadowLayer(productsOfCategoryViewHolder.productNameTextView.shadowRadius, productsOfCategoryViewHolder.productNameTextView.shadowDx, productsOfCategoryViewHolder.productNameTextView.shadowDy, context.getColor(R.color.white))

                productsOfCategoryViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.dark))

                productsOfCategoryViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_solid_icon_light))

                productsOfCategoryViewHolder.productRatingStarsView.background = context.getDrawable(R.drawable.application_rating_glowing_frame_light)
                productsOfCategoryViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.white))

                productsOfCategoryViewHolder.blurryBackground.setSecondOverlayColor(context.getColor(R.color.light_transparent))
                productsOfCategoryViewHolder.blurryBackground.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

            }
        }

    }

    override fun onBindViewHolder(productsOfCategoryViewHolder: ProductsOfCategoryViewHolder, position: Int) {

        productsOfCategoryViewHolder.productNameTextView.text = Html.fromHtml(storefrontContents[position].productName, Html.FROM_HTML_MODE_COMPACT)
        productsOfCategoryViewHolder.productSummaryTextView.text = Html.fromHtml(storefrontContents[position].productSummary, Html.FROM_HTML_MODE_COMPACT)

        productsOfCategoryViewHolder.productCurrentRateView.text = storefrontContents[position].productAttributes[ProductsContentKey.AttributesRatingKey]

        productsOfCategoryViewHolder.productCurrentRateView.setTextColor(context.getColor(R.color.white))

        //Product Icon Image
        Glide.with(context)
            .asDrawable()
            .load(storefrontContents[position].productIconLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CircleCrop())
            .override(256, 256)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return true }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        val vibrantColor = extractVibrantColor(context, resource)

                        context.runOnUiThread {

                            productsOfCategoryViewHolder.productCurrentRateView.setShadowLayer(productsOfCategoryViewHolder.productCurrentRateView.shadowRadius, productsOfCategoryViewHolder.productCurrentRateView.shadowDx, productsOfCategoryViewHolder.productCurrentRateView.shadowDy, vibrantColor)

                            productsOfCategoryViewHolder.installView.backgroundTintList = ColorStateList.valueOf(vibrantColor)
                            productsOfCategoryViewHolder.installView.rippleColor = ColorStateList.valueOf(context.getColor(R.color.white))

                            productsOfCategoryViewHolder.productCurrentRateView.setTextColor(vibrantColor)

                            val applicationGlowingFrame = context.getDrawable(R.drawable.all_application_icon_glowing_frame) as LayerDrawable
                            applicationGlowingFrame.findDrawableByLayerId(R.id.circleIconBackground).setTint(vibrantColor)
                            applicationGlowingFrame.findDrawableByLayerId(R.id.circleIconFrame).setTint(vibrantColor)

                            productsOfCategoryViewHolder.productIconImageView.background = applicationGlowingFrame

                            productsOfCategoryViewHolder.productIconImageView.setImageDrawable(resource)

                        }

                    }

                    return true
                }

            })
            .submit()

        productsOfCategoryViewHolder.rootView.setOnClickListener {

            openProductsDetails(context = context, fragmentInterface = context,
                contentDetailsContainer= context.categoryDetailsLayoutBinding.contentDetailsContainer, productDetailsFragment = context.productDetailsFragment,
                storefrontContents = storefrontContents[position])

        }

        productsOfCategoryViewHolder.rootView.setOnLongClickListener {


            true
        }

        productsOfCategoryViewHolder.installView.setOnClickListener {

            if (!storefrontContents[position].installViewText.equals(context.getString(R.string.installNowText))) {

                shareApplication(context,
                    storefrontContents[position].productName,
                    storefrontContents[position].productName,
                    storefrontContents[position].productSummary)

            } else {

                openPlayStoreToInstall(context = context,
                    aPackageName = (storefrontContents[position].productAttributes[ProductsContentKey.AttributesPackageNameKey].toString()),
                    applicationName = storefrontContents[position].productName,
                    applicationSummary = storefrontContents[position].productSummary)

            }

        }

    }

}