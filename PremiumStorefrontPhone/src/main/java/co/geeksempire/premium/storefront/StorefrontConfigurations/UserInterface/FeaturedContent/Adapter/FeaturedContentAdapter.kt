/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/28/21 3:38 PM
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
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontFeaturedContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.ViewHolder.FeaturedContentViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class FeaturedContentAdapter(private val context: Storefront) : RecyclerView.Adapter<FeaturedContentViewHolder>() {

    val storefrontFeaturedContents: ArrayList<StorefrontFeaturedContentsData> = ArrayList<StorefrontFeaturedContentsData>()

    override fun getItemCount() : Int {

        return storefrontFeaturedContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : FeaturedContentViewHolder {

        return FeaturedContentViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_featured_content_item, viewGroup, false))
    }

    override fun onBindViewHolder(featuredContentViewHolder: FeaturedContentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(featuredContentViewHolder, position, payloads)



    }

    override fun onBindViewHolder(featuredContentViewHolder: FeaturedContentViewHolder, position: Int) {

        featuredContentViewHolder.productNameTextView.text = Html.fromHtml(storefrontFeaturedContents[position].productName, Html.FROM_HTML_MODE_COMPACT)

        featuredContentViewHolder.backgroundCoverImageView.layoutParams = featuredContentViewHolder.backgroundCoverImageView.layoutParams.apply {
            height = 500//500 Pixel
        }

        Glide.with(context)
                .load(storefrontFeaturedContents[position].productIconLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            context.runOnUiThread {

                                val vibrantColor = extractVibrantColor(context, resource)

                                featuredContentViewHolder.productIconBlur.setOverlayColor(setColorAlpha(vibrantColor, 157f))
                                featuredContentViewHolder.productIconBlur.setSecondOverlayColor(context.getColor(R.color.light_transparent_higher))

                                featuredContentViewHolder.productIconImageView.setImageDrawable(resource)

                            }

                        }

                        return false
                    }

                })
                .submit()

        Glide.with(context)
                .load(storefrontFeaturedContents[position].productCoverLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            context.runOnUiThread {

                                val vibrantColor = extractVibrantColor(context, resource)

                                featuredContentViewHolder.productNameBlur.setOverlayColor(context.getColor(R.color.light_transparent_higher))
                                featuredContentViewHolder.productNameBlur.setSecondOverlayColor(setColorAlpha(vibrantColor, 157f))

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
                    .replace(R.id.contentDetailsContainer, context.productDetailsFragment, "Product Details For ${storefrontFeaturedContents[position].productName}")
                    .commit()

        }

    }

}