/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/20/21, 5:41 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.ProductsDetailsConfigurations.YoutubeConfigurations

import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView


class SetupYoutubePlayer(val youTubePlayerView: YouTubeThumbnailView) {

    fun initialize(youtubeAddress: String) {

        val initializedListener = object : YouTubeThumbnailView.OnInitializedListener {

//            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, b: Boolean) {
//
//                youTubePlayer.cueVideo(youtubeAddress.replace("https://youtu.be/", ""))
//
//            }
//
//            override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
//
//            }

            override fun onInitializationSuccess(youTubeThumbnailView: YouTubeThumbnailView?, youTubeThumbnailLoader: YouTubeThumbnailLoader?) {

                youTubeThumbnailLoader?.setVideo(youtubeAddress)

            }

            override fun onInitializationFailure(p0: YouTubeThumbnailView?, p1: YouTubeInitializationResult?) {

            }

        }

        youTubePlayerView.initialize(YoutubeDataStructure.Youtube_Key, initializedListener)

    }

}