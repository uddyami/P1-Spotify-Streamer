package com.dminerdroid.p1_spotify_streamer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class TracksActivityFragment extends Fragment {
    View mRootView;
    ListView lv;
    TracksAdapter adapter;
    public TracksActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView= inflater.inflate(R.layout.fragment_tracks, container, false);
        lv=(ListView)mRootView.findViewById(R.id.listView_tracks);
        if(adapter!=null)
        lv.setAdapter(adapter);
        //save data here
        if(savedInstanceState==null) {
            Bundle arguments = getArguments();
            if (arguments != null) {
                loadTracks(arguments.getString(SearchViewActivityFragment.ARTIST_ID));
            }
            setRetainInstance(true);
        }
        return mRootView;
    }
    @Override
    public void onResume(){
        super.onResume();
            }

    private void loadTracks(String artistId){
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        Map<String,Object> queryParams = new HashMap<String, Object>();
        queryParams.put("country","us");
        spotify.getArtistTopTrack(artistId,queryParams, new Callback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {
                if(!tracks.tracks.isEmpty()) {
                     adapter = new TracksAdapter(getActivity());
                    adapter.setmData(tracks);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetInvalidated();
                }else
                    Toast.makeText(getActivity(), R.string.tracks_not_found, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
