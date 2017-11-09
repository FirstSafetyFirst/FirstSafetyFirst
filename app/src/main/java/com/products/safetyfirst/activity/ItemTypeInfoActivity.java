package com.products.safetyfirst.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.fragment.ItemsFragments.ChecklistFragment;
import com.products.safetyfirst.fragment.ItemsFragments.HowToUseFragment;
import com.products.safetyfirst.fragment.ItemsFragments.InfoFragment;
import com.products.safetyfirst.fragment.ItemsFragments.VideoFragment;
import com.products.safetyfirst.impementations.presenter.KnowItPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.KnowItPresenter;
import com.products.safetyfirst.interfaces.view.KnowItView;
import com.products.safetyfirst.models.KnowItItemType;

import java.net.MalformedURLException;
import java.net.URL;

public class ItemTypeInfoActivity extends AppCompatActivity implements KnowItView {

    public static final String EXTRA_ITEM_NAME = "item_name";
    private TabLayout tabs;
    private ViewPager viewPager;
    private ImageView mainImage;
    private TabLayout.OnTabSelectedListener tabSelectedListener;
    private TabLayout.TabLayoutOnPageChangeListener pageChangeListener;
    private KnowItItemType knowItItemType = null;

    private KnowItPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_type_info);

        mainImage = findViewById(R.id.main_image);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Detail");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String mItemName = getIntent().getStringExtra(EXTRA_ITEM_NAME);
        if (mItemName == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ITEM_NAME");
        }


        presenter = new KnowItPresenterImpl(this);
        presenter.requestSpecificItem(mItemName);

        //((NestedScrollView) findViewById(R.id.nestedScroll)).setFillViewport(true);
        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);

        setupTabs();
    }

    private void setupTabs() {

        String tab_texts[] = {"Information", "How to Use", "Checklist", "Video"};
        Integer images[] = {R.drawable.ic_description,
                R.drawable.ic_howtouse,
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
                assert tab != null;
                tab.select();
            }

        };

        for(int i = 0; i < 4; i++) {
            tab_layouts[i] = getLayoutInflater().inflate(R.layout.item_type_info_tabs, null);
            ((TextView)tab_layouts[i].findViewById(R.id.tabs_text)).setText(tab_texts[i]);
            ((ImageView)tab_layouts[i].findViewById(R.id.tabs_image)).setImageDrawable(getResources().getDrawable(images[i]));
            tabs.addTab(tabs.newTab().setCustomView(tab_layouts[i]));
        }

        final Bundle args = new Bundle();
        args.putParcelable("KnowItItemType",knowItItemType);
        final Fragment fragments[] = {new InfoFragment(),
                new HowToUseFragment(),
                new ChecklistFragment(),
                new VideoFragment() };
        for(Fragment fragment: fragments){
            fragment.setArguments(args);
        }

        FragmentPagerAdapter categoryAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            final String[] titles = {"Info", "How To Use", "Checklist", "Video"};
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
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(pageChangeListener);
        tabs.removeOnTabSelectedListener(tabSelectedListener);
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
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void setViewWithSpecificItem(KnowItItemType knowItItemType) {

        this.knowItItemType = knowItItemType;

        try {
            Glide.with(getApplicationContext()).load(new URL(knowItItemType.getItem_thumb_url())).into(mainImage);
        } catch (MalformedURLException e) {
            Log.e("ItemTypeIngoActivity","Error loading main image");
        }

    }

    public String getKnowItItemInfo(){
        if(knowItItemType != null) return knowItItemType.getItem_info();
        return null;
    }

    public String getKnowItItemHowtoUse(){
        if(knowItItemType != null) return knowItItemType.getHow_to_use();
        return null;
    }

    public String getKnowItItemCheckList(){
        if(knowItItemType != null) return knowItItemType.getChecklist();
        return null;
    }

    public String getKnowItItemVideo(){
        if(knowItItemType != null) return knowItItemType.getVideo_url();
        return null;
    }
}