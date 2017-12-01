package com.nanodegree.yj.bakingapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nanodegree.yj.bakingapp.utilities.JsonUtils;
import com.nanodegree.yj.bakingapp.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public class RecipeTask extends AsyncTask<String, Void, ArrayList<Recipe>> {
//
//        @Override
//        protected ArrayList<Recipe> doInBackground(String... url) {
//
//            if (url[0].isEmpty()) {
//                return null;
//            }
//
//            URL recipeRequestUrl = NetworkUtils.buildUrl(url[0]);
//            //Log.v(TAG, "Parsed data " + weatherRequestUrl);
//            try {
//                // get joson format movie data from server
//                String jsonRecipeResponse = NetworkUtils
//                        .getResponseFromHttpUrl(recipeRequestUrl);
//
//                //Log.v(TAG, "Json Response - " + jsonMoiveResponse);
//                ArrayList<Recipe> recipeStrings = JsonUtils
//                        .getRecipeStringsFromJson(getApplicationContext(), jsonRecipeResponse);
//
//                return recipeStrings;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Recipe> recipes) {
//            super.onPostExecute(recipes);
//        }
//    }
}
