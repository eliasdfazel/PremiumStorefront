/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/27/21, 11:04 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.NewContent.Adapter

import android.graphics.drawable.Drawable
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.Extensions.openProductsDetails
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.NewContent.ViewHolder.NewContentViewHolder
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class NewContentAdapter(private val context: AppCompatActivity,
                        private val contentDetailsContainer: FragmentContainerView, private val productDetailsFragment: ProductDetailsFragment,
                        private val fragmentInterface: FragmentInterface) : RecyclerView.Adapter<NewContentViewHolder>() {

    var themeType: Boolean = ThemeType.ThemeLight

    val storefrontContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : NewContentViewHolder {

        return NewContentViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_new_content_item, viewGroup, false))
    }

    override fun onBindViewHolder(newContentViewHolder: NewContentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(newContentViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {

                newContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                newContentViewHolder.productNameTextView.setShadowLayer(newContentViewHolder.productNameTextView.shadowRadius, newContentViewHolder.productNameTextView.shadowDx, newContentViewHolder.productNameTextView.shadowDy, context.getColor(R.color.white))

                newContentViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_gradient_icon_light))

            }
            ThemeType.ThemeDark -> {

                newContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.light))
                newContentViewHolder.productNameTextView.setShadowLayer(newContentViewHolder.productNameTextView.shadowRadius, newContentViewHolder.productNameTextView.shadowDx, newContentViewHolder.productNameTextView.shadowDy, context.getColor(R.color.black))

                newContentViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_gradient_icon_dark))

            }
            else -> {

                newContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                newContentViewHolder.productNameTextView.setShadowLayer(newContentViewHolder.productNameTextView.shadowRadius, newContentViewHolder.productNameTextView.shadowDx, newContentViewHolder.productNameTextView.shadowDy, context.getColor(R.color.white))

                newContentViewHolder.productDividerView.setImageDrawable(context.getDrawable(R.drawable.diamond_gradient_icon_light))

            }
        }

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

            openProductsDetails(context = context, fragmentInterface = fragmentInterface,
                contentDetailsContainer = contentDetailsContainer, productDetailsFragment = productDetailsFragment,
                storefrontContents = storefrontContents[position])

        }

    }

}