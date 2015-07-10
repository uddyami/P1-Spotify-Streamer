package com.dminerdroid.p1_spotify_streamer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by uddyami on 7/2/15.
 */
public class TracksAdapter extends BaseAdapter {
Context mContext;
    Tracks mData;

    public TracksAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setmData(Tracks data){
        this.mData=data;
    }
    @Override
    public int getCount() {
        if(mData!=null) return mData.tracks.size();
        return 0;
    }

    @Override
    public Track getItem(int position) {

        if(mData!=null) return mData.tracks.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= View.inflate(mContext,R.layout.search_list_item,null);
        }
        Track track= getItem(position);
        TextView name=(TextView) convertView.findViewById(R.id.tv_artist_name);
        String trackDetails=track.name+"\n"+track.album.name;
        name.setText(trackDetails);
        ImageView imageView =(ImageView)convertView.findViewById(R.id.iv_artist_image);
        if(track.album.images!=null && track.album.images.size()>0)
        Picasso.with(mContext).load(track.album.images.get(track.album.images.size()-1).url).into(imageView);
        return convertView;
    }
}
