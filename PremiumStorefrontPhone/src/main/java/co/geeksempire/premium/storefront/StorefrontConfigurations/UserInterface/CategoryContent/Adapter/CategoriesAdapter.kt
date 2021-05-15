/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/15/21, 6:12 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.CategoryContent.Adapter

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontCategoriesData
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.Filter.FilterAllContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.CategoryContent.ViewHolder.CategoriesViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import net.geeksempire.balloon.optionsmenu.library.BalloonItemsAction
import net.geeksempire.balloon.optionsmenu.library.BalloonOptionsMenu

class CategoriesAdapter(private val context: Storefront, private val filterAllContent: FilterAllContent) : RecyclerView.Adapter<CategoriesViewHolder>() {

    val storefrontCategories: ArrayList<StorefrontCategoriesData> = ArrayList<StorefrontCategoriesData>()

    var lastPosition: Int = 0

    override fun getItemCount(): Int {

        return storefrontCategories.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CategoriesViewHolder {

        return CategoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_category_item, viewGroup, false))
    }

    override fun onBindViewHolder(categoriesViewHolder: CategoriesViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(categoriesViewHolder, position, payloads)

        categoriesViewHolder.productIconImageView.background = context.getDrawable(R.drawable.category_background_item)
        categoriesViewHolder.productIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

    }

    override fun onBindViewHolder(categoriesViewHolder: CategoriesViewHolder, position: Int) {

        if (position == 0) {

            categoriesViewHolder.productIconImageView.background = context.getDrawable(R.drawable.category_background_selected_item)
            categoriesViewHolder.productIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

        } else if (storefrontCategories[position].selectedCategory) {

            categoriesViewHolder.productIconImageView.background = context.getDrawable(R.drawable.category_background_selected_item)
            categoriesViewHolder.productIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

        } else {

            categoriesViewHolder.productIconImageView.background = context.getDrawable(R.drawable.category_background_item)
            categoriesViewHolder.productIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

        }

        categoriesViewHolder.productIconImageView.tag = storefrontCategories[position].categoryId
        categoriesViewHolder.productIconImageView.contentDescription = storefrontCategories[position].categoryName

        Glide.with(context)
                .asDrawable()
                .load(storefrontCategories[position].categoryIconLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CircleCrop())
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                        return true
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            context.runOnUiThread {

                                categoriesViewHolder.productIconImageView.setImageDrawable(resource)

                            }

                        }

                        return true
                    }

                })
                .submit()

        categoriesViewHolder.rootView.setOnClickListener {

            if (context.storefrontAllUnfilteredContents.isNotEmpty()) {

                categoriesViewHolder.productIconImageView.background = context.getDrawable(R.drawable.category_background_selected_item)
                categoriesViewHolder.productIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                notifyItemChanged(lastPosition, null)

                if (position == 0) {

                    context.storefrontLiveData.allFilteredContentItemData.postValue(context.storefrontAllUntouchedContents)

                } else {

                    filterAllContent.filterAllContentByCategory(context.storefrontAllUnfilteredContents, storefrontCategories[position].categoryName)

                }

                storefrontCategories[lastPosition].selectedCategory = false
                storefrontCategories[position].selectedCategory = true

                lastPosition = position

            } else {



            }

        }

        categoriesViewHolder.rootView.setOnLongClickListener { view ->

            BalloonOptionsMenu(context = context,
                    rootView = context.storefrontLayoutBinding.rootView,
                    balloonItemsAction = object : BalloonItemsAction {

                        override fun onBalloonItemClickListener(balloonOptionsMenu: BalloonOptionsMenu, balloonOptionsRootView: View, itemView: View) {
                            Log.d(this@CategoriesAdapter.javaClass.simpleName, itemView.tag.toString())

                            balloonOptionsMenu.removeBalloonOption()

                        }

                    }).initializeBalloonPosition(anchorView = view)
                    .setupOptionsItems(arrayListOf("<b>${storefrontCategories[position].categoryName.replace(" Applications", "")}</b>", context.getString(R.string.categoryShowAllApplications)))

            true
        }

    }

}