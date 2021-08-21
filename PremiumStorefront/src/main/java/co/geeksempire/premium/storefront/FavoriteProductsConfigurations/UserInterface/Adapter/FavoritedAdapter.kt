/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/21/21, 6:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.Adapter

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure.FavoriteDataStructure
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.FavoriteProducts
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.ViewHolder.FavoritedViewHolder
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class FavoritedAdapter (val context: FavoriteProducts, var themeType: Boolean = ThemeType.ThemeLight) : RecyclerView.Adapter<FavoritedViewHolder>() {

    val favoritedContentItems: ArrayList<FavoriteDataStructure> = ArrayList<FavoriteDataStructure>()

    object ProductType {
        const val EntryStorefrontApplications = 0
        const val EntryStorefrontGames = 1
        const val EntryStorefrontMovies = 2
        const val EntryStorefrontBooks = 3
    }

    override fun getItemCount(): Int {

        return favoritedContentItems.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoritedViewHolder {

        val layoutId = when (viewType) {
            ProductType.EntryStorefrontApplications -> R.layout.favorited_product_applications_item
            ProductType.EntryStorefrontMovies -> R.layout.favorited_product_movies_item
            else -> R.layout.favorited_product_applications_item
        }

        return FavoritedViewHolder(LayoutInflater.from(context).inflate(layoutId, viewGroup, false))
    }

    override fun getItemViewType(position: Int): Int {

        return when (favoritedContentItems[position].productType) {
            EntryPreferences.EntryStorefrontApplications -> ProductType.EntryStorefrontApplications
            EntryPreferences.EntryStorefrontMovies -> ProductType.EntryStorefrontMovies
            else -> ProductType.EntryStorefrontApplications
        }
    }

    override fun onBindViewHolder(favoritedViewHolder: FavoritedViewHolder, position: Int) {

        when (themeType) {
            ThemeType.ThemeLight -> {

                favoritedViewHolder.rootViewItem.background = context.getDrawable(R.drawable.favorited_background_light)

                favoritedViewHolder.blurryBackgroundItem.setOverlayColor(context.getColor(R.color.light_transparent_high))

                favoritedViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                favoritedViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.dark))

                favoritedViewHolder.removeView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.light))

            }
            ThemeType.ThemeDark -> {

                favoritedViewHolder.rootViewItem.background = context.getDrawable(R.drawable.favorited_background_dark)


                favoritedViewHolder.blurryBackgroundItem.setOverlayColor(context.getColor(R.color.dark_transparent_high))

                favoritedViewHolder.productNameTextView.setTextColor(context.getColor(R.color.light))
                favoritedViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.light))

                favoritedViewHolder.removeView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

            }
            else -> {

                favoritedViewHolder.rootViewItem.background = context.getDrawable(R.drawable.favorited_background_light)

                favoritedViewHolder.blurryBackgroundItem.setOverlayColor(context.getColor(R.color.light_transparent_high))

                favoritedViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                favoritedViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.dark))

                favoritedViewHolder.removeView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.light))

            }
        }

        favoritedViewHolder.productNameTextView.text = Html.fromHtml(favoritedContentItems[position].productName, Html.FROM_HTML_MODE_COMPACT)
        favoritedViewHolder.productSummaryTextView.text = Html.fromHtml(favoritedContentItems[position].productDescription, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(context)
            .asDrawable()
            .load(favoritedContentItems[position].productIcon)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(RoundedCorners(dpToInteger(context, 23)))
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        context.runOnUiThread {

                            favoritedViewHolder.productIconImageView.setImageDrawable(resource)

                            val dominantColor = extractVibrantColor(resource)

                            favoritedViewHolder.installView.backgroundTintList = ColorStateList.valueOf(dominantColor)

                        }

                    }

                    return false
                }


            })
            .submit()

        favoritedViewHolder.installView.setOnClickListener {

            openPlayStoreToInstallApplications(context,
                favoritedContentItems[position].productId,
                favoritedContentItems[position].productName,
                favoritedContentItems[position].productDescription)

        }

        favoritedViewHolder.removeView.setOnClickListener {

            context.favoritedProcess.remove(context.firebaseUser!!.uid, context.firebaseUser.email!!, favoritedContentItems[position].productId)

            favoritedContentItems.removeAt(position)

            val scaleDownAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_down_zero_to_top_end)
            favoritedViewHolder.rootViewItem.startAnimation(scaleDownAnimation)

            scaleDownAnimation.setAnimationListener(object : Animation.AnimationListener {

                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {

                    Handler(Looper.getMainLooper()).postDelayed({

                        notifyDataSetChanged()

                    }, 301)

                }

                override fun onAnimationRepeat(animation: Animation?) {}

            })

            Handler(Looper.getMainLooper()).postDelayed({

                notifyItemRemoved(position)

            }, 357)

        }

    }

}