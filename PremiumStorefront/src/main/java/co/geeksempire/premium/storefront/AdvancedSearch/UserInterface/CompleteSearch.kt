/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/16/21, 6:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.UserInterface

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.AdvancedSearch.DataStructure.CompleteSearchLiveData
import co.geeksempire.premium.storefront.AdvancedSearch.Extensions.setupCompleteSearchUserInterface
import co.geeksempire.premium.storefront.AdvancedSearch.NetworkOperations.SearchAllProducts
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.Adapter.CompleteSearchAdapter
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutGrid
import co.geeksempire.premium.storefront.databinding.CompleteSearchLayoutBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CompleteSearch : AppCompatActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@CompleteSearch)
    }

    val completeSearchLiveData: CompleteSearchLiveData by lazy {
        ViewModelProvider(this@CompleteSearch)[CompleteSearchLiveData::class.java]
    }

    val searchAllProducts: SearchAllProducts by lazy {
        SearchAllProducts(this@CompleteSearch)
    }

    val fullSearchCount = 3
    var searchOperationCounter = 0

    lateinit var completeSearchLayoutBinding: CompleteSearchLayoutBinding

    companion object {
        const val SearchQuery: String = "SearchQuery"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        completeSearchLayoutBinding = CompleteSearchLayoutBinding.inflate(layoutInflater)
        setContentView(completeSearchLayoutBinding.root)

        if (intent.hasExtra(CompleteSearch.SearchQuery)) {

            val searchQuery = intent.getStringExtra(CompleteSearch.SearchQuery)

            if (searchQuery != null) {

                lifecycleScope.launch {

                    themePreferences.checkThemeLightDark().collect { themeType ->

                        setupCompleteSearchUserInterface(themeType)

                        val completeSearchAdapter: CompleteSearchAdapter = CompleteSearchAdapter(this@CompleteSearch, themeType)

                        completeSearchLayoutBinding.searchResultsRecyclerView.layoutManager =   RecycleViewSmoothLayoutGrid(applicationContext, columnCount(applicationContext, 307), RecyclerView.VERTICAL,false)

                        completeSearchLayoutBinding.searchResultsRecyclerView.adapter = completeSearchAdapter

                        completeSearchLiveData.applicationsSearchResults.observe(this@CompleteSearch, {

                            searchOperationCounter++

                            if (it.isNotEmpty()) {

                                completeSearchAdapter.completeSearchResultsItems.addAll(it)

                                completeSearchAdapter.notifyDataSetChanged()

                                if (completeSearchLayoutBinding.waitingView.isShown) {
                                    completeSearchLayoutBinding.waitingView.visibility = View.GONE
                                }

                            } else {

                                if (searchOperationCounter == fullSearchCount) {

                                    if (completeSearchAdapter.completeSearchResultsItems.isEmpty()) {

                                        this@CompleteSearch.finish()

                                    }

                                }

                            }

                        })

                        completeSearchLiveData.gamesSearchResults.observe(this@CompleteSearch, {

                            searchOperationCounter++

                            if (it.isNotEmpty()) {

                                completeSearchAdapter.completeSearchResultsItems.addAll(it)

                                completeSearchAdapter.notifyDataSetChanged()

                                if (completeSearchLayoutBinding.waitingView.isShown) {
                                    completeSearchLayoutBinding.waitingView.visibility = View.GONE
                                }

                            } else {

                                if (searchOperationCounter == fullSearchCount) {

                                    if (completeSearchAdapter.completeSearchResultsItems.isEmpty()) {

                                        this@CompleteSearch.finish()

                                    }

                                }

                            }

                        })

                        completeSearchLiveData.moviesSearchResults.observe(this@CompleteSearch, {

                            searchOperationCounter++

                            if (it.isNotEmpty()) {

                                completeSearchAdapter.completeSearchResultsItems.addAll(it)

                                completeSearchAdapter.notifyDataSetChanged()

                                if (completeSearchLayoutBinding.waitingView.isShown) {
                                    completeSearchLayoutBinding.waitingView.visibility = View.GONE
                                }

                            } else {

                                if (searchOperationCounter == fullSearchCount) {

                                    if (completeSearchAdapter.completeSearchResultsItems.isEmpty()) {

                                        this@CompleteSearch.finish()

                                    }

                                }

                            }

                        })

                        searchAllProducts.startApplicationsSearch(searchQuery)

                        searchAllProducts.startGamesSearch(searchQuery)

                        searchAllProducts.startMoviesSearch(searchQuery)

                    }

                }

            } else {

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                this@CompleteSearch.finish()

            }

        }

        completeSearchLayoutBinding.cancelIconCompleteSearch.setOnClickListener {

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            this@CompleteSearch.finish()

        }

    }

}