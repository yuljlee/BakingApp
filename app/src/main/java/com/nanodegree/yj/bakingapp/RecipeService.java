package com.nanodegree.yj.bakingapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by u2stay1915 on 12/17/17.
 */

public class RecipeService extends IntentService {

    public static final String ACTION_SHOW_RECIPE = "com.nanodegree.yj.bakingapp.action.show_recipe";

    public RecipeService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {

        }
    }
}
