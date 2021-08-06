/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/6/21, 11:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.Adapter

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractMutedColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.isColorLightDark
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.UI.designFeaturedMoviesBackground
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.UI.designFeaturedMoviesPosterBackground
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.UI.designOptionsMoviesBackground
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.ViewHolder.FeaturedMoviesViewHolder
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontMovies
import co.geeksempire.premium.storefront.movies.Utils.Data.openPlayStoreToWatchMovie
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

        return FeaturedMoviesViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_featured_content_item, viewGroup, false))
    }

    override fun onBindViewHolder(featuredMoviesViewHolder: FeaturedMoviesViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(featuredMoviesViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {

                featuredMoviesViewHolder.movieContentBackgroundBlur.setOverlayColor(context.getColor(R.color.light_transparent_high))

                featuredMoviesViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.white_transparent))

                featuredMoviesViewHolder.movieGenreFirst.imageTintList = ColorStateList.valueOf(context.getColor(R.color.premiumDark))
                featuredMoviesViewHolder.movieGenreSecond.imageTintList = ColorStateList.valueOf(context.getColor(R.color.premiumDark))
                featuredMoviesViewHolder.movieGenreThird.imageTintList = ColorStateList.valueOf(context.getColor(R.color.premiumDark))

            }
            ThemeType.ThemeDark -> {

                featuredMoviesViewHolder.movieContentBackgroundBlur.setOverlayColor(context.getColor(R.color.dark_transparent_high))

                featuredMoviesViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black_transparent))

                featuredMoviesViewHolder.movieGenreFirst.imageTintList = ColorStateList.valueOf(context.getColor(R.color.premiumLight))
                featuredMoviesViewHolder.movieGenreSecond.imageTintList = ColorStateList.valueOf(context.getColor(R.color.premiumLight))
                featuredMoviesViewHolder.movieGenreThird.imageTintList = ColorStateList.valueOf(context.getColor(R.color.premiumLight))

            }
        }

    }

    override fun onBindViewHolder(featuredMoviesViewHolder: FeaturedMoviesViewHolder, position: Int) {

        featuredMoviesData[position].data?.let {

            val moviesDataStructure = MoviesDataStructure(it)

            featuredMoviesViewHolder.featuredMovieBackground.setImageDrawable(designFeaturedMoviesBackground(featuredMoviesViewHolder, themeType))

            featuredMoviesViewHolder.moviePosterBackground.background = (designFeaturedMoviesPosterBackground(featuredMoviesViewHolder, themeType))

            featuredMoviesViewHolder.productRatingStarsView.background = (designOptionsMoviesBackground(featuredMoviesViewHolder, themeType))
            featuredMoviesViewHolder.productContentRatingView.background = (designOptionsMoviesBackground(featuredMoviesViewHolder, themeType))

            featuredMoviesViewHolder.movieGenreFirst.background = (designOptionsMoviesBackground(featuredMoviesViewHolder, themeType))
            featuredMoviesViewHolder.movieGenreSecond.background = (designOptionsMoviesBackground(featuredMoviesViewHolder, themeType))
            featuredMoviesViewHolder.movieGenreThird.background = (designOptionsMoviesBackground(featuredMoviesViewHolder, themeType))


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

                                val dominantColor = extractDominantColor(context, resource)
                                val vibrantColor = extractVibrantColor(context, resource)
                                val mutedColor = extractMutedColor(context, resource)

                                val movieGradient = GradientDrawable()
                                movieGradient.orientation = GradientDrawable.Orientation.TL_BR
                                movieGradient.colors = intArrayOf(vibrantColor, vibrantColor, dominantColor, mutedColor, mutedColor)
                                movieGradient.cornerRadii = floatArrayOf(
                                    43f, 43f,
                                    43f, 43f,
                                    43f, 43f,
                                    43f, 43f
                                )
                                movieGradient.gradientType = GradientDrawable.SWEEP_GRADIENT
                                movieGradient.setGradientCenter(1f, 0.5f)

                                featuredMoviesViewHolder.movieContentBackground.setImageDrawable(movieGradient)

                                if (isColorLightDark(vibrantColor)) {

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

                openPlayStoreToWatchMovie(context = context,
                    movieId = moviesDataStructure.movieId(),
                    movieName = moviesDataStructure.movieName(),
                    movieSummary = moviesDataStructure.movieSummary())

            }

        }

    }

}