/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/28/21, 2:49 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.SignInProcess

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountSignIn (val context: AppCompatActivity, val signInInterface: SignInInterface) {

    val firebaseAuth: FirebaseAuth = Firebase.auth

    var firebaseUser: FirebaseUser? = firebaseAuth.currentUser

    companion object {
        private const val ServerClientId = "666238092273-cqggauj6krqbfogu9fkf0pcem5sru9g9.apps.googleusercontent.com"

        const val GoogleSignInRequestCode = 103
    }

    fun createProcess() : ActivityResultContract<Any?, Intent?> {

        return object : ActivityResultContract<Any?, Intent?>() {

            override fun createIntent(context: Context, input: Any?) : Intent {

                val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(ServerClientId)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

                return googleSignInClient.signInIntent
            }

            override fun parseResult(resultCode: Int, result: Intent?) : Intent? {

                val googleSignInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(result)
                googleSignInAccountTask.addOnSuccessListener {

                    val googleSignInAccount = googleSignInAccountTask.getResult(ApiException::class.java)

                    val authenticationCredential = GoogleAuthProvider.getCredential(googleSignInAccount?.idToken, null)

                    firebaseAuth.signInWithCredential(authenticationCredential)
                        .addOnSuccessListener {

                            firebaseUser = it.user

                            signInInterface.signInProcessSucceed(it)

                        }.addOnFailureListener {

                            signInInterface.signInProcessFailed(it.cause?.message)

                        }

                }

                return if (resultCode == Activity.RESULT_OK) {

                    result

                } else {

                    null

                }
            }

        }
    }

}