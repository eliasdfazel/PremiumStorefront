/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/17/21, 4:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.Adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontFeaturedContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.ViewHolder.FeaturedContentViewHolder
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

class FeaturedContentAdapter(private val context: Storefront) : RecyclerView.Adapter<FeaturedContentViewHolder>() {

    val storefrontContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : FeaturedContentViewHolder {

        return FeaturedContentViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_featured_content_item, viewGroup, false))
    }

    override fun onBindViewHolder(featuredContentViewHolder: FeaturedContentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(featuredContentViewHolder, position, payloads)



    }

    override fun onBindViewHolder(featuredContentViewHolder: FeaturedContentViewHolder, position: Int) {

        featuredContentViewHolder.productNameTextView.text = Html.fromHtml(storefrontContents[position].productName, Html.FROM_HTML_MODE_COMPACT)

        featuredContentViewHolder.productCurrentRateView.text = storefrontContents[position].productAttributes[StorefrontFeaturedContentKey.AttributesRatingKey]

        featuredContentViewHolder.productNameTextView.bringToFront()

        //Product Icon Image
        Glide.with(context)
            .asDrawable()
            .load(storefrontContents[position].productIconLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(256, 256)
            .transform(CircleCrop())
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return false }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        val vibrantColor = extractVibrantColor(context, resource)

                        context.runOnUiThread {

                            featuredContentViewHolder.installView.setBackgroundColor(vibrantColor)

                            featuredContentViewHolder.productCurrentRateView.setTextColor(vibrantColor)
                            featuredContentViewHolder.productCurrentRateView.setShadowLayer(featuredContentViewHolder.productCurrentRateView.shadowRadius, featuredContentViewHolder.productCurrentRateView.shadowDx, featuredContentViewHolder.productCurrentRateView.shadowDy, vibrantColor)

                            featuredContentViewHolder.productIconImageView.setImageDrawable(resource)

                        }

                    }

                    return false
                }

            })
            .submit()

        //Product Cover Image
        Glide.with(context)
            .asDrawable()
            .load(storefrontContents[position].productCoverLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(337, 174)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return false }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        context.runOnUiThread {

                            featuredContentViewHolder.backgroundCoverImageView.setImageDrawable(resource)

                        }

                    }

                    return false
                }

            })
            .submit()

        featuredContentViewHolder.rootView.setOnClickListener {

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

        featuredContentViewHolder.rootView.setOnLongClickListener {

            openPlayStoreToInstall(context = context,
                aPackageName = (storefrontContents[position].productAttributes[StorefrontFeaturedContentKey.AttributesPackageNameKey].toString()),
                applicationName = storefrontContents[position].productName,
                applicationSummary = storefrontContents[position].productSummary)

            true
        }

        featuredContentViewHolder.installView.setOnClickListener {

            openPlayStoreToInstall(context = context,
                aPackageName = (storefrontContents[position].productAttributes[StorefrontFeaturedContentKey.AttributesPackageNameKey].toString()),
                applicationName = storefrontContents[position].productName,
                applicationSummary = storefrontContents[position].productSummary)

        }

        featuredContentViewHolder.productNameTextView.setOnClickListener {

        }

    }

}