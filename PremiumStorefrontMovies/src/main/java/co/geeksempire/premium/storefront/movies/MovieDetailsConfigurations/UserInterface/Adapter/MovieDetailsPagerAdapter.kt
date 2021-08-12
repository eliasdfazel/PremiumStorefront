/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/12/21, 10:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter

import android.app.SearchManager
import android.content.Intent
import android.graphics.drawable.Drawable
import android.text.Html
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
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
    }

    override fun onBindViewHolder(movieDetailsViewHolder: MovieDetailsViewHolder, position: Int) {

        moviesDetailsList[position].data?.let { documentSnapshot ->

            val moviesDataStructure = MoviesDataStructure(documentSnapshot)

            movieDetailsViewHolder.movieNameTextView.text = Html.fromHtml(moviesDataStructure.movieName(), Html.FROM_HTML_MODE_COMPACT)

            movieDetailsViewHolder.movieDescriptionFirst.text = Html.fromHtml(moviesDataStructure.movieDescription(), Html.FROM_HTML_MODE_COMPACT)

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

                                val dominantColor = extractDominantColor(context, resource)
                                val vibrantColor = extractVibrantColor(context, resource)

                                movieDetailsViewHolder.movieNameTextView.setTextColor(vibrantColor)
                                movieDetailsViewHolder.movieNameTextView.setShadowLayer(movieDetailsViewHolder.movieNameTextView.shadowRadius, movieDetailsViewHolder.movieNameTextView.shadowDx, movieDetailsViewHolder.movieNameTextView.shadowDy, vibrantColor)

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

                Glide.with(context)
                    .asDrawable()
                    .load(generateMovieStarImage(starName))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(generateMovieStarView(movieDetailsViewHolder, index, starName))

            }

        }

    }

    private fun movieTrailerId(trailerAddress: String) : String {

        return trailerAddress.replace("https://www.youtube.com/watch?v=", "")
    }

    private fun generateMovieStarView(movieDetailsViewHolder: MovieDetailsViewHolder, dataIndex: Int, starName: String) : AppCompatImageView {

        return when (dataIndex) {
            0 -> {

                movieDetailsViewHolder.movieStarFirstImageView.tag = starName

                movieDetailsViewHolder.movieStarFirstImageView.setOnClickListener {

                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                        .putExtra(SearchManager.QUERY, it.tag.toString())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

                movieDetailsViewHolder.movieStarFirstImageView
            }
            1 -> {

                movieDetailsViewHolder.movieStarSecondImageView.tag = starName

                movieDetailsViewHolder.movieStarSecondImageView.setOnClickListener {

                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                        .putExtra(SearchManager.QUERY, it.tag.toString())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

                movieDetailsViewHolder.movieStarSecondImageView
            }
            2 -> {

                movieDetailsViewHolder.movieStarThirdImageView.tag = starName

                movieDetailsViewHolder.movieStarThirdImageView.setOnClickListener {

                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                        .putExtra(SearchManager.QUERY, it.tag.toString())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

                movieDetailsViewHolder.movieStarThirdImageView
            }
            else -> movieDetailsViewHolder.movieStarFirstImageView
        }
    }

    private fun generateMovieStarImage(starName: String) : String {

        return "http://geeksempire.co/Assets/PremiumStorefront/Movies/Stars/" + starName.replace(" ", "")
    }

}