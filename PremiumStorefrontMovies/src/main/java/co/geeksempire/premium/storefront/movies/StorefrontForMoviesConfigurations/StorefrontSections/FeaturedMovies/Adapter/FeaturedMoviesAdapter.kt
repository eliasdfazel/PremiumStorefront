/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/2/21, 11:11 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.Adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.UI.designFeaturedMoviesBackground
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.ViewHolder.FeaturedMoviesViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.DocumentSnapshot
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class FeaturedMoviesAdapter (val context: AppCompatActivity) : RecyclerView.Adapter<FeaturedMoviesViewHolder>() {

    val featuredMoviesData = ArrayList<DocumentSnapshot>()

    override fun getItemCount(): Int {

        return featuredMoviesData.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FeaturedMoviesViewHolder {

        return FeaturedMoviesViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_featured_content_item, viewGroup, false))
    }

    override fun onBindViewHolder(featuredMoviesViewHolder: FeaturedMoviesViewHolder, position: Int) {

        featuredMoviesData[position].data?.let {

            val moviesDataStructure = MoviesDataStructure(it)


            featuredMoviesViewHolder.featuredMovieBackground.setImageDrawable(designFeaturedMoviesBackground(featuredMoviesViewHolder))

            Glide.with(context)
                .asDrawable()
                .load(moviesDataStructure.moviePoster())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(RoundedCorners(dpToInteger(context, 43)))
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return true }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            context.runOnUiThread {

                                featuredMoviesViewHolder.moviePosterImageView.setImageDrawable(resource)

                            }

                        }

                        return true
                    }

                })
                .submit()

        }

    }

}