package com.ratulbintazul.www.drtazulislam.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.ratulbintazul.www.drtazulislam.Adapter.PostAdapter;
import com.ratulbintazul.www.drtazulislam.DataProvider.PostsDataProvider;
import com.ratulbintazul.www.drtazulislam.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Posts extends Fragment {

    public RecyclerView postRecycleView;
    public RecyclerView.Adapter postAdapter;
    public RecyclerView.LayoutManager postLayoutManager;

    public ArrayList<PostsDataProvider> postArrayList = new ArrayList<>();

    public FirebaseRecyclerAdapter<PostsDataProvider,PostAdapter.PostHolder> mAdapter;

    public Posts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_posts, container, false);



        postRecycleView = (RecyclerView)view.findViewById(R.id.postRecyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        postRecycleView.setHasFixedSize(true);


        // use a linear layout manager
        postLayoutManager = new LinearLayoutManager(getContext());
        postRecycleView.setLayoutManager(postLayoutManager);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("post");
//        mAdapter = new FirebaseRecyclerAdapter<PostsDataProvider, PostAdapter.PostHolder>(
//                PostsDataProvider.class,
//                R.layout.post_item_layout,
//                PostAdapter.PostHolder.class,
//                ref) {
//            @Override
//            protected void populateViewHolder(PostAdapter.PostHolder holder, PostsDataProvider model, int position) {
//                //Toast.makeText(view.getContext(),"pos "+position,Toast.LENGTH_SHORT).show();
//                Log.e("error","pos "+position);
//                PostsDataProvider postsDataProvider = postArrayList.get(position);
//
//
//                //holder.message.setText(PostsDataProvider.getMessage());
//                //holder.messageSent.setText(PostsDataProvider.getMessage());
//
//
//                if(postsDataProvider.getPhotoUrl()==null) {
//                    holder.postImg.setVisibility(View.GONE);
//                }else {
//                    Glide.with(view.getContext())
//                            .load(postsDataProvider.getPhotoUrl())
//                            .into(holder.postImg);
//                }
//
//                holder.postTitle.setText(postsDataProvider.getTitle());
//                holder.postOverview.setText(postsDataProvider.getDesc().length()>30? postsDataProvider.getDesc().substring(0,30)+"...": postsDataProvider.getDesc());
//
//            }
//        };
//
//        postRecycleView.setAdapter(mAdapter);


        // specify an adapter
        postAdapter = new PostAdapter(postArrayList,getContext());
        postRecycleView.setAdapter(postAdapter);

        postAdapter.notifyDataSetChanged();
        postRecycleView.swapAdapter(postAdapter,false);


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PostsDataProvider post = dataSnapshot.getValue(PostsDataProvider.class);

                    postArrayList.add(post);
                    postAdapter.notifyDataSetChanged();
//                postRecycleView.swapAdapter(postAdapter,false);
                    Log.e("error","added value ");


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        postArrayList.clear();
    }

//    public boolean postExist(PostsDataProvider post) {
//        String desc = post.getTitle();
//        for(int i=0;i<postArrayList.size();i++) {
//            if(desc.equals(postArrayList.get(i).getTitle()))
//                return true;
//        }
//        return false;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mAdapter.cleanup();
        postArrayList.clear();

        Log.e("error","destroyed ");
    }
}
