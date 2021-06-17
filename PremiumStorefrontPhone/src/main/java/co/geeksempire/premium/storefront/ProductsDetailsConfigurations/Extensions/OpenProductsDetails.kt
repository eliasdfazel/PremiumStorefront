/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/17/21, 2:23 PM
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
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun openProductsDetails(context: AppCompatActivity, fragmentInterface: FragmentInterface,
                        contentDetailsContainer: FragmentContainerView, productDetailsFragment: ProductDetailsFragment,
                        storefrontContents: StorefrontContentsData) = CoroutineScope(Dispatchers.Main).launch {

    delay(333)

    contentDetailsContainer.visibility = View.VISIBLE
    contentDetailsContainer.bringToFront()

    productDetailsFragment.apply {
        this@apply.instanceOfProductDetailsFragment = productDetailsFragment
        instanceOfFragmentInterface = fragmentInterface
        isShowing = true
    }

    productDetailsFragment.arguments = Bundle().apply {
        putString(ProductDataKey.ProductDeveloper, storefrontContents.productAttributes[ProductsContentKey.AttributesDeveloperNameKey])

        putString(ProductDataKey.ProductId, storefrontContents.productAttributes[ProductsContentKey.AttributesPackageNameKey])
        putString(ProductDataKey.ProductPackageName, storefrontContents.productAttributes[ProductsContentKey.AttributesPackageNameKey])

        putString(ProductDataKey.ProductName, storefrontContents.productName)
        putString(ProductDataKey.ProductSummary, storefrontContents.productSummary)
        putString(ProductDataKey.ProductDescription, storefrontContents.productDescription)

        putString(ProductDataKey.ProductIcon, storefrontContents.productIconLink)
        putString(ProductDataKey.ProductCoverImage, storefrontContents.productCoverLink)
        putString(ProductDataKey.ProductYoutubeIntroduction, storefrontContents.productAttributes[ProductsContentKey.AttributesYoutubeIntroductionKey])
    }

    context.supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        .replace(R.id.contentDetailsContainer, productDetailsFragment, "Product Details")
        .commit()

}