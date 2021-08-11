/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 9:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesDetails
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.ViewHolder.MovieDetailsViewHolder
import co.geeksempire.premium.storefront.movies.databinding.MoviesDetailsItemBinding

class MovieDetailsPagerAdapter (val context: MoviesDetails): RecyclerView.Adapter<MovieDetailsViewHolder>() {

    val moviesDetailsList = ArrayList<String>()

    override fun getItemCount(): Int {

        return moviesDetailsList.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieDetailsViewHolder {

        return MovieDetailsViewHolder(MoviesDetailsItemBinding.inflate(context.layoutInflater))
    }

    override fun onBindViewHolder(movieDetailsViewHolder: MovieDetailsViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(movieDetailsViewHolder, position, payloads)
    }

    override fun onBindViewHolder(movieDetailsViewHolder: MovieDetailsViewHolder, position: Int) {



    }

}