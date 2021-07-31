/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/31/21, 1:07 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure

import androidx.annotation.Keep

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

    const val AttributesPackageNameKey = "Software Package Name"

    const val AttributesAndroidCompatibilitiesKey = "Android Compatibilies"
    const val AttributesContentSafetyRatingKey = "Content Safety Rating"

    const val AttributesDeveloperEmailKey = "Software Developer Email"
    const val AttributesDeveloperCountryKey = "Software Developer Country"
    const val AttributesDeveloperStateKey = "Software Developer State"

    const val AttributesDeveloperCityKey = "Software Developer City"
    const val AttributesDeveloperNameKey = "Software Developer Name"
    const val AttributesDeveloperWebsiteKey = "Software Developer Website"

    const val AttributesRatingKey = "Rating"
    const val AttributesYoutubeIntroductionKey = "Youtube Introduction"

    const val AttributesVerticalArtKey = "Vertical Art"
}

object ProductDataKey {
    const val ProductDeveloper = "ProductDeveloper"

    const val ProductId = "ProductId"
    const val ProductPackageName = "ProductPackageName"

    const val ProductCategoryId = "ProductCategoryId"
    const val ProductCategoryName = "ProductCategory"

    const val ProductCoverImage = "ProductCoverImage"
    const val ProductIcon = "ProductIcon"

    const val ProductName = "ProductName"
    const val ProductSummary = "ProductSummary"
    const val ProductDescription = "ProductDescription"

    const val ProductYoutubeIntroduction = "YoutubeIntroduction"

    const val ProductDeveloperCountry = "ProductDeveloperCountry"
    const val ProductDeveloperCity = "ProductDeveloperCity"

    const val ProductDeveloperEmail = "ProductDeveloperEmail"
}

object StorefrontContentsSerialize {
    const val PackageName: String = "Software Package Name"
    const val DeveloperName: String = "Software Developer Name"

    const val DeveloperCity: String = "Software Developer City"
    const val DeveloperCountry: String = "Software Developer Country"
    const val DeveloperState: String = "Software Developer State"

    const val DeveloperEmail: String = "Software Developer mail"
    const val DeveloperWebsite: String = "Software Developer Website"

    const val Rating: String = "Rating"
    const val ContentSafetyRating: String = "Content Safety Rating"

    const val AndroidCompatibilies: String = "Android Compatibilies"

    const val YoutubeIntroduction: String = "Youtube Introduction"
    const val VerticalArt: String = "Vertical Art"

    const val ProductName: String = "productName"
    const val ProductDescription: String = "productDescription"
    const val ProductSummary: String = "productSummary"

    const val ProductCategoryId: String = "productCategoryId"
    const val ProductCategoryName: String = "ProductCategoryName"

    const val ProductCoverLink: String = "productCoverLink"
    const val ProductCreatedData: String = "productCreatedData"
    const val ProductIconLink: String = "productIconLink"

    const val ProductPrice: String = "productPrice"
    const val ProductSalePrice: String = "productSalePrice"

    const val UniqueRecommendation: String = "uniqueRecommendation"
}


/**
 *
 * @param productIconLink : First Image Of Product Gallery from JsonArray "images"
 * @param productCoverLink : Second Image Of Product Gallery from JsonArray "images"
 *
 **/
@Keep
data class StorefrontContentsData (var productName: String, var productDescription: String, var productSummary: String,
                                   var productCategoryName: String, var productCategoryId: Int,
                                   var productIconLink: String, var productCoverLink: String?,
                                   var productPrice: String,
                                   var productSalePrice: String,
                                   var uniqueRecommendation: Boolean = false,
                                   var productAttributes: HashMap<String, String?>,
                                   var installViewText: String = "Install Now")

@Keep
data class StorefrontCategoriesData(var categoryId: Int, var categoryName: String, var categoryIconLink: String, var selectedCategory: Boolean = false)