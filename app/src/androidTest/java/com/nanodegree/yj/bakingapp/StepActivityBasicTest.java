package com.nanodegree.yj.bakingapp;

import android.app.Fragment;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by u2stay1915 on 1/3/18.
 */

@RunWith(AndroidJUnit4.class)
public class StepActivityBasicTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void click_lanchDetail() {

        onView(withId(R.id.recyclerview_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recyclerview_recipe_detail)).check(matches(isDisplayed()));
        intended(hasComponent(RecipeDetailActivity.class.getName()));
        intended(hasExtraWithKey("recipe"));
    }
}
