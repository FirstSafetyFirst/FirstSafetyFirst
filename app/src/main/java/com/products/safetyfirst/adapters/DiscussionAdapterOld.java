package com.products.safetyfirst.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.products.safetyfirst.R;
<<<<<<< HEAD
import com.products.safetyfirst.activity.PostDetailActivity;
import com.products.safetyfirst.activity.ProfileActivity;
import com.products.safetyfirst.models.PostModel;
||||||| merged common ancestors
import com.products.safetyfirst.activity.PostDetailActivity;
import com.products.safetyfirst.activity.ProfileActivity;
import com.products.safetyfirst.models.NewsModel;
import com.products.safetyfirst.models.PostModel;
=======
import com.products.safetyfirst.Pojos.PostModel;
>>>>>>> 61fdc84e51e37e24cc10abc02f412f867bf7210e
import com.products.safetyfirst.viewholder.PostViewHolder;

import java.util.ArrayList;

/**
 * Created by vikas on 09/11/17.
 */

public class DiscussionAdapterOld  extends RecyclerView.Adapter<PostViewHolder> {

    private final Context context;
    private ArrayList<PostModel> postModels = new ArrayList<>();
    private ArrayList<PostModel> tempPostModels = new ArrayList<>();
    private ArrayList<String> tempkeys = new ArrayList<>();
    private ArrayList<String> getKeys = new ArrayList<>();
    private final DatabaseReference mDatabase;
    private ArrayList<PostModel> postArrayList = new ArrayList<>();
    private ArrayList<String> postArrayKey =new ArrayList<>();
    private final Query newsquery;
    private String mLastkey;
    private final ProgressBar mpaginateprogbar;

    public DiscussionAdapterOld(Context cont, Query query, DatabaseReference mDatabase, ProgressBar mpaginateprogbar ) {
        this.context=cont;
        this.newsquery = query;
        this.mDatabase = mDatabase;
        this.mpaginateprogbar = mpaginateprogbar;
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                postArrayKey.add(dataSnapshot.getKey());
                postArrayList.add(dataSnapshot.getValue(PostModel.class));
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

    private void getMoreData(){
        tempkeys= postArrayKey;
        tempPostModels = postArrayList;
        Query getMorePostsQuery=mDatabase.child("posts").orderByKey().endAt(mLastkey).limitToLast(10);
        getMorePostsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                postModels.add(dataSnapshot.getValue(PostModel.class));
                getKeys.add(dataSnapshot.getKey());
                if(postModels.size()==10){
                    postModels.remove(9);
                    getKeys.remove(9);
                    postArrayList = postModels;
                    postArrayKey =getKeys;
                    for(int i = 0; i< tempPostModels.size(); i++){
                        postArrayList.add(tempPostModels.get(i));
                        postArrayKey.add(tempkeys.get(i));
                    }
                    notifyItemRangeInserted(0,9);
                    mpaginateprogbar.setVisibility(View.GONE);
                    getKeys=new ArrayList<>();
                    postModels =new ArrayList<>();
                }
                if(postModels.size()==1){
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
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discussion_item, parent, false);

        return new PostViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {

        /*//  Glide.with(context).load(postArrayList.get(position).getImg_url()).into(holder.images);
        if(postArrayList.get(position).getImage()!= null)
            Glide.with(context).load(postArrayList.get(position).getImage()).into(holder.postImage);

        holder.post_title.setText( postArrayList.get(position).getTitle());
        holder.body.setText(postArrayList.get(position).getBody());

        if(postArrayList.get(position).getAuthorImageUrl()!= null)
            Glide.with(context).load(postArrayList.get(position).getAuthorImageUrl()).into(holder.postImage);

        if(postArrayList.get(position).getAuthor() != null)
            holder.post_author.setText(postArrayList.get(position).getAuthor() );



/**        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postArrayKey.get(position));
                context.startActivity(intent);

            }
        });
<<<<<<< HEAD
        **/
       holder.post_author_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_PROFILE_KEY, postArrayList.get(position).getAuthor());
                context.startActivity(intent);
||||||| merged common ancestors
      /*  holder.post_author_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_PROFILE_KEY, postArrayList.get(position).getAuthor());
                context.startActivity(intent);
=======
>>>>>>> 61fdc84e51e37e24cc10abc02f412f867bf7210e

<<<<<<< HEAD
            }
        });

       holder.postCardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(context, PostDetailActivity.class);
               intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postArrayKey.get(position));
               context.startActivity(intent);
           }
       });
||||||| merged common ancestors
            }
        });
*/
=======
>>>>>>> 61fdc84e51e37e24cc10abc02f412f867bf7210e
        if (position==0){
            mpaginateprogbar.setVisibility(View.VISIBLE);
            getMoreData();
        }*/
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

}