package com.ratulbintazul.www.drtazulislam.fragments;


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
import com.ratulbintazul.www.drtazulislam.Adapter.BookAdapter;
import com.ratulbintazul.www.drtazulislam.DataProvider.BookDataProvider;
import com.ratulbintazul.www.drtazulislam.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Books extends Fragment {

    public RecyclerView bookRecycleView;
    public RecyclerView.Adapter bookAdapter;
    public RecyclerView.LayoutManager bookLayoutManager;

    public ArrayList<BookDataProvider> bookArrayList = new ArrayList<>();


    public Books() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        bookRecycleView = (RecyclerView)view.findViewById(R.id.booksRecyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        bookRecycleView.setHasFixedSize(true);


        // use a linear layout manager
        bookLayoutManager = new LinearLayoutManager(getContext());
        bookRecycleView.setLayoutManager(bookLayoutManager);

        // specify an adapter
        bookAdapter = new BookAdapter(bookArrayList,getContext());
        bookRecycleView.setAdapter(bookAdapter);

        bookAdapter.notifyDataSetChanged();
        bookRecycleView.swapAdapter(bookAdapter,false);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("book");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BookDataProvider book = dataSnapshot.getValue(BookDataProvider.class);

                bookArrayList.add(book);
                bookAdapter.notifyDataSetChanged();
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
        bookArrayList.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bookArrayList.clear();
    }
}
