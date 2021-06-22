/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/22/21, 1:38 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.UserInterface.Extensions

import android.text.Html
import android.view.Gravity
import android.view.Menu
import androidx.appcompat.widget.PopupMenu
import co.geeksempire.premium.storefront.AccountManager.UserInterface.AccountInformation
import co.geeksempire.premium.storefront.BuiltInBrowserConfigurations.UserInterface.BuiltInBrowser
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Data.resizeDrawable
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MoreOptions(private val context: AccountInformation) {

    object MenuItemIdentifier {
        const val PrivacyAgreementItem = 0
        const val SignOutItem = 1
    }

    fun setup() {

        context.accountInformationLayoutBinding.moreOptions.setOnClickListener {

            val popupMenu = PopupMenu(context, it, Gravity.CENTER, 0, R.style.GeeksEmpire_Material)

            popupMenu.menu.add(Menu.NONE, 0, 0,
                Html.fromHtml("<font color='" + context.getColor(R.color.light) + "'>" + context.getString(R.string.privacyAgreement) + "</font>", Html.FROM_HTML_MODE_COMPACT))

            popupMenu.menu.add(Menu.NONE, 1, 1,
                Html.fromHtml("<font color='" + context.getColor(R.color.light) + "'>" + context.getString(R.string.signOutText) + "</font>", Html.FROM_HTML_MODE_COMPACT))
                .icon = context.getDrawable(R.drawable.not_login_icon)?.resizeDrawable(context, 77, 77)

            try {
                val fields = popupMenu.javaClass.declaredFields
                for (field in fields) {
                    if ("mPopup" == field.name) {
                        field.isAccessible = true
                        val menuPopupHelper = field[popupMenu]
                        val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                        val setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon",
                            Boolean::class.javaPrimitiveType
                        )
                        setForceIcons.invoke(menuPopupHelper, true)
                        break
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }

            popupMenu.setOnMenuItemClickListener { menuItem ->

                when (menuItem?.itemId) {
                    MenuItemIdentifier.PrivacyAgreementItem -> {

                        BuiltInBrowser.show(
                            context = context,
                            linkToLoad = context.getString(R.string.privacyAgreementLink),
                            gradientColorOne = context.getColor(R.color.default_color_dark),
                            gradientColorTwo = context.getColor(R.color.default_color_game_dark)
                        )

                        true
                    }
                    MenuItemIdentifier.SignOutItem -> {

                        SnackbarBuilder(context).show(
                            rootView = context.accountInformationLayoutBinding.rootView,
                            messageText = context.getString(R.string.signOutConfirmText),
                            messageDuration = Snackbar.LENGTH_INDEFINITE,
                            actionButtonText = R.string.signOutText,
                            snackbarActionHandlerInterface = object : SnackbarActionHandlerInterface {

                                override fun onActionButtonClicked(snackbar: Snackbar) {
                                    super.onActionButtonClicked(snackbar)

                                    Firebase.auth.signOut()

                                }

                            }
                        )

                        true
                    }
                    else -> {
                        false
                    }
                }
            }

            popupMenu.show()

        }

    }

}