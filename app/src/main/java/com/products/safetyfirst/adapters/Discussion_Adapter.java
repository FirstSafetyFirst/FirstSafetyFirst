package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.products.safetyfirst.R;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.models.Discussion_model;
import com.products.safetyfirst.utils.JustifiedWebView;

import java.util.ArrayList;

/**
 * Created by JHON on 02-Apr-17.
 */

public class Discussion_Adapter extends RecyclerView.Adapter<Discussion_Adapter.DiscussionViewholder> {

   // private List<Discussion_model> horizontalList;

    private final Context context;
    private DatabaseReference mDatabase;
    private ArrayList<Discussion_model> postArrayList = new ArrayList<>();
    private ArrayList<String> postArrayKey =new ArrayList<>();
    private Query postQuery;
    private String mLastkey;
    private ProgressBar mpaginateprogbar;
    ArrayList<Discussion_model> getPost=new ArrayList<>();
    ArrayList<Discussion_model> tempPost=new ArrayList<>();
    ArrayList<String> tempkeys=new ArrayList<>();
    ArrayList<String> getKeys=new ArrayList<>();

public class DiscussionViewholder extends RecyclerView.ViewHolder {

    private ImageView images, overflow,post_author_photo, likeBtn, ansBtn, bookmark ;
    private TextView post_title, dateTime, post_author, post_author_email;
    private JustifiedWebView type_info;
    private Button readMore;

    private DiscussionViewholder(View view) {

        super(view);
        post_author_photo   = (ImageView) view.findViewById(R.id.post_author_photo);
        overflow            = (ImageView) view.findViewById(R.id.overflow);
        likeBtn             = (ImageView) view.findViewById(R.id.LikeBtn);
        ansBtn              = (ImageView) view.findViewById(R.id.ansBtn);
        bookmark            = (ImageView) view.findViewById(R.id.bookmark);
        post_title          = (TextView)  view.findViewById(R.id.post_title);
        dateTime            = (TextView)  view.findViewById(R.id.dateTime);
        post_author         = (TextView)  view.findViewById(R.id.post_author);
        post_author_email   = (TextView)  view.findViewById(R.id.post_author_email);
        readMore            = (Button)    view.findViewById(R.id.view_details);


    }
}


    public Discussion_Adapter(Context cont, Query postQuery,DatabaseReference mDatabase, ProgressBar mpaginateprogbar) {

        this.context=cont;
        this.postQuery = postQuery;
        this.mDatabase = mDatabase;
        this.mpaginateprogbar = mpaginateprogbar;
        postQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                postArrayKey.add(dataSnapshot.getKey());
                postArrayList.add(dataSnapshot.getValue(Discussion_model.class));
                notifyItemInserted(postArrayList.size()-1);
                if(postArrayList.size()==1){
                    mLastkey=dataSnapshot.getKey();
                }

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
    }

    public void getMoreData(){
        tempkeys= postArrayKey;
        tempPost=postArrayList;
        Query Getmorenewsquery=mDatabase.child("posts").orderByKey().endAt(mLastkey).limitToLast(10);
        Getmorenewsquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getPost.add(dataSnapshot.getValue(Discussion_model.class));
                getKeys.add(dataSnapshot.getKey());
                if(getPost.size()==10){
                    getPost.remove(9);
                    getKeys.remove(9);
                    postArrayList=getPost;
                    postArrayKey =getKeys;
                    for(int i=0;i<tempPost.size();i++){
                        postArrayList.add(tempPost.get(i));
                        postArrayKey.add(tempkeys.get(i));
                    }
                    notifyItemRangeInserted(0,9);
                    mpaginateprogbar.setVisibility(View.GONE);
                    getKeys=new ArrayList<>();
                    getPost=new ArrayList<>();
                }
                if(getPost.size()==1){
                    mLastkey=dataSnapshot.getKey();
                }


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

    }


    @Override
    public DiscussionViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discussion_item, parent, false);

        return new DiscussionViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final DiscussionViewholder holder, final int position) {

        String URL="http://fscl01.fonpit.de/userfiles/6727621/image/2016/Nougat/AndroidPIT-Android-N-Nougat-2480.jpg";

        //Glide.with(context).load(URL).transform(new CircleTransform(context)).into(holder.post_author_photo);

        if(postArrayList.get(position).getImg_url() != null)
            Glide.with(context).load(postArrayList.get(position).getImg_url()).into(holder.post_author_photo);

        holder.post_title.setText( postArrayList.get(position).getTitle());
      //  holder.post_author.setText( postArrayList.get(position).getTitle() );
        holder.dateTime.setText("10 May, 2017");
        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, postArrayList.get(position).getImg_url(),Toast.LENGTH_SHORT).show();
            }
        });

        if (position==0){
            mpaginateprogbar.setVisibility(View.VISIBLE);
            getMoreData();
        }
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }
}