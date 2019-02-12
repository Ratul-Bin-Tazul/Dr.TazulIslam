package com.ratulbintazul.www.drtazulislam.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ratulbintazul.www.drtazulislam.Activity.PostDetailsActivity;
import com.ratulbintazul.www.drtazulislam.Activity.ShareActivity;
import com.ratulbintazul.www.drtazulislam.DataProvider.MediaDataProvider;
import com.ratulbintazul.www.drtazulislam.R;
import com.ratulbintazul.www.drtazulislam.Util.VideoCaching;

import java.util.ArrayList;
import java.util.List;

import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.VideoView;

/**
 * Created by SAMSUNG on 9/24/2017.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaHolder>{
    private ArrayList<MediaDataProvider> arrayList;
    private Context context;

    public MediaAdapter(ArrayList<MediaDataProvider> arrayList, Context ctx) {
        this.arrayList = arrayList;
        this.context = ctx;
    }

    @Override
    public MediaAdapter.MediaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item_layout,parent,false);

        MediaAdapter.MediaHolder mediaHolder = new MediaAdapter.MediaHolder(view);
        return mediaHolder;
    }

    @Override
    public void onBindViewHolder(final MediaAdapter.MediaHolder holder, final int position) {

        final MediaDataProvider mediaDataProvider = arrayList.get(position);


        //holder.message.setText(MediaDataProvider.getMessage());
        //holder.messageSent.setText(MediaDataProvider.getMessage());

        holder.mediaTitle.setText(mediaDataProvider.getTitle());

//        VideoInfo videoInfo = new VideoInfo(Uri.parse("http://xxx.mp4"))
//                .setShowTopBar(true)
//                .setTitle(mediaDataProvider.getTitle())
//                .setPortraitWhenFullScreen(true);//portrait when full screen;

        if(mediaDataProvider.getMediaType().equals("video")) {
            holder.mediaImg.setVisibility(View.GONE);
            holder.mediaVideo.setVisibility(View.VISIBLE);

            String url = VideoCaching.getProxy(context).getProxyUrl(mediaDataProvider.getMediaUrl());

            holder.mediaVideo.getVideoInfo()
                    .setTitle(mediaDataProvider.getTitle())
            .setUri(Uri.parse(url));

            holder.mediaVideo.setFingerprint(position);// or using:setFingerprint(videoItem.hashCode())

            final int[] pos = {0};
            holder.mediaVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.mediaVideo.getPlayer().isPlaying()) {
                        pos[0] = holder.mediaVideo.getPlayer().getCurrentPosition();
                        holder.mediaVideo.getPlayer().pause();
                    }else {
                        holder.mediaVideo.getPlayer().seekTo(pos[0]);
                    }

                }
            });

        }else{

            Glide.with(context)
                    .load(mediaDataProvider.getMediaUrl())
                    .into(holder.mediaImg);

        }

    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

        public class MediaHolder extends RecyclerView.ViewHolder {
        public TextView mediaTitle;
        public ImageView mediaImg;
        public VideoView mediaVideo;
        public MediaHolder(final View itemView) {
            super(itemView);
            mediaTitle = (TextView)itemView.findViewById(R.id.mediaTitle);
            mediaImg = (ImageView) itemView.findViewById(R.id.mediaImg);
            mediaVideo = (VideoView) itemView.findViewById(R.id.mediaVideo);
        }

    }
}
