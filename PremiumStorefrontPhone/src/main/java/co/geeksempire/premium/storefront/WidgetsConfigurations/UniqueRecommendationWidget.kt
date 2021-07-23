/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/23/21, 9:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.WidgetsConfigurations

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import co.geeksempire.premium.storefront.R

class UniqueRecommendationWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

    }

    override fun onEnabled(context: Context) {



    }

    override fun onDisabled(context: Context) {

    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        val remoteViews = RemoteViews(context.packageName, R.layout.unique_recommendation_widget)
        //remoteViews.setImageViewBitmap(R.id.appwidget_text, widgetText)

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)

    }

}