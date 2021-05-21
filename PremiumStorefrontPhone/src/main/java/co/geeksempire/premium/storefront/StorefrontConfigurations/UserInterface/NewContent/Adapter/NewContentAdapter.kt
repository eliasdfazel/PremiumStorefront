/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/21/21, 11:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.NewContent.Adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontFeaturedContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.NewContent.ViewHolder.NewContentViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class NewContentAdapter(private val context: Storefront) : RecyclerView.Adapter<NewContentViewHolder>() {

    val storefrontContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : NewContentViewHolder {

        return NewContentViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_new_content_item, viewGroup, false))
    }

    override fun onBindViewHolder(newContentViewHolder: NewContentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(newContentViewHolder, position, payloads)



    }

    override fun onBindViewHolder(newContentViewHolder: NewContentViewHolder, position: Int) {

        newContentViewHolder.productNameTextView.text = Html.fromHtml(storefrontContents[position].productName, Html.FROM_HTML_MODE_COMPACT)

        //Product Icon Image
        Glide.with(context)
            .load(storefrontContents[position].productIconLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CircleCrop())
            .override(256, 256)
            .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return false }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            val vibrantColor = extractVibrantColor(context, resource)

                            context.runOnUiThread {

                                newContentViewHolder.productIconImageView.setImageDrawable(resource)

                            }

                        }

                        return false
                    }

                })
                .submit()

        newContentViewHolder.rootView.setOnClickListener {

            context.storefrontLayoutBinding.contentDetailsContainer.visibility = View.VISIBLE

            context.productDetailsFragment.apply {
                storefrontInstance = this@NewContentAdapter.context
                isShowing = true
            }

            context.productDetailsFragment.arguments = Bundle().apply {
                putString(ProductDataKey.ProductId, storefrontContents[position].productAttributes[StorefrontFeaturedContentKey.AttributesPackageNameKey])
                putString(ProductDataKey.ProductPackageName, storefrontContents[position].productAttributes[StorefrontFeaturedContentKey.AttributesPackageNameKey])

                putString(ProductDataKey.ProductName, storefrontContents[position].productName)
                putString(ProductDataKey.ProductDescription, storefrontContents[position].productDescription)

                putString(ProductDataKey.ProductIcon, storefrontContents[position].productIconLink)
                putString(ProductDataKey.ProductCoverImage, storefrontContents[position].productCoverLink)
                putString(ProductDataKey.ProductYoutubeIntroduction, storefrontContents[position].productAttributes[StorefrontFeaturedContentKey.AttributesYoutubeIntroductionKey])
            }

            context.supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.contentDetailsContainer, context.productDetailsFragment, context.packageName)
                    .commit()

        }

    }

}