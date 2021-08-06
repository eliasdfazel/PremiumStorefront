/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/6/21, 11:36 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.NewMovies.Adapter

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataKey
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.StorefrontMoviesContentsData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.NewMovies.ViewHolder.NewMoviesViewHolder
import co.geeksempire.premium.storefront.movies.Utils.Data.openPlayStoreToWatchMovie
import co.geeksempire.premium.storefront.movies.databinding.StorefrontNewContentItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger
import org.json.JSONArray

class NewMoviesAdapter(private val context: AppCompatActivity) : RecyclerView.Adapter<NewMoviesViewHolder>() {

    var themeType: Boolean = ThemeType.ThemeLight

    val storefrontMoviesContents: ArrayList<StorefrontMoviesContentsData> = ArrayList<StorefrontMoviesContentsData>()

    override fun getItemCount() : Int {

        return storefrontMoviesContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : NewMoviesViewHolder {

        return NewMoviesViewHolder(StorefrontNewContentItemBinding.inflate(context.layoutInflater))
    }

    override fun onBindViewHolder(newContentViewHolder: NewMoviesViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(newContentViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {



            }
            ThemeType.ThemeDark -> {


            }
            else -> {


            }
        }

    }

    override fun onBindViewHolder(newContentViewHolder: NewMoviesViewHolder, position: Int) {

        Glide.with(context)
            .load(storefrontMoviesContents[position].moviePosterLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(RoundedCorners(dpToInteger(context, 19)))
            .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return false }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            val vibrantColor = extractVibrantColor(context, resource)

                            context.runOnUiThread {

                                newContentViewHolder.moviesPosterImageView.setImageDrawable(resource)

                            }

                        }

                        return false
                    }

                })
                .submit()

        newContentViewHolder.rootViewItem.setOnClickListener {

            openPlayStoreToWatchMovie(context = context,
                movieId = JSONArray(storefrontMoviesContents[position].productAttributes[MoviesDataKey.MovieId].toString())[0].toString(),
                movieName = storefrontMoviesContents[position].movieName,
                movieSummary = storefrontMoviesContents[position].movieSummary)

        }

    }

}