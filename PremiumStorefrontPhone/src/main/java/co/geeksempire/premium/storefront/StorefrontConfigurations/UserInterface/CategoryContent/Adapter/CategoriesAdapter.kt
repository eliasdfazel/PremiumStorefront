/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/3/21 7:02 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.CategoryContent.Adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontCategoriesData
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.CategoryContent.ViewHolder.CategoriesViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class CategoriesAdapter(private val context: Storefront) : RecyclerView.Adapter<CategoriesViewHolder>() {

    val storefrontCategories: ArrayList<StorefrontCategoriesData> = ArrayList<StorefrontCategoriesData>()

    override fun getItemCount() : Int {

        return storefrontCategories.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : CategoriesViewHolder {

        return CategoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_new_content_item, viewGroup, false))
    }

    override fun onBindViewHolder(newContentViewHolder: CategoriesViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(newContentViewHolder, position, payloads)



    }

    override fun onBindViewHolder(newContentViewHolder: CategoriesViewHolder, position: Int) {

        Glide.with(context)
                .asDrawable()
                .load(storefrontCategories[position].categoryIconLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CircleCrop())
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            val vibrantColor = extractVibrantColor(context, resource)

                            context.runOnUiThread {

                                newContentViewHolder.productIconImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                                newContentViewHolder.productIconImageView.setImageDrawable(resource)

                            }

                        }

                        return false
                    }

                })
                .submit()

        newContentViewHolder.rootView.setOnClickListener {



        }

    }

}