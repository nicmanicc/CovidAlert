package com.example.covidalert;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class InfoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    //Youtube API key
    public static final String API_KEY = "AIzaSyB3pktGPL3eNpQ0EGcm5TL7A_w-8oZ28YA";
    //Youtube video URL: https://www.youtube.com/watch?v=sHP0UIdZyI4
    public static final String VIDEO_ID = "sHP0UIdZyI4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Retrieve YouTubePlayerView from activity
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtubePlayer);
        //initialize the youtube player with API key and make this class the listener
        youTubePlayerView.initialize(API_KEY, this);
    }

    //Overview: If youtube player successfully initializes, cue the youtube video: https://www.youtube.com/watch?v=sHP0UIdZyI4
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(VIDEO_ID);
    }
    //Overview: Log to Logcat if initialization fails.
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.v("InfoActivity", "Failure to initialize.");
    }

}