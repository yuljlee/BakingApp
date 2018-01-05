package com.nanodegree.yj.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by u2stay1915 on 11/1/17.
 */

public class Recipe implements Parcelable {

    private String recipeId;
    private String name;
    private ArrayList<com.nanodegree.yj.bakingapp.Ingredient> ingredients;
    private ArrayList<com.nanodegree.yj.bakingapp.Step> steps;
    private String servings;
    private String image;

    private Recipe(Parcel in) {
        recipeId = in.readString();
        name = in.readString();
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<com.nanodegree.yj.bakingapp.Ingredient>();
            in.readList(ingredients, com.nanodegree.yj.bakingapp.Ingredient.class.getClassLoader());
        } else {
            ingredients = null;
        }
        if (in.readByte() == 0x01) {
            steps = new ArrayList<com.nanodegree.yj.bakingapp.Step>();
            in.readList(steps, com.nanodegree.yj.bakingapp.Step.class.getClassLoader());
        } else {
            steps = null;
        }

        servings = in.readString();
        image = in.readString();
    }

    public Recipe(String recipeId, String name, ArrayList<com.nanodegree.yj.bakingapp.Ingredient> ingredients, ArrayList<com.nanodegree.yj.bakingapp.Step> steps, String servings, String image) {
        this.recipeId = recipeId;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<com.nanodegree.yj.bakingapp.Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<com.nanodegree.yj.bakingapp.Step> getSteps() {
        return steps;
    }

    public String getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeId);
        dest.writeString(name);

        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }

        if (steps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(steps);
        }
        //dest.writeList(ingredients);
        //dest.writeList(steps);
        dest.writeString(servings);
        dest.writeString(image);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
