/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/12/21, 9:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.Actions.Operation

import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesDetails
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.moviesFilteringSetup
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesSearching.searchingSetup
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesSorting.moviesSortingSetup
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontMovies
import co.geeksempire.premium.storefront.movies.Utils.Data.openPlayStoreToWatchMovie
import co.geeksempire.premium.storefront.movies.Utils.Data.shareMovie

class ActionCenterOperationsMovies {

    fun setupForMoviesStorefront(context: StorefrontMovies, themeType: Boolean = ThemeType.ThemeLight) {

        /* Sort */
        context.storefrontMoviesLayoutBinding.leftActionView.setOnClickListener {

            moviesSortingSetup(context = context, filterAllMovies = context.filterAllMovies,
                sortingInclude = context.storefrontMoviesLayoutBinding.moviesSortingInclude, filteringInclude = context.storefrontMoviesLayoutBinding.moviesFilteringInclude,
                rightActionView = context.storefrontMoviesLayoutBinding.rightActionView, middleActionView =  context.storefrontMoviesLayoutBinding.middleActionView, leftActionView = context.storefrontMoviesLayoutBinding.leftActionView, context.allMoviesAdapter,
                themeType = themeType)

        }

        /* Search */
        context.storefrontMoviesLayoutBinding.middleActionView.setOnClickListener {

            searchingSetup(context, context.filterAllMovies,
                context.storefrontMoviesLayoutBinding.textInputSearchView,
                context.storefrontMoviesLayoutBinding.searchView,
                context.storefrontMoviesLayoutBinding.rightActionView,
                context.storefrontMoviesLayoutBinding.middleActionView,
                context.storefrontMoviesLayoutBinding.leftActionView,
                context.storefrontAllUnfilteredContents,
                themeType)

        }

        /* Filter */
        context.storefrontMoviesLayoutBinding.rightActionView.setOnClickListener {

            moviesFilteringSetup(context = context,
                moviesSortingLayoutBinding = context.storefrontMoviesLayoutBinding.moviesSortingInclude, moviesFilteringLayoutBinding = context.storefrontMoviesLayoutBinding.moviesFilteringInclude,
                rightActionView = context.storefrontMoviesLayoutBinding.rightActionView, middleActionView =  context.storefrontMoviesLayoutBinding.middleActionView, leftActionView = context.storefrontMoviesLayoutBinding.leftActionView, filterOptionsAdapter = context.filterOptionsAdapter,
                storefrontAllUnfilteredContents = context.storefrontAllUnfilteredContents,
                themeType = themeType)

        }

    }

    fun setupForMoviesDetails(context: MoviesDetails, movieId: String, MovieName: String, movieSummary: String) {

        /* Share */
        context.moviesDetailsLayoutBinding.leftActionView.setOnClickListener {

            shareMovie(context = context,
                movieId = movieId,
                movieName = MovieName,
                movieSummary = movieSummary)

        }

        /* Watch */
        context.moviesDetailsLayoutBinding.middleActionView.setOnClickListener {

            openPlayStoreToWatchMovie(context = context,
                movieId = movieId,
                movieName = MovieName,
                movieSummary = movieSummary)

        }

        /* Rate */
        context.moviesDetailsLayoutBinding.rightActionView.setOnClickListener {

            openPlayStoreToWatchMovie(context = context,
                movieId = movieId,
                movieName = MovieName,
                movieSummary = movieSummary)

        }

    }

}