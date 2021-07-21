/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/21/21, 10:35 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
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
import co.geeksempire.premium.storefront.Utils.UI.Colors.colorsTheSame
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractMutedColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class UniqueRecommendationsCategoryAdapter (val context: CategoryDetails, var themeType: Boolean = ThemeType.ThemeLight) : RecyclerView.Adapter<UniqueRecommendationsCategoryViewHolder>() {

    val storefrontContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : UniqueRecommendationsCategoryViewHolder {

        return UniqueRecommendationsCategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.unique_section_item, viewGroup, false))
    }

    override fun onBindViewHolder(uniqueRecommendationsCategoryViewHolder: UniqueRecommendationsCategoryViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(uniqueRecommendationsCategoryViewHolder, position, payloads)


        when (themeType) {
            ThemeType.ThemeLight -> {



            }
            ThemeType.ThemeDark -> {



            }
            else -> {



            }
        }

    }

    override fun onBindViewHolder(uniqueRecommendationsCategoryViewHolder: UniqueRecommendationsCategoryViewHolder, position: Int) {

        Glide.with(context)
            .asDrawable()
            .load(storefrontContents[position].productIconLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CircleCrop())
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return true }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        val dominantColor = extractDominantColor(context, resource)
                        var vibrantColor = extractVibrantColor(context, resource)

                        if (colorsTheSame(dominantColor, vibrantColor)) {
                            vibrantColor = extractMutedColor(context, resource)
                        }

                        context.runOnUiThread {

                            val gradientFeaturedBackground = GradientDrawable(GradientDrawable.Orientation.TR_BL, intArrayOf(dominantColor, vibrantColor))
                            gradientFeaturedBackground.cornerRadius = dpToInteger(context, 19).toFloat()

                            uniqueRecommendationsCategoryViewHolder.verticalArtImageView.background = gradientFeaturedBackground

                        }

                    }

                    return true
                }

            })
            .submit()

        Glide.with(context)
            .asDrawable()
            .load(storefrontContents[position].productAttributes[ProductsContentKey.AttributesVerticalArtKey]?:context.getString(R.string.choicePremiumStorefrontUnique))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(RoundedCorners(dpToInteger(context, 15)))
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return true }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        context.runOnUiThread {

                            val uniqueVerticalArtLayer = context.getDrawable(R.drawable.unique_vertical_art_layer) as LayerDrawable
                            uniqueVerticalArtLayer.setDrawableByLayerId(R.id.verticalArtLayer, resource)

                            uniqueRecommendationsCategoryViewHolder.verticalArtImageView.setImageDrawable(uniqueVerticalArtLayer)

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

    }

}