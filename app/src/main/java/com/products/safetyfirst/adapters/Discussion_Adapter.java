package com.products.safetyfirst.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.PostDetailActivity;
import com.products.safetyfirst.activity.ProfileActivity;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.models.UserModel;
import com.products.safetyfirst.utils.JustifiedWebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Discussion_Adapter extends RecyclerView.Adapter<Discussion_Adapter.DiscussionViewholder> {

    private static final String TAG = "DISCUSSION_MODEL";

    private final Context context;
    private DatabaseReference mDatabase;
    private ArrayList<PostModel> postArrayList = new ArrayList<>();
    private ArrayList<String> postArrayKey = new ArrayList<>();
    private Query postQuery;
    private String key;
    private String mLastkey;
    private ProgressBar mpaginateprogbar;
    private ArrayList<PostModel> getPost = new ArrayList<>();
    private ArrayList<PostModel> tempPost = new ArrayList<>();
    private ArrayList<String> tempkeys = new ArrayList<>();
    private ArrayList<String> getKeys = new ArrayList<>();
    private Map<String, UserModel> userMap = new HashMap<>();

    public Discussion_Adapter(Context cont, Query postQuery, DatabaseReference mDatabase, ProgressBar mpaginateprogbar) {

        this.context = cont;
        this.postQuery = postQuery;
        this.mDatabase = mDatabase;
        this.mpaginateprogbar = mpaginateprogbar;
        postQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                postArrayKey.add(dataSnapshot.getKey());
                postArrayList.add(dataSnapshot.getValue(PostModel.class));

                /* add user */
                addUser(dataSnapshot.getValue(PostModel.class).getUid(), postArrayList.size() - 1, postArrayList.size() - 1);

                notifyItemInserted(postArrayList.size() - 1);
                if (postArrayList.size() == 1) {
                    mLastkey = dataSnapshot.getKey();
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

    private void getMoreData() {
        tempkeys = postArrayKey;
        tempPost = postArrayList;
        Query Getmorenewsquery = mDatabase.child("posts").orderByKey().endAt(mLastkey).limitToLast(10);
        Getmorenewsquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getPost.add(dataSnapshot.getValue(PostModel.class));
                getKeys.add(dataSnapshot.getKey());

                /* add user */
                addUser(dataSnapshot.getValue(PostModel.class).getUid(), 0, 9);

                if (getPost.size() == 10) {
                    getPost.remove(9);
                    getKeys.remove(9);
                    postArrayList = getPost;
                    postArrayKey = getKeys;
                    for (int i = 0; i < tempPost.size(); i++) {
                        postArrayList.add(tempPost.get(i));
                        postArrayKey.add(tempkeys.get(i));
                    }
                    notifyItemRangeInserted(0, 9);
                    mpaginateprogbar.setVisibility(View.GONE);
                    getKeys = new ArrayList<>();
                    getPost = new ArrayList<>();
                }
                if (getPost.size() == 1) {
                    mLastkey = dataSnapshot.getKey();
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
    public void onBindViewHolder(final DiscussionViewholder holder,final int position) {


        if (postArrayList.get(position).getAuthorImageUrl() != null && postArrayList.get(position).getAuthorImageUrl() != "") {
            Glide.with(context).load(postArrayList.get(position).getAuthorImageUrl()).error(R.drawable.ic_person_black_24dp).transform(new CircleTransform(context)).into(holder.post_author_photo);
            Log.e("DiscussionAdapter", "Loaded Image");
        }
        else {
            holder.post_author_photo.setImageResource(R.drawable.ic_person_black_24dp);
            Log.e("DiscussionAdapter", "No Image on Post :: " + postArrayList.get(position).getUid());
        }

        if(postArrayList.get(position).getTitle() != null)
            holder.post_title.setText(postArrayList.get(position).getTitle());

        if(userMap.containsKey(postArrayList.get(position).getUid())) {
            holder.post_author.setText(userMap.get(postArrayList.get(position).getUid()).getUsername());
            holder.post_author_email.setText(userMap.get(postArrayList.get(position).getUid()).getEmail());
        }
            //holder.post_author.setText(postArrayList.get(position).getAuthor());

        if(postArrayList.get(position).getDesc() != null)
            holder.body.setText(postArrayList.get(position).getDesc());


        if (postArrayList.get(position).getStars().containsKey(getUid())) {
            holder.likeBtn.setImageResource(R.drawable.ic_thumb_up_black_24dp);
        } else {
            holder.likeBtn.setImageResource(R.drawable.ic_thumbs_up_down_black_24dp);
        }


        //  holder.post_author.setText( postArrayList.get(position).getTitle() );
        holder.dateTime.setText("10 May, 2017");
        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, postArrayList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postArrayKey.get(position));
                context.startActivity(intent);
                Log.d(TAG,String.valueOf(position) + String.valueOf(postArrayList.get(position).getDesc())+String.valueOf(postArrayList.get(position).getTitle()));
            }
        });

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getUid() == null) {
                    Snackbar.make(v, "Please SIGN IN to like this post", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    key = postArrayKey.get(position);
                    DatabaseReference globalPostRef = mDatabase.child("posts").child(key);
                    DatabaseReference userPostRef = mDatabase.child("user-posts").child(postArrayList.get(position).getUid()).child(key);

                    // Run two transactions
                    onLikeClicked(globalPostRef);
                    onLikeClicked(userPostRef);
                }

            }
        });

        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getUid() == null) {
                    Snackbar.make(v, "Please SIGN IN to bookmark this post", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    key = postArrayKey.get(position);
                    DatabaseReference globalPostRef = mDatabase.child("posts").child(key);
                    DatabaseReference userPostRef = mDatabase.child("user-posts").child(postArrayList.get(position).getUid()).child(key);

                    // Run two transactions
                    onBookMarkClicked(globalPostRef);
                    onBookMarkClicked(userPostRef);
                }

            }
        });

        holder.post_author_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra(ProfileActivity.EXTRA_PROFILE_KEY, postArrayList.get(position).getUid());
                    context.startActivity(intent);



            }
        });

        if (position == 0) {
            mpaginateprogbar.setVisibility(View.VISIBLE);
            getMoreData();
        }
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    private void onLikeClicked(DatabaseReference postRef) {


        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                PostModel p = mutableData.getValue(PostModel.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.getStars().containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.setStarCount(p.getStarCount() - 1);
                    Map<String, Boolean> stars = p.getStars();
                    stars.remove(getUid());

                    p.setStars(stars);

                } else {
                    // Star the post and add self to stars
                    p.setStarCount(p.getStarCount() + 1);
                    Map<String, Boolean> stars = p.getStars();
                    stars.put(getUid(), true);
                    p.setStars(stars);

                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });

    }

    private void onBookMarkClicked(DatabaseReference postRef) {
    }

    void addUser(final String uid, final int notifyStart, final int notifyEnd) {
        if(!userMap.containsKey(uid)){
            Log.e("DiscussionAdapter", "Adding User " + uid);
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    userMap.put(uid, user);
                    notifyItemRangeChanged(notifyStart, notifyEnd);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Nullable
    private String getUid() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            return FirebaseAuth.getInstance().getCurrentUser().getUid();

        else return null;
    }

    public class DiscussionViewholder extends RecyclerView.ViewHolder {

        private ImageView images, overflow, post_author_photo, likeBtn, ansBtn, bookmark;
        private TextView post_title, dateTime, post_author, post_author_email;
        private JustifiedWebView body;
        private Button readMore;
        private LinearLayout post_author_layout;

        private DiscussionViewholder(View view) {

            super(view);
            post_author_photo = (ImageView) view.findViewById(R.id.post_author_photo);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            likeBtn = (ImageView) view.findViewById(R.id.LikeBtn);
            ansBtn = (ImageView) view.findViewById(R.id.ansBtn);
            bookmark = (ImageView) view.findViewById(R.id.bookmark);
            post_title = (TextView) view.findViewById(R.id.post_title);
            body = (JustifiedWebView) view.findViewById(R.id.type_info);
            dateTime = (TextView) view.findViewById(R.id.dateTime);
            post_author = (TextView) view.findViewById(R.id.post_author);
            post_author_email = (TextView) view.findViewById(R.id.post_author_email);
            readMore = (Button) view.findViewById(R.id.view_details);
            post_author_layout = (LinearLayout) view.findViewById(R.id.post_author_layout);


        }
    }
}