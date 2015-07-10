package com.dminerdroid.p1_spotify_streamer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TracksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        if(savedInstanceState==null){
            Bundle arguments= new Bundle();
            arguments.putString(SearchViewActivityFragment.ARTIST_ID,getIntent().getStringExtra(SearchViewActivityFragment.ARTIST_ID));
            arguments.putString(SearchViewActivityFragment.ARTIST_NAME,getIntent().getStringExtra(SearchViewActivityFragment.ARTIST_NAME));

            TracksActivityFragment fragment= new TracksActivityFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.track_container,fragment).commit();
        }
        getSupportActionBar().setTitle("Top 10 Tracks");
        getSupportActionBar().setSubtitle(getIntent().getStringExtra(SearchViewActivityFragment.ARTIST_NAME));

    }



}
