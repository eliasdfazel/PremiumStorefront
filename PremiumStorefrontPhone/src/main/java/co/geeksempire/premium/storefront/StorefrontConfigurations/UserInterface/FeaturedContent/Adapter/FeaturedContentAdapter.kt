/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/19/21 4:11 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.Adapter

import android.graphics.drawable.Drawable
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontFeaturedContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.ViewHolder.FeaturedContentViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
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

        featuredContentViewHolder.productDescriptionTextView.text = Html.fromHtml(storefrontFeaturedContents[position].productDescription, Html.FROM_HTML_MODE_COMPACT)

        featuredContentViewHolder.backgroundCoverImageView.layoutParams = featuredContentViewHolder.backgroundCoverImageView.layoutParams.apply {
            height = 500//500 Pixel
        }

        Glide.with(context)
                .load(storefrontFeaturedContents[position].productCoverLink)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            featuredContentViewHolder.backgroundCoverImageView.setImageDrawable(resource)


                        }

                        return false
                    }

                })
                .submit()

    }

}