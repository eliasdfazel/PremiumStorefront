/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/23/21, 9:02 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesStars.Adapter

import android.app.SearchManager
import android.content.Intent
import android.content.res.ColorStateList
import android.text.Html
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesStars.MoviesStarsData
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesStars.ViewHolder.MovieStarsViewHolder
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.databinding.MoviesStarsItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MovieStarsAdapter (val context: AppCompatActivity, val themeType: Boolean) : RecyclerView.Adapter<MovieStarsViewHolder>() {

    val moviesDetailsList = ArrayList<MoviesStarsData>()

    override fun getItemCount(): Int {

        return moviesDetailsList.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieStarsViewHolder {

        return MovieStarsViewHolder(MoviesStarsItemBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(movieStarsViewHolder: MovieStarsViewHolder, position: Int) {

        when (themeType) {
            ThemeType.ThemeLight -> {

                movieStarsViewHolder.rootView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.premiumLightTransparent))

            }
            ThemeType.ThemeDark -> {

                movieStarsViewHolder.rootView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.premiumDarkTransparent))

            }
        }

        movieStarsViewHolder.rootView.contentDescription = moviesDetailsList[position].movieStarName

        movieStarsViewHolder.movieStarTextView.text = Html.fromHtml(moviesDetailsList[position].movieStarName, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(context)
            .asDrawable()
            .load(generateMovieStarImage(moviesDetailsList[position].movieStarName))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(movieStarsViewHolder.movieStarImageView)

        movieStarsViewHolder.rootView.setOnClickListener { view ->

                context.startActivity(Intent(Intent.ACTION_WEB_SEARCH)
                    .putExtra(SearchManager.QUERY, view.contentDescription.toString())
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

        }

        movieStarsViewHolder.rootView.setOnLongClickListener { view ->

            Toast.makeText(context, view.contentDescription, Toast.LENGTH_LONG).show()

            true
        }

    }

    private fun generateMovieStarImage(starName: String) : String {

        return "https://geeksempire.co/Assets/PremiumStorefront/Movies/Stars/" + starName.replace(" ", "")
    }

}