/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/10/21, 12:43 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.Actions.Operation

import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontMovies
import co.geeksempire.premium.storefront.movies.Utils.Data.openPlayStoreToWatchMovie
import co.geeksempire.premium.storefront.movies.Utils.Data.shareMovie

class ActionCenterOperationsMovies {

    fun setupForMoviesStorefront(context: StorefrontMovies, themeType: Boolean = ThemeType.ThemeLight) {

        /* Sort */
        context.storefrontMoviesLayoutBinding.leftActionView.setOnClickListener {



        }

        /* Search */
        context.storefrontMoviesLayoutBinding.middleActionView.setOnClickListener {



        }

        /* Filter */
        context.storefrontMoviesLayoutBinding.rightActionView.setOnClickListener {



        }

    }

    fun setupForMoviesDetails(context: StorefrontMovies, movieId: String, MovieName: String, movieSummary: String) {

        /* Share */
        context.storefrontMoviesLayoutBinding.leftActionView.setOnClickListener {

            shareMovie(context = context,
                movieId = movieId,
                movieName = MovieName,
                movieSummary = movieSummary)

        }

        /* Watch */
        context.storefrontMoviesLayoutBinding.middleActionView.setOnClickListener {

            openPlayStoreToWatchMovie(context = context,
                movieId = movieId,
                movieName = MovieName,
                movieSummary = movieSummary)

        }

        /* Rate */
        context.storefrontMoviesLayoutBinding.rightActionView.setOnClickListener {

            openPlayStoreToWatchMovie(context = context,
                movieId = movieId,
                movieName = MovieName,
                movieSummary = movieSummary)

        }

    }

}