package com.products.safetyfirst.adaptersnew;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.products.safetyfirst.Pojos.PostModel;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.PostDetailActivity;
import com.products.safetyfirst.androidhelpers.PostDocument;
import com.products.safetyfirst.androidhelpers.PostHelper;
import com.products.safetyfirst.utils.JustifiedWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView adapter for a list of Posts.
 */
public class PostAdapter extends FirestoreAdapter<PostAdapter.ViewHolder>{


    private static final int THRESHOLD = 10;
    private int total_count;

    public interface OnPostSelectedListener {

        void onPostSelected(DocumentSnapshot post);

    }

    private OnPostSelectedListener mListener;

    public PostAdapter(OnPostSelectedListener listener, PostHelper.NotifyAdapter notifyAdapter) {
        super(notifyAdapter);
        Log.e("PostAdapter","Post Adapter Constructor called");
        makeQuery();
        mListener = listener;
        total_count = getItemCount();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.discussion_item, parent, false));
    }

    @Override
    public void onBindViewHolder( ViewHolder holder,final int position) {

        //holder.setIsRecyclable(false);
        holder.bind(getSnapshot(position), mListener);
        if(position + THRESHOLD > getItemCount() && total_count <= getItemCount()){

            makeNextSetOfQuery();
            total_count= total_count + THRESHOLD;
        }

    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.postAuthor.setText("   ");
        holder.postBody.setText("  ");
        holder.postTitle.setText("  ");
        //super.onViewRecycled(holder);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.post_image)
        ImageView imageView;

        @BindView(R.id.post_title)
        TextView postTitle;

        @BindView(R.id.post_author)
        TextView postAuthor;

        @BindView(R.id.post_body)
        JustifiedWebView postBody;

        @BindView(R.id.post_author_photo)
        ImageView postAutorPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final PostDocument snapshot,
                         final OnPostSelectedListener listener) {

            final PostModel postModel= new PostModel(snapshot.getPostDocument().getData(),snapshot.getUserDocument().getData()) ;
            Resources resources = itemView.getResources();

            // Load image
           Glide.with(imageView.getContext())
                    .load(postModel.getPhotoUrl())
                    .into(imageView);
            Glide.with(imageView.getContext())
                    .load(postModel.getAuthorImageUrl())
                    .into(postAutorPhoto);

            if(!postModel.getTitle().isEmpty())
                postTitle.setText(postModel.getTitle());
            else
                postTitle.setText("");

            Log.v("postHelper",postModel.getAuthor());
            if(!postModel.getAuthor().isEmpty())
            postAuthor.setText(postModel.getAuthor());
            else
                postAuthor.setText("");

            if(!postModel.getBody().isEmpty()) {
                postBody.setText(postModel.getBody());
            }
            else
                postBody.setText("");

            Log.v("PostHelper",postModel.getTitle()+"  "+postModel.getBody());

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onPostSelected(snapshot.getPostDocument());

                    }
                }
            });
        }
    }

}
