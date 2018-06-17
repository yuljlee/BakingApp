package com.nanodegree.yj.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nanodegree.yj.bakingapp.data.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by u2stay1915 on 11/22/17.
 */

public class RecipeStepActivityFragment extends Fragment implements Button.OnClickListener
    , ExoPlayer.EventListener {

    private static final String TAG = RecipeStepActivityFragment.class.getSimpleName();

    private ArrayList<Step> mStepList;
    private TextView mDescription;
    private ImageView mStepThumbnail;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private int mStepId;
    private long mPosition;
    private Button mPrevButton;
    private Button mNextButton;
    private static final String STEP_LIST = "step_list";
    private static final String STEP_ID = "step_id";
    private static final String SELECTED_POSITION =  "selected_position";

    public RecipeStepActivityFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);

        if (getActivity().findViewById(R.id.step_linearlayout) == null) { // phone mode

            if (savedInstanceState != null) {
                mStepList = savedInstanceState.getParcelableArrayList(STEP_LIST);
                mStepId = savedInstanceState.getInt(STEP_ID, 0);
                mPosition = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);

                Log.v(TAG, "step_id from savedInstanceState --> " + Integer.toString(mStepId));
            } else {
                mStepList = getActivity().getIntent().getParcelableArrayListExtra("stepList");
                mStepId = getActivity().getIntent().getExtras().getInt("stepId");

                Log.v(TAG, "step_id first --> " + Integer.toString(mStepId));
            }

            String videoUrl = mStepList.get(mStepId).getVideoURL();

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mDescription = (TextView) rootView.findViewById(R.id.description_textview);
                //mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);
                mStepThumbnail = (ImageView) rootView.findViewById(R.id.step_thumbnail);
                mPrevButton = (Button) rootView.findViewById(R.id.prev_button);
                mNextButton = (Button) rootView.findViewById(R.id.next_button);

                showRecipe(mStepId);
                //showRecipeVideo(mStepId);
                setButton();

                if (!videoUrl.isEmpty() && videoUrl != null) {
                    initializePlayer(Uri.parse(mStepList.get(mStepId).getVideoURL()));
                }
//        // previous button
                //Button button_prev = (Button) rootView.findViewById(R.id.previous_button);
                mPrevButton.setOnClickListener(this);
//        // next button
                //Button button_next = (Button) rootView.findViewById(R.id.next_button);
                mNextButton.setOnClickListener(this);
            } else { // landscape mode
                //mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);
                if (!videoUrl.isEmpty() && videoUrl != null) {
                    initializePlayer(Uri.parse(mStepList.get(mStepId).getVideoURL()));
                }
            }
        } else { // tablet landscape
            if (savedInstanceState != null) {
                mStepList = savedInstanceState.getParcelableArrayList(STEP_LIST);
                mStepId = savedInstanceState.getInt(STEP_ID, 0);
                mPosition = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);

                Log.v(TAG, "step_id from savedInstanceState --> " + Integer.toString(mStepId));
            } else {
                //mStepList = getActivity().getIntent().getParcelableArrayListExtra("stepList");
                //mStepId = getActivity().getIntent().getExtras().getInt("stepId");

                if (getArguments() != null) {
                    mStepList = getArguments().getParcelableArrayList("stepList");
                    mStepId = getArguments().getInt("stepId");
                }

                Log.v(TAG, "step_id first --> " + Integer.toString(mStepId));
            }

            String videoUrl = mStepList.get(mStepId).getVideoURL();
            mDescription = (TextView) rootView.findViewById(R.id.description_textview);
            mStepThumbnail = (ImageView) rootView.findViewById(R.id.step_thumbnail);
            showRecipe(mStepId);

            //mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);
            if (!videoUrl.isEmpty() && videoUrl != null) {
                initializePlayer(Uri.parse(mStepList.get(mStepId).getVideoURL()));
            }
        }

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            if (mPosition != C.TIME_UNSET) mExoPlayer.seekTo(mPosition);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPosition = mExoPlayer.getCurrentPosition(); // save current position
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(STEP_LIST, mStepList);
        outState.putInt(STEP_ID, mStepId);
        outState.putLong(SELECTED_POSITION, mPosition); // save player's position

        Log.v(TAG, "step_id save on onSaveInstanceState --> " + Integer.toString(mStepId));
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer(Uri.parse(mStepList.get(mStepId).getVideoURL()));
    }

    private void showRecipe(int index) {
        mDescription.setText(mStepList.get(index).getDescription());

        if (!mStepList.get(index).getThumbnailURL().isEmpty()) {
            Context context = mStepThumbnail.getContext();

            Picasso.with(context)
                     .load(mStepList.get(index).getThumbnailURL())
                     .into(mStepThumbnail);
        }
    }

    private void showRecipeVideo(int index) {
        String videoUrl = mStepList.get(index).getVideoURL();

        if (videoUrl != null && !videoUrl.isEmpty()) {
            //mPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(mStepList.get(index).getVideoURL()));
        } else {
            //mPlayerView.setVisibility(View.INVISIBLE);
        }
    }

    private void setButton() {
        if (mStepId == 0) {
            mPrevButton.setEnabled(false);
            mNextButton.setEnabled(true);
        } else if (mStepId == mStepList.size()-1) {
            mPrevButton.setEnabled(true);
            mNextButton.setEnabled(false);
        } else {
            mPrevButton.setEnabled(true);
            mNextButton.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prev_button:
                //Toast.makeText(getActivity(), "Prev", Toast.LENGTH_LONG).show();
                if (mStepId > 0) { mStepId--; }
                break;
            case R.id.next_button:
                //Toast.makeText(getContext(), "Next", Toast.LENGTH_LONG).show();
                if (mStepId < mStepList.size()-1) { mStepId++; }
                break;
        }

        if (mExoPlayer != null) {
            mPosition = C.TIME_UNSET;
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

        initializePlayer(Uri.parse(mStepList.get(mStepId).getVideoURL()));
        //showRecipeVideo(mStepId);
        showRecipe(mStepId);
        setButton();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
