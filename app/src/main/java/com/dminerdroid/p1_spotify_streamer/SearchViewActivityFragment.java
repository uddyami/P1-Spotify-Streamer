package com.dminerdroid.p1_spotify_streamer;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
public class SearchViewActivityFragment extends Fragment implements AdapterView.OnItemClickListener{
    public static final String ARTIST_ID = "ArtistId";
    View mRootView;
    ListView lv;
    ArtistAdapter adapter;
    EditText searchText;
    public SearchViewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView= inflater.inflate(R.layout.fragment_search_view, container, false);
        lv=(ListView)mRootView.findViewById(R.id.listView_search);
        adapter = new ArtistAdapter(getActivity());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        searchText=(EditText)mRootView.findViewById(R.id.et_search_artist);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH || actionId ==EditorInfo.IME_FLAG_NO_ENTER_ACTION){
                    callSearch( v.getText().toString());
                    return true;
                }

                return false;
            }
        });
        return mRootView;
    }

    @Override
    public void onResume(){
        super.onResume();

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Artist artist=adapter.getItem(position);

        Intent intent= new Intent(getActivity(),TracksActivity.class);
        intent.putExtra(ARTIST_ID,artist.id);
        startActivity(intent);


    }
}
