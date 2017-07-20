package com.products.safetyfirst.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.R;
import com.products.safetyfirst.models.News_model;
import com.products.safetyfirst.utils.JustifiedWebView;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_NEWS_KEY = "post_key";
    private static final String TAG = "NewsDetailActivity";
    private ImageView image_scrolling_top;
    private DatabaseReference mPostReference;
    private ValueEventListener mNewsListener;
    private String mPostKey;
    private TextView mTitleView;
    private JustifiedWebView mBodyView;
    private Button mReadMore;
    private ImageButton mShare;
    private ImageView mNewsImage;

    private String url;
    private String HEADLINE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);


        mBodyView = (JustifiedWebView) findViewById(R.id.news_body);
        mTitleView = (TextView) findViewById(R.id.news_title);
        mReadMore = (Button) findViewById(R.id.read_more);
        mShare =(ImageButton) findViewById(R.id.share);

        mPostKey = getIntent().getStringExtra(EXTRA_NEWS_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_NEWS_KEY");
        }
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("news").child(mPostKey);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton bookmark= (FloatingActionButton) findViewById(R.id.bookmark);

        image_scrolling_top = (ImageView) findViewById(R.id.image_scrolling_top);
        Glide.with(this).load(R.drawable.material_design_3).fitCenter().into(image_scrolling_top);

        mReadMore.setOnClickListener(this);
        mShare.setOnClickListener(this);
        bookmark.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the news
        // [START news_value_event_listener]
        ValueEventListener newsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get News object and use the values to update the UI
                News_model news = dataSnapshot.getValue(News_model.class);
                // [START_EXCLUDE]
//                mAuthorView.setText(news.author);
                HEADLINE = news.getTitle();
                mTitleView.setText(news.getTitle());
                mBodyView.setText(news.getBody());
                url = news.getAuthor();
                if(news.getImg_url()!=null){
                    Glide.with(getApplicationContext()).load(news.getImg_url()).fitCenter().into(image_scrolling_top);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting News failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(NewsDetailActivity.this, "Failed to load news.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(newsListener);
        // [END news_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mNewsListener = newsListener;

    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            CollapsingToolbarLayout collapsing_toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
            collapsing_toolbar_layout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.TRANSPARENT));
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mNewsListener != null) {
            mPostReference.removeEventListener(mNewsListener);
        }

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.read_more) {
              Toast.makeText(NewsDetailActivity.this, url, Toast.LENGTH_SHORT).show();
         //   Intent intent = new Intent(this, WebViewActivity.class);
          //  intent.putExtra("Url", url);
          //  startActivity(intent);
        }
        if (i==R.id.share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, url + "\nShared via:Safety First\nhttps://play.google.com/store/apps/details?id=com.vikas.dtu.safetyfirst2");
            startActivity(intent);
        }

        if (i == R.id.bookmark){
            Toast.makeText(NewsDetailActivity.this, "Bookmark", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }
}
