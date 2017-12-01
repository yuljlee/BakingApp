package com.nanodegree.yj.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

    private RecyclerView mRecyclerView;
    //private MyAdapter mRecipeApdapter;
    private RecipeAdapter mRecipeApdapter;
    //private ArrayList<com.nanodegree.yj.bakingapp.MyData> myDataset;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView)  rootView.findViewById(R.id.recyclerview_recipe);
        //mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        //mRecyclerView.setLayoutManager(layoutManager);

        //GridLayoutManager layoutManager = new GridLayoutManager(rootView.getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
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
        //Toast.makeText(context, "onClicked ---> ", Toast.LENGTH_LONG).show();
        Intent intentToStartDetailActivity = new Intent(context, RecipeDetailActivity.class);

        //Bundle bundle = new Bundle();
        //bundle.putParcelable("recipe", recipe);
        intentToStartDetailActivity.putExtra("recipe", recipe);
        Log.v(TAG, "recipe onClick --> " + recipe.getName());

        startActivity(intentToStartDetailActivity);
    }

    public class RecipeTask extends AsyncTask<String, Void, ArrayList<com.nanodegree.yj.bakingapp.Recipe>> {

        @Override
        protected ArrayList<com.nanodegree.yj.bakingapp.Recipe> doInBackground(String... urls) {
//            Log.v(TAG, "inside doIn: " + urls.toString());
//            if (urls.length == 0) {
//                return null;
//            }

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
