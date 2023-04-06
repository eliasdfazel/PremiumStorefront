/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/21/21, 3:52 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.FeaturedMovies.Adapter

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.Html
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractMutedColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.isColorLightDark
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.openMoviesDetails
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.FeaturedMovies.UI.designFeaturedMoviesBackground
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.FeaturedMovies.UI.designFeaturedMoviesPosterBackground
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.FeaturedMovies.UI.designOptionsMoviesBackground
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.FeaturedMovies.ViewHolder.FeaturedMoviesViewHolder
import co.geeksempire.premium.storefront.movies.databinding.StorefrontFeaturedMoviesItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.DocumentSnapshot
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class FeaturedMoviesAdapter (val context: StorefrontMovies) : RecyclerView.Adapter<FeaturedMoviesViewHolder>() {

    var themeType: Boolean = ThemeType.ThemeLight

    val featuredMoviesData = ArrayList<DocumentSnapshot>()

    override fun getItemCount(): Int {

        return featuredMoviesData.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FeaturedMoviesViewHolder {

        return FeaturedMoviesViewHolder(StorefrontFeaturedMoviesItemBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(featuredMoviesViewHolder: FeaturedMoviesViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(featuredMoviesViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {

                featuredMoviesViewHolder.movieContentBackgroundBlur.setOverlayColor(context.getColor(R.color.light_transparent_higher))

                featuredMoviesViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light_transparent_high))

                featuredMoviesViewHolder.movieGenreFirst.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))
                featuredMoviesViewHolder.movieGenreSecond.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))
                featuredMoviesViewHolder.movieGenreThird.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))

            }
            ThemeType.ThemeDark -> {

                featuredMoviesViewHolder.movieContentBackgroundBlur.setOverlayColor(context.getColor(R.color.dark_transparent_high))

                featuredMoviesViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark_transparent_high))

                featuredMoviesViewHolder.movieGenreFirst.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))
                featuredMoviesViewHolder.movieGenreSecond.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))
                featuredMoviesViewHolder.movieGenreThird.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))

            }
            else -> {}
        }

    }

    override fun onBindViewHolder(featuredMoviesViewHolder: FeaturedMoviesViewHolder, position: Int) {

        featuredMoviesData[position].data?.let {

            val moviesDataStructure = MoviesDataStructure(it)

            featuredMoviesViewHolder.featuredMovieBackground.setImageDrawable(designFeaturedMoviesBackground(featuredMoviesViewHolder, themeType))

            featuredMoviesViewHolder.moviePosterBackground.background = (designFeaturedMoviesPosterBackground(featuredMoviesViewHolder, themeType))

            val optionMoviesBackground = designOptionsMoviesBackground(featuredMoviesViewHolder, themeType)

            featuredMoviesViewHolder.productRatingStarsView.background = optionMoviesBackground
            featuredMoviesViewHolder.productContentRatingView.background = optionMoviesBackground

            featuredMoviesViewHolder.movieGenreFirst.background = optionMoviesBackground
            featuredMoviesViewHolder.movieGenreSecond.background = optionMoviesBackground
            featuredMoviesViewHolder.movieGenreThird.background = optionMoviesBackground

            featuredMoviesViewHolder.movieNameTextView.text = Html.fromHtml(moviesDataStructure.movieName(), Html.FROM_HTML_MODE_COMPACT)
            featuredMoviesViewHolder.movieSummaryTextView.text = Html.fromHtml(moviesDataStructure.movieSummary().substring(IntRange(0, moviesDataStructure.movieSummary().length/2)), Html.FROM_HTML_MODE_COMPACT)

            featuredMoviesViewHolder.productCurrentRateView.text = Html.fromHtml(moviesDataStructure.movieRating(), Html.FROM_HTML_MODE_COMPACT)

            Glide.with(context)
                .asDrawable()
                .load(moviesDataStructure.moviePoster())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(RoundedCorners(dpToInteger(context, 37)))
                .addListener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return true }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            context.runOnUiThread {

                                featuredMoviesViewHolder.moviePosterImageView.setImageDrawable(resource)

                                val dominantColor = extractDominantColor(resource)
                                val vibrantColor = extractVibrantColor(resource)
                                val mutedColor = extractMutedColor(resource)

                                val movieGradient = GradientDrawable()
                                movieGradient.orientation = GradientDrawable.Orientation.TL_BR
                                movieGradient.colors = intArrayOf(vibrantColor, vibrantColor, dominantColor, mutedColor, mutedColor)
                                movieGradient.cornerRadii = floatArrayOf(
                                    43f, 43f,//Bottom Right
                                    33f, 33f,//Bottom Left
                                    33f, 33f,//Top Left
                                    43f, 43f//Top Right
                                )
                                movieGradient.gradientType = GradientDrawable.SWEEP_GRADIENT
                                movieGradient.setGradientCenter(1f, 0.5f)

                                featuredMoviesViewHolder.movieContentBackground.setImageDrawable(movieGradient)

                                if (isColorLightDark(dominantColor)) {

                                    featuredMoviesViewHolder.movieNameTextView.setTextColor(context.getColor(R.color.dark))
                                    featuredMoviesViewHolder.movieSummaryTextView.setTextColor(context.getColor(R.color.dark))

                                } else {

                                    featuredMoviesViewHolder.movieNameTextView.setTextColor(context.getColor(R.color.light))
                                    featuredMoviesViewHolder.movieSummaryTextView.setTextColor(context.getColor(R.color.light))

                                }

                                featuredMoviesViewHolder.productCurrentRateView.setTextColor(dominantColor)

                            }

                        }

                        return true
                    }

                })
                .submit()

            Glide.with(context)
                .asDrawable()
                .load(moviesDataStructure.movieContentSafetyRatingIcon())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .addListener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return true }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            context.runOnUiThread {

                                featuredMoviesViewHolder.productContentRatingView.setImageDrawable(resource)
                                featuredMoviesViewHolder.productContentRatingView.visibility = View.VISIBLE

                            }

                        }

                        return true
                    }

                })
                .submit()

            val movieGenres = moviesDataStructure.movieGenres().split(",")
            Log.d(this@FeaturedMoviesAdapter.javaClass.simpleName, movieGenres.toString())

            Glide.with(context)
                .asDrawable()
                .load(try { context.genresData.getGenreIconByName(movieGenres[0]) } catch (e: Exception) { "" })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(featuredMoviesViewHolder.movieGenreFirst)

            Glide.with(context)
                .asDrawable()
                .load(try { context.genresData.getGenreIconByName(movieGenres[1]) } catch (e: Exception) { "" })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(featuredMoviesViewHolder.movieGenreSecond)

            Glide.with(context)
                .asDrawable()
                .load(try { context.genresData.getGenreIconByName(movieGenres[2]) } catch (e: Exception) { "" })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(featuredMoviesViewHolder.movieGenreThird)

            featuredMoviesViewHolder.rootViewItem.setOnClickListener {

                openMoviesDetails(context = context,
                    moviePrimaryGenre = moviesDataStructure.moviePrimaryGenre(),
                    movieProductId = moviesDataStructure.movieProductId())

            }

        }

    }

}