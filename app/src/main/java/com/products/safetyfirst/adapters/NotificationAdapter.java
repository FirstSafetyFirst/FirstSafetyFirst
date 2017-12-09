package com.products.safetyfirst.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.HomeActivity;
import com.products.safetyfirst.activity.NewsDetailActivity;
import com.products.safetyfirst.activity.PostDetailActivity;
import com.products.safetyfirst.Pojos.NotificationModel;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by rishabh on 4/1/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private RealmResults<NotificationModel> items;
    private final Context mContext;
    private final Realm realm;

    public NotificationAdapter(Context context, Realm realmInstance) {
        this.mContext = context;
        this.realm = realmInstance;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<NotificationModel> query = realm.where(NotificationModel.class);
                items = query.findAll();
            }
        });
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        final NotificationModel notif = items.get(getItemCount() - position - 1);
        holder.bodyView.setText(notif.getBody());
        if(notif.getTitle() != null)
            holder.titleView.setText(notif.getTitle());
        else {
            holder.titleView.setText("Safety First");
        }
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(notif.getType() == NotificationModel.NEWS || notif.getType() == NotificationModel.NEWS_WITH_IMAGE) {
                    intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra(NewsDetailActivity.EXTRA_NEWS_KEY, notif.getExtraString());
                }
                else if(notif.getType() == NotificationModel.COMMENT_ON_POST){
                    intent = new Intent(mContext, PostDetailActivity.class);
                    intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, notif.getExtraString());
                } else {
                    intent = new Intent(mContext, HomeActivity.class);
                }
                intent.putExtra("fromNotification", notif.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        public final TextView bodyView;
        public final View mainView;
        public final TextView titleView;
        //TODO add upvotes and downvotes round_blue_dark


        public NotificationViewHolder(View itemView) {
            super(itemView);

            mainView = itemView;
            bodyView = itemView.findViewById(R.id.notification_descr);
            titleView = itemView.findViewById(R.id.notification_title);
        }
    }
}
