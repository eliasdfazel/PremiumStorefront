/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/2/21, 10:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.OldContent.Adapter

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.DataStructure.CategoriesDataKeys
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetails
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.Extensions.openProductsDetails
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.OldContent.ViewHolder.OldContentViewHolder
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.isColorLightDark
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class OldContentAdapter(private val context: AppCompatActivity,
                        private val contentDetailsContainer: FragmentContainerView, private val productDetailsFragment: ProductDetailsFragment,
                        private val fragmentInterface: FragmentInterface) : RecyclerView.Adapter<OldContentViewHolder>() {

    var themeType: Boolean = ThemeType.ThemeLight

    val storefrontContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : OldContentViewHolder {

        return OldContentViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_old_content_item, viewGroup, false))
    }

    override fun onBindViewHolder(oldContentViewHolder: OldContentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(oldContentViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {

                oldContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                oldContentViewHolder.productNameTextView.setShadowLayer(oldContentViewHolder.productNameTextView.shadowRadius, oldContentViewHolder.productNameTextView.shadowDx, oldContentViewHolder.productNameTextView.shadowDy, context.getColor(R.color.white))

            }
            ThemeType.ThemeDark -> {

                oldContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.light))
                oldContentViewHolder.productNameTextView.setShadowLayer(oldContentViewHolder.productNameTextView.shadowRadius, oldContentViewHolder.productNameTextView.shadowDx, oldContentViewHolder.productNameTextView.shadowDy, context.getColor(R.color.black))

            }
            else -> {

                oldContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                oldContentViewHolder.productNameTextView.setShadowLayer(oldContentViewHolder.productNameTextView.shadowRadius, oldContentViewHolder.productNameTextView.shadowDx, oldContentViewHolder.productNameTextView.shadowDy, context.getColor(R.color.white))

            }
        }

    }

    override fun onBindViewHolder(oldContentViewHolder: OldContentViewHolder, position: Int) {

        oldContentViewHolder.productNameTextView.text = Html.fromHtml(storefrontContents[position].productName, Html.FROM_HTML_MODE_COMPACT)

        //Product Icon Image
        Glide.with(context)
            .load(storefrontContents[position].productIconLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CenterCrop())
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return false }

                override fun onResourceReady(productIcon: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    productIcon?.let {

                        val dominantColor = extractDominantColor(productIcon)

                        context.runOnUiThread {

                            val squircleDrawableBackground = context.getDrawable(R.drawable.squircle_icon_light)!!.mutate()
                            squircleDrawableBackground.setTint(dominantColor)

                            val backgroundLayer = arrayOf<Drawable>(squircleDrawableBackground, productIcon)
                            val backgroundLayerDrawables = LayerDrawable(backgroundLayer)

                            oldContentViewHolder.productIconImageView.setImageDrawable(backgroundLayerDrawables)

                            oldContentViewHolder.productCategoryImageView.backgroundTintList = ColorStateList.valueOf(dominantColor)

                            if (isColorLightDark(dominantColor)) {

                                oldContentViewHolder.productCategoryImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

                            } else {

                                oldContentViewHolder.productCategoryImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                            }

                        }

                    }

                    return false
                }

            })
            .submit()

        Glide.with(context)
            .asDrawable()
            .load((context.application as PremiumStorefrontApplication).categoryData.getCategoryIconByName(storefrontContents[position].productCategoryName))
            .into(oldContentViewHolder.productCategoryImageView)

        oldContentViewHolder.productCategoryImageView.setOnClickListener {

            context.startActivity(Intent(context, CategoryDetails::class.java).apply {
                putExtra(CategoriesDataKeys.CategoryId, storefrontContents[position].productCategoryId)
                putExtra(CategoriesDataKeys.CategoryName, storefrontContents[position].productCategoryName)
                putExtra(CategoriesDataKeys.CategoryIcon, (context.application as PremiumStorefrontApplication).categoryData.getCategoryIconByName(storefrontContents[position].productCategoryName))
                putExtra(GeneralEndpoints.QueryType.QueryTypeCase, if (context.javaClass.simpleName.contains("Applications")) {
                    GeneralEndpoints.QueryType.ApplicationsQuery
                } else {
                    GeneralEndpoints.QueryType.GamesQuery
                })
            }, ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, 0).toBundle())

        }

        oldContentViewHolder.rootView.setOnClickListener {

            val scaleUpBound = AnimationUtils.loadAnimation(context, R.anim.scale_up_bounce_interpolator)

            oldContentViewHolder.rootView.startAnimation(scaleUpBound)

            scaleUpBound.setAnimationListener(object : Animation.AnimationListener {

                override fun onAnimationStart(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {

                    openProductsDetails(context = context, fragmentInterface = fragmentInterface,
                        contentDetailsContainer = contentDetailsContainer, productDetailsFragment = productDetailsFragment,
                        storefrontContents = storefrontContents[position])

                }

                override fun onAnimationRepeat(animation: Animation?) {

                }

            })

        }

    }

}