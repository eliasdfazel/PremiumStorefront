/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/6/21, 6:20 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.Adapter

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontFeaturedContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.ViewHolder.AllContentViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class AllContentAdapter(private val context: Storefront) : RecyclerView.Adapter<AllContentViewHolder>() {

    val storefrontContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : AllContentViewHolder {

        return AllContentViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_all_content_item, viewGroup, false))
    }

    override fun onBindViewHolder(allContentViewHolder: AllContentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(allContentViewHolder, position, payloads)



    }

    override fun onBindViewHolder(allContentViewHolder: AllContentViewHolder, position: Int) {

        allContentViewHolder.productNameTextView.text = Html.fromHtml(storefrontContents[position].productName, Html.FROM_HTML_MODE_COMPACT)
        allContentViewHolder.productSummaryTextView.text = Html.fromHtml(storefrontContents[position].productSummary, Html.FROM_HTML_MODE_COMPACT)

        allContentViewHolder.productCurrentRateView.text = storefrontContents[position].productAttributes[StorefrontFeaturedContentKey.AttributesRatingKey]

        //Product Icon Image
        Glide.with(context)
                .load(storefrontContents[position].productIconLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CircleCrop())
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return false }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            val vibrantColor = extractVibrantColor(context, resource)

                            context.runOnUiThread {

                                allContentViewHolder.installView.backgroundTintList = ColorStateList.valueOf(vibrantColor)
                                allContentViewHolder.installView.rippleColor = ColorStateList.valueOf(context.getColor(R.color.white))

                                allContentViewHolder.productCurrentRateView.setTextColor(vibrantColor)
                                allContentViewHolder.productCurrentRateView.setShadowLayer(allContentViewHolder.productCurrentRateView.shadowRadius, allContentViewHolder.productCurrentRateView.shadowDx, allContentViewHolder.productCurrentRateView.shadowDy, vibrantColor)

                                val applicationGlowingFrame = context.getDrawable(R.drawable.application_icon_glowing_frame) as LayerDrawable
                                applicationGlowingFrame.findDrawableByLayerId(R.id.circleIconBackground).setTint(vibrantColor)
                                applicationGlowingFrame.findDrawableByLayerId(R.id.circleIconFrame).setTint(vibrantColor)

                                allContentViewHolder.productIconImageView.background = applicationGlowingFrame

                                allContentViewHolder.productIconImageView.setImageDrawable(resource)

                            }

                        }

                        return false
                    }

                })
                .submit()

        allContentViewHolder.rootView.setOnClickListener {

            context.storefrontLayoutBinding.contentDetailsContainer.visibility = View.VISIBLE

            context.productDetailsFragment.apply {
                isShowing = true
            }

            context.productDetailsFragment.arguments = Bundle().apply {


            }

            context.supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, 0)
                    .replace(R.id.contentDetailsContainer, context.productDetailsFragment, "Product Details For ${storefrontContents[position].productName}")
                    .commit()

        }

        allContentViewHolder.rootView.setOnLongClickListener {


            true
        }

        allContentViewHolder.installView.setOnClickListener {

            openPlayStoreToInstall(context = context,
                aPackageName = (storefrontContents[position].productAttributes[StorefrontFeaturedContentKey.AttributesPackageNameKey].toString()),
                applicationName = storefrontContents[position].productName,
                applicationSummary = storefrontContents[position].productSummary)

        }

    }

}