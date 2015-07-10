package com.dminerdroid.p1_spotify_streamer;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
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
        if(adapter!=null)
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        if(savedInstanceState==null){
            setRetainInstance(true);
        }
        searchText=(EditText)mRootView.findViewById(R.id.et_search_artist);
        searchText.setOnEditorActionListener(searcher);

        return mRootView;
    }


    private void callSearch(String s) {
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
       spotify.searchArtists(s, new Callback<ArtistsPager>() {
            @Override
            public void success(ArtistsPager artistsPager, Response response) {

                if(response.getStatus()==200){
                    if(artistsPager!=null && artistsPager.artists.total>0) {
                        adapter = new ArtistAdapter(getActivity());
                        adapter.setmData(artistsPager);
                        adapter.notifyDataSetInvalidated();
                        lv.setAdapter(adapter);

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
    TextView.OnEditorActionListener searcher =new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId== EditorInfo.IME_ACTION_SEARCH || actionId ==EditorInfo.IME_FLAG_NO_ENTER_ACTION){
                callSearch(v.getText().toString());
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }

            return false;
        }
    };
}
