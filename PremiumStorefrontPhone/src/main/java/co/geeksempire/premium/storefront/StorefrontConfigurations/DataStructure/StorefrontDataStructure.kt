/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/17/21, 2:23 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure

object ProductsContentKey {
    const val IdKey = "id"

    const val NameKey = "name"
    const val DescriptionKey = "description"
    const val SummaryKey = "short_description"

    const val RegularPriceKey = "regular_price"
    const val SalePriceKey = "sale_price"

    const val CategoriesKey = "categories"
    const val TagsKey = "tags"

    const val ImagesKey = "images"
    const val ImageKey = "image"
    const val ImageSourceKey = "src"

    const val AttributesKey = "attributes"
    const val AttributeOptionsKey = "options"

    const val AttributesPackageNameKey = "Package Name"

    const val AttributesAndroidCompatibilitiesKey = "Android Compatibilies"
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

object ProductDataKey {
    const val ProductDeveloper = "ProductDeveloper"

    const val ProductId = "ProductId"
    const val ProductPackageName = "ProductPackageName"

    const val ProductCoverImage = "ProductCoverImage"
    const val ProductIcon = "ProductIcon"

    const val ProductName = "ProductName"
    const val ProductSummary = "ProductSummary"
    const val ProductDescription = "ProductDescription"

    const val ProductYoutubeIntroduction = "YoutubeIntroduction"
}

/**
 *
 * @param productIconLink : First Image Of Product Gallery from JsonArray "images"
 * @param productCoverLink : Second Image Of Product Gallery from JsonArray "images"
 *
 **/
data class StorefrontContentsData (var productName: String, var productDescription: String, var productSummary: String,
                                   var productCategory: String,
                                   var productIconLink: String, var productCoverLink: String?,
                                   var productPrice: String,
                                   var productSalePrice: String,
                                   var productAttributes: HashMap<String, String>,
                                   var installViewText: String = "Install Now")

data class StorefrontCategoriesData(var categoryId: Long, var categoryName: String, var categoryIconLink: String, var selectedCategory: Boolean = false)