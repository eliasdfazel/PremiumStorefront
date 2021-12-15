/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/15/21, 8:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.Adapter

import android.text.Html
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.AdvancedSearch.DataStructure.CompleteSearchContent
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.CompleteSearch
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.ViewHolder.CompleteSearchViewHolder
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
import co.geeksempire.premium.storefront.databinding.CompleteSearchLayoutItemBinding

class CompleteSearchAdapter (private val context: CompleteSearch, private val themeType: Boolean) : RecyclerView.Adapter<CompleteSearchViewHolder>() {

    val completeSearchResultsItems: ArrayList<CompleteSearchContent> = ArrayList<CompleteSearchContent>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CompleteSearchViewHolder {

        return CompleteSearchViewHolder(CompleteSearchLayoutItemBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun getItemCount(): Int {

        return completeSearchResultsItems.size
    }

    override fun onBindViewHolder(completeSearchViewHolder: CompleteSearchViewHolder, position: Int) {

        when (themeType) {
            ThemeType.ThemeLight -> {



            }
            ThemeType.ThemeDark -> {



            }
        }

        when (completeSearchResultsItems[position].searchResultType) {
            GeneralEndpoints.QueryType.ApplicationsQuery -> {

                completeSearchViewHolder.blurryBackground.setOverlayColor(setColorAlpha(context.getColor(R.color.applicationsSectionColor), 173f))

                completeSearchViewHolder.searchQueryType.setImageDrawable(context.getDrawable(R.drawable.applications_icon))

            }
            GeneralEndpoints.QueryType.GamesQuery -> {

                completeSearchViewHolder.blurryBackground.setOverlayColor(setColorAlpha(context.getColor(R.color.gamesSectionColor), 173f))

                completeSearchViewHolder.searchQueryType.setImageDrawable(context.getDrawable(R.drawable.games_icon))

            }
            GeneralEndpoints.QueryType.MoviesQuery -> {

                completeSearchViewHolder.blurryBackground.setOverlayColor(setColorAlpha(context.getColor(R.color.moviesSectionColor), 173f))

                completeSearchViewHolder.searchQueryType.setImageDrawable(context.getDrawable(R.drawable.movies_icon))

            }
        }

        completeSearchViewHolder.productTitle.text = Html.fromHtml(completeSearchResultsItems[position].productName, Html.FROM_HTML_MODE_COMPACT)

    }

}