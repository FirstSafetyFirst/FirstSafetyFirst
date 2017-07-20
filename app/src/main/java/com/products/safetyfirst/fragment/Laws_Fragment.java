package com.products.safetyfirst.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.products.safetyfirst.R;

/**
 * Created by profileconnect on 20/04/17.
 */

public class Laws_Fragment extends Fragment {
    public static final String ARG_TITLE = "arg_title";
    private TabLayout tabLayout;
    private ViewPager viewPager;
//    ViewPagerAdapter adapter;

    private TabLayout categoryTabs;
    private ViewPager categoryView;
    private TabLayout.OnTabSelectedListener tabSelectedListener;
    private TabLayout.TabLayoutOnPageChangeListener pageChangeListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_know_it_second, container, false);
//        viewPager = (ViewPager)rootView.findViewById(R.id.know_it_viewpager);
//        //setupViewPager(viewPager);
//        tabLayout = (TabLayout)rootView.findViewById(R.id.category_tabs);
//        tabLayout.setupWithViewPager(viewPager);

        TelephonyManager tm = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = tm.getSimCountryIso();
        Toast.makeText(getContext(), countryCode, Toast.LENGTH_LONG).show();

        categoryTabs = (TabLayout) rootView.findViewById(R.id.category_tabs);
        categoryView = (ViewPager) rootView.findViewById(R.id.know_it_viewpager);

        setupTabs();

        return rootView;
    }



//    private void setupViewPager(ViewPager viewPager) {
//        adapter = new ViewPagerAdapter(getChildFragmentManager());
//        adapter.addFragment(new National_Fragment(), "NATIONAL");
//        adapter.addFragment(new National_Fragment(), "STATE");
//        adapter.addFragment(new National_Fragment(), "INTERNATIONAL");
//        viewPager.setAdapter(adapter);
//    }
//
//    static class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }



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

        View tab1 = newTab("State Laws", R.color.colorAccent, 0);
        View tab2 = newTab("National Laws", R.color.white, -1);
        View tab3 = newTab("International Laws", R.color.white, -1);

        categoryTabs.addTab(categoryTabs.newTab().setCustomView(tab1));
        categoryTabs.addTab(categoryTabs.newTab().setCustomView(tab2));
        categoryTabs.addTab(categoryTabs.newTab().setCustomView(tab3));

        FragmentPagerAdapter categoryAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            Fragment fragments[] = {new State_Fragment(), new National_Fragment(), new International_Fragment()};
            String titles[] = {"State Laws", "National Laws", "International Laws"};
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
        categoryView.setAdapter(categoryAdapter);
        categoryView.setCurrentItem(0);
        categoryTabs.getTabAt(0).select();

        categoryView.addOnPageChangeListener(pageChangeListener);
        categoryTabs.addOnTabSelectedListener(tabSelectedListener);
    }

    /**
     *
     * @param text
     * @param colorResource
     * @param textSize
     * @return
     */

    private View newTab(String text, int colorResource, int textSize) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.know_it_tabitem, null);
        TextView textView = (TextView)view.findViewById(R.id.tab_text);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(colorResource));
        if(textSize == -1){
            textView.setTextAppearance(getContext(), R.style.TextAppearance_AppCompat_Small);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryTabs.removeOnTabSelectedListener(tabSelectedListener);
        categoryView.removeOnPageChangeListener(pageChangeListener);
    }

}
