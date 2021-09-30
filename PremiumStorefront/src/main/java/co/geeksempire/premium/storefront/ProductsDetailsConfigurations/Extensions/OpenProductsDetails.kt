/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 9/30/21, 8:56 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.ProductsDetailsConfigurations.Extensions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataStructure
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun openFirestoreProductsDetails(context: AppCompatActivity, fragmentInterface: FragmentInterface,
                                 contentDetailsContainer: FragmentContainerView, productDetailsFragment: ProductDetailsFragment,
                                 productDataStructure: ProductDataStructure) = CoroutineScope(Dispatchers.Main).launch {

    delay(333)

    productDetailsFragment.apply {
        this@apply.instanceOfProductDetailsFragment = productDetailsFragment
        instanceOfFragmentInterface = fragmentInterface
        isShowing = true
    }

    productDetailsFragment.arguments = Bundle().apply {
        putString(ProductDataKey.ProductDeveloper, productDataStructure.productDeveloperName())

        putString(ProductDataKey.ProductId, productDataStructure.productPackageName())
        putString(ProductDataKey.ProductPackageName, productDataStructure.productPackageName())

        putInt(ProductDataKey.ProductCategoryId, productDataStructure.productCategoryId())
        putString(ProductDataKey.ProductCategoryName, productDataStructure.productCategoryName())

        putString(ProductDataKey.ProductName, productDataStructure.productName())
        putString(ProductDataKey.ProductSummary, productDataStructure.productSummary())
        putString(ProductDataKey.ProductDescription, productDataStructure.productDescription())

        putString(ProductDataKey.ProductIcon, productDataStructure.productIcon())
        putString(ProductDataKey.ProductCoverImage, productDataStructure.productCoverImage())
        putString(ProductDataKey.ProductYoutubeIntroduction, productDataStructure.productYoutubeIntroduction())

        putString(ProductDataKey.ProductDeveloperCountry, productDataStructure.softwareDeveloperCountry())
        putString(ProductDataKey.ProductDeveloperCity, productDataStructure.softwareDeveloperCity())

        putString(ProductDataKey.ProductDeveloperEmail, productDataStructure.softwareDeveloperEmail())
    }

    context.supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        .replace(R.id.contentDetailsContainer, productDetailsFragment, "Product Details")
        .commit()

}

fun openProductsDetails(context: AppCompatActivity, fragmentInterface: FragmentInterface,
                        contentDetailsContainer: FragmentContainerView, productDetailsFragment: ProductDetailsFragment,
                        storefrontContents: StorefrontContentsData) = CoroutineScope(Dispatchers.Main).launch {

    delay(333)

    productDetailsFragment.apply {
        this@apply.instanceOfProductDetailsFragment = productDetailsFragment
        instanceOfFragmentInterface = fragmentInterface
        isShowing = true
    }

    productDetailsFragment.arguments = Bundle().apply {
        putString(ProductDataKey.ProductDeveloper, storefrontContents.productAttributes[ProductsContentKey.AttributesDeveloperNameKey])

        putString(ProductDataKey.ProductId, storefrontContents.productAttributes[ProductsContentKey.AttributesPackageNameKey])
        putString(ProductDataKey.ProductPackageName, storefrontContents.productAttributes[ProductsContentKey.AttributesPackageNameKey])

        putInt(ProductDataKey.ProductCategoryId, storefrontContents.productCategoryId)
        putString(ProductDataKey.ProductCategoryName, storefrontContents.productCategoryName)

        putString(ProductDataKey.ProductName, storefrontContents.productName)
        putString(ProductDataKey.ProductSummary, storefrontContents.productSummary)
        putString(ProductDataKey.ProductDescription, storefrontContents.productDescription)

        putString(ProductDataKey.ProductIcon, storefrontContents.productIconLink)
        putString(ProductDataKey.ProductCoverImage, storefrontContents.productCoverLink)
        putString(ProductDataKey.ProductYoutubeIntroduction, storefrontContents.productAttributes[ProductsContentKey.AttributesYoutubeIntroductionKey])

        putString(ProductDataKey.ProductDeveloperCountry, storefrontContents.productAttributes[ProductsContentKey.AttributesDeveloperCountryKey])
        putString(ProductDataKey.ProductDeveloperCity, storefrontContents.productAttributes[ProductsContentKey.AttributesDeveloperCityKey])

        putString(ProductDataKey.ProductDeveloperEmail, storefrontContents.productAttributes[ProductsContentKey.AttributesDeveloperEmailKey])
    }

    context.supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        .replace(R.id.contentDetailsContainer, productDetailsFragment, "Product Details")
        .commit()

}