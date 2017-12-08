package com.products.safetyfirst.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.EventsDetailActivity;
import com.products.safetyfirst.impementations.presenter.EventsPresenterImpl;
import com.products.safetyfirst.interfaces.adapter.EventsAdapterView;
import com.products.safetyfirst.interfaces.presenter.EventsPresenter;
import com.products.safetyfirst.models.EventModel;
import com.products.safetyfirst.viewholder.EventViewHolder;

import java.util.ArrayList;

/**
 * Created by VIKAS on 11-Oct-17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> implements EventsAdapterView{


    private final ArrayList<EventModel> mEventsList = new ArrayList<>();
    private final ArrayList<String> mKeysList = new ArrayList<>();
    private final EventsPresenter presenter;
    private final Context context;
    private final String mUserId;

    public EventsAdapter(Context context, String userId){
        this.presenter = new EventsPresenterImpl(this);
        this.context = context;
        this.mUserId = userId;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);

        return new EventViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final EventViewHolder holder, final int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        holder.mView.startAnimation(animation);

        EventModel event = mEventsList.get(position);

        if(event.getTitle() != null) holder.title.setText(event.getTitle());
        if(event.getTimestamp() != null) {
          //  SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
          //  sfd.format(new Date(timestamp))
            holder.dateTime.setText(DateUtils.getRelativeTimeSpanString(
                    (long) event.getTimestamp()).toString());
        }



        if(event.action != null && event.action.containsKey(mUserId)){
            switch (String.valueOf(event.action.get(mUserId))){
                case "1": //going
                    holder.going.setText("Maybe you will go");
                    break;
                case "0": //not going
                    holder.maybe.setText("You are not going");
                    break;
                default:
                    holder.going.setText("Going");
                    holder.maybe.setText("Not Going");
                    break;
            }
        }

        holder.eventCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventsDetailActivity.class);
                intent.putExtra(EventsDetailActivity.EXTRA_EVENT_KEY, mKeysList.get(position));
                context.startActivity(intent);
            }
        });
/**
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventsDetailActivity.class);
                intent.putExtra(EventsDetailActivity.EXTRA_EVENT_KEY, mKeysList.get(position));
                context.startActivity(intent);

            }
        });
**/

    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    @Override
    public void addAllEvents(ArrayList<EventModel> events) {
        mEventsList.clear();
        mEventsList.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public void addAllKeys(ArrayList<String> keys) {
        mKeysList.clear();
        mKeysList.addAll(keys);
    }

    @Override
    public void request() {
        presenter.request();
    }

}