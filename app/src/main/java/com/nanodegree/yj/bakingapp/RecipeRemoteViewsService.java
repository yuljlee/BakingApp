package com.nanodegree.yj.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nanodegree.yj.bakingapp.data.Ingredient;
import com.nanodegree.yj.bakingapp.data.Recipe;

import java.util.ArrayList;


/**
 * Created by u2stay1915 on 12/19/17.
 */

public class RecipeRemoteViewsService extends RemoteViewsService {

    private static final String TAG = RecipeRemoteViewsService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.v(TAG, "RecipeRemoteViewsService Started...");
        return new RecipeRemoteViewsFactory(this.getApplicationContext());
    }

    class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        Recipe mRecipe;
        ArrayList<Ingredient> mIngredient;

        public RecipeRemoteViewsFactory(Context context) {
            mContext = context;
            Log.v(TAG, "Factory Started...");
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            String json = preferences.getString(MainActivityFragment.SHARED_PREFS_KEY, "");
            if (!json.equals("")) {
                Gson gson = new Gson();
                mIngredient = gson.fromJson(json, new TypeToken<ArrayList<Ingredient>>() {
                }.getType());
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mIngredient != null) {
                return mIngredient.size();
            } else return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            remoteViews.setTextViewText(R.id.widget_text_view, mIngredient.get(position).getAll());
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}