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

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.text.Html
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetails
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.ViewHolder.ProductsOfCategoryViewHolder
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.Extensions.openFirestoreProductsDetails
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataStructure
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.databinding.ProductsOfCategoryItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.DocumentSnapshot

class ProductsOfCategoryAdapter (val context: CategoryDetails, var themeType: Boolean = ThemeType.ThemeLight) : RecyclerView.Adapter<ProductsOfCategoryViewHolder>() {

    val storefrontContents: ArrayList<DocumentSnapshot> = ArrayList<DocumentSnapshot>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : ProductsOfCategoryViewHolder {

        return ProductsOfCategoryViewHolder(ProductsOfCategoryItemBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(productsOfCategoryViewHolder: ProductsOfCategoryViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(productsOfCategoryViewHolder, position, payloads)

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

        storefrontContents[position].data?.let {

            val productDataStructure = ProductDataStructure(it)

            productsOfCategoryViewHolder.productNameTextView.text = Html.fromHtml(productDataStructure.productName(), Html.FROM_HTML_MODE_COMPACT)
            productsOfCategoryViewHolder.productSummaryTextView.text = Html.fromHtml(productDataStructure.productSummary(), Html.FROM_HTML_MODE_COMPACT)

            productsOfCategoryViewHolder.productCurrentRateView.text = productDataStructure.productRating()

            productsOfCategoryViewHolder.productCurrentRateView.setTextColor(context.getColor(R.color.white))

            //Product Icon Image
            Glide.with(context)
                .asDrawable()
                .load(productDataStructure.productIcon())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CircleCrop())
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return true }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            val vibrantColor = extractVibrantColor(resource)

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

                openFirestoreProductsDetails(context = context, fragmentInterface = context,
                    contentDetailsContainer= context.categoryDetailsLayoutBinding.contentDetailsContainer, productDetailsFragment = context.productDetailsFragment,
                    productDataStructure = productDataStructure)

            }

            productsOfCategoryViewHolder.rootView.setOnLongClickListener {


                true
            }

            productsOfCategoryViewHolder.installView.setOnClickListener {

                openPlayStoreToInstallApplications(context = context,
                    aPackageName = productDataStructure.productPackageName(),
                    applicationName = productDataStructure.productName(),
                    applicationSummary = productDataStructure.productSummary())

            }

        }



    }

}