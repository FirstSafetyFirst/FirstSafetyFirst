package com.products.safetyfirst.adaptersnew;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.Query;
import com.products.safetyfirst.Pojos.Comment;
import com.products.safetyfirst.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * RecyclerView adapter for a list of {@link Comment}.
 */
public class CommentAdapter extends FirestoreAdapter<CommentAdapter.ViewHolder> {

    public CommentAdapter(Query query) {
        super(query);
        makeQuery(query);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getSnapshot(position).getPostDocument().toObject(Comment.class));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private static final SimpleDateFormat FORMAT  = new SimpleDateFormat(
                "MM/dd/yyyy", Locale.US);

        @BindView(R.id.user_name)
        TextView nameView;

        @BindView(R.id.rating_item_rating)
        MaterialRatingBar ratingBar;

        @BindView(R.id.comment_body)
        TextView textView;

        @BindView(R.id.rating_item_date)
        TextView dateView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Comment comment) {
            nameView.setText(comment.getAuthor());
            textView.setText(comment.getText());

            if (comment.getTimestamp() != null) {
                dateView.setText(FORMAT.format(comment.getTimestamp()));
            }
        }
    }

}
