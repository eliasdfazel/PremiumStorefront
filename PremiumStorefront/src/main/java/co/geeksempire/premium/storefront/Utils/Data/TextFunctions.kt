/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 8:32 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Data

import android.graphics.Paint
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView

fun generateHashTag(inputText: String) : String {

    val hashTagBuilder = StringBuilder()

    inputText.split(" ").forEachIndexed { index, text ->

        hashTagBuilder.append("#${text}" + " ")

    }

    return hashTagBuilder.toString()
}

fun generatePassword(inputText: String) : String {

    val charList = inputText.reversed().toList().shuffled()

    val passwordBuilder = StringBuilder()

    charList.forEachIndexed { index, text ->

        passwordBuilder.append("$text")

    }

    return passwordBuilder.toString()
}

fun String.widthOfText(textSize: Float) : Float {

    val paint = Paint()
    paint.textSize = textSize

    return paint.measureText(this@widthOfText, 0, this@widthOfText.length)
}

fun renderedText(textView: AppCompatTextView, startLine: Int, endLine: Int): String {

    val displayedText = textView.text.toString().substring(textView.layout.getLineStart(startLine), textView.layout.getEllipsisCount(endLine))
    Log.d("Rendered Text", displayedText)

    return displayedText
}