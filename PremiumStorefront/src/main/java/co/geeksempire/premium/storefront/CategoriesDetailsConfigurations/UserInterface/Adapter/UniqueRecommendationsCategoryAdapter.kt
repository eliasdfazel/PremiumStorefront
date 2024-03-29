/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/2/21, 11:25 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetails
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.ViewHolder.UniqueRecommendationsCategoryViewHolder
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.Extensions.openFirestoreProductsDetails
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataStructure
import co.geeksempire.premium.storefront.Utils.UI.Colors.colorsTheSame
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractMutedColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.databinding.UniqueSectionItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.DocumentSnapshot
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class UniqueRecommendationsCategoryAdapter (val context: CategoryDetails, var themeType: Boolean = ThemeType.ThemeLight) : RecyclerView.Adapter<UniqueRecommendationsCategoryViewHolder>() {

    val storefrontContents: ArrayList<DocumentSnapshot> = ArrayList<DocumentSnapshot>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : UniqueRecommendationsCategoryViewHolder {

        return UniqueRecommendationsCategoryViewHolder(UniqueSectionItemBinding.inflate(context.layoutInflater, viewGroup, false))
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

        storefrontContents[position].data?.let {

            val productDataStructure = ProductDataStructure(it)

            Glide.with(context)
                .asDrawable()
                .load(productDataStructure.productIcon()?:context.getString(R.string.choicePremiumStorefrontUnique))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CircleCrop())
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return true }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            val dominantColor = extractDominantColor(resource)
                            var vibrantColor = extractVibrantColor(resource)

                            if (colorsTheSame(dominantColor, vibrantColor)) {
                                vibrantColor = extractMutedColor(resource)
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
                .load(productDataStructure.verticalArt())
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

                openFirestoreProductsDetails(context = context, fragmentInterface = context,
                    contentDetailsContainer= context.categoryDetailsLayoutBinding.contentDetailsContainer, productDetailsFragment = context.productDetailsFragment,
                    productDataStructure = productDataStructure)

            }

            uniqueRecommendationsCategoryViewHolder.rootView.setOnLongClickListener {


                true
            }

        }

    }

}