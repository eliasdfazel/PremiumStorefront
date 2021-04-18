/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/18/21 2:50 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure

object StorefrontFeaturedContentKey {
    const val ApplicationName = "name"
    const val ApplicationDescription = "description"
    const val ApplicationSummary = "short_description"

    const val ProductImages = "images"
}

/**
 *
 * @param applicationIconLink : First Image Of Product Gallery from JsonArray "images"
 * @param applicationCoverLink : Second Image Of Product Gallery from JsonArray "images"
 *
 * @param applicationPrice : Html Price
 **/
data class StorefrontFeaturedContentsData (var applicationName: String, var applicationDescription: String, var applicationSummary: String,

                                           var applicationIconLink: String, var applicationCoverLink: String,

                                           var applicationPackageName: String, var applicationAndroidCapabilities: String = "Unknown",
                                           var applicationRating: String,
                                           var applicationYoutubeIntroductionLink: String,
                                           var applicationDeveloperWebsite: String, var applicationDeveloperEmail: String,
                                           var applicationDeveloperName: String, var applicationDeveloperCountry: String, var applicationDeveloperState: String, var applicationDeveloperCity: String,
                                           var applicationPrice: String,
                                           var applicationContentSafetyRating: String)