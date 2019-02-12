package com.ratulbintazul.www.drtazulislam.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ratulbintazul.www.drtazulislam.DataProvider.PostsDataProvider;
import com.ratulbintazul.www.drtazulislam.R;

import java.util.HashMap;
import java.util.Map;

public class AuthorActivity extends AppCompatActivity {


    private StorageReference storage;
    private DatabaseReference database;

    ImageButton postImage;
    EditText postTitle;
    EditText postDescription;
    Button postBtn,uploadMedia;
    private ProgressDialog progressBar;
    Uri imageUri = null;

    private final int GALLARY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        storage = FirebaseStorage.getInstance().getReference();


        postImage = (ImageButton)findViewById(R.id.post_img);
        postTitle = (EditText)findViewById(R.id.post_title);
        postDescription = (EditText)findViewById(R.id.post_details);
        postBtn = (Button)findViewById(R.id.post_button);
        uploadMedia = (Button)findViewById(R.id.media_button);

        progressBar = new ProgressDialog(this);

        //image btn gets the image
        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,GALLARY_REQUEST_CODE);
            }
        });

        //post btn start posting
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });

        uploadMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuthorActivity.this,MediaActivity.class));

            }
        });
    }



    private void startPosting() {


        progressBar.setMessage("Posting to Blog ...");

        database = FirebaseDatabase.getInstance().getReference().child("post");

        final String postTtl = postTitle.getText().toString();
        final String postDesc = postDescription.getText().toString();



        if(!TextUtils.isEmpty(postTtl) && !TextUtils.isEmpty(postDesc) && imageUri!=null) {


            progressBar.show(); //showing the progress bar

            StorageReference filePath = storage.child("Blog_post_images").child(imageUri.getLastPathSegment());

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = database.push(); //pusing the data
                    PostsDataProvider postsDataProvider = new PostsDataProvider(postDesc,downloadUrl.toString(),postTtl);
                    newPost.setValue(postsDataProvider);

//                    newPost.child("title").setValue(postTtl);
//                    newPost.child("description").setValue(postDesc);
//                    newPost.child("image").setValue(downloadUrl.toString());

                    progressBar.dismiss();

                    sendNotificationToUser("puf", "Hi there puf!");
                    
                    Intent i = new Intent(AuthorActivity.this,MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            });
        }
        else if(!TextUtils.isEmpty(postTtl) && !TextUtils.isEmpty(postDesc)) {

            DatabaseReference newPost = database.push(); //pusing the data
            PostsDataProvider postsDataProvider = new PostsDataProvider(postDesc,null,postTtl);
            newPost.setValue(postsDataProvider);

            Intent i = new Intent(AuthorActivity.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();

        }else{
            Toast.makeText(this,"Please input a title and details of your post.", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendNotificationToUser(String puf, String s) {

        DatabaseReference notifications = FirebaseDatabase.getInstance().getReference().child("notificationRequests");

        Map notification = new HashMap<>();
        notification.put("Title", "Prof. Tazul Islam posted a new post");
        notification.put("Message", postTitle.getText().toString());

        notifications.push().setValue(notification);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLARY_REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            postImage.setImageURI(imageUri);
        }
    }
}
