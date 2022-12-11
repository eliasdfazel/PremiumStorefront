package co.geeksempire.premium.storefront.Utils.Operations

import android.os.Build
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

interface NavigationListener {
    fun backNavigation()
}
class NavigationOperations (private val activity: AppCompatActivity) {

    fun listenBackPressed(navigationListener: NavigationListener) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            activity.onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {

                navigationListener.backNavigation()

            }

        } else {

            activity.onBackPressedDispatcher.addCallback(activity, object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {

                    navigationListener.backNavigation()

                }

            })

        }

    }

}