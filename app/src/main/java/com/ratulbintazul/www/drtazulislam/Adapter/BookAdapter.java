package com.ratulbintazul.www.drtazulislam.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
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
import com.ratulbintazul.www.drtazulislam.Activity.PdfReaderActivity;
import com.ratulbintazul.www.drtazulislam.Activity.ShareActivity;
import com.ratulbintazul.www.drtazulislam.DataProvider.BookDataProvider;
import com.ratulbintazul.www.drtazulislam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSUNG on 9/29/2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder>{
    private ArrayList<BookDataProvider> arrayList;
    private Context context;

    public BookAdapter(ArrayList<BookDataProvider> arrayList, Context ctx) {
        this.arrayList = arrayList;
        this.context = ctx;
    }

    @Override
    public BookAdapter.BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_layout,parent,false);

        BookAdapter.BookHolder BookHolder = new BookAdapter.BookHolder(view);
        return BookHolder;
    }

    @Override
    public void onBindViewHolder(final BookAdapter.BookHolder holder, final int position) {

        final BookDataProvider booksDataProvider = arrayList.get(position);

        //holder.message.setText(BooksDataProvider.getMessage());
        //holder.messageSent.setText(BooksDataProvider.getMessage());

        holder.bookName.setText(booksDataProvider.getName());

        Glide.with(context)
                .load(booksDataProvider.getThumbnail())
                .into(holder.thumbnail);

        holder.bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PdfReaderActivity.class);
                i.putExtra("downloadUrl",booksDataProvider.getPdf());
                context.startActivity(i);
            }
        });

    }

    
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        public TextView bookName;
        public ImageView thumbnail;
        CardView bookCard;
        public BookHolder(final View itemView) {
            super(itemView);
            bookName = (TextView)itemView.findViewById(R.id.bookName);
            thumbnail = (ImageView) itemView.findViewById(R.id.bookThumbnail);
            bookCard = (CardView) itemView.findViewById(R.id.bookcardView);
            
        }


    }
}
