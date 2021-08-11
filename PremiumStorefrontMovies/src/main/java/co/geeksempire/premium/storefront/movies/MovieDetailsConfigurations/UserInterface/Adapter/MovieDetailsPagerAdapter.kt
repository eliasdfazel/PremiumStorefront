/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 2:29 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.isColorLightDark
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesDetails
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.ViewHolder.MovieDetailsViewHolder
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.databinding.MoviesDetailsItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.DocumentSnapshot

class MovieDetailsPagerAdapter (val context: MoviesDetails, var themeType: Boolean = ThemeType.ThemeLight): RecyclerView.Adapter<MovieDetailsViewHolder>() {

    val moviesDetailsList = ArrayList<DocumentSnapshot>()

    override fun getItemCount(): Int {

        return moviesDetailsList.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieDetailsViewHolder {

        return MovieDetailsViewHolder(MoviesDetailsItemBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(movieDetailsViewHolder: MovieDetailsViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(movieDetailsViewHolder, position, payloads)
    }

    override fun onBindViewHolder(movieDetailsViewHolder: MovieDetailsViewHolder, position: Int) {

        moviesDetailsList[position].data?.let { documentSnapshot ->

            val moviesDataStructure = MoviesDataStructure(documentSnapshot)

            println(">>> " + moviesDataStructure.movieName())

            Glide.with(context)
                .asDrawable()
                .load(moviesDataStructure.moviePoster())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                        return true
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        resource?.let {

                            context.runOnUiThread {

                                movieDetailsViewHolder.moviePostBackground.background = resource

                                val dominantColor = extractDominantColor(context, resource)
                                val vibrantColor = extractVibrantColor(context, resource)

                                movieDetailsViewHolder.blurryBackground.setSecondOverlayColor(setColorAlpha(dominantColor, 73f))
                                movieDetailsViewHolder.blurryBackground.setOverlayColor(when (themeType) {
                                    ThemeType.ThemeLight -> {
                                        context.getColor(R.color.premiumDarkTransparent)
                                    }
                                    ThemeType.ThemeDark -> {
                                        context.getColor(R.color.premiumDarkTransparent)
                                    }
                                    else -> {
                                        context.getColor(R.color.premiumLightTransparent)
                                    }
                                })

                                if (isColorLightDark(dominantColor)) {

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                                        context.window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                                        context.window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

                                    } else {

                                        @Suppress("DEPRECATION")
                                        context.window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                                        } else {
                                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                        }

                                    }

                                } else {

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                                        context.window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                                        context.window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

                                    } else {

                                        @Suppress("DEPRECATION")
                                        context.window.decorView.systemUiVisibility = 0

                                    }

                                }

                            }

                        }

                        return true
                    }

                })
                .submit()

        }

    }

}