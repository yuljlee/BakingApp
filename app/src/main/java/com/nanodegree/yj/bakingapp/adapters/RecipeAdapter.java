package com.nanodegree.yj.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nanodegree.yj.bakingapp.R;
import com.nanodegree.yj.bakingapp.Recipe;


import java.util.ArrayList;

/**
 * Created by u2stay1915 on 11/6/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    ArrayList<Recipe> mRecipeArrayList;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mRecipeName;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            mRecipeName = (TextView) view.findViewById((R.id.textview));

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipeArrayList.get(adapterPosition);

            Log.v(TAG, "recipe onClick --> " + recipe.getSteps());
            mClickHandler.onClick(recipe);
        }
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToRoot = false;

        View view = inflater.inflate(layoutIdForListItem, parent, attachToRoot);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipeArrayList.get(position);
        holder.mRecipeName.setText((CharSequence) recipe.getName());
    }

    @Override
    public int getItemCount() {
        if (mRecipeArrayList == null) return 0;
        return mRecipeArrayList.size();
    }

    public void setRecipeData(ArrayList<Recipe> recipes) {
        mRecipeArrayList = recipes;
        notifyDataSetChanged();
    }
}