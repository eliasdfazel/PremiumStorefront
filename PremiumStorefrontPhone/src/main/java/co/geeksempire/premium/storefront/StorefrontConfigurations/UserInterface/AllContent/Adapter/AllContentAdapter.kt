/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/29/21 7:04 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.Adapter

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
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.ViewHolder.AllContentViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
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

        allContentViewHolder.productCurrentRateView.text = storefrontContents[position].productAttributes[StorefrontFeaturedContentKey.AttributesRatingKey]

        //Product Icon Image
        Glide.with(context)
                .load(storefrontContents[position].productIconLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return false }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            val vibrantColor = extractVibrantColor(context, resource)

                            context.runOnUiThread {

                                allContentViewHolder.productNameBlur.setOverlayColor(setColorAlpha(vibrantColor, 131f))
                                allContentViewHolder.productNameBlur.setSecondOverlayColor(context.getColor(R.color.light_transparent_high))

                                allContentViewHolder.productCurrentRateView.setTextColor(vibrantColor)
                                allContentViewHolder.productCurrentRateView.setShadowLayer(allContentViewHolder.productCurrentRateView.shadowRadius, allContentViewHolder.productCurrentRateView.shadowDx, allContentViewHolder.productCurrentRateView.shadowDy, vibrantColor)

                                allContentViewHolder.productIconBlur.setSecondOverlayColor(setColorAlpha(vibrantColor, 151f))
                                allContentViewHolder.productIconBlur.setOverlayColor(context.getColor(R.color.light_transparent_high))

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

    }

}