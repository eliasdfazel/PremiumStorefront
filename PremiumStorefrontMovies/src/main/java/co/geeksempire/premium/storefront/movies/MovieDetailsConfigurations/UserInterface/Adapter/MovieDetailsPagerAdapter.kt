/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/21/21, 9:55 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter

import android.app.SearchManager
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.text.Html
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UI.designOptionsMoviesBackground
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesDetails
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.ViewHolder.MovieDetailsViewHolder
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.databinding.MoviesDetailsItemBinding
import co.geeksempire.youtubeplayer.player.YouTubePlayer
import co.geeksempire.youtubeplayer.player.listeners.AbstractYouTubePlayerListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.DocumentSnapshot
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger


class MovieDetailsPagerAdapter (val context: MoviesDetails, var themeType: Boolean = ThemeType.ThemeLight): RecyclerView.Adapter<MovieDetailsViewHolder>() {

    val moviesDetailsList = ArrayList<DocumentSnapshot>()

    override fun getItemCount(): Int {

        return moviesDetailsList.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieDetailsViewHolder {

        return MovieDetailsViewHolder(MoviesDetailsItemBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(movieDetailsViewHolder: MovieDetailsViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(movieDetailsViewHolder, position, payloads)

        moviesDetailsList[position].data?.let { documentSnapshot ->

            val moviesDataStructure = MoviesDataStructure(documentSnapshot)

            val movieGenres = moviesDataStructure.movieGenres().split(",")
            Log.d(this@MovieDetailsPagerAdapter.javaClass.simpleName, movieGenres.toString())

            val firstGenre = movieGenres[0]
            Glide.with(context)
                .asDrawable()
                .load(try { context.genresData.getGenreIconByName(movieGenres[0]) } catch (e: Exception) { "" })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(movieDetailsViewHolder.movieGenreFirst)
            movieDetailsViewHolder.movieGenreFirst.contentDescription = firstGenre
            movieDetailsViewHolder.movieGenreFirst.setOnClickListener {

            }

            val secondGenre = movieGenres[1]
            Glide.with(context)
                .asDrawable()
                .load(try { context.genresData.getGenreIconByName(movieGenres[1]) } catch (e: Exception) { "" })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(movieDetailsViewHolder.movieGenreSecond)
            movieDetailsViewHolder.movieGenreSecond.contentDescription = secondGenre
            movieDetailsViewHolder.movieGenreSecond.setOnClickListener {

            }

            val thirdGenre = movieGenres[2]
            Glide.with(context)
                .asDrawable()
                .load(try { context.genresData.getGenreIconByName(movieGenres[2]) } catch (e: Exception) { "" })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(movieDetailsViewHolder.movieGenreThird)
            movieDetailsViewHolder.movieGenreThird.contentDescription = thirdGenre
            movieDetailsViewHolder.movieGenreThird.setOnClickListener {

            }

        }

        when (themeType) {
            ThemeType.ThemeLight -> {

                movieDetailsViewHolder.blurryMovieName.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

                movieDetailsViewHolder.backgroundMovieName.background = AppCompatResources.getDrawable(context, R.drawable.movie_name_dimension_effect_light)

                movieDetailsViewHolder.movieDescription.setTextColor(context.getColor(R.color.dark))

                movieDetailsViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light_transparent_high))

                movieDetailsViewHolder.movieGenreFirst.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))
                movieDetailsViewHolder.movieGenreSecond.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))
                movieDetailsViewHolder.movieGenreThird.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))

            }
            ThemeType.ThemeDark -> {

                movieDetailsViewHolder.blurryMovieName.setOverlayColor(context.getColor(R.color.dark_transparent_high))

                movieDetailsViewHolder.backgroundMovieName.background = AppCompatResources.getDrawable(context, R.drawable.movie_name_dimension_effect_dark)

                movieDetailsViewHolder.movieDescription.setTextColor(context.getColor(R.color.light))

                movieDetailsViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark_transparent_high))

                movieDetailsViewHolder.movieGenreFirst.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))
                movieDetailsViewHolder.movieGenreSecond.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))
                movieDetailsViewHolder.movieGenreThird.imageTintList = ColorStateList.valueOf(context.getColor(R.color.black))

            }
        }

    }



    override fun onBindViewHolder(movieDetailsViewHolder: MovieDetailsViewHolder, position: Int) {

        moviesDetailsList[position].data?.let { documentSnapshot ->

            val optionsMoviesBackground = designOptionsMoviesBackground(movieDetailsViewHolder, themeType)

            movieDetailsViewHolder.productRatingStarsView.background = optionsMoviesBackground
            movieDetailsViewHolder.productContentRatingView.background = optionsMoviesBackground

            movieDetailsViewHolder.movieGenreFirst.background = optionsMoviesBackground
            movieDetailsViewHolder.movieGenreSecond.background = optionsMoviesBackground
            movieDetailsViewHolder.movieGenreThird.background = optionsMoviesBackground

            val moviesDataStructure = MoviesDataStructure(documentSnapshot)

            movieDetailsViewHolder.movieNameTextView.text = Html.fromHtml(moviesDataStructure.movieName(), Html.FROM_HTML_MODE_COMPACT)

            movieDetailsViewHolder.movieDescription.text = Html.fromHtml(moviesDataStructure.movieDescription(), Html.FROM_HTML_MODE_COMPACT)

            movieDetailsViewHolder.productCurrentRateView.text = Html.fromHtml(moviesDataStructure.movieRating(), Html.FROM_HTML_MODE_COMPACT)

            Glide.with(context)
                .asDrawable()
                .load(moviesDataStructure.moviePoster())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .addListener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                        return true
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            context.runOnUiThread {

                                movieDetailsViewHolder.moviePostBackground.background = resource

                                val dominantColor = extractDominantColor(resource)
                                val vibrantColor = extractVibrantColor(resource)

                                movieDetailsViewHolder.movieNameTextView.setTextColor(vibrantColor)
                                movieDetailsViewHolder.movieNameTextView.setShadowLayer(movieDetailsViewHolder.movieNameTextView.shadowRadius, movieDetailsViewHolder.movieNameTextView.shadowDx, movieDetailsViewHolder.movieNameTextView.shadowDy, setColorAlpha(vibrantColor, 19f))

                                movieDetailsViewHolder.blurryBackground.setOverlayColor(setColorAlpha(dominantColor, 111f))
                                movieDetailsViewHolder.blurryBackground.setSecondOverlayColor(when (themeType) {
                                    ThemeType.ThemeLight -> {
                                        context.getColor(R.color.premiumLightTransparent)
                                    }
                                    ThemeType.ThemeDark -> {
                                        context.getColor(R.color.premiumDarkTransparent)
                                    }
                                    else -> {
                                        context.getColor(R.color.premiumLightTransparent)
                                    }
                                })

                                movieDetailsViewHolder.backgroundMovieName.background = (when (themeType) {
                                    ThemeType.ThemeLight -> {
                                        val movieNameGlow = AppCompatResources.getDrawable(context, R.drawable.movie_name_dimension_effect_light) as LayerDrawable
                                        movieNameGlow.findDrawableByLayerId(R.id.lineGlow).setTint(vibrantColor)

                                        movieNameGlow
                                    }
                                    ThemeType.ThemeDark -> {
                                        val movieNameGlow = AppCompatResources.getDrawable(context, R.drawable.movie_name_dimension_effect_dark) as LayerDrawable
                                        movieNameGlow.findDrawableByLayerId(R.id.lineGlow).setTintList(ColorStateList.valueOf(vibrantColor))

                                        movieNameGlow
                                    }
                                    else -> {
                                        val movieNameGlow = AppCompatResources.getDrawable(context, R.drawable.movie_name_dimension_effect_light) as LayerDrawable
                                        movieNameGlow.findDrawableByLayerId(R.id.lineGlow).setTintList(ColorStateList.valueOf(vibrantColor))

                                        movieNameGlow
                                    }
                                })

                                movieDetailsViewHolder.productCurrentRateView.setTextColor(vibrantColor)
                                movieDetailsViewHolder.productCurrentRateView.setShadowLayer(movieDetailsViewHolder.productCurrentRateView.shadowRadius, movieDetailsViewHolder.productCurrentRateView.shadowDx, movieDetailsViewHolder.productCurrentRateView.shadowDy, vibrantColor)

                            }

                        }

                        return true
                    }

                })
                .submit()

            Glide.with(context)
                .asDrawable()
                .load(moviesDataStructure.moviePoster())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(RoundedCorners(dpToInteger(context, 19)))
                .into(movieDetailsViewHolder.moviesPosterImageView)

            Glide.with(context)
                .asDrawable()
                .load(moviesDataStructure.movieContentSafetyRatingIcon())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(movieDetailsViewHolder.productContentRatingView)

            context.lifecycle.addObserver(movieDetailsViewHolder.movieTrailerYouTube)

            movieDetailsViewHolder.movieTrailerYouTube.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {

                    val videoId = movieTrailerId(moviesDataStructure.movieTrailer())

                    youTubePlayer.loadVideo(videoId, 0f)

                }

            })

            moviesDataStructure.movieStars().split(",").sortedBy {

                it
            }.forEachIndexed { index, starName ->

                val starImageView = generateMovieStarView(movieDetailsViewHolder, index, starName)

                Glide.with(context)
                    .asDrawable()
                    .load(generateMovieStarImage(starName))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(starImageView)

            }

        }

    }

    private fun movieTrailerId(trailerAddress: String) : String {

        return trailerAddress.replace("https://www.youtube.com/watch?v=", "")
    }

    private fun generateMovieStarView(movieDetailsViewHolder: MovieDetailsViewHolder, dataIndex: Int, starName: String) : AppCompatImageView {

        return when (dataIndex) {
            0 -> {

                movieDetailsViewHolder.movieStarFirstImageView.visibility = View.VISIBLE

                movieDetailsViewHolder.movieStarFirstImageView.tag = starName

                movieDetailsViewHolder.movieStarFirstImageView.setOnClickListener {

                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                        .putExtra(SearchManager.QUERY, it.tag.toString())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

                movieDetailsViewHolder.movieStarFirstImageView
            }
            1 -> {

                movieDetailsViewHolder.movieStarSecondImageView.visibility = View.VISIBLE

                movieDetailsViewHolder.movieStarSecondImageView.tag = starName

                movieDetailsViewHolder.movieStarSecondImageView.setOnClickListener {

                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                        .putExtra(SearchManager.QUERY, it.tag.toString())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

                movieDetailsViewHolder.movieStarSecondImageView
            }
            2 -> {

                movieDetailsViewHolder.movieStarThirdImageView.visibility = View.VISIBLE

                movieDetailsViewHolder.movieStarThirdImageView.tag = starName

                movieDetailsViewHolder.movieStarThirdImageView.setOnClickListener {

                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                        .putExtra(SearchManager.QUERY, it.tag.toString())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

                movieDetailsViewHolder.movieStarThirdImageView
            }
            3 -> {

                movieDetailsViewHolder.movieStarFourthImageView.visibility = View.VISIBLE

                movieDetailsViewHolder.movieStarFourthImageView.tag = starName

                movieDetailsViewHolder.movieStarFourthImageView.setOnClickListener {

                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                        .putExtra(SearchManager.QUERY, it.tag.toString())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

                movieDetailsViewHolder.movieStarFourthImageView
            }
            4 -> {

                movieDetailsViewHolder.movieStarFifthImageView.visibility = View.VISIBLE

                movieDetailsViewHolder.movieStarFifthImageView.tag = starName

                movieDetailsViewHolder.movieStarFifthImageView.setOnClickListener {

                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                        .putExtra(SearchManager.QUERY, it.tag.toString())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

                movieDetailsViewHolder.movieStarFifthImageView
            }
            5 -> {

                movieDetailsViewHolder.movieStarSixthImageView.visibility = View.VISIBLE

                movieDetailsViewHolder.movieStarSixthImageView.tag = starName

                movieDetailsViewHolder.movieStarSixthImageView.setOnClickListener {

                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                        .putExtra(SearchManager.QUERY, it.tag.toString())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

                movieDetailsViewHolder.movieStarSixthImageView
            }
            else -> {

                movieDetailsViewHolder.movieStarFirstImageView.visibility = View.VISIBLE

                movieDetailsViewHolder.movieStarFirstImageView.tag = starName

                movieDetailsViewHolder.movieStarFirstImageView.setOnClickListener {

                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                        .putExtra(SearchManager.QUERY, it.tag.toString())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

                movieDetailsViewHolder.movieStarFirstImageView
            }
        }
    }

    private fun generateMovieStarImage(starName: String) : String {

        return "http://geeksempire.co/Assets/PremiumStorefront/Movies/Stars/" + starName.replace(" ", "")
    }

}