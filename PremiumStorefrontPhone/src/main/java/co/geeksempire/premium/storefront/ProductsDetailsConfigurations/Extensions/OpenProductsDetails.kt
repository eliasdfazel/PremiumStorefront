/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/15/21, 9:18 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.ProductsDetailsConfigurations.Extensions

import android.os.Bundle
import android.view.View
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import com.abanabsalan.aban.magazine.Utils.System.hideKeyboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun openProductsDetails(context: Storefront, storefrontContents: ArrayList<StorefrontContentsData>, position: Int) = CoroutineScope(Dispatchers.Main).launch {

    hideKeyboard(context, context.storefrontLayoutBinding.searchView)

    delay(333)

    context.storefrontLayoutBinding.contentDetailsContainer.visibility = View.VISIBLE

    context.productDetailsFragment.apply {
        storefrontInstance = context
        isShowing = true
    }

    context.productDetailsFragment.arguments = Bundle().apply {
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
        .replace(R.id.contentDetailsContainer, context.productDetailsFragment, "Product Details")
        .commit()

}