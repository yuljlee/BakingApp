package com.nanodegree.yj.bakingapp.utilities;

import android.content.Context;
import android.util.Log;

import com.nanodegree.yj.bakingapp.Ingredient;
import com.nanodegree.yj.bakingapp.Recipe;
import com.nanodegree.yj.bakingapp.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by u2stay1915 on 11/3/17.
 */

public final class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    static public ArrayList<Recipe> getRecipeStringsFromJson(Context context, String recipeJsonStr)
            throws JSONException {

        JSONArray jsonArray = new JSONArray(recipeJsonStr);
        ArrayList<Recipe> arrayListRecipe = new ArrayList<Recipe>();
        String id;
        String name;
        String servings;
        String image;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            id = jsonObject.getString("id");
            name = jsonObject.getString("name");
            servings = jsonObject.getString("servings");
            image = jsonObject.getString("image");

            JSONArray jsonArrayIngredients = jsonObject.getJSONArray("ingredients");
            ArrayList<Ingredient> arrayListIngredient = new ArrayList<Ingredient>();

            for (int j = 0; j < jsonArrayIngredients.length(); j++) {
                JSONObject jsonObjectIngredients = jsonArrayIngredients.getJSONObject(j);

                String quantity = jsonObjectIngredients.getString("quantity");
                String measure = jsonObjectIngredients.getString("measure");
                String ingredient = jsonObjectIngredients.getString("ingredient");

                arrayListIngredient.add(new Ingredient(quantity, measure, ingredient));
            }

            JSONArray jsonArraySteps = jsonObject.getJSONArray("steps");
            ArrayList<Step> arrayListStep = new ArrayList<Step>();

            for (int k = 0; k < jsonArraySteps.length(); k++) {
                JSONObject jsonObjectSteps = jsonArraySteps.getJSONObject(k);

                int stepId = jsonObjectSteps.getInt("id");
                String shortDescription = jsonObjectSteps.getString("shortDescription");
                String description = jsonObjectSteps.getString("description");
                String videoURL = jsonObjectSteps.getString("videoURL");
                String thumbnailURL = jsonObjectSteps.getString("thumbnailURL");

                arrayListStep.add(new Step(stepId, shortDescription, description, videoURL, thumbnailURL));
            }

            arrayListRecipe.add(new Recipe(id, name, arrayListIngredient, arrayListStep, servings, image));

            Log.v(TAG, "recipe: " + arrayListRecipe);
        }

        return arrayListRecipe;
    }
}
