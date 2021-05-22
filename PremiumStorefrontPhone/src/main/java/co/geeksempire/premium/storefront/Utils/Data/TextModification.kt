/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/22/21, 9:32 AM
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

    println(">>> " + hashTagBuilder.toString())

    return hashTagBuilder.toString()
}