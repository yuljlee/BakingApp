package com.nanodegree.yj.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        Intent listIntent = new Intent(context, RecipeRemoteViewsService.class);
        listIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        listIntent.putExtra("Random", Math.random() * 1000); // Add a random integer to stop the Intent being ignored.  This is needed for some API levels due to intent caching
        listIntent.setData(Uri.parse(listIntent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_view);
        views.setRemoteAdapter(R.id.widget_listview, listIntent);

        // Widgets allow click handlers to only launch pending intents
        //views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        views.setOnClickPendingIntent(R.id.recipe_name_textview, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), BakingAppWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}