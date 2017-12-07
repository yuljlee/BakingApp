package com.nanodegree.yj.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.yj.bakingapp.Ingredient;
import com.nanodegree.yj.bakingapp.R;
import com.nanodegree.yj.bakingapp.Recipe;
import com.nanodegree.yj.bakingapp.Step;

import java.util.ArrayList;

/**
 * Created by u2stay1915 on 11/8/17.
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RecipeDetailAdapter.class.getSimpleName();

    private final int VIEW_TYPE_INGREDIENT_HEADER = 0;
    private final int VIEW_TYPE_INGREDIENT_LIST = 1;
    private final int VIEW_TYPE_STEP_HEADER = 2;
    private final int VIEW_TYPE_STEP_LIST = 3;

    private ArrayList<Ingredient> ingredientArrayList;
    private ArrayList<Step> stepArrayList;
    private final RecipeDetailAdapter.RecipeDetailAdapterOnClickHandler mClickHandler;

    public interface RecipeDetailAdapterOnClickHandler {
        void onClick(int stepId);
    }

    public RecipeDetailAdapter(RecipeDetailAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public class ViewHolderIngredientHeader extends RecyclerView.ViewHolder {

        public final TextView mIngredientHeaderTextview;

        public ViewHolderIngredientHeader(View view) {
            super(view);
            mIngredientHeaderTextview = (TextView) view.findViewById(R.id.ingredient_header_textview);
        }
    }

    public class ViewHolderIngredientList extends RecyclerView.ViewHolder {

        public final TextView mIngredientListTextview;

        public ViewHolderIngredientList(View view) {
            super(view);
            mIngredientListTextview = (TextView) view.findViewById(R.id.ingredient_textview);
        }
    }

    public class ViewHolderStepHeader extends RecyclerView.ViewHolder {

        public final TextView mStepTextview;

        public ViewHolderStepHeader(View view) {
            super(view);
            mStepTextview = (TextView) view.findViewById(R.id.step_header_textview);
        }
    }

    public class ViewHolderStepList extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mStepListTextview;

        public ViewHolderStepList(View view) {
            super(view);
            //mStepListTextview = (TextView) view.findViewById(R.id.step_list_textview);
            mStepListTextview = (TextView) view.findViewById(R.id.recipe_detail_textview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step step = stepArrayList.get(adapterPosition - 2 - ingredientArrayList.size());

            Log.v(TAG, "step onClick --> " + step.getDescription());
            mClickHandler.onClick(step.getStepId());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return VIEW_TYPE_INGREDIENT_HEADER;
        else if (position > 0 && position <= ingredientArrayList.size())
            return VIEW_TYPE_INGREDIENT_LIST;
        else if (position == ingredientArrayList.size() + 1)
            return VIEW_TYPE_STEP_HEADER;
        else
            return VIEW_TYPE_STEP_LIST;

        //return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        boolean attachToRoot = false;

        if (viewType == VIEW_TYPE_INGREDIENT_HEADER) {
            int layoutIdForListItem = R.layout.recipe_ingredient_header;
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(layoutIdForListItem, parent, attachToRoot);

            return new ViewHolderIngredientHeader(view);
        }
        else if (viewType == VIEW_TYPE_INGREDIENT_LIST){
            int layoutIdForListItem = R.layout.recipe_ingredient_list;
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(layoutIdForListItem, parent, attachToRoot);

            return new ViewHolderIngredientList(view);
        } else if (viewType == VIEW_TYPE_STEP_HEADER) {
            int layoutIdForListItem = R.layout.recipe_step_header;
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(layoutIdForListItem, parent, attachToRoot);

            return new ViewHolderStepHeader(view);
        }
        else {
            //int layoutIdForListItem = R.layout.recipe_step_list;
            int layoutIdForListItem = R.layout.recipe_detail_item;
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(layoutIdForListItem, parent, attachToRoot);

            return new ViewHolderStepList(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_INGREDIENT_HEADER:
                ViewHolderIngredientHeader viewHolderIngredientHeader = (ViewHolderIngredientHeader) holder;
                viewHolderIngredientHeader.mIngredientHeaderTextview.setText("Recipe Ingredients");
                break;
            case VIEW_TYPE_INGREDIENT_LIST:
                ViewHolderIngredientList viewHolderIngredientList = (ViewHolderIngredientList) holder;
                viewHolderIngredientList
                        .mIngredientListTextview
                        .setText(ingredientArrayList.get(position - 1).getAll());
                break;
            case VIEW_TYPE_STEP_HEADER:
                ViewHolderStepHeader viewHolderStepHeader = (ViewHolderStepHeader) holder;
                viewHolderStepHeader.mStepTextview.setText("Steps");
                break;
            case VIEW_TYPE_STEP_LIST:
                ViewHolderStepList viewHolderStepList = (ViewHolderStepList) holder;
                viewHolderStepList.mStepListTextview
                        .setText(stepArrayList.get(position - 2 - ingredientArrayList.size()).getShortDescription());
//                viewHolderStepList.mStepListTextview.setText(stepArrayList.get(position - 1).getShortDescription());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size() + stepArrayList.size() + 2;
        //return stepArrayList.size() + 1;
    }

    public void setStep(ArrayList<Step> step) {
        stepArrayList = step;
        notifyDataSetChanged();
    }

    public void setIngredient(ArrayList<Ingredient> ingredient) {
        ingredientArrayList = ingredient;
        notifyDataSetChanged();
    }
}
