/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/9/21, 8:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.GenreContent.Adapter

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterAllContent
import co.geeksempire.premium.storefront.databinding.StorefrontCategoryItemBinding
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.StorefrontGenresData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.GenreContent.ViewHolder.GenresViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.firestore.DocumentSnapshot
import net.geeksempire.balloon.optionsmenu.library.BalloonItemsAction
import net.geeksempire.balloon.optionsmenu.library.BalloonOptionsMenu
import net.geeksempire.balloon.optionsmenu.library.OptionDataItems
import net.geeksempire.balloon.optionsmenu.library.TitleTextViewCustomization

class GenresAdapter(private val context: AppCompatActivity,
                    private val filterAllContent: FilterAllContent,
                    private val allFilteredContentItemData: MutableLiveData<Pair<ArrayList<DocumentSnapshot>, Boolean>>,
                    private val storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot>,
                    private val storefrontAllUntouchedContents: ArrayList<DocumentSnapshot>,
                    private val genreIndicatorTextView: TextView,
                    private val categoriesRecyclerView: RecyclerView,
                    private val balloonOptionsMenu: BalloonOptionsMenu) : RecyclerView.Adapter<GenresViewHolder>() {

    var themeType: Boolean = ThemeType.ThemeLight

    val storefrontGenres: ArrayList<StorefrontGenresData> = ArrayList<StorefrontGenresData>()

    private var lastPosition: Int = 0

    override fun getItemCount(): Int {

        return storefrontGenres.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GenresViewHolder {

        return GenresViewHolder(StorefrontCategoryItemBinding.inflate(context.layoutInflater))
    }

    override fun onBindViewHolder(genresViewHolder: GenresViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(genresViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {

                genresViewHolder.genreIconImageView.background = getDrawable(context, R.drawable.category_background_item_light)
                genresViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

            }
            ThemeType.ThemeDark -> {

                genresViewHolder.genreIconImageView.background = getDrawable(context, R.drawable.category_background_item_dark)
                genresViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

            }
        }

        genresViewHolder.genreIconImageView.background = if (storefrontGenres[position].selectedCategory) {

            getDrawable(context, when (themeType) {
                ThemeType.ThemeLight -> {
                    R.drawable.category_background_item_dark
                }
                ThemeType.ThemeDark -> {
                    R.drawable.category_background_item_light
                }
                else -> R.drawable.category_background_item_dark
            })

        } else {

            getDrawable(context, when (themeType) {
                ThemeType.ThemeLight -> {
                    R.drawable.category_background_item_light
                }
                ThemeType.ThemeDark -> {
                    R.drawable.category_background_item_dark
                }
                else -> R.drawable.category_background_item_light
            })

        }

    }


    override fun onBindViewHolder(categoriesViewHolder: GenresViewHolder, position: Int) {

        categoriesViewHolder.genreIconImageView.background = if (storefrontGenres[position].selectedCategory) {

            getDrawable(context, when (themeType) {
                ThemeType.ThemeLight -> {
                    R.drawable.category_background_item_dark
                }
                ThemeType.ThemeDark -> {
                    R.drawable.category_background_item_light
                }
                else -> R.drawable.category_background_item_dark
            })

        } else {

            getDrawable(context, when (themeType) {
                ThemeType.ThemeLight -> {
                    R.drawable.category_background_item_light
                }
                ThemeType.ThemeDark -> {
                    R.drawable.category_background_item_dark
                }
                else -> R.drawable.category_background_item_light
            })

        }

        categoriesViewHolder.genreIconImageView.imageTintList = if (storefrontGenres[position].selectedCategory) {

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

        categoriesViewHolder.genreIconImageView.tag = storefrontGenres[position].genreId
        categoriesViewHolder.genreIconImageView.contentDescription = storefrontGenres[position].genreName

        Glide.with(context)
                .asDrawable()
                .load(storefrontGenres[position].genreIconLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(categoriesViewHolder.genreIconImageView)

        categoriesViewHolder.rootView.setOnClickListener {

            if (storefrontGenres[position].selectedCategory) {

                allFilteredContentItemData.postValue(Pair(storefrontAllUntouchedContents, true))

                storefrontGenres[position].selectedCategory = false

                notifyItemChanged(position, storefrontGenres[position])

            } else {

                if (storefrontAllUnfilteredContents.isNotEmpty()) {

                    notifyItemChanged(lastPosition, storefrontGenres[lastPosition])

                    if (position == 0) {

                        allFilteredContentItemData.postValue(Pair(storefrontAllUntouchedContents, true))

                    } else {

//                        filterAllContent.filterAllMoviesByCategory(storefrontAllUnfilteredContents, storefrontGenres[position].genreName)

                    }

                    storefrontGenres[lastPosition].selectedCategory = false
                    storefrontGenres[position].selectedCategory = true

                    lastPosition = position

                    categoriesViewHolder.genreIconImageView.background = context.getDrawable(R.drawable.category_background_item_dark)
                    categoriesViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                    when (themeType) {
                        ThemeType.ThemeLight -> {

                            categoriesViewHolder.genreIconImageView.background = context.getDrawable(R.drawable.category_background_item_dark)
                            categoriesViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                        }
                        ThemeType.ThemeDark -> {

                            categoriesViewHolder.genreIconImageView.background = context.getDrawable(R.drawable.category_background_item_light)
                            categoriesViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

                        }
                    }

                    genreIndicatorTextView.text = storefrontGenres[position].genreName
                    genreIndicatorTextView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))

                } else {



                }

            }

        }

        categoriesViewHolder.rootView.setOnLongClickListener { view ->

            balloonOptionsMenu.also {

                it.initializeBalloonPosition(anchorView = view, horizontalOffset = categoriesRecyclerView.width)

                it.setupOptionsItems(
                    menuId = storefrontGenres[position].genreId.toString(),
                    menuTitle = storefrontGenres[position].genreName,
                    titlesOfItems = arrayListOf<OptionDataItems>(OptionDataItems(storefrontGenres[position].genreId.toString(), context.getString(R.string.categoryShowAllApplications))),
                    titleTextViewCustomization = TitleTextViewCustomization(textSize = 37f, textColor = context.getColor(R.color.dark), textShadowColor = context.getColor(R.color.dark_transparent_high), textFont = ResourcesCompat.getFont(context, R.font.upcil)?: Typeface.DEFAULT)
                )

                it.setupActionListener(balloonItemsAction = object : BalloonItemsAction {

                    override fun onBalloonItemClickListener(balloonOptionsMenu: BalloonOptionsMenu, balloonOptionsRootView: View, itemView: View, itemTextView: TextView, itemData: OptionDataItems) {


                        balloonOptionsMenu.removeBalloonOption()

                        Log.d(this@GenresAdapter.javaClass.simpleName, itemView.tag.toString())
                    }

                })

            }

            true
        }

    }

}