/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 8:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

@file:JvmName("YouTubePlayerUtils")
package co.geeksempire.youtubeplayer.player.utils

import androidx.lifecycle.Lifecycle
import co.geeksempire.youtubeplayer.player.YouTubePlayer

/**
 * Calls [YouTubePlayer.cueVideo] or [YouTubePlayer.loadVideo] depending on which one is more appropriate.
 * If it can't decide, calls [YouTubePlayer.cueVideo] by default.
 *
 * In most cases you want to avoid calling [YouTubePlayer.loadVideo] if the Activity/Fragment is not in the foreground.
 * This function automates these checks for you.
 * @param lifecycle the lifecycle of the Activity or Fragment containing the YouTubePlayerView.
 * @param videoId id of the video.
 * @param startSeconds the time from which the video should start playing.
 */
fun YouTubePlayer.loadOrCueVideo(lifecycle: Lifecycle, videoId: String, startSeconds: Float) {
    loadOrCueVideo(lifecycle.currentState == Lifecycle.State.RESUMED, videoId, startSeconds)
}


@JvmSynthetic internal fun YouTubePlayer.loadOrCueVideo(canLoad: Boolean, videoId: String, startSeconds: Float) {
    if (canLoad)
        loadVideo(videoId, startSeconds)
    else
        cueVideo(videoId, startSeconds)
}