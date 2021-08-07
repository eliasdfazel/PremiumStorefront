/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/7/21, 9:29 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.AllMovies.Adapter

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.AllMovies.ViewHolder.AllMoviesViewHolder
import co.geeksempire.premium.storefront.movies.Utils.Data.openPlayStoreToWatchMovie
import co.geeksempire.premium.storefront.movies.databinding.StorefrontAllContentItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.DocumentSnapshot
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class AllMoviesAdapter(private val context: AppCompatActivity) : RecyclerView.Adapter<AllMoviesViewHolder>() {

    var themeType: Boolean = ThemeType.ThemeLight

    val storefrontMoviesContents: ArrayList<DocumentSnapshot> = ArrayList<DocumentSnapshot>()

    override fun getItemCount() : Int {

        return storefrontMoviesContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : AllMoviesViewHolder {

        return AllMoviesViewHolder(StorefrontAllContentItemBinding.inflate(context.layoutInflater))
    }

    override fun onBindViewHolder(allMoviesViewHolder: AllMoviesViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(allMoviesViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {



            }
            ThemeType.ThemeDark -> {


            }
            else -> {


            }
        }

    }

    override fun onBindViewHolder(allMoviesViewHolder: AllMoviesViewHolder, position: Int) {

        storefrontMoviesContents[position].data?.let {

            val moviesDataStructure = MoviesDataStructure(it)

            Glide.with(context)
                .load(moviesDataStructure.moviePoster())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(RoundedCorners(dpToInteger(context, 23)))
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return false }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            val vibrantColor = extractVibrantColor(context, resource)

                            context.runOnUiThread {

                                allMoviesViewHolder.moviesPosterImageView.setImageDrawable(resource)

                                allMoviesViewHolder.movieGlowingBackground.backgroundTintList = ColorStateList.valueOf(setColorAlpha(vibrantColor, 153f))
                            }

                        }

                        return false
                    }

                })
                .submit()

            allMoviesViewHolder.rootViewItem.setOnClickListener {

                openPlayStoreToWatchMovie(context = context,
                    movieId = moviesDataStructure.movieId(),
                    movieName = moviesDataStructure.movieName(),
                    movieSummary = moviesDataStructure.movieSummary())

            }

        }

    }

}