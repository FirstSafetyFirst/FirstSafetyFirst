package com.products.safetyfirst.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.impementations.EventsDetailPresenterImpl;
import com.products.safetyfirst.interfaces.EventsDetailPresenter;
import com.products.safetyfirst.interfaces.EventsDetailView;
import com.products.safetyfirst.models.Event_model;
import com.products.safetyfirst.utils.JustifiedWebView;

import static com.products.safetyfirst.utils.Constants.GOING;
import static com.products.safetyfirst.utils.Constants.INTERESTED;

public class EventsDetailActivity extends BaseActivity implements View.OnClickListener, EventsDetailView {

    public static String EXTRA_EVENT_KEY = "event_key";

    EventsDetailPresenter presenter;

    private FloatingActionButton fab,fab1;
    private ImageView image_scrolling_top;
    private String mEventKey;
    private TextView mTitleView;
    private JustifiedWebView mBodyView;
    private ImageButton mShare, mBookmark;
    private String url;
    private String HEADLINE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_detail);


        mBodyView = (JustifiedWebView) findViewById(R.id.body);
        mTitleView = (TextView) findViewById(R.id.title);
        mBookmark = (ImageButton) findViewById(R.id.bookmark);
        mShare =(ImageButton) findViewById(R.id.share);

        mEventKey = getIntent().getStringExtra(EXTRA_EVENT_KEY);
        if (mEventKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_EVENT_KEY");
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        image_scrolling_top = (ImageView) findViewById(R.id.image_scrolling_top);
        Glide.with(this).load(R.mipmap.ic_launcher).fitCenter().into(image_scrolling_top);

        mBookmark.setOnClickListener(this);
        mShare.setOnClickListener(this);

        fab = (FloatingActionButton)findViewById(R.id.going);
        fab1 = (FloatingActionButton)findViewById(R.id.interested);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);

        presenter = new EventsDetailPresenterImpl(this, mEventKey);
        presenter.requestEvent();
    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.going:
                setAction(GOING);
                break;
            case R.id.interested:
                setAction(INTERESTED);
                break;
            case R.id.share:
                share();
                break;
            case R.id.bookmark:
                setBookMark();
                break;

        }
    }

    @Override
    public void setBookMark() {
        if(isLoggedIn()) {
            presenter.setBookMark(mEventKey);
        }
        else{
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    R.string.not_signed_in, Snackbar.LENGTH_SHORT);
            mySnackbar.setAction(R.string.signIn, new MySignInListener(getBaseContext()));
            mySnackbar.show();
        }
    }


    @Override
    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, url + "\nShared via:Safety First\nhttps://play.google.com/store/apps/details?id=com.vikas.dtu.safetyfirst2");
        startActivity(intent);
    }

    @Override
    public void setAction(int actionId) {
        if(isLoggedIn()) {
            presenter.setAction(actionId);
        }
        else{
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    R.string.not_signed_in, Snackbar.LENGTH_SHORT);
            mySnackbar.setAction(R.string.signIn, new MySignInListener(getBaseContext()));
            mySnackbar.show();
        }

    }

    @Override
    public void setEvent(Event_model event) {
       if(event.getTitle() != null) mTitleView.setText(event.getTitle());
       if(event.getDesc() != null ) mBodyView.setText(event.getDesc());
       if(event.getUrl() != null ) Glide.with(getApplicationContext()).load(event.getUrl()).fitCenter().into(image_scrolling_top);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }
}
