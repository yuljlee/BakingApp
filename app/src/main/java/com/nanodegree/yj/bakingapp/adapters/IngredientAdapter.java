package com.nanodegree.yj.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nanodegree.yj.bakingapp.Ingredient;

import java.util.ArrayList;

/**
 * Created by u2stay1915 on 11/14/17.
 */

public class IngredientAdapter extends RecyclerView.Adapter {

    private ArrayList<Ingredient> mIngredientArrayList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (null == mIngredientArrayList) return 0;
        return mIngredientArrayList.size();
    }
}
