/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/2/21, 2:30 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.SignInProcess

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

interface SignInInterface {
    fun signInProcessSucceed() {
        Log.d(this@SignInInterface.javaClass.simpleName, "Sign In Process Succeed")
    }

    fun signInProcessSucceed(firebaseAuthentication: FirebaseAuth) {
        Log.d(this@SignInInterface.javaClass.simpleName, "Sign In Process Succeed")
    }

    fun signInProcessSucceed(authenticationResult: AuthResult) {
        Log.d(this@SignInInterface.javaClass.simpleName, "Sign In Process Succeed")
    }

    fun userCreated(accountData: AccountData) {
        Log.d(this@SignInInterface.javaClass.simpleName, "User Created Successfully")

    }

    fun signInProcessFailed(error: String?) {
        Log.d(this@SignInInterface.javaClass.simpleName, "Sign In Process Failed: ${error}")
    }
}