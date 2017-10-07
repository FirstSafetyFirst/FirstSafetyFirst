package com.products.safetyfirst.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.fragment.ProfileFragment.AnswersFragment;
import com.products.safetyfirst.fragment.ProfileFragment.ProjectsFragment;
import com.products.safetyfirst.fragment.ProfileFragment.QuestionsFragment;
import com.products.safetyfirst.impementations.ProfileActivityPresenterImpl;
import com.products.safetyfirst.interfaces.ProfileActivityPresenter;
import com.products.safetyfirst.interfaces.ProfileActivityView;
import com.products.safetyfirst.models.UserModel;

public class ProfileActivity extends BaseActivity
        implements ProjectsFragment.OnFragmentInteractionListener,
        AnswersFragment.OnFragmentInteractionListener,
        QuestionsFragment.OnFragmentInteractionListener, ProfileActivityView {

    public static final String EXTRA_PROFILE_KEY = "post_key";
    private static final String TAG = "ProfileActivity";
    private static String mProfileKey;
    TabLayout tabLayout;
    private TextView mUserName;
    private TextView mCompany;
    private TextView mDesignation;
    private Button updateBtn;
    private Switch mFollowSwitch;
    private ImageView mProfileImage;
    private ProfileActivityPresenter presenter;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public static String getProfileKey() {
        return mProfileKey;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        ViewGroup root = (ViewGroup) findViewById(R.id.toolbar);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_app_bar_profile, root);

        presenter = new ProfileActivityPresenterImpl(this);


        mUserName       = (TextView) findViewById(R.id.user_name);
        mCompany        = (TextView) findViewById(R.id.company_name);
        mDesignation    = (TextView) findViewById(R.id.user_designation);
        updateBtn = (Button) findViewById(R.id.update);
        mProfileImage = (ImageView) findViewById(R.id.profile_image);
        mFollowSwitch = (Switch) findViewById(R.id.follow);

//        Toolbar parent =(Toolbar) v.getParent();
//        parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
//        parent.setContentInsetsAbsolute(0,0);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        changeTabsFont();
        mProfileKey = getIntent().getStringExtra(EXTRA_PROFILE_KEY);
        if (mProfileKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_PROFILE_KEY");
        }

        if (getCurrentUserId() != null) {
            if (getCurrentUserId() == mProfileKey) {
                mFollowSwitch.setVisibility(View.GONE);
                updateBtn.setVisibility(View.VISIBLE);
            }
        }

        if (!isLoggedIn()) {
            mFollowSwitch.setVisibility(View.GONE);
            updateBtn.setVisibility(View.GONE);
        }

        presenter.requestUser(mProfileKey);

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

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setViewWithUser(UserModel user) {

        if (user != null) {
            //Toast.makeText(this, "Going to set user", Toast.LENGTH_SHORT).show();
            mUserName.setText(user.getUsername());

            if (user.getCompany() != null) mCompany.setText(user.getCompany());
            if (user.getDesignation() != null) mDesignation.setText(user.getDesignation());
            if (user.getPhotoUrl() != null) {
                Glide.with(ProfileActivity.this).load(user.getPhotoUrl())
                        .error(R.drawable.ic_person_black_24dp)
                        .transform(new CircleTransform(ProfileActivity.this))
                        .into(mProfileImage);
            }

        } else {
            Toast.makeText(this, "Fetching user info failed", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void navigateToHome() {

    }

    @Override
    public void onFollowError() {
        Toast.makeText(this, "Could not follow this user", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0:
                    return new QuestionsFragment();
                case 1:
                    return new AnswersFragment();
                case 2:
                    Bundle bundle = new Bundle();
                    bundle.putString("PROFILE_KEY", mProfileKey);
                    Fragment fragment = new ProjectsFragment();
                    fragment.setArguments(bundle);
                    return fragment;
            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Questions";
                case 1:
                    return "Answers";
                case 2:
                    return "Projects";
            }
            return null;
        }
    }
}
