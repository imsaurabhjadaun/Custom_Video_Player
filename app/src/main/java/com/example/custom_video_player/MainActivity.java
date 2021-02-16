package com.example.custom_video_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainActivity extends AppCompatActivity {

    //Initialize variable
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView btnFullscreen;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variable
        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progress_bar);
        btnFullscreen = findViewById(R.id.bt_fullscreen);

        //Make activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Video url
        Uri videoUrl = Uri.parse("https://i.imgur.com/7bMqysJ.mp4");

        //Initialize load control
        LoadControl loadControl = new DefaultLoadControl();

        //Initialize band width meter
        BandwidthMeter bandwidthMeter  = new DefaultBandwidthMeter();

        //Initialize track selector
        TrackSelector trackSelector =  new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter));

        //Initialize simple exo player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                MainActivity.this,trackSelector,loadControl);

        //Initialize data source factory
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory(
                "exoplayer_video");

        //Initialize extractors factory
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        //Initialize media source
        MediaSource mediaSource = new ExtractorMediaSource(videoUrl
           ,factory,extractorsFactory,null,null);

        //Set player
        playerView.setPlayer(simpleExoPlayer);

        //Keep screen on
        playerView.setKeepScreenOn(true);

        //Prepare media
        simpleExoPlayer.prepare(mediaSource);

        //play video when ready
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                //Check condition
                if (playbackState == Player.STATE_BUFFERING){
                    //When buffering
                    //show progress bar
                    progressBar.setVisibility(View.VISIBLE);
                }else if (playbackState == Player.STATE_READY){
                    //When ready
                    //Hide progress
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        btnFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Check condition
                if(flag){
                   //When flag is true
                   //set enter full screen image
                   btnFullscreen.setImageDrawable(getResources()
                   .getDrawable(R.drawable.fullscreen));

                   //Set portrait orientation
                   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                   //Set flag value is false
                   flag = false;
               }else {
                   //When flag is false
                   //Set exit full screen image
                   btnFullscreen.setImageDrawable(getResources()
                   .getDrawable(R.drawable.fullscreen_exit));

                   //Set Landscape orientation
                   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                   //Set flag value is tue
                   flag = true;
               }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Stop video when ready
        simpleExoPlayer.setPlayWhenReady(false);

        //Get playback state
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //Play video when ready
        simpleExoPlayer.setPlayWhenReady(true);

        //Get playback state
        simpleExoPlayer.getPlaybackState();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}