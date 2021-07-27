/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/10/21, 10:05 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.ready.keep.notes.Invitations.Utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.R

class ShareIt (val context: AppCompatActivity) {

    fun invokeTextSharing(shareText: String) {

        val shareIntent: Intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, shareText)
            addCategory(Intent.CATEGORY_DEFAULT)
            type = "text/plain"
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.inviteTitle)))

    }

}