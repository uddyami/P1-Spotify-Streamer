package com.dminerdroid.p1_spotify_streamer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by uddyami on 7/2/15.
 */
public class ArtistAdapter extends BaseAdapter {
Context mContext;
    ArtistsPager mData;

    public ArtistAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setmData(ArtistsPager data){
        this.mData=data;
    }
    @Override
    public int getCount() {
        if(mData!=null) return mData.artists.limit;
        return 0;
    }

    @Override
    public Artist getItem(int position) {

        if(mData!=null) return mData.artists.items.get(position);
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
        Artist artist= getItem(position);
        TextView name=(TextView) convertView.findViewById(R.id.tv_artist_name);
        name.setText(artist.name);
        ImageView imageView =(ImageView)convertView.findViewById(R.id.iv_artist_image);
        if(artist.images!=null && artist.images.size()>0)
        Picasso.with(mContext).load(artist.images.get(artist.images.size()-1).url).into(imageView);
        return convertView;
    }
}
