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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.R;
import com.products.safetyfirst.impementations.NewsDetailPresenterImpl;
import com.products.safetyfirst.interfaces.NewsDetailPresenter;
import com.products.safetyfirst.interfaces.NewsDetailView;
import com.products.safetyfirst.models.News_model;
import com.products.safetyfirst.utils.JustifiedWebView;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener, NewsDetailView {
    public static final String EXTRA_NEWS_KEY = "post_key";
    private static final String TAG = "NewsDetailActivity";
    NewsDetailPresenter presenter;
    FloatingActionButton fab;
    private ImageView image_scrolling_top;
    private DatabaseReference mPostReference;
    private ValueEventListener mNewsListener;
    private String mNewsKey;
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

        mNewsKey = getIntent().getStringExtra(EXTRA_NEWS_KEY);
        if (mNewsKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_NEWS_KEY");
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fab = (FloatingActionButton) findViewById(R.id.bookmark);

        image_scrolling_top = (ImageView) findViewById(R.id.image_scrolling_top);
        Glide.with(this).load(R.mipmap.ic_launcher).fitCenter().into(image_scrolling_top);

        mReadMore.setOnClickListener(this);
        mShare.setOnClickListener(this);
        fab.setOnClickListener(this);

        presenter = new NewsDetailPresenterImpl(this, mNewsKey);
        presenter.requestNews();
    }

    @Override
    public void onStart() {
        super.onStart();
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
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.read_more) {
            readMore();
        }
        if (i==R.id.share) {
            share();
        }

        if (i == R.id.bookmark){
            setBookMark();
        }
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void setBookMark() {
        presenter.setBookMark(mNewsKey);
    }

    @Override
    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, url + "\nShared via:Safety First\nhttps://play.google.com/store/apps/details?id=com.vikas.dtu.safetyfirst2");
        startActivity(intent);
    }

    @Override
    public void readMore() {
        Toast.makeText(NewsDetailActivity.this, url, Toast.LENGTH_SHORT).show();
        //   Intent intent = new Intent(this, WebViewActivity.class);
        //  intent.putExtra("Url", url);
        //  startActivity(intent);
    }

    @Override
    public void setNews(News_model news) {
        HEADLINE = news.getTitle();
        mTitleView.setText(news.getTitle());
        mBodyView.setText(news.getBody());
        url = news.getAuthor();

        if (news.bookmarks.containsKey(getCurrentUserId())) {
            fab.setImageResource(R.drawable.ic_bookmark_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }

        if (news.getImgUrl() != null) {
            Glide.with(getApplicationContext()).load(news.getImgUrl()).fitCenter().into(image_scrolling_top);

        }
    }
}
