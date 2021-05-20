/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/20/21, 9:19 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.ProductsDetailsConfigurations.YoutubeConfigurations

import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener
import com.google.android.youtube.player.YouTubeThumbnailView


class SetupYoutubePlayer(val youTubePlayerView: YouTubeThumbnailView) {

    fun initialize(youtubeAddress: String) {

        val initializedListener = object : YouTubeThumbnailView.OnInitializedListener {

            override fun onInitializationSuccess(youTubeThumbnailView: YouTubeThumbnailView?, youTubeThumbnailLoader: YouTubeThumbnailLoader?) {

                youTubeThumbnailLoader?.setVideo(youtubeAddress.replace("https://youtu.be/", ""))
                youTubeThumbnailLoader?.setOnThumbnailLoadedListener(object: OnThumbnailLoadedListener {

                        override fun onThumbnailLoaded(youTubeThumbnailView: YouTubeThumbnailView, aString: String) {

                            youTubeThumbnailLoader.release()

                        }

                        override fun onThumbnailError(youTubeThumbnailView: YouTubeThumbnailView, errorReason: YouTubeThumbnailLoader.ErrorReason) {

                            youTubeThumbnailLoader.release()

                        }

                    })

            }

            override fun onInitializationFailure(youTubeThumbnailView: YouTubeThumbnailView?, youTubeInitializationResult: YouTubeInitializationResult?) {

            }

        }

        youTubePlayerView.initialize(YoutubeDataStructure.Youtube_Key, initializedListener)

    }

}