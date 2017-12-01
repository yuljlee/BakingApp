package com.nanodegree.yj.bakingapp;

import android.os.AsyncTask;

import com.nanodegree.yj.bakingapp.utilities.JsonUtils;
import com.nanodegree.yj.bakingapp.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by u2stay1915 on 11/2/17.
 */
//
//public class RecipeTask extends AsyncTask<String, void, ArrayList<Recipe>> {
//
//    @Override
//    protected ArrayList<Recipe> doInBackground(String url) {
//
//        if (url.isEmpty()) {
//            return null;
//        }
//
//        URL recipeRequestUrl = NetworkUtils.buildUrl(url);
//        //Log.v(TAG, "Parsed data " + weatherRequestUrl);
//        try {
//            // get joson format movie data from server
//            String jsonRecipeResponse = NetworkUtils
//                    .getResponseFromHttpUrl(recipeRequestUrl);
//
//            //Log.v(TAG, "Json Response - " + jsonMoiveResponse);
//            ArrayList<Recipe> recipeStrings = JsonUtils
//                    .getRecipeStringsFromJson(this, jsonRecipeResponse);
//
//            return recipeStrings;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    @Override
//    protected void onPostExecute(ArrayList<Recipe>) {
//        super.onPostExecute(o);
//
//    }
//}
