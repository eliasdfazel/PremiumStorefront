/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 9:55 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter

import android.graphics.Color
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.ViewHolder.MovieDetailsViewHolder
import co.geeksempire.premium.storefront.movies.databinding.MoviesDetailsItemBinding

class MovieDetailsPagerAdapter (val context: AppCompatActivity): RecyclerView.Adapter<MovieDetailsViewHolder>() {

    val listOfFragment = ArrayList<String>()

    val colors = arrayOf(Color.RED, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.BLACK, Color.WHITE, Color.YELLOW, Color.DKGRAY, Color.GRAY, Color.GREEN)

    override fun getItemCount(): Int {

        return listOfFragment.size
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