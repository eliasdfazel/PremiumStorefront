/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/18/21 3:26 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure

object StorefrontFeaturedContentKey {
    const val NameKey = "name"
    const val DescriptionKey = "description"
    const val SummaryKey = "short_description"

    const val CategoriesKey = "categories"
    const val TagsKey = "tags"

    const val ImagesKey = "images"
    const val ImageSourceKey = "src"

    const val AttributesKey = "attributes"
    const val AttributeOptionsKey = "options"
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