package com.ratulbintazul.www.drtazulislam.Activity;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.ratulbintazul.www.drtazulislam.R;
import com.robertsimoes.shareable.Shareable;

import in.championswimmer.libsocialbuttons.buttons.BtnFacebook;
import in.championswimmer.libsocialbuttons.buttons.BtnGoogleplus;
import in.championswimmer.libsocialbuttons.buttons.BtnLinkedin;
import in.championswimmer.libsocialbuttons.buttons.BtnTumblr;
import in.championswimmer.libsocialbuttons.buttons.BtnTwitter;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ShareActivity extends AppCompatActivity {

    BtnFacebook btnFacebook;
    BtnGoogleplus btnGoogleplus;
    BtnLinkedin btnLinkedin;
    BtnTwitter btnTwitter;
    BtnTumblr btnTumblr;

    String post = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        post = getIntent().getStringExtra("post");

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
}
