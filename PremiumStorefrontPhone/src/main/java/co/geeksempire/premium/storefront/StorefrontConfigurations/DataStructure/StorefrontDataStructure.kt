/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/18/21 3:48 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure

object StorefrontFeaturedContentKey {
    const val NameKey = "name"
    const val DescriptionKey = "description"
    const val SummaryKey = "short_description"

    const val RegularPriceKey = "regular_price"
    const val SalePriceKey = "sale_price"

    const val CategoriesKey = "categories"
    const val TagsKey = "tags"

    const val ImagesKey = "images"
    const val ImageSourceKey = "src"

    const val AttributesKey = "attributes"
    const val AttributeOptionsKey = "options"

    const val AttributesPackageNameKey = "Package Name"

    const val AttributesAndroidCompatibiliesKey = "Android Compatibilies"
    const val AttributesContentSafetyRatingKey = "Content Safety Rating"

    const val AttributesDeveloperEmailKey = "Developer Email"
    const val AttributesDeveloperCountryKey = "Developer Country"
    const val AttributesDeveloperStateKey = "Developer State"

    const val AttributesDeveloperCityKey = "Developer City"
    const val AttributesDeveloperNameKey = "Developer Name"
    const val AttributesDeveloperWebsiteKey = "Developer Website"

    const val AttributesRatingKey = "Rating"
    const val AttributesYoutubeIntroductionKey = "Youtube Introduction"

}

/**
 *
 * @param productIconLink : First Image Of Product Gallery from JsonArray "images"
 * @param productCoverLink : Second Image Of Product Gallery from JsonArray "images"
 *
 **/
data class StorefrontFeaturedContentsData (var productName: String, var productDescription: String, var productSummary: String,
                                           var productIconLink: String, var productCoverLink: String,
                                           var productPrice: String,
                                           var productSalePrice: String,
                                           var productAttributes: HashMap<String, String>)