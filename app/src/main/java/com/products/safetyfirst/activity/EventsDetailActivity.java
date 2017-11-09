package com.products.safetyfirst.activity;

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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
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
import com.products.safetyfirst.utils.JustifiedWebView;
import com.products.safetyfirst.utils.PrefManager;

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
    ActionBar actionBar;

    private TabLayout tabs;
    private ViewPager viewPager;
    private ImageView mainImage;
    private TabLayout.OnTabSelectedListener tabSelectedListener;
    private TabLayout.TabLayoutOnPageChangeListener pageChangeListener;

    private EventModel event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_detail);

        mainImage = (ImageView) findViewById(R.id.main_image);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Detail");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);



        mEventKey = getIntent().getStringExtra(EXTRA_EVENT_KEY);
        if (mEventKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_EVENT_KEY");
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

     //   mBookmark.setOnClickListener(this);
     //   mShare.setOnClickListener(this);

      //  fab = (FloatingActionButton)findViewById(R.id.going);
      //  fab1 = (FloatingActionButton)findViewById(R.id.interested);
      //  fab.setOnClickListener(this);
      //  fab1.setOnClickListener(this);

        presenter = new EventsDetailPresenterImpl(this, mEventKey);
        presenter.requestEvent();

        PrefManager prefManager = new PrefManager(this);
        if (prefManager.isFirstEventsLaunch()) {
            showTutorial();
        }

        prefManager.setFirstEventsLaunch(false);
    }

    void setupTabs() {

        String tab_texts[] = {"Information", "Visitors", "Video"};
        Integer images[] = {R.drawable.ic_description,
                R.drawable.ic_checklist,
                R.drawable.ic_video};
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
            String titles[] = {"Info", "Visitors", "Video"};
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
    public void setEvent(EventModel event) {

        this.event = event;

        if (actionBar != null) {
            actionBar.setTitle(event.getTitle());
        }

        setupTabs();

       // Toast.makeText(this, event.getDesc(), Toast.LENGTH_SHORT).show();

        ImageView main_image = findViewById(R.id.main_image);
        if(event.getThumbUrl() != null ) Glide.with(getApplicationContext()).load(event.getThumbUrl()).fitCenter().into(main_image);

        Toast.makeText(this, ""+event.getThumbUrl(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+event.getVisitors(), Toast.LENGTH_SHORT).show();

     /*  if(event.getTitle() != null) mTitleView.setText(event.getTitle());
       if(event.getDesc() != null ) mBodyView.setText(event.getDesc());
       if(event.getUrl() != null ) Glide.with(getApplicationContext()).load(event.getUrl()).fitCenter().into(image_scrolling_top);

        if(event.bookmarks != null){
            if(event.bookmarks.containsKey(getCurrentUserId()))
                mBookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);

            else
                mBookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }*/

  /*      Analytics.logEventViewItem(getApplicationContext(),event.getTimestamp().toString(),mTitleView.getText().toString(),"event");
        if(event.action != null){
            if (event.action.containsKey(getCurrentUserId())) {

                long a = (long) event.action.get(getCurrentUserId());

                if(a==1){
                    fab.setImageResource(R.drawable.ic_event_available_black_24dp);
                    fab.setColorFilter(Color.argb(255, 255, 127, 80));

                    fab1.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                    fab1.setColorFilter(Color.argb(255, 255, 255, 255));
                }

                if(a==0){
                    fab.setImageResource(R.drawable.ic_today_black_24dp);
                    fab.setColorFilter(Color.argb(255, 255, 255, 255));

                    fab1.setImageResource(R.drawable.ic_event_black_24dp);
                    fab1.setColorFilter(Color.argb(255, 255, 127, 80));
                }


            } else {
                fab.setImageResource(R.drawable.ic_today_black_24dp);
                fab1.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);

                fab.setColorFilter(Color.argb(255, 255, 255, 255));
                fab1.setColorFilter(Color.argb(255, 255, 255, 255));
            }
        }else{
            fab.setImageResource(R.drawable.ic_today_black_24dp);
            fab1.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);

            fab.setColorFilter(Color.argb(255, 255, 255, 255));
            fab1.setColorFilter(Color.argb(255, 255, 255, 255));
        }
*/
    }

    @Override
    public void onError(String string) {
        //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        //Toast.makeText(this, "Going", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }


    void showTutorial(){
/*
        final Display display = getWindowManager().getDefaultDisplay();
        final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_camera_alt_black_24dp);
        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

        final SpannableString sassyDesc = new SpannableString("It tells your friends that you are Going to this event");
        sassyDesc.setSpan(new StyleSpan(Typeface.ITALIC), sassyDesc.length() - "Going".length(), sassyDesc.length(), 0);

        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar
                      //  TapTarget.forView(fab, "This is an action button", sassyDesc).id(1),
                        // Likewise, this tap target will target the search button
                        TapTarget.forView(fab1, "This is another action button", "It tells your friends that you are Interested to this event")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorPrimaryLight)
                                //   .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .id(2),

                        TapTarget.forView(mBookmark, "This is the BookMark button", "You can use it to find this event later !!")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .id(3),

                        TapTarget.forView(mShare, "This is the Share button", "You can share news with your friends !!")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .id(4)
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
                        final AlertDialog dialog = new AlertDialog.Builder(EventsDetailActivity.this)
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
        final SpannableString spannedDesc = new SpannableString("Hey!! You can see the Event Details here");
        spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "Event Details".length(), spannedDesc.length(), 0);
        TapTargetView.showFor(this, TapTarget.forBounds(droidTarget, "Event Details", spannedDesc)
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

*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(pageChangeListener);
        tabs.removeOnTabSelectedListener(tabSelectedListener);
    }

    public String getEventInfo(){
        if(event != null) return event.getDesc();
        Toast.makeText(this, "null event", Toast.LENGTH_SHORT).show();
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
