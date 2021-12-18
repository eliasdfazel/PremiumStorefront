/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/18/21, 4:58 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.UserInterface

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.AdvancedSearch.DataStructure.CompleteSearchLiveData
import co.geeksempire.premium.storefront.AdvancedSearch.Extensions.generateRandomDiamond
import co.geeksempire.premium.storefront.AdvancedSearch.Extensions.setupCompleteSearchUserInterface
import co.geeksempire.premium.storefront.AdvancedSearch.Extensions.setupSearchView
import co.geeksempire.premium.storefront.AdvancedSearch.NetworkOperations.SearchAllProducts
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.Adapter.CompleteSearchAdapter
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutGrid
import co.geeksempire.premium.storefront.databinding.CompleteSearchLayoutBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

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

    var completeSearchAdapter: CompleteSearchAdapter? = null

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

                completeSearchLayoutBinding.searchQueryText.text = searchQuery

                lifecycleScope.launch {

                    themePreferences.checkThemeLightDark().collect { themeType ->

                        setupCompleteSearchUserInterface(themeType)

                        generateRandomDiamond(themeType)

                        completeSearchAdapter = CompleteSearchAdapter(this@CompleteSearch, themeType)

                        completeSearchLayoutBinding.searchResultsRecyclerView.layoutManager =   RecycleViewSmoothLayoutGrid(applicationContext, columnCount(applicationContext, 307), RecyclerView.VERTICAL,false)

                        completeSearchLayoutBinding.searchResultsRecyclerView.adapter = completeSearchAdapter

                        completeSearchLiveData.applicationsSearchResults.observe(this@CompleteSearch, {

                            searchOperationCounter++

                            if (it.isNotEmpty()) {

                                completeSearchAdapter!!.completeSearchResultsItems.addAll(it)

                                completeSearchAdapter!!.notifyDataSetChanged()

                                if (completeSearchLayoutBinding.waitingView.isShown) {
                                    completeSearchLayoutBinding.waitingView.visibility = View.GONE
                                }

                            } else {

                                if (searchOperationCounter == fullSearchCount) {

                                    if (completeSearchAdapter!!.completeSearchResultsItems.isEmpty()) {

                                        Toast.makeText(applicationContext, getString(R.string.nothingFoundText), Toast.LENGTH_LONG).show()

                                        lifecycleScope.launch {

                                            themePreferences.checkThemeLightDark().collect { themeType ->

                                                setupSearchView(themeType)

                                            }

                                        }

                                    }

                                }

                            }

                            if (searchOperationCounter == fullSearchCount) {

                                showSearchActionView()

                            }

                        })

                        completeSearchLiveData.gamesSearchResults.observe(this@CompleteSearch, {

                            searchOperationCounter++

                            if (it.isNotEmpty()) {

                                completeSearchAdapter!!.completeSearchResultsItems.addAll(it)

                                completeSearchAdapter!!.notifyDataSetChanged()

                                if (completeSearchLayoutBinding.waitingView.isShown) {
                                    completeSearchLayoutBinding.waitingView.visibility = View.GONE
                                }

                            } else {

                                if (searchOperationCounter == fullSearchCount) {

                                    if (completeSearchAdapter!!.completeSearchResultsItems.isEmpty()) {

                                        Toast.makeText(applicationContext, getString(R.string.nothingFoundText), Toast.LENGTH_LONG).show()

                                        lifecycleScope.launch {

                                            themePreferences.checkThemeLightDark().collect { themeType ->

                                                setupSearchView(themeType)

                                            }

                                        }

                                    }

                                }

                            }

                            if (searchOperationCounter == fullSearchCount) {

                                showSearchActionView()

                            }

                        })

                        completeSearchLiveData.moviesSearchResults.observe(this@CompleteSearch, {

                            searchOperationCounter++

                            if (it.isNotEmpty()) {

                                completeSearchAdapter!!.completeSearchResultsItems.addAll(it)

                                completeSearchAdapter!!.notifyDataSetChanged()

                                if (completeSearchLayoutBinding.waitingView.isShown) {
                                    completeSearchLayoutBinding.waitingView.visibility = View.GONE
                                }

                            } else {

                                if (searchOperationCounter == fullSearchCount) {

                                    if (completeSearchAdapter!!.completeSearchResultsItems.isEmpty()) {

                                        Toast.makeText(applicationContext, getString(R.string.nothingFoundText), Toast.LENGTH_LONG).show()

                                        lifecycleScope.launch {

                                            themePreferences.checkThemeLightDark().collect { themeType ->

                                                setupSearchView(themeType)

                                            }

                                        }

                                    }

                                }

                            }

                            if (searchOperationCounter == fullSearchCount) {

                                showSearchActionView()

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

        completeSearchLayoutBinding.searchIconCompleteSearch.setOnClickListener {

            lifecycleScope.launch {

                themePreferences.checkThemeLightDark().collect { themeType ->

                    setupSearchView(themeType)

                }

            }

        }

        completeSearchLayoutBinding.goBackView.setOnClickListener {

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            this@CompleteSearch.finish()

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        this@CompleteSearch.finish()

    }

    private fun showSearchActionView() = CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {

        completeSearchLayoutBinding.searchQueryText.setOnClickListener {

            lifecycleScope.launch {

                themePreferences.checkThemeLightDark().collect { themeType ->

                    setupSearchView(themeType)

                }

            }

        }

        completeSearchLayoutBinding.searchBackgroundCompleteSearch.visibility = View.VISIBLE
        completeSearchLayoutBinding.searchBackgroundCompleteSearch.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

        delay(373)

        completeSearchLayoutBinding.searchIconCompleteSearch.visibility = View.VISIBLE
        completeSearchLayoutBinding.searchIconCompleteSearch.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.scale_up_bounce_interpolator))

    }

}