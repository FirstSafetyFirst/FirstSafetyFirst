package com.products.safetyfirst.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.fragment.ItemsFragments.EventInfoFragment;
import com.products.safetyfirst.fragment.ItemsFragments.EventVideoFragment;
import com.products.safetyfirst.fragment.ItemsFragments.VisitorsListFragment;
import com.products.safetyfirst.impementations.presenter.EventsDetailPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.EventsDetailPresenter;
import com.products.safetyfirst.interfaces.view.EventsDetailView;
import com.products.safetyfirst.models.EventModel;
import com.products.safetyfirst.utils.Analytics;
import com.products.safetyfirst.utils.JustifiedWebView;
import com.products.safetyfirst.utils.PrefManager;

import jp.wasabeef.richeditor.RichEditor;

import static com.products.safetyfirst.utils.Constants.GOING;
import static com.products.safetyfirst.utils.Constants.INTERESTED;

@SuppressWarnings({"ALL", "EmptyMethod"})
public class EventsDetailActivity extends BaseActivity implements View.OnClickListener, EventsDetailView {

    public static final String EXTRA_EVENT_KEY = "event_key";

    private EventsDetailPresenter presenter;

    private FloatingActionButton fab,fab1;
    private ImageView image_scrolling_top;
    private String mEventKey;
    private TextView mTitleView;
    private JustifiedWebView mBodyView;
    private ImageButton mShare, mBookmark;
    private Button mInterested;
    private String url;
    private String HEADLINE;
    private ActionBar actionBar;

    private String deepLink;

    private TabLayout tabs;
    private ViewPager viewPager;
    private ImageView mainImage;
    private TabLayout.OnTabSelectedListener tabSelectedListener;
    private TabLayout.TabLayoutOnPageChangeListener pageChangeListener;

    private EventModel event;

    boolean tabsSetup = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_events_detail);

        mainImage = findViewById(R.id.main_image);
        mInterested = findViewById(R.id.interested);
        mShare = findViewById(R.id.share);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Event Details");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);



        mEventKey = getIntent().getStringExtra(EXTRA_EVENT_KEY);
        if (mEventKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_EVENT_KEY");
        }
      //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        mInterested.setOnClickListener(this);
        mShare.setOnClickListener(this);

        presenter = new EventsDetailPresenterImpl(this, mEventKey);
        presenter.requestEvent();

        PrefManager prefManager = new PrefManager(this);
        if (prefManager.isFirstEventsLaunch()) {
            showTutorial();
        }

        prefManager.setFirstEventsLaunch(false);
    }

    private void setupTabs() {

        if(tabsSetup) return;
        tabsSetup = true;
        String tab_texts[] = {"Information", "Visitors", "Video"};
        Integer images[] = {R.drawable.ic_info_black_24dp,
                R.drawable.ic_playlist_add_check_black_24dp,
                R.drawable.ic_videocam_black_24dp};
        View tab_layouts[] = new View[4];

        tabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        };
        pageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(tabs) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                TabLayout.Tab tab = tabs.getTabAt(position);
                tab.select();
            }

        };

        for(int i = 0; i < 3; i++) {
            tab_layouts[i] = getLayoutInflater().inflate(R.layout.item_type_info_tabs, null);
            ((TextView)tab_layouts[i].findViewById(R.id.tabs_text)).setText(tab_texts[i]);
            ((ImageView)tab_layouts[i].findViewById(R.id.tabs_image)).setImageDrawable(getResources().getDrawable(images[i]));
            tabs.addTab(tabs.newTab().setCustomView(tab_layouts[i]));
        }

        final Bundle args = new Bundle();

        final Fragment fragments[] = {
                new EventInfoFragment(),
                new VisitorsListFragment(),
                new EventVideoFragment() };
        for(Fragment fragment: fragments){
            fragment.setArguments(args);
        }

        FragmentPagerAdapter categoryAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            final String[] titles = {"Info", "Visitors", "Video"};
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

        };
        viewPager.setAdapter(categoryAdapter);
        viewPager.setCurrentItem(0);
        tabs.getTabAt(0).select();

        viewPager.addOnPageChangeListener(pageChangeListener);
        tabs.addOnTabSelectedListener(tabSelectedListener);
    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.going:
                setAction(GOING);
                break;
            case R.id.interested:
                //setAction(INTERESTED);

                AlertDialog.Builder builder = new AlertDialog.Builder(EventsDetailActivity.this);
                builder.setTitle(R.string.pick_avatar)
                        .setItems(R.array.avatas_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.setAction(which);
                            }
                        });
                builder.show();

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
        if (deepLink != null) {
            Intent intent = new Intent();
            String msg = "Hey see this Event: " + deepLink;
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            intent.setType("text/plain");
            startActivity(intent);

        }
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
    public void setEvent(EventModel event) {

        Analytics.logEventViewItem(getApplicationContext(),event.getTimestamp().toString(),event.getTitle(),"event");

        this.event = event;
        this.deepLink = event.getDeeplink();

        if (actionBar != null) {
            actionBar.setTitle(event.getTitle());
        }

        setupTabs();

        ImageView main_image = findViewById(R.id.main_image);
        if(event.getThumbUrl() != null ) Glide.with(getApplicationContext()).load(event.getThumbUrl()).fitCenter().into(main_image);

    }

    @Override
    public void onError(String string) {
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }


    private void showTutorial(){
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(pageChangeListener);
        tabs.removeOnTabSelectedListener(tabSelectedListener);
    }

    public String getEventInfo(){
        if(event != null) return event.getDesc();
        return null;
    }

    public String getVisitors(){
        if(event != null) return event.getVisitors();
        return null;
    }

    public String getEventVideo(){
        if(event != null) return event.getVideo();
        return null;
    }
}
