package com.products.safetyfirst.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.fragment.InfoFragment;
import com.products.safetyfirst.fragment.TypeFragment;

public class KnowItSecondActivity extends AppCompatActivity {

    private TabLayout categoryTabs;
    private ViewPager categoryView;
    private int position;
    private TabLayout.OnTabSelectedListener tabSelectedListener;
    private TabLayout.TabLayoutOnPageChangeListener pageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_it_second);
        setSupportActionBar((Toolbar) findViewById(R.id.know_it_toolbar));
        position = getIntent().getIntExtra(KnowItMainActivity.position, 0);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getStringArray(R.array.item_title)[position]);
        }


        categoryTabs = (TabLayout) findViewById(R.id.category_tabs);
        categoryView = (ViewPager) findViewById(R.id.know_it_viewpager);

        setupTabs();

    }

    void setupTabs() {
        tabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                categoryView.setCurrentItem(tab.getPosition(), true);
                ((TextView)tab.getCustomView().findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView)tab.getCustomView().findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        };
        pageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(categoryTabs) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                TabLayout.Tab tab = categoryTabs.getTabAt(position);
                tab.select();
            }

        };
        View tab1 = getLayoutInflater().inflate(R.layout.know_it_tabitem, null);
        ((TextView)tab1.findViewById(R.id.tab_text)).setText("Info");
        ((TextView)tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.colorAccent));
        View tab2 = getLayoutInflater().inflate(R.layout.know_it_tabitem, null);
        ((TextView)tab2.findViewById(R.id.tab_text)).setText("Type");
        ((TextView)tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.white));

        categoryTabs.addTab(categoryTabs.newTab().setCustomView(tab1));
        categoryTabs.addTab(categoryTabs.newTab().setCustomView(tab2));

        final Bundle args = new Bundle();
        args.putInt(KnowItMainActivity.position, position);

        FragmentPagerAdapter categoryAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            Fragment fragments[] = {new InfoFragment(), new TypeFragment()};
            String titles[] = {"Info", "Types"};
            @Override
            public Fragment getItem(int position) {
                fragments[1].setArguments(args);
                fragments[0].setArguments(args);
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
        categoryView.setAdapter(categoryAdapter);
        categoryView.setCurrentItem(0);
        categoryTabs.getTabAt(0).select();

        categoryView.addOnPageChangeListener(pageChangeListener);
        categoryTabs.addOnTabSelectedListener(tabSelectedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
