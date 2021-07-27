/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/26/21, 7:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.InApplicationReview

import androidx.datastore.preferences.core.booleanPreferencesKey
import co.geeksempire.premium.storefront.Database.Preferences.PreferencesIO
import com.google.android.play.core.review.ReviewInfo
import kotlinx.coroutines.flow.Flow

object Review {
    const val ReviewSubmitted = "ReviewSubmitted"
}

interface ReviewInterface {
    fun reviewSubmitted(reviewInfo: ReviewInfo) {}
}

class ReviewUtils (private val preferencesIO: PreferencesIO) {

    suspend fun reviewSubmitted(inputValue: Boolean) {

        preferencesIO.savePreferences(booleanPreferencesKey(Review.ReviewSubmitted), inputValue)

    }

    fun reviewSubmitted() : Flow<Boolean> {

        return preferencesIO.readPreferencesBoolean(booleanPreferencesKey(Review.ReviewSubmitted), false)
    }

}