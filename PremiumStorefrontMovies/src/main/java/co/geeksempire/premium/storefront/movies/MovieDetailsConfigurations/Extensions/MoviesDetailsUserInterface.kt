/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 9:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.Extensions

import androidx.viewpager2.widget.ViewPager2
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesDetails

fun MoviesDetails.setupMoviesDetailsUserInterface() {

    moviesDetailsLayoutBinding.moviesViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    moviesDetailsLayoutBinding.moviesViewPager.adapter = movieDetailsPagerAdapter

    moviesDetailsLayoutBinding.moviesViewPager.setPageTransformer { page, position ->

        val width = page.width
        val height = page.height

        val rotation = -13f * position * -1.25f

        page.pivotX = (width * 1.7f)
        page.pivotY = height.toFloat()

        page.rotation = rotation

    }

}