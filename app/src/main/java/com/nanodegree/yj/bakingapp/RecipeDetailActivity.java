package com.nanodegree.yj.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nanodegree.yj.bakingapp.adapters.RecipeDetailAdapter;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.step_linearlayout) != null) {
            //mTwoPane = true;

            Bundle bundle = getIntent().getExtras();
            com.nanodegree.yj.bakingapp.Recipe recipe = (com.nanodegree.yj.bakingapp.Recipe) bundle.getParcelable("recipe");

            ArrayList<com.nanodegree.yj.bakingapp.Step> stepArrayList = new ArrayList<com.nanodegree.yj.bakingapp.Step>();
            stepArrayList = recipe.getSteps();

            if (savedInstanceState == null) {

                Fragment fragment = new RecipeStepActivityFragment();

                Bundle bundleStep = new Bundle();

                bundleStep.putParcelableArrayList("stepList", stepArrayList);
                bundleStep.putInt("stepId", 0);

                fragment.setArguments(bundleStep);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.step_container, fragment)
                        .commit();
            }
        }
    }
}
