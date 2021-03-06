package com.products.safetyfirst.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.products.safetyfirst.R;
import com.products.safetyfirst.impementations.presenter.NewsDetailPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.NewsDetailPresenter;
import com.products.safetyfirst.interfaces.view.NewsDetailView;
import com.products.safetyfirst.Pojos.NewsModel;
import com.products.safetyfirst.utils.Analytics;
import com.products.safetyfirst.utils.JustifiedWebView;
import com.products.safetyfirst.utils.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings({"ALL", "EmptyMethod"})
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener, NewsDetailView {

    public static final String EXTRA_NEWS_KEY = "post_key";
    private static final String TAG = "NewsDetailActivity";

    private NewsDetailPresenter presenter;

    @BindView(R.id.bookmark)
    FloatingActionButton fab;

    @BindView(R.id.image_scrolling_top)
    ImageView image_scrolling_top;

    @BindView(R.id.title)
    TextView mTitleView;

    @BindView(R.id.body)
    JustifiedWebView mBodyView;

    @BindView(R.id.read_more)
    Button mReadMore;

    @BindView(R.id.share)
    ImageButton mShare;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbar;

    private String url;
    private String deepLink = null;
    private String HEADLINE;
    private String mNewsKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ButterKnife.bind(this);

        mCollapsingToolbar.setTitle("");

        mNewsKey = getIntent().getStringExtra(EXTRA_NEWS_KEY);
        if (mNewsKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_NEWS_KEY");
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Glide.with(this).load(R.drawable.ic_launcher).fitCenter().into(image_scrolling_top);

        mReadMore.setOnClickListener(this);
        mShare.setOnClickListener(this);

        presenter = new NewsDetailPresenterImpl(this, mNewsKey);
        presenter.requestNews();
        PrefManager prefManager = new PrefManager(this);
        if (prefManager.isFirstNewsLaunch()) {
            showTutorial();
        }

        prefManager.setFirstNewsLaunch(false);

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

            CollapsingToolbarLayout collapsing_toolbar_layout = findViewById(R.id.collapsing_toolbar_layout);
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

    @OnClick(R.id.bookmark)
    @Override
    public void setBookMark() {
        if(isLoggedIn()) {
            presenter.setBookMark(mNewsKey);
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
        if (deepLink != null) {
            Intent intent = new Intent();
            String msg = "Hey see this News: " + deepLink;
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            intent.setType("text/plain");
            startActivity(intent);

        }
    }

    @Override
    public void readMore() {
          Intent intent = new Intent(this, WebViewActivity.class);
          intent.putExtra("Url", url);
          startActivity(intent);
    }

    @Override
    public void setNews(NewsModel news) {
        HEADLINE = news.getTitle();
        mTitleView.setText(news.getTitle());
        mBodyView.setText(news.getBody());
        url = news.getAuthor();
        deepLink = news.getDeeplink();
        Analytics.logEventViewItem(getApplicationContext(),mTitleView.getText().toString(),HEADLINE,"news");

        if (news.bookmarks.containsKey(getCurrentUserId())) {
            fab.setImageResource(R.drawable.ic_bookmark_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }

        if (news.getImgUrl() != null) {

            Glide.with(getApplicationContext())
                    .load(news.getImgUrl())
                    .asBitmap()
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>(100,100) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                            image_scrolling_top.setImageBitmap(bitmap); // Possibly runOnUiThread()
                        }
                    });

        }
    }


    private void showTutorial(){

        final Display display = getWindowManager().getDefaultDisplay();
        final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_camera_alt_black_24dp);
        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

        final SpannableString sassyDesc = new SpannableString("You can bookmark news for future reference");
        sassyDesc.setSpan(new StyleSpan(Typeface.ITALIC), sassyDesc.length() - "bookmark news".length(), sassyDesc.length(), 0);

        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar
                        TapTarget.forView(fab, "This is the bookmark button", sassyDesc).id(1),
                        // Likewise, this tap target will target the search button
                        TapTarget.forView(mReadMore, "This is the Read button", "It takes you to the website where this news came from...")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                             //   .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .id(2),
                        // You can also target the overflow button in your toolbar
                        TapTarget.forView(mShare, "This is the Share button", "You can share news with your friends !!")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .id(3)
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                      //  Toast.makeText(NewsDetailActivity.this, "You are educated now", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        final AlertDialog dialog = new AlertDialog.Builder(NewsDetailActivity.this)
                                .setTitle("Uh oh")
                                .setMessage("You canceled the sequence")
                                .setPositiveButton("Oops", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                        .cancelable(false)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

        // You don't always need a sequence, and for that there's a single time tap target
        final SpannableString spannedDesc = new SpannableString("Hey!! You can see the News Details here");
        spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "News Details".length(), spannedDesc.length(), 0);
        TapTargetView.showFor(this, TapTarget.forBounds(droidTarget, "News Details", spannedDesc)
                .cancelable(false)
                .drawShadow(true)
                .titleTextDimen(R.dimen.title_text_size)
                .tintTarget(false), new TapTargetView.Listener() {
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);
                sequence.start();
            }

            @Override
            public void onOuterCircleClick(TapTargetView view) {
                super.onOuterCircleClick(view);
              //  Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                Log.d("TapTargetViewSample", "You dismissed me :(");
            }
        });


    }

}
