/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 9/29/21, 10:38 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.CategoryContent.Adapter

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.DataStructure.CategoriesDataKeys
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetails
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterAllContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontCategoriesData
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.CategoryContent.ViewHolder.CategoriesViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.geeksempire.balloon.optionsmenu.library.BalloonItemsAction
import net.geeksempire.balloon.optionsmenu.library.BalloonOptionsMenu
import net.geeksempire.balloon.optionsmenu.library.OptionDataItems
import net.geeksempire.balloon.optionsmenu.library.TitleTextViewCustomization

class CategoriesAdapter(private val context: AppCompatActivity,
                        private val filterAllContent: FilterAllContent,
                        private val allFilteredContentItemData: MutableLiveData<Pair<ArrayList<StorefrontContentsData>, Boolean>>,
                        private val storefrontAllUnfilteredContents: ArrayList<StorefrontContentsData>,
                        private val storefrontAllUntouchedContents: ArrayList<StorefrontContentsData>,
                        private val categoryIndicatorTextView: TextView,
                        private val categoriesRecyclerView: RecyclerView,
                        private val balloonOptionsMenu: BalloonOptionsMenu) : RecyclerView.Adapter<CategoriesViewHolder>() {

    var themeType: Boolean = ThemeType.ThemeLight

    val storefrontCategories: ArrayList<StorefrontCategoriesData> = ArrayList<StorefrontCategoriesData>()

    private var lastPosition: Int = 0

    override fun getItemCount(): Int {

        return storefrontCategories.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CategoriesViewHolder {

        return CategoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_category_item, viewGroup, false))
    }

    override fun onBindViewHolder(categoriesViewHolder: CategoriesViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(categoriesViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {

                categoriesViewHolder.categoryIconImageView.background = AppCompatResources.getDrawable(
                    context,
                    R.drawable.category_background_item_light
                )
                categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

            }
            ThemeType.ThemeDark -> {

                categoriesViewHolder.categoryIconImageView.background = AppCompatResources.getDrawable(
                    context,
                    R.drawable.category_background_item_dark
                )
                categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

            }
        }

        categoriesViewHolder.categoryIconImageView.background = if (storefrontCategories[position].selectedCategory) {

            AppCompatResources.getDrawable(
                context, when (themeType) {
                    ThemeType.ThemeLight -> {
                        categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                        R.drawable.category_background_item_dark
                    }
                    ThemeType.ThemeDark -> {
                        categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

                        R.drawable.category_background_item_light
                    }
                    else -> {
                        categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                        R.drawable.category_background_item_dark
                    }
                }
            )

        } else {

            AppCompatResources.getDrawable(
                context, when (themeType) {
                    ThemeType.ThemeLight -> {
                        categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

                        R.drawable.category_background_item_light
                    }
                    ThemeType.ThemeDark -> {
                        categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                        R.drawable.category_background_item_dark
                    }
                    else -> {
                        categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

                        R.drawable.category_background_item_light
                    }
                }
            )

        }

    }

    override fun onBindViewHolder(categoriesViewHolder: CategoriesViewHolder, position: Int) {

        categoriesViewHolder.categoryIconImageView.background = if (storefrontCategories[position].selectedCategory) {

            context.getDrawable(when (themeType) {
                ThemeType.ThemeLight -> {
                    R.drawable.category_background_item_dark
                }
                ThemeType.ThemeDark -> {
                    R.drawable.category_background_item_light
                }
                else -> R.drawable.category_background_item_dark
            })

        } else {

            context.getDrawable(when (themeType) {
                ThemeType.ThemeLight -> {
                    R.drawable.category_background_item_light
                }
                ThemeType.ThemeDark -> {
                    R.drawable.category_background_item_dark
                }
                else -> R.drawable.category_background_item_light
            })

        }

        categoriesViewHolder.categoryIconImageView.imageTintList = if (storefrontCategories[position].selectedCategory) {

            ColorStateList.valueOf(when (themeType) {
                ThemeType.ThemeLight -> {
                    context.getColor(R.color.light)
                }
                ThemeType.ThemeDark -> {
                    context.getColor(R.color.dark)
                }
                else -> context.getColor(R.color.light)
            })

        } else {

            ColorStateList.valueOf(when (themeType) {
                ThemeType.ThemeLight -> {
                    context.getColor(R.color.dark)
                }
                ThemeType.ThemeDark -> {
                    context.getColor(R.color.light)
                }
                else -> context.getColor(R.color.dark)
            })

        }

        categoriesViewHolder.categoryIconImageView.tag = storefrontCategories[position].categoryId
        categoriesViewHolder.categoryIconImageView.contentDescription = storefrontCategories[position].categoryName

        Glide.with(context)
                .asDrawable()
                .load(storefrontCategories[position].categoryIconLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(categoriesViewHolder.categoryIconImageView)

        categoriesViewHolder.rootView.setOnClickListener {

            if (storefrontCategories[position].selectedCategory) {

                allFilteredContentItemData.postValue(Pair(storefrontAllUntouchedContents, true))

                storefrontCategories[position].selectedCategory = false

                notifyItemChanged(position, storefrontCategories[position])

            } else {

                if (storefrontAllUnfilteredContents.isNotEmpty()) {

                    if (position == 0) {

                        allFilteredContentItemData.postValue(Pair(storefrontAllUntouchedContents, true))

                    } else {

                        filterAllContent.filterAllContentsByCategory(storefrontAllUnfilteredContents, storefrontCategories[position].categoryName)

                    }

                    storefrontCategories[lastPosition].selectedCategory = false
                    storefrontCategories[position].selectedCategory = true

                    notifyItemChanged(lastPosition, storefrontCategories[lastPosition])

                    lastPosition = position

                    categoriesViewHolder.categoryIconImageView.background = context.getDrawable(R.drawable.category_background_item_dark)
                    categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                    when (themeType) {
                        ThemeType.ThemeLight -> {

                            categoriesViewHolder.categoryIconImageView.background = context.getDrawable(R.drawable.category_background_item_dark)
                            categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                        }
                        ThemeType.ThemeDark -> {

                            categoriesViewHolder.categoryIconImageView.background = context.getDrawable(R.drawable.category_background_item_light)
                            categoriesViewHolder.categoryIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

                        }
                    }

                    categoryIndicatorTextView.text = storefrontCategories[position].categoryName
                    categoryIndicatorTextView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))

                } else {

                    context.startActivity(Intent(context, CategoryDetails::class.java).apply {
                        putExtra(CategoriesDataKeys.CategoryId, storefrontCategories[position].categoryId)
                        putExtra(CategoriesDataKeys.CategoryName, storefrontCategories[position].categoryName)
                        putExtra(CategoriesDataKeys.CategoryIcon, storefrontCategories[position].categoryIconLink)
                    }, ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, 0).toBundle())

                }

            }

        }

        categoriesViewHolder.rootView.setOnLongClickListener { view ->

            balloonOptionsMenu.also {

                it.initializeBalloonPosition(anchorView = view, horizontalOffset = categoriesRecyclerView.width)

                it.setupOptionsItems(
                    menuId = storefrontCategories[position].categoryId.toString(),
                    menuTitle = storefrontCategories[position].categoryName.split(" ").first(),
                    titlesOfItems = arrayListOf<OptionDataItems>(OptionDataItems(storefrontCategories[position].categoryId.toString(), context.getString(R.string.categoryShowAllApplications))),
                    titleTextViewCustomization = TitleTextViewCustomization(textSize = 37f, textColor = context.getColor(R.color.dark), textShadowColor = context.getColor(R.color.dark_transparent_high), textFont = ResourcesCompat.getFont(context, R.font.upcil)?: Typeface.DEFAULT)
                )

                it.setupActionListener(balloonItemsAction = object : BalloonItemsAction {

                    override fun onBalloonItemClickListener(balloonOptionsMenu: BalloonOptionsMenu, balloonOptionsRootView: View, itemView: View, itemTextView: TextView, itemData: OptionDataItems) {

                        context.startActivity(Intent(context, CategoryDetails::class.java).apply {
                            putExtra(CategoriesDataKeys.CategoryId, storefrontCategories[position].categoryId)
                            putExtra(CategoriesDataKeys.CategoryName, storefrontCategories[position].categoryName)
                            putExtra(CategoriesDataKeys.CategoryIcon, storefrontCategories[position].categoryIconLink)
                        }, ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, 0).toBundle())

                        balloonOptionsMenu.removeBalloonOption()

                        Log.d(this@CategoriesAdapter.javaClass.simpleName, itemView.tag.toString())
                    }

                })

            }

            true
        }

    }

}