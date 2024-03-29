/*
 * Copyright © 2022 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/15/22, 5:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.DeveloperContents.Adapter

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.text.Html
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.Extensions.openProductsDetails
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.AllContent.ViewHolder.AllContentViewHolder
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import co.geeksempire.premium.storefront.databinding.StorefrontAllContentItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class AllDeveloperContentsAdapter(private val context: AppCompatActivity,
                        private val contentDetailsContainer: FragmentContainerView, private val productDetailsFragment: ProductDetailsFragment,
                        private val fragmentInterface: FragmentInterface) : RecyclerView.Adapter<AllContentViewHolder>() {

    var themeType: Boolean = ThemeType.ThemeLight

    val storefrontContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : AllContentViewHolder {

        return AllContentViewHolder(StorefrontAllContentItemBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(allContentViewHolder: AllContentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(allContentViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {

                allContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                allContentViewHolder.productNameTextView.setShadowLayer(allContentViewHolder.productNameTextView.shadowRadius, allContentViewHolder.productNameTextView.shadowDx, allContentViewHolder.productNameTextView.shadowDy, context.getColor(R.color.white))

                allContentViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.dark))

                allContentViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_solid_icon_light))
                allContentViewHolder.productDividerView.background = context.getDrawable(R.drawable.all_content_divider_light)

                allContentViewHolder.productRatingStarsView.background = context.getDrawable(R.drawable.application_rating_glowing_frame_light)
                allContentViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.white))

            }
            ThemeType.ThemeDark -> {

                allContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.light))
                allContentViewHolder.productNameTextView.setShadowLayer(allContentViewHolder.productNameTextView.shadowRadius, allContentViewHolder.productNameTextView.shadowDx, allContentViewHolder.productNameTextView.shadowDy, context.getColor(R.color.black))

                allContentViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.light))

                allContentViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_solid_icon_dark))
                allContentViewHolder.productDividerView.background = context.getDrawable(R.drawable.all_content_divider_dark)

                allContentViewHolder.productRatingStarsView.background = context.getDrawable(R.drawable.application_rating_glowing_frame_dark)
                allContentViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black_transparent))

            }
            else -> {

                allContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                allContentViewHolder.productNameTextView.setShadowLayer(allContentViewHolder.productNameTextView.shadowRadius, allContentViewHolder.productNameTextView.shadowDx, allContentViewHolder.productNameTextView.shadowDy, context.getColor(R.color.white))

                allContentViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.dark))

                allContentViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_solid_icon_light))
                allContentViewHolder.productDividerView.background = context.getDrawable(R.drawable.all_content_divider_light)

                allContentViewHolder.productRatingStarsView.background = context.getDrawable(R.drawable.application_rating_glowing_frame_light)
                allContentViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.white))

            }
        }

    }

    override fun onBindViewHolder(allContentViewHolder: AllContentViewHolder, position: Int) {

        allContentViewHolder.productNameTextView.text = Html.fromHtml(storefrontContents[position].productName, Html.FROM_HTML_MODE_COMPACT)
        allContentViewHolder.productSummaryTextView.text = Html.fromHtml(storefrontContents[position].productSummary, Html.FROM_HTML_MODE_COMPACT)

        allContentViewHolder.productCurrentRateView.text = storefrontContents[position].productAttributes[ProductsContentKey.AttributesRatingKey]

        allContentViewHolder.productCurrentRateView.setTextColor(context.getColor(R.color.white))

        if (position == storefrontContents.lastIndex) {



        } else {



        }

        //Product Icon Image
        Glide.with(context)
            .asDrawable()
            .load(storefrontContents[position].productIconLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CircleCrop())
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return true }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        val vibrantColor = extractVibrantColor(resource)

                        context.runOnUiThread {

                            allContentViewHolder.productCurrentRateView.setShadowLayer(allContentViewHolder.productCurrentRateView.shadowRadius, allContentViewHolder.productCurrentRateView.shadowDx, allContentViewHolder.productCurrentRateView.shadowDy, vibrantColor)

                            allContentViewHolder.installView.backgroundTintList = ColorStateList.valueOf(vibrantColor)
                            allContentViewHolder.installView.rippleColor = ColorStateList.valueOf(context.getColor(R.color.white))

                            allContentViewHolder.productCurrentRateView.setTextColor(vibrantColor)

                            val applicationGlowingFrame = context.getDrawable(R.drawable.all_application_icon_glowing_frame) as LayerDrawable
                            applicationGlowingFrame.findDrawableByLayerId(R.id.circleIconBackground).setTint(vibrantColor)
                            applicationGlowingFrame.findDrawableByLayerId(R.id.circleIconFrame).setTint(vibrantColor)

                            allContentViewHolder.productIconImageView.background = applicationGlowingFrame

                            allContentViewHolder.productIconImageView.setImageDrawable(resource)

                        }

                    }

                    return true
                }

            })
            .submit()

        allContentViewHolder.rootView.setOnClickListener {

            openProductsDetails(context = context, fragmentInterface = fragmentInterface,
                contentDetailsContainer = contentDetailsContainer, productDetailsFragment = productDetailsFragment,
                storefrontContents = storefrontContents[position])

        }

        allContentViewHolder.rootView.setOnLongClickListener {


            true
        }

        allContentViewHolder.installView.setOnClickListener {

            openPlayStoreToInstallApplications(context = context,
                aPackageName = (storefrontContents[position].productAttributes[ProductsContentKey.AttributesPackageNameKey].toString()),
                applicationName = storefrontContents[position].productName,
                applicationSummary = storefrontContents[position].productSummary)

        }

    }

}