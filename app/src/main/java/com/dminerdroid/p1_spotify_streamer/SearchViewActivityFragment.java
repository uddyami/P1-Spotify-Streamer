package com.dminerdroid.p1_spotify_streamer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchViewActivityFragment extends Fragment {
    View mRootView;
    ListView lv;
    ArtistAdapter adapter;
    public SearchViewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView= inflater.inflate(R.layout.fragment_search_view, container, false);
        lv=(ListView)mRootView.findViewById(R.id.listView_search);
        adapter = new ArtistAdapter(getActivity());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(artistClick);
        return mRootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        callSearch("coldplay");
    }

    private void callSearch(String s) {
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
       spotify.searchArtists(s, new Callback<ArtistsPager>() {
            @Override
            public void success(ArtistsPager artistsPager, Response response) {

                if(response.getStatus()==200){
                    if(artistsPager!=null && artistsPager.artists.total>0) {
                        adapter.setmData(artistsPager);
                        adapter.notifyDataSetInvalidated();
                    }else
                    Toast.makeText( getActivity(),R.string.artist_not_found,Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    AdapterView.OnItemClickListener artistClick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Artist artist=adapter.getItem(position);
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            Map<String,Object> queryParams = new HashMap<String, Object>();
            queryParams.put("country","us");
            spotify.getArtistTopTrack(artist.id,queryParams, new Callback<Tracks>() {
                @Override
                public void success(Tracks tracks, Response response) {
                    if(!tracks.tracks.isEmpty()) {
                        TracksAdapter adapter = new TracksAdapter(getActivity());
                        adapter.setmData(tracks);
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetInvalidated();
                    }else
                        Toast.makeText( getActivity(),R.string.tracks_not_found,Toast.LENGTH_SHORT).show();

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    };

}
