package com.ratulbintazul.www.drtazulislam.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.ratulbintazul.www.drtazulislam.R;
import com.robertsimoes.shareable.Shareable;

public class PostDetailsActivity extends AppCompatActivity {

    LikeButton love;
    ImageView postImg;
    TextView postTitle,postDesc,share,loveText;
    int loveCount=0;

    String post = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        postImg = (ImageView)findViewById(R.id.postDetailsPhoto);
        postTitle = (TextView) findViewById(R.id.postDetailsTitle);
        postDesc = (TextView)findViewById(R.id.postDetails);
        share = (TextView)findViewById(R.id.postDetailsShareBtn);

        love = (LikeButton) findViewById(R.id.postDetailsLoveBtn);
        loveText = (TextView)findViewById(R.id.postDetailsLove);

        String imgUrl = getIntent().getStringExtra("photoUrl");
        if(imgUrl==null)
            postImg.setVisibility(View.GONE);
        else{
            Glide.with(this)
                    .load(imgUrl)
                    .into(postImg);
        }

        final String title = getIntent().getStringExtra("title");
        final String desc = getIntent().getStringExtra("desc");
        //String loveNum = getIntent().getStringExtra("loveCount");
        loveCount = getIntent().getIntExtra("loveCount",0);

        postTitle.setText(title);
        postDesc.setText(desc);

        post = title+"\n"+desc;

        love.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                loveText.setTextColor(getResources().getColor(R.color.loveColor,null));

                final String[] key = {""};
                FirebaseDatabase.getInstance().getReference().child("post").orderByChild("desc").equalTo(desc).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            key[0] = child.getKey();
                        }

                        FirebaseDatabase.getInstance().getReference().child("post").child(key[0]).child("love").setValue(loveCount+1);
                        //Toast.makeText(context,"key "+ key[0],Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                loveText.setTextColor(getResources().getColor(R.color.grayColor,null));

                final String[] key = {""};
                FirebaseDatabase.getInstance().getReference().child("post").orderByChild("desc").equalTo(desc).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            key[0] = child.getKey();
                        }

                        FirebaseDatabase.getInstance().getReference().child("post").child(key[0]).child("love").setValue(loveCount-1);
                        //Toast.makeText(context,"key "+ key[0],Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String post = title+"\n"+desc;//+"\n"+ Html.fromHtml("<b>Written By:\nProf. Tazul Islam</b>",Html.FROM_HTML_MODE_COMPACT);

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/*");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, post);
                startActivity(Intent.createChooser(sharingIntent,"Share using"));

            }
        });
    }

    public void shareFacebook(View v) {

        ShareDialog shareDialog;
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);

        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setQuote(post)
                .setContentUrl(Uri.parse("http://ratulbintazul.com/"))
                .build();

        shareDialog.show(linkContent);
    }

    public void shareTwitter(View v) {

        Shareable shareAction = new Shareable.Builder(this)
                .message(post)
                .url("http://ratulbintazul.com")
                .socialChannel(Shareable.Builder.TWITTER)
                .build();
        shareAction.share();
    }

    public void shareLinkdin(View v) {

        Shareable shareAction = new Shareable.Builder(this)
                .message(post)
                .url("http://ratulbintazul.com")
                .socialChannel(Shareable.Builder.LINKED_IN)
                .build();
        shareAction.share();
    }

    public void shareGooglePlus(View v) {

        Shareable shareAction = new Shareable.Builder(this)
                .message(post)
                .url("http://ratulbintazul.com")
                .socialChannel(Shareable.Builder.GOOGLE_PLUS)
                .build();
        shareAction.share();
    }

    public void shareTumblr(View v) {

        Shareable shareAction = new Shareable.Builder(this)
                .message(post)
                .url("http://ratulbintazul.com")
                .socialChannel(Shareable.Builder.TUMBLR)
                .build();
        shareAction.share();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
