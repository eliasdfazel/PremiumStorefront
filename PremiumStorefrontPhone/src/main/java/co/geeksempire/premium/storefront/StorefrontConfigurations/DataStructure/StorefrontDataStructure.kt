/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/18/21 1:54 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure

/**
 * @param applicationCoverLink : Second Image Of Product Gallery
 **/
data class StorefrontFeaturedProductData (var applicationName: String, var applicationDescription: String, var applicationSummary: String,
                                          var applicationIconLink: String, var applicationCoverLink: String, var applicationYoutubeIntroductionLink: String,
                                          var applicationPackageName: String, var applicationAndroidCapabilities: String = "Unknown",
                                          var applicationRating: String,
                                          var applicationDeveloperWebsite: String, var applicationDeveloperEmail: String,
                                          var applicationDeveloperName: String, var applicationDeveloperCountry: String,  var applicationDeveloperState: String,  var applicationDeveloperCity: String,
                                          var applicationContentSafetyRating: String)