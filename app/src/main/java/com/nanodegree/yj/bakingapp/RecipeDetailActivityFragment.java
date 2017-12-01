package com.nanodegree.yj.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nanodegree.yj.bakingapp.adapters.RecipeDetailAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */

// This fragment displays all of the Ingredients and Steps in the list
public class RecipeDetailActivityFragment extends Fragment implements RecipeDetailAdapter.RecipeDetailAdapterOnClickHandler {

    private static final String TAG = RecipeDetailActivityFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecipeDetailAdapter mRecipeDetailAdapter;
    private com.nanodegree.yj.bakingapp.Recipe mRecipe;
    private ArrayList<com.nanodegree.yj.bakingapp.Step> mStepArrayList;

    public RecipeDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        mRecipe = getActivity().getIntent().getParcelableExtra("recipe");

        //Recipe recipe = (Recipe) bundle.getParcelable("recipe");

        mRecyclerView = (RecyclerView)  rootView.findViewById(R.id.recyclerview_recipe_detail);
        //mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeDetailAdapter = new RecipeDetailAdapter(this);
        mRecyclerView.setAdapter(mRecipeDetailAdapter);

        Bundle bundle = getActivity().getIntent().getExtras();
        com.nanodegree.yj.bakingapp.Recipe recipe = (com.nanodegree.yj.bakingapp.Recipe) bundle.getParcelable("recipe");
        // recipe name
        //getActivity().setTitle(recipe.getName());

        mStepArrayList = new ArrayList<com.nanodegree.yj.bakingapp.Step>();
        mStepArrayList = recipe.getSteps();

        Log.v(TAG, "mRecipe --> " + recipe.getName());
        if (recipe == null){
            Toast.makeText(getActivity(), "no data ---> ", Toast.LENGTH_LONG).show();
        } else {
            mRecipeDetailAdapter.setIngredient(recipe.getIngredients());
            mRecipeDetailAdapter.setStep(recipe.getSteps());
        }

        return rootView;
    }

    @Override
    public void onClick(int stepId) {
        //Toast.makeText(getActivity(), "show me", Toast.LENGTH_LONG).show();

        Context context = getActivity();
        //Toast.makeText(context, "onClicked ---> ", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, RecipeStepActivity.class);

        //Bundle bundle = new Bundle();
        //bundle.putParcelable("recipe", recipe);
        intent.putParcelableArrayListExtra("stepList", mStepArrayList);
        intent.putExtra("stepId", stepId);
        //Log.v(TAG, "step onClick --> " + Integer.toString(stepId));

        startActivity(intent);
    }
}
