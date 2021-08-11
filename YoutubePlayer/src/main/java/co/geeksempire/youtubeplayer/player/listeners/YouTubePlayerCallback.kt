/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 8:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.youtubeplayer.player.listeners

import co.geeksempire.youtubeplayer.player.YouTubePlayer

interface YouTubePlayerCallback {
    fun onYouTubePlayer(youTubePlayer: YouTubePlayer)
}