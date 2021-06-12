/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 11:16 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.Extensions

import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountSignIn
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoriteProductQueryInterface
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import com.google.android.material.snackbar.Snackbar

fun ProductDetailsFragment.startFavoriteProcess(productId: String, productName: String, productDescription: String) {

    if (storefrontInstance?.accountSignIn!!.firebaseUser != null) {

        favoritedProcess.isProductFavorited(storefrontInstance?.accountSignIn!!.firebaseUser!!.uid, productId,
            object : FavoriteProductQueryInterface {

                override fun favoriteProduct(isProductFavorited: Boolean) {
                    super.favoriteProduct(isProductFavorited)

                    if (isProductFavorited) {

                        if (networkCheckpoint.networkConnection()) {

                            favoritedProcess.remove(userUniqueIdentifier = storefrontInstance?.accountSignIn!!.firebaseUser!!.uid, productId)

                            productDetailsLayoutBinding.favoriteView.setImageDrawable(requireContext().getDrawable(
                                R.drawable.favorite_icon))

                        } else {

                            SnackbarBuilder(requireContext()).show (
                                rootView = productDetailsLayoutBinding.rootViewFragment,
                                messageText= getString(R.string.noNetworkConnection),
                                messageDuration = Snackbar.LENGTH_INDEFINITE,
                                actionButtonText = R.string.retryText,
                                snackbarActionHandlerInterface = object :
                                    SnackbarActionHandlerInterface {

                                    override fun onActionButtonClicked(snackbar: Snackbar) {
                                        super.onActionButtonClicked(snackbar)

                                        if (networkCheckpoint.networkConnection()) {

                                            favoritedProcess.remove(userUniqueIdentifier = storefrontInstance?.accountSignIn!!.firebaseUser!!.uid, productId)

                                            productDetailsLayoutBinding.favoriteView.setImageDrawable(requireContext().getDrawable(
                                                R.drawable.favorite_icon))

                                            snackbar.dismiss()

                                        } else {



                                        }

                                    }

                                }
                            )

                        }

                    } else {

                        if (networkCheckpoint.networkConnection()) {

                            favoritedProcess.add(userUniqueIdentifier = storefrontInstance?.accountSignIn!!.firebaseUser!!.uid,
                                productId, productName, productDescription)

                            productDetailsLayoutBinding.favoriteView.setImageDrawable(requireContext().getDrawable(
                                R.drawable.favorited_icon))

                        } else {

                            SnackbarBuilder(requireContext()).show (
                                rootView = productDetailsLayoutBinding.rootViewFragment,
                                messageText= getString(R.string.noNetworkConnection),
                                messageDuration = Snackbar.LENGTH_INDEFINITE,
                                actionButtonText = R.string.retryText,
                                snackbarActionHandlerInterface = object :
                                    SnackbarActionHandlerInterface {

                                    override fun onActionButtonClicked(snackbar: Snackbar) {
                                        super.onActionButtonClicked(snackbar)

                                        if (networkCheckpoint.networkConnection()) {

                                            favoritedProcess.add(userUniqueIdentifier = storefrontInstance?.accountSignIn!!.firebaseUser!!.uid,
                                                productId, productName, productDescription)

                                            productDetailsLayoutBinding.favoriteView.setImageDrawable(requireContext().getDrawable(
                                                R.drawable.favorited_icon))

                                            snackbar.dismiss()

                                        } else {



                                        }

                                    }

                                }
                            )

                        }

                    }

                }

            })


    } else {

        storefrontInstance?.accountSelector!!.launch(AccountSignIn.GoogleSignInRequestCode)

    }

}