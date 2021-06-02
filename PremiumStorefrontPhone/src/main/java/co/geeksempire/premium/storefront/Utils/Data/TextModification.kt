/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/2/21, 2:48 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Data

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