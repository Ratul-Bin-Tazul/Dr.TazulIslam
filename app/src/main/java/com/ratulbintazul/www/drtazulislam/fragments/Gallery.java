package com.ratulbintazul.www.drtazulislam.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ratulbintazul.www.drtazulislam.Adapter.MediaAdapter;
import com.ratulbintazul.www.drtazulislam.Adapter.MediaAdapter;
import com.ratulbintazul.www.drtazulislam.DataProvider.MediaDataProvider;
import com.ratulbintazul.www.drtazulislam.DataProvider.MediaDataProvider;
import com.ratulbintazul.www.drtazulislam.R;

import java.util.ArrayList;


public class Gallery extends Fragment {

    public RecyclerView mediaRecycleView;
    public RecyclerView.Adapter mediaAdapter;
    public RecyclerView.LayoutManager mediaLayoutManager;

    public ArrayList<MediaDataProvider> mediaArrayList = new ArrayList<>();

    DatabaseReference mediaRef;
    
    public Gallery() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        mediaRecycleView = (RecyclerView)view.findViewById(R.id.mediaRecyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mediaRecycleView.setHasFixedSize(true);


        // use a linear layout manager
        mediaLayoutManager = new LinearLayoutManager(getContext());
        mediaRecycleView.setLayoutManager(mediaLayoutManager);

        // specify an adapter
        mediaAdapter = new MediaAdapter(mediaArrayList,getContext());
        mediaRecycleView.setAdapter(mediaAdapter);

        mediaAdapter.notifyDataSetChanged();
        mediaRecycleView.swapAdapter(mediaAdapter,false);

        mediaRef = FirebaseDatabase.getInstance().getReference().child("media");

        // specify an adapter
        mediaAdapter = new MediaAdapter(mediaArrayList,getContext());
        mediaRecycleView.setAdapter(mediaAdapter);

        mediaAdapter.notifyDataSetChanged();
        mediaRecycleView.swapAdapter(mediaAdapter,false);


        mediaRef = FirebaseDatabase.getInstance().getReference().child("media");

        mediaRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MediaDataProvider mediaDataProvider = dataSnapshot.getValue(MediaDataProvider.class);
                mediaArrayList.add(mediaDataProvider);
                mediaAdapter.notifyDataSetChanged();
                mediaRecycleView.swapAdapter(mediaAdapter,false);
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
        mediaArrayList.clear();
    }
}
