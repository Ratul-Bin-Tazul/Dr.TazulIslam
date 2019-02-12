package com.ratulbintazul.www.drtazulislam.Util;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ratulbintazul.www.drtazulislam.DataProvider.MediaDataProvider;
import com.ratulbintazul.www.drtazulislam.R;

/**
 * Created by SAMSUNG on 9/25/2017.
 */

public class MediaUploader extends IntentService {


    Notification.Builder notificationBuilder;
    NotificationManager notificationManager;
    Notification notification;
    Integer notificationID = 100;

    int progress = 0;

    private StorageReference storage;
    private DatabaseReference database;

    public MediaUploader() {
        super("MediaUploader");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();

        final String media = workIntent.getStringExtra("media");
        final String mediaTitle = workIntent.getStringExtra("mediaTitle");

        // Do work here, based on the contents of dataString
        showProgressNotification();

        storage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference().child("media");


        StorageReference filePath;

        if(media.equals("image")) {
            filePath = storage.child("media_images").child(Uri.parse(dataString).getLastPathSegment());
        }else {
            filePath = storage.child("media_videos").child(Uri.parse(dataString).getLastPathSegment());
        }

        filePath.putFile(Uri.parse(dataString))
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {



                        //Update notification information:

                        int currentProgress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());

                        Log.e("progress",""+progress);

                        if(currentProgress>progress) {
                            notificationBuilder.setProgress(100, progress, true);

                            //Send the notification:
                            notificationManager.notify(notificationID, notification);
                        }


                        progress = currentProgress;

                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        notificationManager.cancelAll();
                        notificationManager.cancel(notificationID);
                        notificationManager.cancelAll();
                        notificationBuilder.setOngoing(false);
                        notificationManager.notify(notificationID, notification);


                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        DatabaseReference newPost = FirebaseDatabase.getInstance().getReference().child("media").push();

                        MediaDataProvider mediaDataProvider = new MediaDataProvider(media,downloadUrl.toString(),mediaTitle);
                        newPost.setValue(mediaDataProvider);
                    }
                });


    }

    private void showProgressNotification() {



        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Set notification information:
        notificationBuilder = new Notification.Builder(getApplicationContext());
        notificationBuilder.setOngoing(true)
                .setContentTitle("Uploading Media")
                .setContentText("Progress")
                .setSmallIcon(R.drawable.ic_cloud_upload_black_24dp)
                .setProgress(100, 0, false)
        .setOngoing(true);

        //Send the notification:
        notification = notificationBuilder.build();
        //notificationManager.notify(notificationID, notification);

    }
}