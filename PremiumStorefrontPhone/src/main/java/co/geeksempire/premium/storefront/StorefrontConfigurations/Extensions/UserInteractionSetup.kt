/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/7/21, 12:16 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountSignIn
import co.geeksempire.premium.storefront.AccountManager.UserInterface.AccountInformation
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.FavoriteProducts
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import com.google.android.material.snackbar.Snackbar

fun Storefront.userInteractionSetup() {

    storefrontLayoutBinding.profileView.setOnClickListener {

        if (firebaseUser == null) {

            accountSelector.launch(AccountSignIn.GoogleSignInRequestCode)

        } else {

            startActivity(Intent(applicationContext, AccountInformation::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_in_right, R.anim.slide_out_left).toBundle())

        }

    }

    storefrontLayoutBinding.preferencesView.setOnClickListener {

        SnackbarBuilder(applicationContext).show (
            rootView = storefrontLayoutBinding.rootView,
            messageText= "Coming Soon... Follow Us To Get Notified",
            messageDuration = Snackbar.LENGTH_INDEFINITE,
            actionButtonText = R.string.followUsText,
            snackbarActionHandlerInterface = object : SnackbarActionHandlerInterface {

                override fun onActionButtonClicked(snackbar: Snackbar) {
                    super.onActionButtonClicked(snackbar)

                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebookLink)))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                    snackbar.dismiss()

                }

            }
        )

    }

    storefrontLayoutBinding.favoritesView.setOnClickListener {

        startActivity(
            Intent(this@userInteractionSetup, FavoriteProducts::class.java),
            ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_from_right, R.anim.slide_out_left).toBundle())

    }

}