package com.ratulbintazul.www.drtazulislam.Activity;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ratulbintazul.www.drtazulislam.DataProvider.MediaDataProvider;
import com.ratulbintazul.www.drtazulislam.DataProvider.PostsDataProvider;
import com.ratulbintazul.www.drtazulislam.R;
import com.ratulbintazul.www.drtazulislam.Util.MediaUploader;

public class MediaActivity extends AppCompatActivity {

    private StorageReference storage;
    private DatabaseReference database;



    ImageButton mediaPicker;
    TextView mediaTitle,selectedMediaName;
    Button uploadMediButton;

    Uri mediaUri = null;
    private final int MEDIA_PICKER_SELECT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);


        WebView web = new WebView(this);
        web.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {

            }
        });

        storage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference().child("media");

        mediaPicker = (ImageButton)findViewById(R.id.postMedia);
        mediaTitle = (TextView) findViewById(R.id.selectedMediaTitle);
        selectedMediaName = (TextView) findViewById(R.id.selectedMediaName);
        uploadMediButton = (Button) findViewById(R.id.upload_media_button);

        mediaPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/* video/*");
                startActivityForResult(pickIntent, MEDIA_PICKER_SELECT);
            }
        });

        uploadMediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaUri != null && !mediaTitle.getText().toString().isEmpty()) {

                    if (mediaUri.toString().contains("image")) {
                        //handle image
                        //Post to firebase server
                        postMedia("image");

                    } else  if (mediaUri.toString().contains("video")) {
                        //handle video
                        //Post to firebase server
                        postMedia("video");
                    }

                }else {
                    Toast.makeText(MediaActivity.this,"Please select a media and a media title!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void postMedia(final String media) {

        Intent mServiceIntent = new Intent(this, MediaUploader.class);
        mServiceIntent.setData(mediaUri);
        mServiceIntent.putExtra("media",media);
        mServiceIntent.putExtra("mediaTitle",mediaTitle.getText().toString());
        startService(mServiceIntent);

        Intent i = new Intent(MediaActivity.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==MEDIA_PICKER_SELECT && resultCode == RESULT_OK) {
            Uri selectedMediaUri = data.getData();
            mediaUri = selectedMediaUri;

            mediaPicker.setVisibility(View.GONE);
            selectedMediaName.setText(getFileName(mediaUri));
            selectedMediaName.setVisibility(View.VISIBLE);
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
