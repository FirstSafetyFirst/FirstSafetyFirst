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
import android.widget.Toast;

import com.products.safetyfirst.R;
import com.products.safetyfirst.fragment.DiscussionFragment;
import com.products.safetyfirst.fragment.Events_Fragment;
import com.products.safetyfirst.fragment.News_Fragment;

public class BookmarkActivity extends AppCompatActivity {
    private TabLayout bookmarkTabs;
    private ViewPager bookmarkView;
    private TabLayout.OnTabSelectedListener tabSelectedListener;
    private TabLayout.TabLayoutOnPageChangeListener pageChangeListener;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        setSupportActionBar((Toolbar) findViewById(R.id.bookmark_toolbar));

        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        }
        else
            Toast.makeText(this,"null",Toast.LENGTH_SHORT).show();
        bookmarkTabs=(TabLayout)findViewById(R.id.bookmarks_tabs);
        bookmarkView=(ViewPager)findViewById(R.id.bookmark_viewpager);
        setUpTabs();
    }

    private void setUpTabs() {
        tabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bookmarkView.setCurrentItem(tab.getPosition(), true);
                ((TextView)tab.getCustomView().findViewById(R.id.tab_text)).
                        setTextColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView)tab.getCustomView().findViewById(R.id.tab_text)).
                        setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        };
        pageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(bookmarkTabs) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                TabLayout.Tab tab = bookmarkTabs.getTabAt(position);
                tab.select();
            }

        };
        View tab1 = getLayoutInflater().inflate(R.layout.know_it_tabitem, null);
        ((TextView)tab1.findViewById(R.id.tab_text)).setText("News");
        ((TextView)tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.colorAccent));
        View tab2 = getLayoutInflater().inflate(R.layout.know_it_tabitem, null);
        ((TextView)tab2.findViewById(R.id.tab_text)).setText("Events");
        ((TextView)tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.white));
        View tab3 = getLayoutInflater().inflate(R.layout.know_it_tabitem, null);
        ((TextView)tab3.findViewById(R.id.tab_text)).setText("Posts");
        ((TextView)tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.white));
        bookmarkTabs.addTab(bookmarkTabs.newTab().setCustomView(tab1));
        bookmarkTabs.addTab(bookmarkTabs.newTab().setCustomView(tab2));
        bookmarkTabs.addTab(bookmarkTabs.newTab().setCustomView(tab3));
        bundle=new Bundle();
        bundle.putString("action","bookmark");
        final Fragment f1= new News_Fragment();
        f1.setArguments(bundle);
        final Fragment f2= new Events_Fragment();
        f2.setArguments(bundle);
        final Fragment f3= new DiscussionFragment();
        f3.setArguments(bundle);
        FragmentPagerAdapter categoryAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            Fragment fragments[] = {f1,f2,f3};
            String titles[] = {"News", "Events","Posts"};
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
                return "Bookmarks";
            }

        };

        bookmarkView.setAdapter(categoryAdapter);
        bookmarkView.setCurrentItem(0);
        bookmarkTabs.getTabAt(0).select();

        bookmarkView.addOnPageChangeListener(pageChangeListener);
        bookmarkTabs.addOnTabSelectedListener(tabSelectedListener);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookmarkTabs.removeOnTabSelectedListener(tabSelectedListener);
        bookmarkView.removeOnPageChangeListener(pageChangeListener);
    }
}
