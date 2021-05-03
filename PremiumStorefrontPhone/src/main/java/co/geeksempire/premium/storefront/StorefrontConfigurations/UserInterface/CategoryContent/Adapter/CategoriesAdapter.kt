/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/3/21 7:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.CategoryContent.Adapter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontCategoriesData
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.CategoryContent.ViewHolder.CategoriesViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import com.caverock.androidsvg.SVG
import java.net.URL


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

        val svg = SVG.getFromInputStream(URL(storefrontCategories[position].categoryIconLink).openStream())

        val newBM = Bitmap.createBitmap(Math.ceil(svg.documentWidth.toDouble()).toInt(), Math.ceil(svg.documentHeight.toDouble()).toInt(), Bitmap.Config.ARGB_8888)
        val bmcanvas = Canvas(newBM)

        bmcanvas.drawRGB(255, 255, 255)
        svg.renderToCanvas(bmcanvas)

//        Glide.with(context)
//                .asDrawable()
//                .load(storefrontCategories[position].categoryIconLink)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .transform(CircleCrop())
//                .listener(object : RequestListener<Drawable> {
//
//                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                        return false
//                    }
//
//                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//
//                        resource?.let {
//
//                            val vibrantColor = extractVibrantColor(context, resource)
//
//                            context.runOnUiThread {
//
//                                newContentViewHolder.productIconImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
//                                newContentViewHolder.productIconImageView.setImageDrawable(resource)
//
//                            }
//
//                        }
//
//                        return false
//                    }
//
//                })
//                .submit()

        newContentViewHolder.rootView.setOnClickListener {



        }

    }

}