/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/15/21, 10:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.ProductsDetailsConfigurations.Extensions

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun openProductsDetails(context: AppCompatActivity,
                        contentDetailsContainer: FragmentContainerView, productDetailsFragment: ProductDetailsFragment,
                        storefrontContents: ArrayList<StorefrontContentsData>, position: Int) = CoroutineScope(Dispatchers.Main).launch {

    delay(333)

    contentDetailsContainer.visibility = View.VISIBLE

    productDetailsFragment.apply {
        storefrontInstance = if (context is Storefront) {
            context as Storefront
        } else {
            null
        }
        isShowing = true
    }

    productDetailsFragment.arguments = Bundle().apply {
        putString(ProductDataKey.ProductId, storefrontContents[position].productAttributes[ProductsContentKey.AttributesPackageNameKey])
        putString(ProductDataKey.ProductPackageName, storefrontContents[position].productAttributes[ProductsContentKey.AttributesPackageNameKey])

        putString(ProductDataKey.ProductName, storefrontContents[position].productName)
        putString(ProductDataKey.ProductSummary, storefrontContents[position].productSummary)
        putString(ProductDataKey.ProductDescription, storefrontContents[position].productDescription)

        putString(ProductDataKey.ProductIcon, storefrontContents[position].productIconLink)
        putString(ProductDataKey.ProductCoverImage, storefrontContents[position].productCoverLink)
        putString(ProductDataKey.ProductYoutubeIntroduction, storefrontContents[position].productAttributes[ProductsContentKey.AttributesYoutubeIntroductionKey])
    }

    context.supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        .replace(R.id.contentDetailsContainer, productDetailsFragment, "Product Details")
        .commit()

}