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

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.R
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import kotlinx.coroutines.launch

class InApplicationReviewProcess (private val context: AppCompatActivity) {

    private val reviewUtils: ReviewUtils by lazy {
        ReviewUtils((context.application as PremiumStorefrontApplication).preferencesIO)
    }

    private val reviewManager = ReviewManagerFactory.create(context)

    private val fakeReviewManager = FakeReviewManager(context)

    fun start(reviewInterface: ReviewInterface) {

        val requestReviewFlow = if (BuildConfig.DEBUG) {
            Log.d(this@InApplicationReviewProcess.javaClass.simpleName, "Fake Review Flow Invoked")

            fakeReviewManager.requestReviewFlow()

        } else {
            Log.d(this@InApplicationReviewProcess.javaClass.simpleName, "Real Review Flow Invoked")

            reviewManager.requestReviewFlow()

        }

        requestReviewFlow.addOnSuccessListener { reviewInfo ->

            val reviewFlow = if (BuildConfig.DEBUG) {
                fakeReviewManager.launchReviewFlow(context, reviewInfo)
            } else {
                reviewManager.launchReviewFlow(context, reviewInfo)
            }

            reviewFlow.addOnSuccessListener {
                Log.d(this@InApplicationReviewProcess.javaClass.simpleName, "Real Review Flow Showed Successfully")

                context.lifecycleScope.launch {

                    reviewUtils.reviewSubmitted(true)

                    reviewInterface.reviewSubmitted(reviewInfo)

                }

            }.addOnFailureListener {

                Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(context.getString(R.string.premiumStorefrontPlayStoreLink))
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this@apply)
                }

            }

        }.addOnFailureListener {
            Log.d(this@InApplicationReviewProcess.javaClass.simpleName, "In Application Review Process Error ${it.printStackTrace().toString()}")

            Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(context.getString(R.string.premiumStorefrontPlayStoreLink))
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this@apply)
            }

        }

    }

}