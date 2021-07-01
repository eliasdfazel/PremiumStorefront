/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/1/21, 10:11 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.SignInProcess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.Utils.Data.generatePassword
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

@Keep
data class AccountData(var usernameId: String, var userEmailAddress: String, var userPassword: String)

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

                            it.user?.let { currentUser ->

                                firebaseUser = it.user

                                signInInterface.signInProcessSucceed(it)

                                createGeeksEmpireUser(currentUser)

                            }

                        }.addOnFailureListener {

                            signInInterface.signInProcessFailed(it.cause?.message)

                        }

                }.addOnFailureListener {

                    signInInterface.signInProcessFailed(it.cause?.message)

                }

                return if (resultCode == Activity.RESULT_OK) {

                    result

                } else {

                    null

                }
            }

        }
    }

    private fun createGeeksEmpireUser(firebaseUser: FirebaseUser) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val endpointAddress = "https://geeksempire.co/wp-json/wp/v2/users/register"

        val serverUrl = URL(endpointAddress)

        val connection = serverUrl.openConnection() as HttpsURLConnection
        connection.requestMethod = "POST"
        connection.connectTimeout = 3000
        connection.doOutput = true

        val usernameId = firebaseUser.email!!.split("@").first()
        val userEmailAddress = firebaseUser.email
        val userPassword = generatePassword(firebaseUser.uid)

        val inputData = JSONObject("{" +
                "\"username\": \"${usernameId}\"," +
                "\"email\": \"${userEmailAddress}\"," +
                "\"password\": \"${userPassword}\"" +
                "}").toString()

        val postData: ByteArray = inputData.toByteArray(StandardCharsets.UTF_8)

        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Content-length", postData.size.toString())
        connection.setRequestProperty("Content-Type", "application/json")

        try {

            val outputStream: DataOutputStream = DataOutputStream(connection.outputStream)

            outputStream.write(postData)
            outputStream.flush()

        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
            Log.d(this@AccountSignIn.javaClass.simpleName, "Geeks Empire User Created")

            signInInterface.userCreated(AccountData(usernameId, userEmailAddress, userPassword))

        }

    }

}