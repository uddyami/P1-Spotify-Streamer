package com.dminerdroid.p1_spotify_streamer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class TracksActivityFragment extends Fragment {

    public TracksActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //save data here
        return inflater.inflate(R.layout.fragment_tracks, container, false);
    }
}
