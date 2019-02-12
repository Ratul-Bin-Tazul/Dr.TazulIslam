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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ratulbintazul.www.drtazulislam.Activity.PostDetailsActivity;
import com.ratulbintazul.www.drtazulislam.Activity.ShareActivity;
import com.ratulbintazul.www.drtazulislam.DataProvider.PostsDataProvider;
import com.ratulbintazul.www.drtazulislam.R;
import com.robertsimoes.shareable.Shareable;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by SAMSUNG on 9/16/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>{
    private ArrayList<PostsDataProvider> arrayList;
    private Context context;

    public PostAdapter(ArrayList<PostsDataProvider> arrayList, Context ctx) {
        this.arrayList = arrayList;
        this.context = ctx;
    }

    @Override
    public PostAdapter.PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_layout,parent,false);

        PostAdapter.PostHolder PostHolder = new PostAdapter.PostHolder(view);
        return PostHolder;
    }

    @Override
    public void onBindViewHolder(final PostAdapter.PostHolder holder, final int position) {

        final PostsDataProvider postsDataProvider = arrayList.get(position);

        //holder.message.setText(PostsDataProvider.getMessage());
        //holder.messageSent.setText(PostsDataProvider.getMessage());

        if(postsDataProvider.getPhotoUrl()==null) {
            holder.postImg.setVisibility(View.GONE);
        }else {
            Glide.with(context)
                    .load(postsDataProvider.getPhotoUrl())
                    .into(holder.postImg);
        }

        holder.postTitle.setText(postsDataProvider.getTitle());
        holder.postOverview.setText(postsDataProvider.getDesc().length()>120? postsDataProvider.getDesc().substring(0,120)+"...": postsDataProvider.getDesc());

        holder.likeCount.setText(""+(postsDataProvider.getLove()));
        //holder.likeCount.setText(postsDataProvider.getLove());
        //checking and setting liked
        if(arrayList.get(position).isLoved())
            holder.love.setLiked(true);

        //listener for like count
        holder.love.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                postsDataProvider.setLove(arrayList.get(position).getLove()+1);
                postsDataProvider.setLoved(true);
                holder.likeCount.setText(""+(postsDataProvider.getLove()));

                final String[] key = {""};
                FirebaseDatabase.getInstance().getReference().child("post").orderByChild("desc").equalTo(postsDataProvider.getDesc()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            key[0] = child.getKey();
                        }

                        FirebaseDatabase.getInstance().getReference().child("post").child(key[0]).child("love").setValue(postsDataProvider.getLove());
                        //Toast.makeText(context,"key "+ key[0],Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                arrayList.get(position).setLove(arrayList.get(position).getLove()-1);
                arrayList.get(position).setLoved(false);
                holder.likeCount.setText(""+(postsDataProvider.getLove()));

                final String[] key = {""};
                FirebaseDatabase.getInstance().getReference().child("post").orderByChild("desc").equalTo(postsDataProvider.getDesc()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            key[0] = child.getKey();
                        }

                        FirebaseDatabase.getInstance().getReference().child("post").child(key[0]).child("love").setValue(postsDataProvider.getLove());
                        //Toast.makeText(context,"key "+ key[0],Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PostDetailsActivity.class);
                i.putExtra("title",postsDataProvider.getTitle());
                i.putExtra("desc",postsDataProvider.getDesc());
                i.putExtra("photoUrl",postsDataProvider.getPhotoUrl());
                i.putExtra("loveCount",postsDataProvider.getLove());
                context.startActivity(i);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String post = postsDataProvider.getTitle()+"\n"+postsDataProvider.getDesc();
//
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("text/*");
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, post);
//                context.startActivity(Intent.createChooser(sharingIntent,"Share using"));

                //doSocialShare(postsDataProvider.getTitle(),postsDataProvider.getDesc(),"ww.ratulbintazul.com");

                //String post = postsDataProvider.getTitle()+"\n"+postsDataProvider.getDesc();
                //setupFacebookShareIntent(post);

                String post = postsDataProvider.getTitle()+"\n"+postsDataProvider.getDesc();
                Intent i = new Intent(context, ShareActivity.class);
                i.putExtra("post",post);
                context.startActivity(i);
            }
        });
    }



    public void doSocialShare(String title, String text, String url){
        // First search for compatible apps with sharing (Intent.ACTION_SEND)
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        // Set title and text to share when the user selects an option.
        shareIntent.putExtra(Intent.EXTRA_TITLE, title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                targetedShare.setType("text/plain"); // put here your mime type
                targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                targetedShareIntents.add(targetedShare);
            }
            // Then show the ACTION_PICK_ACTIVITY to let the user select it
            Intent intentPick = new Intent();
            intentPick.setAction(Intent.ACTION_PICK_ACTIVITY);
            // Set the title of the dialog
            intentPick.putExtra(Intent.EXTRA_TITLE, title);
            intentPick.putExtra(Intent.EXTRA_INTENT, shareIntent);
            intentPick.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray());
            // Call StartActivityForResult so we can get the app name selected by the user
            ((Activity)context).startActivityForResult(intentPick, 2);
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        public TextView postTitle,postOverview,likeCount;
        public ImageView postImg;
        public LikeButton love;
        public CardView cardView;
        public TextView share;
        public PostHolder(final View itemView) {
            super(itemView);
            postTitle = (TextView)itemView.findViewById(R.id.postTitle);
            postOverview = (TextView)itemView.findViewById(R.id.postOverview);
            likeCount = (TextView)itemView.findViewById(R.id.likeCount);
            postImg = (ImageView) itemView.findViewById(R.id.postImg);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            love = (LikeButton)itemView.findViewById(R.id.likeBtn);
            share = (TextView) itemView.findViewById(R.id.postShareBtn);

        }


    }
}
