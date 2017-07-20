package com.products.safetyfirst.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.fragment.ItemsFragments.TypeChecklistFragment;
import com.products.safetyfirst.fragment.ItemsFragments.TypeHowToUseFragment;
import com.products.safetyfirst.fragment.ItemsFragments.TypeInfoFragment;
import com.products.safetyfirst.fragment.ItemsFragments.TypeVideoFragment;

public class ItemTypeInfoActivity extends AppCompatActivity {

    public static final String tool = "tool";
    public static final String typeNumber = "typeNumber";
    private TabLayout tabs;
    private ViewPager viewPager;
    private ImageView mainImage;
    private TabLayout.OnTabSelectedListener tabSelectedListener;
    private TabLayout.TabLayoutOnPageChangeListener pageChangeListener;
    private int toolValue;
    private int typeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_type_info);

        mainImage = (ImageView) findViewById(R.id.main_image);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolValue = getIntent().getIntExtra(tool, 0);
        typeValue = getIntent().getIntExtra(typeNumber, 0);

        TypedArray imageArray = getResources().obtainTypedArray(R.array.third_image);
        int imageId = imageArray.getResourceId(toolValue,0);
        TypedArray a = getResources().obtainTypedArray(imageId);
        Drawable image = a.getDrawable(typeValue);
        mainImage.setImageDrawable(image);

        a.recycle();
        imageArray.recycle();

        //((NestedScrollView) findViewById(R.id.nestedScroll)).setFillViewport(true);
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupTabs();
    }

    void setupTabs() {

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
        args.putInt(tool, toolValue);
        args.putInt(typeNumber, typeValue);
        final Fragment fragments[] = {new TypeInfoFragment(),
                new TypeHowToUseFragment(),
                new TypeChecklistFragment(),
                new TypeVideoFragment() };
        for(Fragment fragment: fragments){
            fragment.setArguments(args);
        }

        FragmentPagerAdapter categoryAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            String titles[] = {"Info", "How To Use", "Checklist", "Video"};
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
}
