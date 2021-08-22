/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/22/21, 5:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter

import android.app.SearchManager
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.text.Html
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
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
import net.geekstools.imageview.customshapes.ShapesImage


class MovieDetailsPagerAdapter (var context: MoviesDetails, var themeType: Boolean = ThemeType.ThemeLight): RecyclerView.Adapter<MovieDetailsViewHolder>() {

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

    }



    override fun onBindViewHolder(movieDetailsViewHolder: MovieDetailsViewHolder, position: Int) {

        when (themeType) {
            ThemeType.ThemeLight -> {

                movieDetailsViewHolder.blurryMovieName.setOverlayColor(try {
                    ContextCompat.getColor(context, R.color.premiumLightTransparent)
                } catch (e: Exception) {
                    Color.argb(129, 213, 224, 243)
                })

                try {
                    movieDetailsViewHolder.backgroundMovieName.background = AppCompatResources.getDrawable(context, R.drawable.movie_name_dimension_effect_light)
                } catch (e: Exception) {

                }

                movieDetailsViewHolder.movieDescription.setTextColor(try {
                    ContextCompat.getColor(context, R.color.dark)
                } catch (e: Exception) {
                    Color.rgb(10, 15, 24)

                })

                movieDetailsViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(try {
                    ContextCompat.getColor(context, R.color.light_transparent_high)
                } catch (e: Exception) {
                    Color.argb(13, 242, 247, 255)
                })

                movieDetailsViewHolder.movieGenreFirst.imageTintList = ColorStateList.valueOf(try {
                    ContextCompat.getColor(context, R.color.black)
                } catch (e: Exception) {
                    Color.BLACK
                })
                movieDetailsViewHolder.movieGenreSecond.imageTintList = ColorStateList.valueOf(try {
                    ContextCompat.getColor(context, R.color.black)
                } catch (e: Exception) {
                    Color.BLACK
                })
                movieDetailsViewHolder.movieGenreThird.imageTintList = ColorStateList.valueOf(try {
                    ContextCompat.getColor(context, R.color.black)
                } catch (e: Exception) {
                    Color.BLACK
                })

            }
            ThemeType.ThemeDark -> {

                movieDetailsViewHolder.blurryMovieName.setOverlayColor(try {
                    ContextCompat.getColor(context, R.color.premiumDarkTransparent)
                } catch (e: Exception) {
                    Color.argb(129, 60, 67, 77)

                })

                try {
                    movieDetailsViewHolder.backgroundMovieName.background = AppCompatResources.getDrawable(context, R.drawable.movie_name_dimension_effect_dark)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                movieDetailsViewHolder.movieDescription.setTextColor(try {
                    ContextCompat.getColor(context, R.color.light)
                } catch (e: Exception) {
                    Color.rgb(242, 247, 255)
                })

                movieDetailsViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(try {
                    ContextCompat.getColor(context, R.color.dark_transparent_high)
                } catch (e: Exception) {
                    Color.argb(13, 10, 15, 24)
                })

                movieDetailsViewHolder.productRatingStarsView.imageTintList = ColorStateList.valueOf(try {
                    ContextCompat.getColor(context, R.color.light_transparent_high)
                } catch (e: Exception) {
                    Color.argb(13, 242, 247, 255)
                })

                movieDetailsViewHolder.movieGenreFirst.imageTintList = ColorStateList.valueOf(try {
                    ContextCompat.getColor(context, R.color.black)
                } catch (e: Exception) {
                    Color.BLACK
                })
                movieDetailsViewHolder.movieGenreSecond.imageTintList = ColorStateList.valueOf(try {
                    ContextCompat.getColor(context, R.color.black)
                } catch (e: Exception) {
                    Color.BLACK
                })
                movieDetailsViewHolder.movieGenreThird.imageTintList = ColorStateList.valueOf(try {
                    ContextCompat.getColor(context, R.color.black)
                } catch (e: Exception) {
                    Color.BLACK
                })

            }
        }

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

                                context.shadowAnimationUtils.textShadowValueAnimatorLoop(view = movieDetailsViewHolder.movieNameTextView,
                                    startValue = 0f, endValue = movieDetailsViewHolder.movieNameTextView.shadowRadius,
                                    startDuration = 1757, endDuration = 1357,
                                    shadowColor = movieDetailsViewHolder.movieNameTextView.shadowColor, shadowX = movieDetailsViewHolder.movieNameTextView.shadowDx, shadowY = movieDetailsViewHolder.movieNameTextView.shadowDy,
                                    numberOfLoop = 3)

                                movieDetailsViewHolder.blurryBackground.setOverlayColor(setColorAlpha(dominantColor, 111f))
                                movieDetailsViewHolder.blurryBackground.setSecondOverlayColor(when (themeType) {
                                    ThemeType.ThemeLight -> {
                                        try {
                                            ContextCompat.getColor(context, R.color.premiumLightTransparent)
                                        } catch (e: Exception) {
                                            e.printStackTrace()

                                            Color.argb(129, 213, 224, 243)
                                        }
                                    }
                                    ThemeType.ThemeDark -> {
                                        try {
                                            ContextCompat.getColor(context, R.color.premiumDarkTransparent)
                                        } catch (e: Exception) {
                                            e.printStackTrace()

                                            Color.argb(129, 60, 67, 77)
                                        }
                                    }
                                    else -> {
                                        try {
                                            ContextCompat.getColor(context, R.color.premiumLightTransparent)
                                        } catch (e: Exception) {
                                            e.printStackTrace()

                                            Color.argb(129, 213, 224, 243)
                                        }
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

    private fun generateMovieStarView(movieDetailsViewHolder: MovieDetailsViewHolder, dataIndex: Int, starName: String) : ShapesImage {

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