/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 12:11 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.ViewHolder

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.databinding.MoviesDetailsItemBinding
import co.geeksempire.youtubeplayer.player.views.YouTubePlayerView
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class MovieDetailsViewHolder (moviesDetailsItemBinding: MoviesDetailsItemBinding) : RecyclerView.ViewHolder(moviesDetailsItemBinding.root) {
    val rootViewItem: ConstraintLayout = moviesDetailsItemBinding.rootViewItem

    val allItemContainer: ConstraintLayout = moviesDetailsItemBinding.allItemContainer

    val moviePostBackground: AppCompatImageView = moviesDetailsItemBinding.moviePostBackground
    val blurryBackground: RealtimeBlurView = moviesDetailsItemBinding.blurryBackground

    val moviesPosterImageView: AppCompatImageView = moviesDetailsItemBinding.moviesPosterImageView

    val movieNameTextView: AppCompatTextView = moviesDetailsItemBinding.movieNameTextView
    val blurryMovieName: RealtimeBlurView = moviesDetailsItemBinding.blurryMovieName
    val backgroundMovieName: AppCompatImageView = moviesDetailsItemBinding.backgroundMovieName

    val movieDescription: AppCompatTextView = moviesDetailsItemBinding.movieDescription

    val productRatingStarsView: AppCompatImageView = moviesDetailsItemBinding.productRatingStarsView
    val productCurrentRateView: AppCompatTextView = moviesDetailsItemBinding.productCurrentRateView

    val productContentRatingView: AppCompatImageView = moviesDetailsItemBinding.productContentRatingView

    val movieGenreFirst: AppCompatImageView = moviesDetailsItemBinding.movieGenreFirst
    val movieGenreSecond: AppCompatImageView = moviesDetailsItemBinding.movieGenreSecond
    val movieGenreThird: AppCompatImageView = moviesDetailsItemBinding.movieGenreThird

    val movieTrailerYouTube: YouTubePlayerView = moviesDetailsItemBinding.movieTrailerYouTube

    val movieStarFirstImageView: AppCompatImageView = moviesDetailsItemBinding.firstStar
    val movieStarSecondImageView: AppCompatImageView = moviesDetailsItemBinding.secondStar
    val movieStarThirdImageView: AppCompatImageView = moviesDetailsItemBinding.thirdStar

    val movieStarFourthImageView: AppCompatImageView = moviesDetailsItemBinding.fourthStar
    val movieStarFifthImageView: AppCompatImageView = moviesDetailsItemBinding.fifthStar
    val movieStarSixthImageView: AppCompatImageView = moviesDetailsItemBinding.sixthStar

}