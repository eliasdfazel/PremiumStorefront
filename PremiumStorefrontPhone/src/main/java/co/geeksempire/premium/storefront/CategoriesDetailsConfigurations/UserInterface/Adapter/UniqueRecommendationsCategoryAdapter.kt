/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/19/21, 2:58 PM
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
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.ViewHolder.UniqueRecommendationsCategoryViewHolder
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

class UniqueRecommendationsCategoryAdapter (val context: CategoryDetails, var themeType: Boolean = ThemeType.ThemeLight) : RecyclerView.Adapter<UniqueRecommendationsCategoryViewHolder>() {

    val storefrontContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : UniqueRecommendationsCategoryViewHolder {

        return UniqueRecommendationsCategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.products_of_category_item, viewGroup, false))
    }

    override fun onBindViewHolder(uniqueRecommendationsCategoryViewHolder: UniqueRecommendationsCategoryViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(uniqueRecommendationsCategoryViewHolder, position, payloads)

        if (!storefrontContents[position].installViewText.equals(context.getString(R.string.installNowText))) {

            uniqueRecommendationsCategoryViewHolder.installView.icon = context.getDrawable(R.drawable.share_icon)
            uniqueRecommendationsCategoryViewHolder.installView.iconSize = dpToInteger(context, 23)

        }

        uniqueRecommendationsCategoryViewHolder.installView.text = storefrontContents[position].installViewText

        when (themeType) {
            ThemeType.ThemeLight -> {

                uniqueRecommendationsCategoryViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                uniqueRecommendationsCategoryViewHolder.productNameTextView.setShadowLayer(uniqueRecommendationsCategoryViewHolder.productNameTextView.shadowRadius, uniqueRecommendationsCategoryViewHolder.productNameTextView.shadowDx, uniqueRecommendationsCategoryViewHolder.productNameTextView.shadowDy, context.getColor(R.color.white))

                uniqueRecommendationsCategoryViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.dark))

                uniqueRecommendationsCategoryViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_solid_icon_light))

                uniqueRecommendationsCategoryViewHolder.productRatingStarsView.background = context.getDrawable(R.drawable.application_rating_glowing_frame_light)
                uniqueRecommendationsCategoryViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.white))

                uniqueRecommendationsCategoryViewHolder.blurryBackground.setSecondOverlayColor(context.getColor(R.color.light_transparent))
                uniqueRecommendationsCategoryViewHolder.blurryBackground.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

            }
            ThemeType.ThemeDark -> {

                uniqueRecommendationsCategoryViewHolder.productNameTextView.setTextColor(context.getColor(R.color.light))
                uniqueRecommendationsCategoryViewHolder.productNameTextView.setShadowLayer(uniqueRecommendationsCategoryViewHolder.productNameTextView.shadowRadius, uniqueRecommendationsCategoryViewHolder.productNameTextView.shadowDx, uniqueRecommendationsCategoryViewHolder.productNameTextView.shadowDy, context.getColor(R.color.black))

                uniqueRecommendationsCategoryViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.light))

                uniqueRecommendationsCategoryViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_solid_icon_dark))

                uniqueRecommendationsCategoryViewHolder.productRatingStarsView.background = context.getDrawable(R.drawable.application_rating_glowing_frame_dark)
                uniqueRecommendationsCategoryViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black_transparent))

                uniqueRecommendationsCategoryViewHolder.blurryBackground.setSecondOverlayColor(context.getColor(R.color.dark_transparent))
                uniqueRecommendationsCategoryViewHolder.blurryBackground.setOverlayColor(context.getColor(R.color.premiumDarkTransparent))

            }
            else -> {

                uniqueRecommendationsCategoryViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                uniqueRecommendationsCategoryViewHolder.productNameTextView.setShadowLayer(uniqueRecommendationsCategoryViewHolder.productNameTextView.shadowRadius, uniqueRecommendationsCategoryViewHolder.productNameTextView.shadowDx, uniqueRecommendationsCategoryViewHolder.productNameTextView.shadowDy, context.getColor(R.color.white))

                uniqueRecommendationsCategoryViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.dark))

                uniqueRecommendationsCategoryViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_solid_icon_light))

                uniqueRecommendationsCategoryViewHolder.productRatingStarsView.background = context.getDrawable(R.drawable.application_rating_glowing_frame_light)
                uniqueRecommendationsCategoryViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.white))

                uniqueRecommendationsCategoryViewHolder.blurryBackground.setSecondOverlayColor(context.getColor(R.color.light_transparent))
                uniqueRecommendationsCategoryViewHolder.blurryBackground.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

            }
        }

    }

    override fun onBindViewHolder(uniqueRecommendationsCategoryViewHolder: UniqueRecommendationsCategoryViewHolder, position: Int) {

        uniqueRecommendationsCategoryViewHolder.productNameTextView.text = Html.fromHtml(storefrontContents[position].productName, Html.FROM_HTML_MODE_COMPACT)
        uniqueRecommendationsCategoryViewHolder.productSummaryTextView.text = Html.fromHtml(storefrontContents[position].productSummary, Html.FROM_HTML_MODE_COMPACT)

        uniqueRecommendationsCategoryViewHolder.productCurrentRateView.text = storefrontContents[position].productAttributes[ProductsContentKey.AttributesRatingKey]

        uniqueRecommendationsCategoryViewHolder.productCurrentRateView.setTextColor(context.getColor(R.color.white))

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

                            uniqueRecommendationsCategoryViewHolder.productCurrentRateView.setShadowLayer(uniqueRecommendationsCategoryViewHolder.productCurrentRateView.shadowRadius, uniqueRecommendationsCategoryViewHolder.productCurrentRateView.shadowDx, uniqueRecommendationsCategoryViewHolder.productCurrentRateView.shadowDy, vibrantColor)

                            uniqueRecommendationsCategoryViewHolder.installView.backgroundTintList = ColorStateList.valueOf(vibrantColor)
                            uniqueRecommendationsCategoryViewHolder.installView.rippleColor = ColorStateList.valueOf(context.getColor(R.color.white))

                            uniqueRecommendationsCategoryViewHolder.productCurrentRateView.setTextColor(vibrantColor)

                            val applicationGlowingFrame = context.getDrawable(R.drawable.all_application_icon_glowing_frame) as LayerDrawable
                            applicationGlowingFrame.findDrawableByLayerId(R.id.circleIconBackground).setTint(vibrantColor)
                            applicationGlowingFrame.findDrawableByLayerId(R.id.circleIconFrame).setTint(vibrantColor)

                            uniqueRecommendationsCategoryViewHolder.productIconImageView.background = applicationGlowingFrame

                            uniqueRecommendationsCategoryViewHolder.productIconImageView.setImageDrawable(resource)

                        }

                    }

                    return true
                }

            })
            .submit()

        uniqueRecommendationsCategoryViewHolder.rootView.setOnClickListener {

            openProductsDetails(context = context, fragmentInterface = context,
                contentDetailsContainer= context.categoryDetailsLayoutBinding.contentDetailsContainer, productDetailsFragment = context.productDetailsFragment,
                storefrontContents = storefrontContents[position])

        }

        uniqueRecommendationsCategoryViewHolder.rootView.setOnLongClickListener {


            true
        }

        uniqueRecommendationsCategoryViewHolder.installView.setOnClickListener {

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