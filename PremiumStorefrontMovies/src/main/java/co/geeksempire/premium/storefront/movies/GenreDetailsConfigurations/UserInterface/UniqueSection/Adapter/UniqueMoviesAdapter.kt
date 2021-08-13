/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/13/21, 9:14 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.UserInterface.UniqueSection.Adapter

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.UserInterface.GenreDetails
import co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.UserInterface.UniqueSection.ViewHolder.UniqueMoviesViewHolder
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesDetails
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.databinding.GenreUniqueMoviesItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.DocumentSnapshot
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class UniqueMoviesAdapter (val context: GenreDetails) : RecyclerView.Adapter<UniqueMoviesViewHolder>() {

    val uniqueMoviesData = ArrayList<DocumentSnapshot>()

    override fun getItemCount(): Int {

        return uniqueMoviesData.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UniqueMoviesViewHolder {

        return UniqueMoviesViewHolder(GenreUniqueMoviesItemBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(uniqueMoviesViewHolder: UniqueMoviesViewHolder, position: Int) {

        uniqueMoviesData[position].data?.let {

            val moviesDataStructure = MoviesDataStructure(it)

            Glide.with(context)
                .asDrawable()
                .load(moviesDataStructure.moviePoster())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(RoundedCorners(dpToInteger(context, 23)))
                .addListener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                        return true
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            context.runOnUiThread {

                                uniqueMoviesViewHolder.moviesPosterImageView.setImageDrawable(resource)

                            }

                        }

                        return true
                    }

                })
                .submit()

            uniqueMoviesViewHolder.rootViewItem.setOnClickListener {

                MoviesDetails.openMoviesDetails(context = context,
                    moviePrimaryGenre = moviesDataStructure.moviePrimaryGenre(),
                    movieProductId = moviesDataStructure.movieProductId())

            }

        }

    }

}