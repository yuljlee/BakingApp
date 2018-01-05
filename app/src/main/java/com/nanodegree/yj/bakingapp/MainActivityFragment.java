package com.nanodegree.yj.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nanodegree.yj.bakingapp.adapters.RecipeAdapter;
import com.nanodegree.yj.bakingapp.utilities.JsonUtils;
import com.nanodegree.yj.bakingapp.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    public static final String SHARED_PREFS_KEY = "SHARED_PREFS_KEY";
    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeApdapter;

    public MainActivityFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView)  rootView.findViewById(R.id.recyclerview_recipe);
        //mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        //mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
        //mRecipeApdapter = new MyAdapter();
        //Context context = getActivity().getApplicationContext();
        mRecipeApdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeApdapter);

        showRecipeList();

        return rootView;
    }

    private void showRecipeList() {

        new RecipeTask().execute();
        Log.v(TAG, "Task begin --> ");
    }

    @Override
    public void onClick(com.nanodegree.yj.bakingapp.Recipe recipe) {
        Context context = getActivity();
        Intent intent = new Intent(context, RecipeDetailActivity.class);

        intent.putExtra("recipe", recipe);
        Log.v(TAG, "recipe onClick --> " + recipe.getName());

        startActivity(intent);

        Gson gson = new Gson();
        String json = gson.toJson(recipe.getIngredients());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SHARED_PREFS_KEY, json).commit();

        broadcastIntent();
    }

    private void broadcastIntent() {
        Intent intent = new Intent(getActivity(), BakingAppWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE\"");
        getActivity().sendBroadcast(intent);
    }

    public class RecipeTask extends AsyncTask<String, Void, ArrayList<com.nanodegree.yj.bakingapp.Recipe>> {

        @Override
        protected ArrayList<com.nanodegree.yj.bakingapp.Recipe> doInBackground(String... urls) {

            URL recipeRequestUrl = NetworkUtils.buildUrl();
            Log.v(TAG, "url: " + recipeRequestUrl);
            try {
                // get joson format movie data from server
                String jsonRecipeResponse = NetworkUtils
                        .getResponseFromHttpUrl(recipeRequestUrl);

                Log.v(TAG, "Json Response - " + jsonRecipeResponse);
                ArrayList<com.nanodegree.yj.bakingapp.Recipe> recipeStrings = JsonUtils
                        .getRecipeStringsFromJson(getActivity(), jsonRecipeResponse);

                return recipeStrings;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<com.nanodegree.yj.bakingapp.Recipe> recipes) {
            super.onPostExecute(recipes);
            mRecipeApdapter.setRecipeData(recipes);
        }
    }
}
