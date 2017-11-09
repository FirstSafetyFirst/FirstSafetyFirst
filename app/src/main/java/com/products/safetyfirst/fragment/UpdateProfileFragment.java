package com.products.safetyfirst.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.fragment.ProfileFragment.ProjectsFragment;

import static com.products.safetyfirst.activity.HomeActivity.bottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProfileFragment extends Fragment {
    public static final String ARG_TITLE = "arg_title";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayout categoryTabs;
    private ViewPager categoryView;
    private TabLayout.OnTabSelectedListener tabSelectedListener;
    private TabLayout.TabLayoutOnPageChangeListener pageChangeListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_update_profile, container, false);

        categoryTabs = rootView.findViewById(R.id.profile_tabs);
        categoryView = rootView.findViewById(R.id.profile_viewpager);

        setupTabs();
        bottomNavigationView.setVisibility(View.GONE);
        return rootView;
    }



    private void setupTabs() {
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

        View tab1 = newTab("Personal", R.color.colorAccent, 0);
        View tab2 = newTab("Projects", R.color.white, -1);
        View tab3 = newTab("Fields", R.color.white, -1);

        categoryTabs.addTab(categoryTabs.newTab().setCustomView(tab1));
        categoryTabs.addTab(categoryTabs.newTab().setCustomView(tab2));
        categoryTabs.addTab(categoryTabs.newTab().setCustomView(tab3));

        FragmentPagerAdapter categoryAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            final Fragment[] fragments = {new Personal_Fragment(), new ProjectsFragment(), new Interest_Fragment()};
            final String[] titles = {"Personal", "Projects", "Fields"};
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
        TextView textView = view.findViewById(R.id.tab_text);
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
